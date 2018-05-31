package com.marygalejabagat.it350.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import com.marygalejabagat.it350.constants.Preferences;

public class AppPreference {
    protected static final String PREFERENCE_NAME = "share_preference";
    private static AppPreference singleton = null;

    protected static SharedPreferences sharedPreferences;
    protected static Context context;

    public synchronized static AppPreference getInstance(Context context) {
        if (singleton == null) {
            singleton = new AppPreference(context);
        }
        return (singleton);
    }

    protected AppPreference(Context ctx) {
        context = ctx;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    public void setStringPreferences(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setIntPreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setLongPreferences(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void setFloatPreferences(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void setBooleanPreferences(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getStringPreferences(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public int getIntPreferences(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public long getLongPreferences(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public float getFloatPreferences(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public Boolean getBooleanPreferences(String key, Boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void setIsLogin(boolean isLogin) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Preferences.PREF_IS_LOGIN, isLogin);
        editor.apply();
    }

    public Boolean getIsLogin() {
        return sharedPreferences.getBoolean(Preferences.PREF_IS_LOGIN, false);
    }

    public void setStringSetPreferences(String key, Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public void setCurrentUser(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getCurrentUser(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public Set<String> getStringSetPreferences(String key, Set<String> defaultValue) {
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    public void setJSONObject(String key, JSONObject object) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, object.toString());
        editor.apply();
    }

    public JSONObject getJSONObject(String key) throws JSONException {
        return new JSONObject(sharedPreferences.getString(key, "{}"));
    }

}
