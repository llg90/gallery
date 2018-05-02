package com.wuhan.gallery.bean;

import java.util.List;

//HostFragment获取的数据源
public class HostDataBean {
    private List<ImageBean> maxlist;
    private List<ImageBean> clicklist;
    private List<ImageBean> likelist;

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

    public List<ImageBean> getLikelist() {
        return likelist;
    }

    public void setLikelist(List<ImageBean> likelist) {
        this.likelist = likelist;
    }
}
