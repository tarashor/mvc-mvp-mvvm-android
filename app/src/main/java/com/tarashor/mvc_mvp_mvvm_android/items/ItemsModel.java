package com.tarashor.mvc_mvp_mvvm_android.items;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IDataSource;

import java.util.ArrayList;
import java.util.List;

public class ItemsModel {
    private List<Item> mItems = new ArrayList<>();

    private final IDataSource mDataSource;

    public ItemsModel(IDataSource dataSource) {
        mDataSource = dataSource;
        refreshItems();
    }

    public void refreshItems() {
        if (mDataSource == null) return;
        mDataSource.getItems(new IDataSource.LoadItemsCallback() {
            @Override
            public void onTasksLoaded(List<Item> items) {
                mItems = new ArrayList<>(items);
            }

            @Override
            public void onDataNotAvailable() {
                mItems = new ArrayList<>();
            }
        });
    }

    public void removeItemByPosition(int position) {
        Item removedItem = mItems.remove(position);
        if (mDataSource == null) return;
        mDataSource.removeItem(removedItem);
    }

    public void addItem(Item newItem) {
        mItems.add(newItem);
        if (mDataSource == null) return;
        mDataSource.saveItem(newItem);
    }


    public List<Item> getItems() {
        return mItems;
    }
}
