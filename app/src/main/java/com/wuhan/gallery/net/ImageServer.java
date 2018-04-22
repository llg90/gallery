package com.wuhan.gallery.net;

import com.wuhan.gallery.bean.HostDataBean;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ImageServer {

    @GET("execute/clicktopload")
    Observable<NetworkDataBean<HostDataBean>> clicktopload();

    @GET("execute/images")
    Observable<NetworkDataBean<List<ImageBean>>> getImagesByType(@Query("type") int type);

    @FormUrlEncoded
    @POST("execute/browse")
    Observable<NetworkDataBean<List<ImageBean>>> getImageBrowse(@Field("id") int userId,
                                                                @Field("status") int status);  //1为浏览，2为收藏，3为点

    @FormUrlEncoded
    @POST("execute/add")
    Observable<NetworkDataBean<Boolean>> setImageStatus(@Field("userid") int userId,
                                                        @Field("imagesid") int imagesId,
                                                        @Field("status") int status);//1为浏览，2为收藏，3为点
}
