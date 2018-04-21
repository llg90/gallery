package com.wuhan.gallery.net;

import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserServer {

    @FormUrlEncoded
    @POST("user/login")
    Observable<NetworkDataBean<UserBean>> login(@Field("username") String name, @Field("password") String password);

    @POST("uploadImages")
    Observable<NetworkDataBean<String>> upUserIcon(@Body RequestBody body);

    @FormUrlEncoded
    @POST("user/register")
    Observable<NetworkDataBean<Boolean>> register(@Field("username") String name, @Field("password") String password,
                                         @Field("email") String email, @Field("telephone") String telephone);
}
