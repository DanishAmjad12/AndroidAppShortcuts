package com.appshortcuts.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by daamjad on 3/29/2017.
 */

public class PrefUtil {
    private static final String PREFERENCES_FILE_NAME = "app_shorcuts";
    private static final String PREF_USER_LOGIN_TAG="user_login_tag";


    public static void clearAllSharedPreference(final Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }

    public static void setPrefLoginSession(final Context context, final boolean isUserLogin) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().putBoolean(PREF_USER_LOGIN_TAG, isUserLogin).apply();
    }

    public static boolean getPrefIsUserLogin(final Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(PREF_USER_LOGIN_TAG, false);
    }
}
