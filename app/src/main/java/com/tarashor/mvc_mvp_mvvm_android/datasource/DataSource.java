package com.tarashor.mvc_mvp_mvvm_android.datasource;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;

import java.util.List;

public interface DataSource {

    interface LoadItemsCallback {

        void onTasksLoaded(List<Item> items);

        void onDataNotAvailable();

    }

    void getItems(LoadItemsCallback callback);

    void saveItem(Item item);

    void removeItem(Item removedItem);
}
