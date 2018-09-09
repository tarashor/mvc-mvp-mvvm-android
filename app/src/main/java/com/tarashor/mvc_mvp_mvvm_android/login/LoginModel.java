package com.tarashor.mvc_mvp_mvvm_android.login;

import android.text.TextUtils;

import com.tarashor.mvc_mvp_mvvm_android.R;

import java.util.List;

class LoginModel {
    private List<String> emails;

    private String mEmail;
    private String mPassword;

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }


    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setEmailAndPassword(String email, String password) throws InvalidEmailException,
            RequiredEmailException,
            InvalidPasswordException {
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            throw new InvalidPasswordException();
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            throw new RequiredEmailException();

        } else if (!isEmailValid(email)) {
            throw new InvalidEmailException();
        }

        mEmail = email;
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

    public Boolean isMatch(String email, String password) {
        return mEmail.equals(email) && mPassword.equals(password);
    }
}
