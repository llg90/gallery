package com.wuhan.gallery.constant;

public enum ImageStatusEnum {
    BROWSE(1, "浏览"),COLLECTION(2, "收藏"),CLICK(3, "点赞");

    private int value;
    private String name;

    ImageStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue(){
        return value;
    }

    public static ImageStatusEnum getOfValue(int value) {
        for (ImageStatusEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }
}

/*public class ImageStatusEnum{
    public static final String TYPE_BROWSE = "浏览";
    public static final String TYPE_COLLECTION = "收藏";
    public static final String TYPE_CLICK = "点赞";

    private int value;
    private String name;

    //Retention 是元注解，简单地讲就是系统提供的，用于定义注解的“注解”
    @Retention(RetentionPolicy.SOURCE)
    //这里指定int的取值只能是以下范围
    @StringDef({TYPE_BROWSE, TYPE_COLLECTION, TYPE_CLICK})
    @interface ImageStatusEnumDef {

    }
    public ImageStatusEnum(@ImageStatusEnumDef int value, @ImageStatusEnumDef String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue(){
        return value;
    }

    public static ImageStatusEnum getOfValue(@ImageStatusEnumDef int value) {
        for (ImageStatusEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }


}*/