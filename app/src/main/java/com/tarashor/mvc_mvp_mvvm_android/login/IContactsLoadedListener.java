package com.tarashor.mvc_mvp_mvvm_android.login;

import java.util.List;

interface IContactsLoadedListener {
    void onEmailsLoaded(List<String> emails);
}
