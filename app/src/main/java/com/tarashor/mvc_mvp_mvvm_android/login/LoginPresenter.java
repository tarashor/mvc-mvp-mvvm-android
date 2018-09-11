package com.tarashor.mvc_mvp_mvvm_android.login;

import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IUserPreferences;

import java.util.List;

public class LoginPresenter {
    private final LoginModel mLoginModel;

    private ILoginView mLoginView;
    private UserLoginTask mAuthTask;
    private IUserPreferences mUserPreferences;

    public LoginPresenter(IUserPreferences userPreferences) {
        mUserPreferences = userPreferences;
        mLoginModel = new LoginModel();
    }

    public void setLoginView(ILoginView loginView) {
        this.mLoginView = mLoginView;
    }

    public void attemptLogin(String email, String password) {
        if (mAuthTask != null) {
            return;
        }

        mLoginView.cleanUpErrors();

        try {
            mLoginModel.setPassword(password);
            mLoginModel.setEmail(email);
            mAuthTask = new UserLoginTask(this);
            mAuthTask.execute((Void) null);
        } catch (InvalidEmailException e) {
            mLoginView.setEmailError(R.string.error_invalid_email);
        } catch (RequiredEmailException e) {
            mLoginView.setEmailError(R.string.error_field_required);
        } catch (InvalidPasswordException e) {
            mLoginView.setPasswordError(R.string.error_invalid_password);
        }
    }

    public void loadEmailsToAutoComplete() {
        mLoginView.startLoadingEmails(new IContactsLoadedListener() {
            @Override
            public void onEmailsLoaded(List<String> emails) {
                mLoginModel.setEmails(emails);
                mLoginView.updateEmails(mLoginModel);
            }
        });
    }

    public void authStarted() {
        mLoginView.showProgress(true);
    }

    public void authFinished() {
        mAuthTask = null;
        mLoginView.showProgress(false);
    }

    public void handleAuthResult(Boolean success) {
        mUserPreferences.setUserLoggedIn(success);

        if (success) {
            mLoginView.showMainScreen();
        } else {
            mLoginView.setPasswordError(R.string.error_incorrect_password);
        }
    }

    public Boolean isModelMatch(String email, String password) {
        return mLoginModel.getEmail().equals(email) && mLoginModel.getPassword().equals(password);
    }
}
