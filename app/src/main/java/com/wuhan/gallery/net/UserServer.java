package com.wuhan.gallery.net;

import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserServer {

    @FormUrlEncoded
    @POST("user/login.app")
    Observable<NetworkDataBean<UserBean>> login(@Field("user_name") String name, @Field("password") String password);
}
