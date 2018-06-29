package com.nbs.simplefootballapp.data.libs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ParameterInterceptor implements Interceptor {

    private HashMap<String, String> params;

    public ParameterInterceptor(HashMap<String, String> params) {
        this.params = params;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .url(mapParameters(chain));

        Request request = requestBuilder.build();
        return chain.proceed(request);

    }

    private HttpUrl mapParameters(Chain chain) {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl.Builder builder = originalHttpUrl.newBuilder();

        for (Entry<String, String> entry : params.entrySet()) {
            builder.addQueryParameter(entry.getKey(),entry.getValue());
        }

        return builder.build();
    }
}
