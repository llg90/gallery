package com.wuhan.gallery.net;

import com.wuhan.gallery.bean.HostDataBean;
import com.wuhan.gallery.bean.NetworkDataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ImageServer {

    @GET("execute/clicktopload")
    Observable<NetworkDataBean<HostDataBean>> clicktopload();
}
