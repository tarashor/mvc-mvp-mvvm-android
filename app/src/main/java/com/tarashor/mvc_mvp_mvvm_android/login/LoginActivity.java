package com.tarashor.mvc_mvp_mvvm_android.login;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.databinding.ActivityLoginBinding;
import com.tarashor.mvc_mvp_mvvm_android.datasource.UserPreferences;
import com.tarashor.mvc_mvp_mvvm_android.items.MainActivity;

import java.util.Collections;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements ILoginNavigator {

    private LoginViewModel mLoginViewModel;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private ActivityLoginBinding mActivityLoginBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mLoginViewModel = new LoginViewModel(UserPreferences.getInstance(this));
        mLoginViewModel.setLoginNavigator(this);

        mActivityLoginBinding.setViewModel(mLoginViewModel);


        mActivityLoginBinding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    mLoginViewModel.attemptLogin();
                    return true;
                }
                return false;
            }
        });


        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, Collections.<String>emptyList());

        mActivityLoginBinding.email.setAdapter(adapter);

        populateAutoComplete();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        mLoginViewModel.loadEmailsToAutoComplete();
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mActivityLoginBinding.email, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    @Override
    public void showMainScreen() {
        MainActivity.start(this);
        finish();
    }

    @Override
    public void startLoadingEmails(IContactsLoadedListener listener) {
        ContactsLoaderCallbacks contactsLoaderCallbacks = new ContactsLoaderCallbacks(this, listener);
        getSupportLoaderManager().initLoader(0, null, contactsLoaderCallbacks);
    }



    public static void start(Context context) {
        if (context != null){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }


}

