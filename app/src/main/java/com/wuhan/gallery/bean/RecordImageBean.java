package com.wuhan.gallery.bean;

import java.util.List;

public class RecordImageBean {
    private int type;
    private String time;
    private List<ImageBean> urls;

    public RecordImageBean(int type, String time, List<ImageBean> url) {
        super();
        this.type = type;
        this.time = time;
        this.urls = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ImageBean> getUrls() {
        return urls;
    }

    public void setUrls(List<ImageBean> url) {
        this.urls = url;
    }
}
