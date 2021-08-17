package com.example.manager;

import android.util.Log;

import com.example.commonparams.CommonParms;
import com.example.utils.MmkvUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenHeaderInterceptor implements Interceptor {
    private static final String TAG = "TokenHeaderInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        String cookie = MmkvUtil.getString(CommonParms.COOKIE_LOGIN_KEY);
        if (cookie.isEmpty()) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        } else {
            Request originalRequest = chain.request();
            //好家伙 还是传cookie
            Request updateRequest = originalRequest.newBuilder()
                    .header("cookie", MmkvUtil.getString(CommonParms.COOKIE_LOGIN_KEY))
                    .build();
            return chain.proceed(updateRequest);
        }
    }
}
