package com.tarashor.mvc_mvp_mvvm_android;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
    private static final String SHARED_PREFERENCES_NAME = "user_prefs";
    private static final String USER_LOGGED_KEY = "is_user_logged_in";
    private final SharedPreferences mPrefs;

    private static UserPreferences sInstance;

    public static UserPreferences getInstance(Context context){
        if (sInstance == null){
            synchronized (UserPreferences.class){
                sInstance = new UserPreferences(context);
            }
        }
        return sInstance;
    }

    private UserPreferences(Context context) {
        mPrefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    public boolean isUserLoggedIn() {
        return  mPrefs.getBoolean(USER_LOGGED_KEY, false);
    }

    public boolean setUserLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(USER_LOGGED_KEY, isLoggedIn);
        return editor.commit();
    }
}
