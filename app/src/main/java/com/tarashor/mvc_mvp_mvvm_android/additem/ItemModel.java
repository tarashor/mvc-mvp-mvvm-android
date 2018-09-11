package com.tarashor.mvc_mvp_mvvm_android.additem;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IDataSource;
import com.tarashor.mvc_mvp_mvvm_android.datasource.LocalDatasource;

public class ItemModel {
    private final IDataSource mDataSource;
    private Item mItem  = null;
    private boolean newItem;

//    public ItemModel(IDataSource datasource, int itemId){
//        mDatasource = datasource;
//        mItem = mDatasource.getItemById(itemId);
//    }

    public ItemModel(IDataSource dataSource) {
        mItem = new Item();
        newItem = true;
        mDataSource = dataSource;
    }

    public void setItemName(String itemName) {
        mItem.setName(itemName);
    }

    public Item getItem() {
        return mItem;
    }

    public boolean isNewItem() {
        return newItem;
    }

    public void saveItem() {
        if (mDataSource == null) return;
        mDataSource.saveItem(mItem);
    }
}
