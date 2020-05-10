package com.fara.projects.adhub.advertising;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.fara.projects.adhub.AdHub;
import com.fara.projects.adhub.R;
import com.fara.projects.adhub.enums.BannerType;
import com.fara.projects.adhub.enums.NativeTemplateType;

import ir.tapsell.sdk.AdRequestCallback;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellShowOptions;
import ir.tapsell.sdk.bannerads.TapsellBannerType;
import ir.tapsell.sdk.bannerads.TapsellBannerView;
import ir.tapsell.sdk.bannerads.TapsellBannerViewEventListener;
import ir.tapsell.sdk.nativeads.TapsellNativeBannerManager;
import ir.tapsell.sdk.nativeads.TapsellNativeBannerViewManager;

public class TapSell {
    public static void showVideoAd(final Context context, final String zoneId, final AdHub.VideoAd.OnAdShowListener adShowListener) {
        Tapsell.requestAd(context,
                zoneId,
                new TapsellAdRequestOptions(),
                new TapsellAdRequestListener() {
                    @Override
                    public void onAdAvailable(String adId) {
                        if (adShowListener != null)
                            adShowListener.onAdLoaded();

                        Tapsell.showAd(context,
                                zoneId,
                                adId,
                                new TapsellShowOptions(),
                                new TapsellAdShowListener() {
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

                                    @Override
                                    public void onRewarded(boolean completed) {
                                        if (adShowListener != null)
                                            adShowListener.onAdCompleted();
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
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        TapsellBannerView tapsellBannerView = null;

        if (bannerType.getValue().equals(BannerType.BANNER_320x50.getValue()))
            tapsellBannerView = new TapsellBannerView(context, TapsellBannerType.BANNER_320x50, zoneId);
        else if (bannerType.getValue().equals(BannerType.BANNER_320x100.getValue()))
            tapsellBannerView = new TapsellBannerView(context, TapsellBannerType.BANNER_320x100, zoneId);
        else if (bannerType.getValue().equals(BannerType.BANNER_250x250.getValue()))
            tapsellBannerView = new TapsellBannerView(context, TapsellBannerType.BANNER_250x250, zoneId);
        else if (bannerType.getValue().equals(BannerType.BANNER_300x250.getValue()))
            tapsellBannerView = new TapsellBannerView(context, TapsellBannerType.BANNER_300x250, zoneId);

        tapsellBannerView.setLayoutParams(layoutParams);
        tapsellBannerView.setId(R.id.adBannerStandard);

        adContainer.addView(tapsellBannerView);

        if (bannerType.getValue().equals(BannerType.BANNER_320x50.getValue()))
            tapsellBannerView.loadAd(context, zoneId, TapsellBannerType.BANNER_320x50);
        else if (bannerType.getValue().equals(BannerType.BANNER_320x100.getValue()))
            tapsellBannerView.loadAd(context, zoneId, TapsellBannerType.BANNER_320x100);
        else if (bannerType.getValue().equals(BannerType.BANNER_250x250.getValue()))
            tapsellBannerView.loadAd(context, zoneId, TapsellBannerType.BANNER_250x250);
        else if (bannerType.getValue().equals(BannerType.BANNER_300x250.getValue()))
            tapsellBannerView.loadAd(context, zoneId, TapsellBannerType.BANNER_300x250);

        tapsellBannerView.setEventListener(new TapsellBannerViewEventListener() {
            @Override
            public void onRequestFilled() {
                if (onAdShowListener != null)
                    onAdShowListener.onAdLoaded();
            }

            @Override
            public void onNoAdAvailable() {
                if (onAdShowListener != null)
                    onAdShowListener.onAdFailed("No Ad Available");
            }

            @Override
            public void onNoNetwork() {
                if (onAdShowListener != null)
                    onAdShowListener.onAdFailed("No NetWork");
            }

            @Override
            public void onError(String message) {
                if (onAdShowListener != null)
                    onAdShowListener.onAdFailed(message);
            }

            @Override
            public void onHideBannerView() {
                if (onAdShowListener != null)
                    onAdShowListener.onAdClosed();
            }
        });
    }

    public static void showDefaultNativeAd(final Context context, String zoneId, NativeTemplateType templateType, final RelativeLayout adContainer, String testDeviceId, AdHub.BannerAd.OnAdShowListener adShowListener) {
        String[] splitZoneId = zoneId.split("\\*");

        if (templateType.getValue().equals(NativeTemplateType.SMALL_VIEW.getValue())) {
            buildNativeSmallViewAd(context, splitZoneId[0], adContainer, adShowListener);
        } else if (templateType.getValue().equals(NativeTemplateType.MEDIUM_VIEW.getValue())) {
            buildNativeMediumViewAd(context, splitZoneId[1], adContainer, adShowListener);
        }
    }

    public static void showCustomNativeAd() {
        buildNativeCustomViewAd();
    }

    /*---- Native Section ----*/

    private static void buildNativeSmallViewAd(final Context context, final String zoneId, RelativeLayout adContainer, final AdHub.BannerAd.OnAdShowListener adShowListener) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setId(R.id.adBannerNative);

        adContainer.addView(frameLayout);

        ViewGroup viewGroup = frameLayout;

        final TapsellNativeBannerViewManager nativeBannerViewManager = new TapsellNativeBannerManager
                .Builder()
                .setParentView(viewGroup)
                .setContentViewTemplate(R.layout.tapsell_content_banner_ad_template)
                .setAppInstallationViewTemplate(R.layout.tapsell_app_installation_banner_ad_template)
                .inflateTemplate(context);

        TapsellNativeBannerManager.getAd(context, zoneId,
                new AdRequestCallback() {
                    @Override
                    public void onResponse(String[] adId) {
                        TapsellNativeBannerManager.bindAd(
                                context,
                                nativeBannerViewManager,
                                zoneId,
                                adId[0]);

                        if (adShowListener != null) {
                            adShowListener.onAdLoaded();
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        if (adShowListener != null) {
                            adShowListener.onAdFailed(message);
                        }
                    }
                });
    }

    private static void buildNativeMediumViewAd(final Context context, final String zoneId, RelativeLayout adContainer, final AdHub.BannerAd.OnAdShowListener adShowListener) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setId(R.id.adBannerNative);

        adContainer.addView(frameLayout);

        ViewGroup viewGroup = frameLayout;

        final TapsellNativeBannerViewManager nativeBannerViewManager = new TapsellNativeBannerManager
                .Builder()
                .setParentView(viewGroup)
                .setContentViewTemplate(R.layout.tapsell_content_banner_ad_template)
                .setAppInstallationViewTemplate(R.layout.tapsell_app_installation_banner_ad_template)
                .inflateTemplate(context);

        TapsellNativeBannerManager.getAd(context, zoneId,
                new AdRequestCallback() {
                    @Override
                    public void onResponse(String[] adId) {
                        TapsellNativeBannerManager.bindAd(
                                context,
                                nativeBannerViewManager,
                                zoneId,
                                adId[0]);

                        if (adShowListener != null) {
                            adShowListener.onAdLoaded();
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        if (adShowListener != null) {
                            adShowListener.onAdFailed(message);
                        }
                    }
                });
    }

    private static void buildNativeCustomViewAd() {
        // TODO
    }
}
