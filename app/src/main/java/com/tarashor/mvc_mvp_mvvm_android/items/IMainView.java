package com.tarashor.mvc_mvp_mvvm_android.items;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;

interface IMainView {
    void startLogin();

    void startAddItem();

    void notifyItemAdded(Item newItem);

    ItemsController getController();
}
