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
