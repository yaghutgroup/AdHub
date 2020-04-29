package com.fara.projects.adhub.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static SharedPreferencesManager sharedPreferencesManager;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private final static String PREF_NAME = "com.fara.projects.yaghutads";

    private final static String KEY_APP_ID = "app_id";

    public SharedPreferencesManager(Context context) {
        this.context = context;
        this.preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if (sharedPreferencesManager == null)
            sharedPreferencesManager = new SharedPreferencesManager(context);

        return sharedPreferencesManager;
    }

    //--------------- APP ID

    public void setAppId(String appId) {
        editor.putString(KEY_APP_ID, appId);
        editor.commit();
    }

    public String getAppId() {
        return preferences.getString(KEY_APP_ID, "");
    }
}