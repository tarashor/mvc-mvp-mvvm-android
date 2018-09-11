package com.tarashor.mvc_mvp_mvvm_android.items;

interface IItemsNavigator {
    void startLogin();

    void startAddItem();

    void notifyItemAdded(String message);

}
