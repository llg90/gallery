package com.wuhan.gallery.bean;


import android.os.Parcel;
import android.os.Parcelable;

/*"id":6,"imageurl":"GD6QUV20180418145822.jpg","type":2,"clickcount":9*/
public class ImageBean implements Parcelable{
    private int id;
    private String imageurl;
    private int type;
    private int clickcount;
 //   private int

    protected ImageBean(Parcel in) {
        id = in.readInt();
        imageurl = in.readString();
        type = in.readInt();
        clickcount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imageurl);
        dest.writeInt(type);
        dest.writeInt(clickcount);
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
}
