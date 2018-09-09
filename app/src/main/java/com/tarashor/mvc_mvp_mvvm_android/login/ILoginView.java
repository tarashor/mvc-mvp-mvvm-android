package com.tarashor.mvc_mvp_mvvm_android.login;

public interface ILoginView {
    void startLoadingEmails(IContactsLoadedListener listener);

    void updateEmails(LoginModel loginModel);

    void cleanUpErrors();

    void setEmailError(int messageId);

    void setPasswordError(int messageId);

    void showProgress(boolean isLoading);

    void showMainScreen();
}
