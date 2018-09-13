package com.tarashor.mvc_mvp_mvvm_android.login;

import android.os.AsyncTask;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final LoginViewModel mLoginViewModel;

    private final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    UserLoginTask(LoginViewModel loginViewModel) {
        mLoginViewModel = loginViewModel;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLoginViewModel.authStarted();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            return mLoginViewModel.isModelMatch(pieces[0], pieces[1]);
        }

        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        mLoginViewModel.authFinished();
        mLoginViewModel.handleAuthResult(success);
    }

    @Override
    protected void onCancelled() {
        mLoginViewModel.authFinished();
    }
}
