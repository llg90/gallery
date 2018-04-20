package com.wuhan.gallery.net;

import com.wuhan.gallery.bean.HostDataBean;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageServer {

    @GET("execute/clicktopload")
    Observable<NetworkDataBean<HostDataBean>> clicktopload();
}
