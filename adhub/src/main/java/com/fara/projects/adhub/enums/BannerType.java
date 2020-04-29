package com.fara.projects.adhub.enums;

public enum BannerType {
    BANNER_250x250("banner_250x250"), BANNER_300x250("banner_300x250"), BANNER_320x50("banner_320x50"), BANNER_320x100("banner_320x100");

    private String _value;

    BannerType(String val) {
        this._value = val;
    }

    public String getValue() {
        return _value;
    }

    public static BannerType byValue(String val) {
        for (BannerType item : BannerType.values()) {
            if (item.getValue() == val)
                return item;
        }
        return null;
    }
}