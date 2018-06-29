package com.nbs.simplefootballapp.data.libs;

import android.support.annotation.Nullable;

import com.nbs.simplefootballapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpClientFactory {
    private static final int DEFAULT_MAX_REQUEST = 30;

    private static final int TIMEOUT = 20;

    private OkHttpClientFactory() {

    }

    public static OkHttpClient create(@Nullable ParameterInterceptor parameterInterceptor) {


        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        if (parameterInterceptor != null) {
            builder.addInterceptor(parameterInterceptor);
        }

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor).build();
        }
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(DEFAULT_MAX_REQUEST);
        builder.dispatcher(dispatcher);

        return builder.build();
    }
}
