package com.fara.projects.adhub.enums;

public enum NativeTemplateType {
    SMALL_VIEW("small"), MEDIUM_VIEW("medium");

    private String _value;

    NativeTemplateType(String val) {
        this._value = val;
    }

    public String getValue() {
        return _value;
    }

    public static NativeTemplateType byValue(String val) {
        for (NativeTemplateType item : NativeTemplateType.values()) {
            if (item.getValue() == val)
                return item;
        }
        return null;
    }
}