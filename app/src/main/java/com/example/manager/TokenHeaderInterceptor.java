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
        String token = MmkvUtil.getString(CommonParms.SOB_TOKEN);
        Log.d(TAG, "TokenHeaderInterceptor sob token  --> " + token);
        if (token.isEmpty()) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        } else {
            Request originalRequest = chain.request();
            //key的话以后台给的为准，我这边是叫token
            Request updateRequest = originalRequest.newBuilder().header("sob_token", token).build();
            return chain.proceed(updateRequest);
        }
    }
}
