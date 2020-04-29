package com.fara.projects.adhub.utils;

//NativeTemplateLayouts templateLayouts = new NativeTemplateLayouts.Builder()
//        .setAdmobTemplateLayout(1)
//        .setTapsellTemplateLayout(2)
//        .setYadTemplateLayout(3)
//        .build();

public class NativeTemplateLayouts {
    private int admobTemplateLayout;
    private int tapsellTemplateLayout;
    private int yadTemplateLayout;

    public NativeTemplateLayouts(Builder builder) {
        this.admobTemplateLayout = builder.admobTemplateLayout;
        this.tapsellTemplateLayout = builder.tapsellTemplateLayout;
        this.yadTemplateLayout = builder.yadTemplateLayout;
    }

    public int getAdmobTemplateLayout() {
        return admobTemplateLayout;
    }

    public int getTapsellTemplateLayout() {
        return tapsellTemplateLayout;
    }

    public int getYadTemplateLayout() {
        return yadTemplateLayout;
    }

    public static class Builder {
        private int admobTemplateLayout;
        private int tapsellTemplateLayout;
        private int yadTemplateLayout;

        public Builder() {
        }

        public Builder setAdmobTemplateLayout(int admobTemplateLayout) {
            this.admobTemplateLayout = admobTemplateLayout;
            return this;
        }

        public Builder setTapsellTemplateLayout(int tapsellTemplateLayout) {
            this.tapsellTemplateLayout = tapsellTemplateLayout;
            return this;
        }

        public Builder setYadTemplateLayout(int yadTemplateLayout) {
            this.yadTemplateLayout = yadTemplateLayout;
            return this;
        }

        public NativeTemplateLayouts build() {
            return new NativeTemplateLayouts(this);
        }
    }
}