package com.wuhan.gallery.net;

import com.wuhan.gallery.bean.HostDataBean;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ImageServer {

    //获取首页图片
    @GET("execute/clicktopload")
    Observable<NetworkDataBean<HostDataBean>> clicktopload(@Query("id") int id);

    //不同类型标签中的图片
    @FormUrlEncoded
    @POST("execute/images")
    Observable<NetworkDataBean<List<ImageBean>>> getImagesByType(
            @Field("type") int type,
            @Field("pagecount") int page,
            @Field("id") int userId);

    //图片的浏览状态
    @FormUrlEncoded
    @POST("execute/browse")
    Observable<NetworkDataBean<List<ImageBean>>> getImageBrowse(
            @Field("id") int userId,
            @Field("status") int status);

    //提交执行的操作
    @FormUrlEncoded
    @POST("execute/add")
    Observable<NetworkDataBean<Boolean>> addImageStatus(
            @Field("userid") int userId,
            @Field("imagesid") int imagesId,
            @Field("status") int status);//1为浏览，2为收藏，3为点赞

    //取消执行的操作
    @FormUrlEncoded
    @POST("execute/cancel")
    Observable<NetworkDataBean<Boolean>> cancelImageStatus(
            @Field("userid") int userId,
            @Field("imagesid") int imagesId,
            @Field("status") int status);//1为浏览，2为收藏，3为点赞

    //上传图片的操作
    @POST("addImages")
    Observable<NetworkDataBean<Boolean>> uploadImage(@Body RequestBody body);
}
