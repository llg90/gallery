package com.wuhan.gallery;

import android.app.Application;

import com.wuhan.gallery.bean.UserBean;


public class GalleryApplication extends Application {
    private static GalleryApplication sContext;
    private static UserBean mUserBean;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static GalleryApplication getContext() {
        return sContext;
    }

    public static UserBean getUserBean() {
        return mUserBean;
    }

    public static void setUserBean(UserBean userBean) {
        mUserBean = userBean;
    }
}
