package com.fenjin.data.network;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:15:50
 * Summary:
 */
public class NetworkRepository {

    private static final int READ_TIMEOUT = 15;

    private static final int CONNECT_TIMEOUT = 15;

    private static final int WRITE_TIMEOUT = 15;

    private static NetworkRepository instance;

    private ServerInterface serverInterface;

    public static NetworkRepository getInstance(){
        if (instance == null){
            synchronized (NetworkRepository.class){
                if (instance == null){
                    instance = new NetworkRepository();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init(){
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        String baseUrl = "http://47.95.225.104:8081/workRecord/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        serverInterface = retrofit.create(ServerInterface.class);
    }

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            String method = request.method();
            if ("POST".equals(method)) {//打印post请求体
                Buffer buffer = new Buffer();
                request.body().writeTo(buffer);
                LogUtils.file("OkHttpLog", String.format("发送请求:%s %n requestBody:%s",
                        request.url(), buffer.readUtf8()));

            } else {
                LogUtils.file("OkHttpLog", String.format("发送请求:%s", request.url()));
            }

            long t1 = System.nanoTime();//请求发起的时间

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);

            LogUtils.file("OkHttpLog",
                    String.format(Locale.CHINESE,"接收响应:%s %n" +
                                    "httpCode:%s %n" +
                                    "responseBody:%s %n" +
                                    "time consuming:%.1fms %n",
                            response.request().url(),
                            response.code(),
                            responseBody.string(),
                            (t2 - t1) / 1e6d));
            return response;
        }
    }

    private static class TrustAllCertsManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            Log.d("TrustAllCertsManager","checkClientTrusted");
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            Log.d("TrustAllCertsManager","checkServerTrusted");
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
    }

}
