package com.tarashor.mvc_mvp_mvvm_android.login;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IUserPreferences;
import com.tarashor.mvc_mvp_mvvm_android.items.BindingAdapters;

import java.util.List;

public class LoginViewModel {
    private final LoginModel mLoginModel;

    private ILoginNavigator mLoginNavigator;
    private UserLoginTask mAuthTask;
    private IUserPreferences mUserPreferences;

    public ObservableBoolean isProgressVisible = new ObservableBoolean(false);
    public ObservableInt emailErrorStringId = new ObservableInt(BindingAdapters.EMPTY_STRING_ID);
    public ObservableInt passwordErrorStringId = new ObservableInt(BindingAdapters.EMPTY_STRING_ID);
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableList<String> contacts = new ObservableArrayList<>();

    public LoginViewModel(IUserPreferences userPreferences) {
        mUserPreferences = userPreferences;
        mLoginModel = new LoginModel();
    }

    public void setLoginNavigator(ILoginNavigator loginNavigator) {
        this.mLoginNavigator = loginNavigator;
    }

    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        cleanUpErrors();

        try {
            mLoginModel.setPassword(password.get());
            mLoginModel.setEmail(email.get());
            mAuthTask = new UserLoginTask(this);
            mAuthTask.execute((Void) null);
        } catch (InvalidEmailException e) {
            emailErrorStringId.set(R.string.error_invalid_email);
        } catch (RequiredEmailException e) {
            emailErrorStringId.set(R.string.error_field_required);
        } catch (InvalidPasswordException e) {
            passwordErrorStringId.set(R.string.error_invalid_password);
        }
    }

    private void cleanUpErrors() {
        emailErrorStringId.set(BindingAdapters.EMPTY_STRING_ID);
        passwordErrorStringId.set(BindingAdapters.EMPTY_STRING_ID);
    }

    public void loadEmailsToAutoComplete() {
        mLoginNavigator.startLoadingEmails(new IContactsLoadedListener() {
            @Override
            public void onEmailsLoaded(List<String> emails) {
                contacts.clear();
                contacts.addAll(emails);
            }
        });
    }

    public void authStarted() {
        isProgressVisible.set(true);
    }

    public void authFinished() {
        mAuthTask = null;
        isProgressVisible.set(false);
    }

    public void handleAuthResult(Boolean success) {
        mUserPreferences.setUserLoggedIn(success);

        if (success) {
            mLoginNavigator.showMainScreen();
        } else {
            passwordErrorStringId.set(R.string.error_incorrect_password);
        }
    }

    public Boolean isModelMatch(String email, String password) {
        return mLoginModel.getEmail().equals(email) && mLoginModel.getPassword().equals(password);
    }
}
