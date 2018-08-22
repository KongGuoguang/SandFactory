package com.fenjin.sandfactory.util;

import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Author:kongguoguang
 * Date:2018-08-20
 * Time:10:53
 * Summary:
 */
public class ErrorCodeUtil {

    /*
     * connect exception
     */
    public static final int CONNECT_ERROR = -100;
    /*
     * protocol error.
     */
    public static final int PROTOCOL_ERROR = -101;
    /*
     * socket error
     */
    public static final int SOCKET_ERROR = -102;
    /*
     * socket timeout
     */
    public static final int TIMEOUT = -103;

    //服务器响应内容错误
    public static final int REPONSE_ERROR = -104;

    public static final int TOKEN_TIME_OUT = 401;//登录超时

    public static int getErrorCode(Throwable t){

        if (t instanceof HttpException){
            return  ((HttpException) t).code();
        }

        if (t instanceof ConnectException){
            return CONNECT_ERROR;
        }

        if (t instanceof SocketTimeoutException){
            return TIMEOUT;
        }

        if (t instanceof ProtocolException){
            return PROTOCOL_ERROR;
        }

        if (t instanceof MalformedJsonException){
            return REPONSE_ERROR;
        }

        if (t instanceof IOException){
            return SOCKET_ERROR;
        }

        return 0;

    }
}
