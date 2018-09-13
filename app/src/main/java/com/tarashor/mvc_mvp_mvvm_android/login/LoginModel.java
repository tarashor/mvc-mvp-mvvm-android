package com.tarashor.mvc_mvp_mvvm_android.login;

import android.text.TextUtils;

import com.tarashor.mvc_mvp_mvvm_android.R;

import java.util.List;

class LoginModel {
    private String mEmail;
    private String mPassword;

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setEmail(String email) throws InvalidEmailException, RequiredEmailException
    {
        if (TextUtils.isEmpty(email)) {
            throw new RequiredEmailException();

        } else if (!isEmailValid(email)) {
            throw new InvalidEmailException();
        }

        mEmail = email;
    }

    public void setPassword(String password) throws InvalidPasswordException {
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            throw new InvalidPasswordException();
        }

        mPassword = password;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
