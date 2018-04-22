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
    private int status;
    //   private int

    protected ImageBean(Parcel in) {
        id = in.readInt();
        imageurl = in.readString();
        addtime = in.readString();
        type = in.readInt();
        clickcount = in.readInt();
        browsecount = in.readInt();
        collectcount = in.readInt();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imageurl);
        dest.writeString(addtime);
        dest.writeInt(type);
        dest.writeInt(clickcount);
        dest.writeInt(browsecount);
        dest.writeInt(collectcount);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel in) {
            return new ImageBean(in);
        }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
