package com.wuhan.gallery;

import android.app.Application;
import android.content.Context;

import com.wuhan.gallery.bean.UserBean;

/**
 * @author: 李利刚
 * @E-mail: lgzc_work@163.com
 * @date: 2018-04-11 15:36
 * @describe:
 */
public class GalleryApplication extends Application {
    private static GalleryApplication sContext;
    private UserBean mUserBean;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static GalleryApplication getContext() {
        return sContext;
    }

    public UserBean getUserBean() {
        return mUserBean;
    }

    public void setUserBean(UserBean userBean) {
        mUserBean = userBean;
    }
}
