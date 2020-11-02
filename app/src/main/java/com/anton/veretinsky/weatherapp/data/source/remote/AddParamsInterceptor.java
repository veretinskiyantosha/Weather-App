package com.anton.veretinsky.weatherapp.data.source.remote;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.anton.veretinsky.weatherapp.data.source.remote.Constants.LANG_PARAM;
import static com.anton.veretinsky.weatherapp.data.source.remote.Constants.LANG_PARAM_VALUE;
import static com.anton.veretinsky.weatherapp.data.source.remote.Constants.UNITS_PARAM;
import static com.anton.veretinsky.weatherapp.data.source.remote.Constants.UNITS_PARAM_VALUE;

public class AddParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl url = chain.request().url()
                .newBuilder()
                .addQueryParameter(UNITS_PARAM, UNITS_PARAM_VALUE)
                .addQueryParameter(LANG_PARAM, LANG_PARAM_VALUE)
                .build();
        Request request = chain.request().newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
