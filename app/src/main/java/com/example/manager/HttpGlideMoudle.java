package com.example.manager;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import com.example.base.BaseRetrofit;

/**
 * 作者：zzx on 2021/8/8 19:48
 * 作用： xxxx
 */
@GlideModule
public class HttpGlideMoudle extends AppGlideModule {
    private static final String TAG = "HttpGlideMoudle";

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class
                , new OkHttpUrlLoader.Factory(BaseRetrofit.INSTANCE.getGlideOkHttpClient()));

        Log.d(TAG, "registerComponents --> ");
    }
}

