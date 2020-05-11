package com.fara.projects.adhub.advertising;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fara.projects.adhub.AdHub;
import com.fara.projects.adhub.R;
import com.fara.projects.adhub.enums.BannerType;
import com.fara.projects.adhub.enums.NativeTemplateType;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Arrays;
import java.util.List;

public class AdMob {
    public static void showVideoAd(final Context context, final String zoneId, final AdHub.VideoAd.OnAdShowListener adShowListener) {
        final RewardedVideoAd mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                if (adShowListener != null) {
                    adShowListener.onAdLoaded();
                }

                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }

            }

            @Override
            public void onRewardedVideoAdOpened() {
                if (adShowListener != null)
                    adShowListener.onAdOpened();
            }

            @Override
            public void onRewardedVideoStarted() {
                if (adShowListener != null)
                    adShowListener.onAdStarted();
            }

            @Override
            public void onRewardedVideoAdClosed() {
                if (adShowListener != null)
                    adShowListener.onAdClosed();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                if (adShowListener != null)
                    adShowListener.onAdFailedToLoad("Google AdMob error code " + i);
            }

            @Override
            public void onRewardedVideoCompleted() {
                if (adShowListener != null)
                    adShowListener.onAdCompleted();
            }
        });
        mRewardedVideoAd.loadAd(zoneId, new AdRequest.Builder().build());
    }

    public static void showStandardBannerAd(final Context context, final String zoneId, BannerType bannerType, RelativeLayout adContainer, final AdHub.BannerAd.OnAdShowListener onAdShowListener) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        AdView adView = new AdView(context);
        adView.setLayoutParams(layoutParams);
        adView.setId(R.id.adBannerStandard);
        adView.setAdUnitId(zoneId);

        if (bannerType.getValue() == BannerType.BANNER_250x250.getValue())
            adView.setAdSize(new AdSize(250, 250));
        else if (bannerType.getValue() == BannerType.BANNER_300x250.getValue())
            adView.setAdSize(new AdSize(300, 250));
        else if (bannerType.getValue() == BannerType.BANNER_320x50.getValue())
            adView.setAdSize(new AdSize(320, 50));
        else if (bannerType.getValue() == BannerType.BANNER_320x100.getValue())
            adView.setAdSize(new AdSize(320, 100));

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (onAdShowListener != null)
                    onAdShowListener.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                if (onAdShowListener != null)
                    onAdShowListener.onAdFailed(String.valueOf(errorCode));
            }

            @Override
            public void onAdOpened() {
                if (onAdShowListener != null)
                    onAdShowListener.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                if (onAdShowListener != null)
                    onAdShowListener.onAdClicked();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                if (onAdShowListener != null)
                    onAdShowListener.onAdClosed();
            }
        });

        adContainer.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public static void showDefaultNativeAd(final Context context, String zoneId, NativeTemplateType templateType, final RelativeLayout adContainer, String testDeviceId, AdHub.BannerAd.OnAdShowListener adShowListener) {
       if (testDeviceId != null || !testDeviceId.isEmpty()) {
           List<String> testDeviceIds = Arrays.asList(testDeviceId);
           RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
           MobileAds.setRequestConfiguration(configuration);
       }

        if (templateType.getValue().equals(NativeTemplateType.SMALL_VIEW.getValue())) {
            buildNativeSmallViewAd(context, zoneId, adContainer, adShowListener);
        } else if (templateType.getValue().equals(NativeTemplateType.MEDIUM_VIEW.getValue())) {
            buildNativeMediumViewAd(context, zoneId, adContainer, adShowListener);
        }
    }

    public static void showCustomNativeAd(Context context, String zoneId, RelativeLayout adContainer, int yourTemplateLayout, final AdHub.BannerAd.OnAdShowListener adShowListener) {
        buildNativeCustomViewAd(context, zoneId, adContainer, yourTemplateLayout, adShowListener);
    }

    /*---- Native Section ----*/

    private static void buildNativeSmallViewAd(Context context, String zoneId, RelativeLayout adContainer, final AdHub.BannerAd.OnAdShowListener adShowListener) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        NativeExpressAdView adView = new NativeExpressAdView(context);
        adView.setLayoutParams(layoutParams);
        adView.setId(R.id.adBannerNative);
        adView.setAdSize(new AdSize(280, 80));
        adView.setAdUnitId(zoneId);

        adContainer.addView(adView);

        adView.setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build());

        VideoController videoController = adView.getVideoController();
        videoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            @Override
            public void onVideoStart() {
                super.onVideoStart();
            }

            @Override
            public void onVideoPlay() {
                super.onVideoPlay();
            }

            @Override
            public void onVideoEnd() {
                super.onVideoEnd();
            }
        });

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                if (adShowListener != null)
                    adShowListener.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

                if (adShowListener != null)
                    adShowListener.onAdFailed("Banner Native Error Code " + i);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();

                if (adShowListener != null)
                    adShowListener.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                if (adShowListener != null)
                    adShowListener.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();

                if (adShowListener != null)
                    adShowListener.onAdClicked();
            }
        });

        adView.loadAd(new AdRequest.Builder().build());
    }

    private static void buildNativeMediumViewAd(Context context, String zoneId, RelativeLayout adContainer, final AdHub.BannerAd.OnAdShowListener adShowListener) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        NativeExpressAdView adView = new NativeExpressAdView(context);
        adView.setLayoutParams(layoutParams);
        adView.setId(R.id.adBannerNative);
        adView.setAdSize(new AdSize(280, 250));
        adView.setAdUnitId(zoneId);

        adView.setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build());

        VideoController videoController = adView.getVideoController();
        videoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            @Override
            public void onVideoStart() {
                super.onVideoStart();
            }

            @Override
            public void onVideoPlay() {
                super.onVideoPlay();
            }

            @Override
            public void onVideoEnd() {
                super.onVideoEnd();
            }
        });

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                if (adShowListener != null)
                    adShowListener.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

                if (adShowListener != null)
                    adShowListener.onAdFailed("Banner Native Error Code " + i);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();

                if (adShowListener != null)
                    adShowListener.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                if (adShowListener != null)
                    adShowListener.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();

                if (adShowListener != null)
                    adShowListener.onAdClicked();
            }
        });

        adContainer.addView(adView);
        adView.loadAd(new AdRequest.Builder().build());
    }

    private static void buildNativeCustomViewAd(Context context, String zoneId, final RelativeLayout adContainer, final int yourTemplateLayout, final AdHub.BannerAd.OnAdShowListener adShowListener) {
        AdLoader adLoader = new AdLoader.Builder(context, zoneId).forCustomTemplateAd("10063170", new NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener() {
                            @Override
                            public void onCustomTemplateAdLoaded(NativeCustomTemplateAd ad) {
                                // Display ad and record impression
                                ViewGroup adView = (ViewGroup) adContainer;
                                displayCustomTemplateAd(adView, ad, yourTemplateLayout);
                            }
                        },
                        null).build();
    }

    private static void displayCustomTemplateAd (ViewGroup parent, final NativeCustomTemplateAd ad, int yourTemplateLayout) {
        // Inflate a layout and add it to the parent ViewGroup.
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View adView = inflater.inflate(yourTemplateLayout, parent);

        // Show the custom template
        TextView headline = adView.findViewById(R.id.headline);
        TextView caption = adView.findViewById(R.id.caption);
        ImageView mainImage = adView.findViewById(R.id.mainImage);
        headline.setText(ad.getText("Headline"));
        caption.setText(ad.getText("Caption"));
        mainImage.setImageDrawable(ad.getImage("MainImage").getDrawable());

        // Record an impression
        ad.recordImpression();

        // Handle clicks on image
        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.performClick("MainImage");
            }
        });
    }
}