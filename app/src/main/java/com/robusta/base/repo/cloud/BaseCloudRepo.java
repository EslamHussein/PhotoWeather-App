package com.robusta.base.repo.cloud;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseCloudRepo {

    protected <T> T create(Class<T> clazz) {
        T service = retrofit().create(clazz);
        return service;
    }

    private Retrofit retrofit() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();
        return new Retrofit.Builder()
                .baseUrl(CloudConfig.BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
