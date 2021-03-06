package com.fenjin.data.network;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.data.entity.GetAllChannelResult;
import com.fenjin.data.entity.GetChannelResult;
import com.fenjin.data.entity.LoginParam;
import com.fenjin.data.entity.LoginResult;
import com.fenjin.data.entity.ModifyPasswordParam;
import com.fenjin.data.entity.ModifyPasswordResult;
import com.fenjin.data.entity.TodayCountResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

    private CameraInterface cameraInterface;

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

        HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                LogUtils.d(message);
            }
        };

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(logger);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        initServerInterface(client);

        initCameraInterface(client);
    }

    private void initServerInterface(OkHttpClient client){
        String baseUrl = "http://117.158.160.210:9090/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        serverInterface = retrofit.create(ServerInterface.class);
    }

    private void initCameraInterface(OkHttpClient client){

        String baseUrl = "http://112.35.23.101:10800/api/v1/";
//        String baseUrl = "http://117.158.160.210:10800/api/v1/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        cameraInterface = retrofit.create(CameraInterface.class);

    }

    public Observable<LoginResult> login(LoginParam loginParam){
        return serverInterface.login(loginParam);
    }

    public Observable<ChengZhongRecordListResult> getChengZhongRecordList(String token, int pageNum,
                                                                          int pageSize, String searchKey){
        return serverInterface.getChengZhongRecordList(token,pageNum, pageSize, searchKey);
    }

    public Observable<GetAllChannelResult> getAllChannel(){
        return cameraInterface.getAllChannel();
    }

    public Observable<GetChannelResult> getChannel(int channel,String protocol){
        return cameraInterface.getChannel(channel, protocol);
    }

    public Observable<GetChannelResult> touchChannel(int channel, String line, String protocol){
        return cameraInterface.touchChannel(channel, line, protocol);
    }

    public Observable<ModifyPasswordResult> modifyPassword(String token, ModifyPasswordParam modifyPasswordParam) {
        return serverInterface.modifyPassword(token, modifyPasswordParam);
    }

    public Observable<TodayCountResult> getTodayCountResult(String token) {
        return serverInterface.getTodayCountResult(token);
    }


//    private static class LoggingInterceptor implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
//            Request request = chain.request();
//            String method = request.method();
//            if ("POST".equals(method)) {//打印post请求体
//                Buffer buffer = new Buffer();
//                request.body().writeTo(buffer);
//                LogUtils.file("OkHttpLog", String.format("发送请求:%s %n requestBody:%s",
//                        request.url(), buffer.readUtf8()));
//
//            } else {
//                LogUtils.file("OkHttpLog", String.format("发送请求:%s", request.url()));
//            }
//
//            long t1 = System.nanoTime();//请求发起的时间
//
//            Response response = chain.proceed(request);
//            long t2 = System.nanoTime();//收到响应的时间
//
//            //这里不能直接使用response.body().string()的方式输出日志
//            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
//            //个新的response给应用层处理
//            ResponseBody responseBody = response.peekBody(1024 * 1024);
//
//            LogUtils.file("OkHttpLog",
//                    String.format(Locale.CHINESE,"接收响应:%s %n" +
//                                    "httpCode:%s %n" +
//                                    "responseBody:%s %n" +
//                                    "time consuming:%.1fms %n",
//                            response.request().url(),
//                            response.code(),
//                            responseBody.string(),
//                            (t2 - t1) / 1e6d));
//            return response;
//        }
//    }


}
