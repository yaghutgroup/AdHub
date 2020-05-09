package com.fara.projects.adhub;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import com.fara.projects.adhub.advertising.AdMob;
import com.fara.projects.adhub.advertising.TapSell;
import com.fara.projects.adhub.enums.BannerType;
import com.fara.projects.adhub.enums.NativeTemplateType;
import com.fara.projects.adhub.restclient.HTTPRequestHelper;
import com.fara.projects.adhub.utils.NativeTemplateLayouts;
import com.fara.projects.adhub.utils.SharedPreferencesManager;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import ir.tapsell.sdk.Tapsell;

public class AdHub {
    public static void initialize(final Application application, String appId) {
        SharedPreferencesManager.getInstance(application.getApplicationContext()).setAppId(appId);

        new HTTPRequestHelper().getAllSdkAppIDFromServer(application.getApplicationContext(), new HTTPRequestHelper.OnCallBackListener() {
            @Override
            public void OnResponse(String result) {
                try {
                    Log.d("----- Result", result);

                    JSONObject obj = new JSONObject(result);
                    String admob = obj.optString("admob", "");
                    String yad = obj.optString("yad", "");
                    String tapsell = obj.optString("tapsell", "");

                    MobileAds.initialize(application, admob);
                    Tapsell.initialize(application, tapsell);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                Log.d("--- ADHUB -> ERROR", message);
            }
        });
    }

    public static class VideoAd {
        public interface OnAdShowListener {
            public abstract void onAdLoaded();
            public abstract void onAdStarted();
            public abstract void onAdOpened();
            public abstract void onAdClosed();
            public abstract void onAdCompleted();
            public abstract void onAdFailedToLoad(String message);
        }

        public static void showAd(final Context context, String zoneId, final OnAdShowListener adShowListener) {
            new HTTPRequestHelper().getVideoAdFromServer(context, zoneId, new HTTPRequestHelper.OnCallBackListener() {
                @Override
                public void OnResponse(String result) {
                    try {
                        Log.d("----- Result", result);

                        JSONObject obj = new JSONObject(result);
                        String zoneId = obj.optString("zone_id", "");
                        String advertiseType = obj.optString("advertise_type", "");

                        if (advertiseType.equals("admob")) {
                            AdMob.showVideoAd(context, zoneId, adShowListener);
                        } else if (advertiseType.equals("tapsell")) {
                            TapSell.showVideoAd(context, zoneId, adShowListener);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String message) {
                    Log.d("--- ADHUB -> ERROR", message);
                }
            });
        }
    }

    public static class BannerAd {
        public interface OnAdShowListener {
            public abstract void onAdLoaded();
            public abstract void onAdFailed(String message);
            public abstract void onAdClicked();
            public abstract void onAdOpened();
            public abstract void onAdClosed();
        }

        public static class Native {
            private Context context;
            private String zoneId;
            private NativeTemplateType templateType;
            private RelativeLayout adContainer;

            public static void showDefaultAd(final Context context, String zoneId, final NativeTemplateType templateType, final RelativeLayout adContainer, final String testDeviceId, final OnAdShowListener onAdShowListener) {
                new HTTPRequestHelper().getBannerNativeAdFromServer(context, zoneId, new HTTPRequestHelper.OnCallBackListener() {
                    @Override
                    public void OnResponse(String result) {
                        try {
                            Log.d("----- Result", result);

                            JSONObject obj = new JSONObject(result);
                            String zoneId = obj.optString("zone_id", "");
                            String advertiseType = obj.optString("advertise_type", "");

                            if (advertiseType.equals("admob")) {
                                AdMob.showDefaultNativeAd(context, zoneId, templateType, adContainer, testDeviceId, onAdShowListener);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Log.d("--- ADHUB -> ERROR", message);
                    }
                });
            }

            public static void showCustomAd(final Context context, String zoneId, NativeTemplateLayouts templateLayouts, final RelativeLayout adContainer) {
                new HTTPRequestHelper().getBannerNativeAdFromServer(context, zoneId, new HTTPRequestHelper.OnCallBackListener() {
                    @Override
                    public void OnResponse(String result) {
                        try {
                            Log.d("----- Result", result);

                            JSONObject obj = new JSONObject(result);
                            String zoneId = obj.optString("zone_id", "");
                            String advertiseType = obj.optString("advertise_type", "");

                            // TODO
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Log.d("--- ADHUB -> ERROR", message);
                    }
                });
            }
        }

        public static class Standard {
            public static void showAd(final Context context, String zoneId, final BannerType bannerType, final RelativeLayout adContainer, final OnAdShowListener onAdShowListener) {
                new HTTPRequestHelper().getBannerStandardAdFromServer(context, zoneId, new HTTPRequestHelper.OnCallBackListener() {
                    @Override
                    public void OnResponse(String result) {
                        try {
                            Log.d("----- Result", result);

                            JSONObject obj = new JSONObject(result);
                            String zoneId = obj.optString("zone_id", "");
                            String advertiseType = obj.optString("advertise_type", "");

                            if (advertiseType.equals("admob")) {
                                AdMob.showStandardBannerAd(context, zoneId, bannerType, adContainer, onAdShowListener);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Log.d("--- ADHUB -> ERROR", message);
                    }
                });
            }
        }
    }
}