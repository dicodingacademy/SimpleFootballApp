package com.nbs.simplefootballapp.di.module;

import com.nbs.simplefootballapp.BuildConfig;
import com.nbs.simplefootballapp.data.libs.ApiClient;
import com.nbs.simplefootballapp.data.libs.ApiManager;
import com.nbs.simplefootballapp.data.libs.ApiService;
import com.nbs.simplefootballapp.data.libs.IApiManager;
import com.nbs.simplefootballapp.data.libs.OkHttpClientFactory;
import com.nbs.simplefootballapp.data.libs.ParameterInterceptor;

import java.util.HashMap;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class ApiModule {
    @Provides
    ParameterInterceptor provideParameterInterceptor() {
        //params in hashmap object
        HashMap<String, String> params = new HashMap<>();

        return new ParameterInterceptor(params);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkhttpClient(ParameterInterceptor parameterInterceptor) {
        return OkHttpClientFactory.create(parameterInterceptor);
    }

    @Singleton
    @Provides
    String provideBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    @Singleton
    @Provides
    IApiManager provideApiManager(ApiManager apiManager){
        return apiManager;
    }

    @Singleton
    @Provides
    ApiClient provideApiClient(OkHttpClient okHttpClient, String url) {
        return ApiService.createService(ApiClient.class, okHttpClient, url);
    }
}
