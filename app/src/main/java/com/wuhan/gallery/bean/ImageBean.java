package com.wuhan.gallery.bean;


import android.os.Parcel;
import android.os.Parcelable;

/*"id":6,"imageurl":"GD6QUV20180418145822.jpg","type":2,"clickcount":9*/
public class ImageBean implements Parcelable{
    private int id;
    private String imageurl;
    private String addtime;
    private int type;
    private int clickcount;
    private int browsecount;
    private int collectcount;
    private int clickstatus;
    private int collectstatus;
    private int status;

    /**
     * 包装了需要传输的数据,然后在Binder中传输,用于跨进程传输数据
     */
    protected ImageBean(Parcel in) {
        id = in.readInt();
        imageurl = in.readString();
        addtime = in.readString();
        type = in.readInt();
        clickcount = in.readInt();
        browsecount = in.readInt();
        collectcount = in.readInt();
        clickstatus = in.readInt();
        collectstatus = in.readInt();
        status = in.readInt();
    }

    //实现序列化
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imageurl);
        dest.writeString(addtime);
        dest.writeInt(type);
        dest.writeInt(clickcount);
        dest.writeInt(browsecount);
        dest.writeInt(collectcount);
        dest.writeInt(clickstatus);
        dest.writeInt(collectstatus);
        dest.writeInt(status);
    }

    //负责文件描述,针对一些特殊的需要描述信息的对象,需要返回1,其他情况返回0
    @Override
    public int describeContents() {
        return 0;
    }


    //负责反序列化
    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        //从序列化后的对象中创建原始对象
        @Override
        public ImageBean createFromParcel(Parcel source) {
            return new ImageBean(source);
        }

        //创建指定长度的原始对象数组
        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getClickcount() {
        return clickcount;
    }

    public void setClickcount(int clickcount) {
        this.clickcount = clickcount;
    }

    public int getBrowsecount() {
        return browsecount;
    }

    public void setBrowsecount(int browsecount) {
        this.browsecount = browsecount;
    }

    public int getCollectcount() {
        return collectcount;
    }

    public void setCollectcount(int collectcount) {
        this.collectcount = collectcount;
    }

    public int getClickstatus() {
        return clickstatus;
    }

    public void setClickstatus(int clickstatus) {
        this.clickstatus = clickstatus;
    }

    public int getCollectstatus() {
        return collectstatus;
    }

    public void setCollectstatus(int collectstatus) {
        this.collectstatus = collectstatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
