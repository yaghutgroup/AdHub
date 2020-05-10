package com.fara.projects.adhub.advertising;

import android.content.Context;
import android.widget.RelativeLayout;

import com.fara.projects.adhub.AdHub;
import com.fara.projects.adhub.enums.BannerType;
import com.fara.yad.Yad;

public class Yads {
    public static void showVideoAd(final Context context, final String zoneId, final AdHub.VideoAd.OnAdShowListener adShowListener) {
        Yad.VideoAd.requestAd(context, zoneId, new Yad.VideoAd.OnAdRequestListener() {
            @Override
            public void onAvailable(String adId) {
                Yad.VideoAd.showAd(context, zoneId, adId, new Yad.VideoAd.OnAdShowListener() {
                    @Override
                    public void onCompleted(boolean completed) {
                        if (adShowListener != null)
                            adShowListener.onAdCompleted();
                    }

                    @Override
                    public void onOpened() {
                        if (adShowListener != null)
                            adShowListener.onAdOpened();
                    }

                    @Override
                    public void onClosed() {
                        if (adShowListener != null)
                            adShowListener.onAdClosed();
                    }

                    @Override
                    public void onError(String message) {
                        if (adShowListener != null)
                            adShowListener.onAdFailedToLoad(message);
                    }
                });
            }

            @Override
            public void onError(String message) {
                if (adShowListener != null)
                    adShowListener.onAdFailedToLoad(message);
            }
        });
    }

    public static void showStandardBannerAd(final Context context, final String zoneId, BannerType bannerType, RelativeLayout adContainer, final AdHub.BannerAd.OnAdShowListener onAdShowListener) {
        com.fara.yad.enums.BannerType bannerSize = com.fara.yad.enums.BannerType.BANNER_320x50;

        if (bannerType.getValue().equals(BannerType.BANNER_250x250.getValue())) {
            bannerSize = com.fara.yad.enums.BannerType.BANNER_250x250;
        } else if (bannerType.getValue().equals(BannerType.BANNER_300x250.getValue())) {
            bannerSize = com.fara.yad.enums.BannerType.BANNER_300x250;
        } else if (bannerType.getValue().equals(BannerType.BANNER_320x50.getValue())) {
            bannerSize = com.fara.yad.enums.BannerType.BANNER_320x50;
        } else if (bannerType.getValue().equals(BannerType.BANNER_320x100.getValue())) {
            bannerSize = com.fara.yad.enums.BannerType.BANNER_320x100;
        }

        Yad.BannerAd.Standard.showAd(context, zoneId, bannerSize, adContainer, new Yad.BannerAd.OnAdShowListener() {
            @Override
            public void onFilled() {
                if (onAdShowListener != null)
                    onAdShowListener.onAdLoaded();
            }

            @Override
            public void onError(String message) {
                if (onAdShowListener != null)
                    onAdShowListener.onAdFailed(message);
            }
        });
    }
}