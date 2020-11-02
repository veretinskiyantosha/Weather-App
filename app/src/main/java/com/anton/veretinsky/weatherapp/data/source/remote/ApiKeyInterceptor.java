package com.anton.veretinsky.weatherapp.data.source.remote;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.anton.veretinsky.weatherapp.data.source.remote.Constants.API_KEY;
import static com.anton.veretinsky.weatherapp.data.source.remote.Constants.API_KEY_VALUE;

public class ApiKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl url = chain.request().url()
                .newBuilder()
                .addQueryParameter(API_KEY, API_KEY_VALUE)
                .build();
        Request request = chain.request().newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
