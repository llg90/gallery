package com.wuhan.gallery.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public enum SingletonNetServer {
    INSTANCE;

    public static final String sIMAGE_SERVER_HOST = "http://192.168.1.125:5000/images/";
    public static final String SUCCESS = "0000";
    private UserServer mUserServer;
    private ImageServer mImageServer;

    SingletonNetServer() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl("http://192.168.1.125:5000/")
                .build();

        mUserServer = retrofit.create(UserServer.class);
        mImageServer = retrofit.create(ImageServer.class);
    }

    public UserServer getUserServer() {
        return mUserServer;
    }

    public ImageServer getImageServer() {
        return mImageServer;
    }
}
