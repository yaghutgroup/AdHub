package com.fara.projects.adhub.restclient;

import android.content.Context;
import android.util.Log;

import com.fara.projects.adhub.restclient.asyncs.GetAllSdkAppID;
import com.fara.projects.adhub.restclient.asyncs.GetBannerNativeAd;
import com.fara.projects.adhub.restclient.asyncs.GetBannerStandardAd;
import com.fara.projects.adhub.restclient.asyncs.GetVideoAd;

public class HTTPRequestHelper {
    public interface OnCallBackListener {
        public abstract void OnResponse(String result);
        public abstract void onError(String message);
    }

    public void getAllSdkAppIDFromServer(Context context, OnCallBackListener callBackListener) {
        new GetAllSdkAppID(context, callBackListener);
    }

    public void getVideoAdFromServer(Context context, String zoneId, OnCallBackListener callBackListener) {
        Log.d("-----", "step 2");
        new GetVideoAd(context, zoneId, callBackListener);
    }

    public void getBannerStandardAdFromServer(Context context, String zoneId, OnCallBackListener callBackListener) {
        new GetBannerStandardAd(context, zoneId, callBackListener);
    }

    public void getBannerNativeAdFromServer(final Context context, String zoneId, OnCallBackListener callBackListener) {
        new GetBannerNativeAd(context, zoneId, callBackListener);
    }
}