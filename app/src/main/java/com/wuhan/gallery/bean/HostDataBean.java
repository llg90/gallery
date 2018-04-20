package com.wuhan.gallery.bean;

import java.util.List;

public class HostDataBean {
    private List<ImageBean> maxlist;
    private List<ImageBean> clicklist;

    public List<ImageBean> getMaxlist() {
        return maxlist;
    }

    public void setMaxlist(List<ImageBean> maxlist) {
        this.maxlist = maxlist;
    }

    public List<ImageBean> getClicklist() {
        return clicklist;
    }

    public void setClicklist(List<ImageBean> clicklist) {
        this.clicklist = clicklist;
    }
}
