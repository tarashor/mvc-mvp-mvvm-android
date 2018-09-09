package com.tarashor.mvc_mvp_mvvm_android.datasource;

public interface IUserPreferences {
    boolean isUserLoggedIn();
    boolean setUserLoggedIn(boolean isLoggedIn);
}
