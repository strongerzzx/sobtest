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
        String sobToken = MmkvUtil.getString(CommonParms.SOB_TOKEN);
        Request request = chain.request();
        Log.d(TAG, "sobToken  --> " + sobToken);
        if (sobToken.isEmpty()) {
            return chain.proceed(request);
        } else {
            Request tokenRequest = request.newBuilder().header("sob_token", sobToken).build();
            return chain.proceed(tokenRequest);
        }
    }
}
