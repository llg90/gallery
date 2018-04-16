package com.wuhan.gallery.bean;

import java.util.List;

public class RecordImageBean {
    private int type;
    private String time;
    private List<String> urls;

    public RecordImageBean(int type, String time, List<String> urls) {
        this.type = type;
        this.time = time;
        this.urls = urls;
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

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
