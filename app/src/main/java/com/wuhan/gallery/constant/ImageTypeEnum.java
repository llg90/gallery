package com.wuhan.gallery.constant;

public enum ImageTypeEnum {
    STAR(1, "明星"),Scenery(2, "风景"),Comic(3, "动漫"),Drama(4, "剧照"),UserUpload(5, "用户专区");

    private int value;
    private String name;

    ImageTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue(){
        return value;
    }

    public String getName(){
        return name;
    }

    public static ImageTypeEnum getOfValue(int value) {
        for (ImageTypeEnum imageTypeEnum : values()) {
            if (imageTypeEnum.getValue() == value) {
                return imageTypeEnum;
            }
        }
        return null;
    }
}
