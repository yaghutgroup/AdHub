package com.fara.projects.adhub.restclient.asyncs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fara.projects.adhub.AdHubConfig;
import com.fara.projects.adhub.restclient.HTTPRequestHelper;
import com.fara.projects.adhub.restclient.httprequest.HttpGet;
import com.fara.projects.adhub.restclient.httprequest.utils.Http;
import com.fara.projects.adhub.utils.SharedPreferencesManager;

import org.json.JSONObject;

import java.net.HttpURLConnection;

    public class GetBannerNativeAd extends AsyncTask<Void, Void, String> {
        private Context context;
        private String zoneId;
        private HTTPRequestHelper.OnCallBackListener callBackListener;

    public GetBannerNativeAd(Context context, String zoneId, HTTPRequestHelper.OnCallBackListener callBackListener) {
        this.context = context;
        this.zoneId = zoneId;
        this.callBackListener = callBackListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String responseBody = "";

        try {
            String url = AdHubConfig.URL_ADVERTISE_BANNER_NATIVE + "?app_id=" + SharedPreferencesManager.getInstance(this.context).getAppId() + "&zone_id=" + zoneId + "&type=banner_native";

            Log.d("--- ADHUB -> URL", url);

            Http httpGet = new HttpGet(url);
            int responseCode = httpGet
                    .setConnectionTimeout(15000)
                    .setReadTimeout(15000)
                    .execute();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                responseBody = httpGet.getResponseBody();
            } else {
                if (this.callBackListener != null)
                    this.callBackListener.onError("Error " + responseCode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.callBackListener.onError(ex.getMessage());
        }

        return responseBody;
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            int result = obj.optInt("result", 0);
            String message = obj.optString("message", "");
            JSONObject data = obj.optJSONObject("data");

            if (result == 1) {
                if (this.callBackListener != null)
                    this.callBackListener.OnResponse(data.toString());
            } else {
                if (this.callBackListener != null)
                    this.callBackListener.onError(message);
            }
        } catch (Exception ex) {
            this.callBackListener.onError(ex.getMessage());
        }
    }
}