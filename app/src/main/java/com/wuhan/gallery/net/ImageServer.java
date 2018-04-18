package com.wuhan.gallery.net;

import com.wuhan.gallery.base.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageServer {
    @GET("")
    Observable<NetworkDataBean<ImageBean>> getImage(@Query("tab") String tab);
}
