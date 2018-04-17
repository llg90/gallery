package com.wuhan.gallery.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public enum SingletonNetServer {
    INSTANCE;

    private UserServer mUserServer;

    SingletonNetServer() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rapapi.org/mockjsdata/33408/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mUserServer = retrofit.create(UserServer.class);
    }

    public UserServer getUserServer() {
        return mUserServer;
    }
}
