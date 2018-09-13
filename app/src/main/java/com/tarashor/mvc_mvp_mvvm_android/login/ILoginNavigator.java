package com.tarashor.mvc_mvp_mvvm_android.login;

public interface ILoginNavigator {
    void startLoadingEmails(IContactsLoadedListener listener);

    void showMainScreen();
}
