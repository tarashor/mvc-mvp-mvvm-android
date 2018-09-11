package com.tarashor.mvc_mvp_mvvm_android.additem;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.tarashor.mvc_mvp_mvvm_android.datasource.LocalDatasource;

public class AddItemViewModel extends BaseObservable {
    private final ItemModel mItemModel;
    private IItemNavigator mItemNavigator;

    public AddItemViewModel(LocalDatasource dataSource, IItemNavigator mItemNavigator) {
        mItemModel = new ItemModel(dataSource);
        this.mItemNavigator = mItemNavigator;
        if (!mItemModel.isNewItem()) {
            itemName.set(mItemModel.getItem().getName());
        }
    }

    public void saveItem() {
        mItemModel.setItemName(itemName.get());
        mItemModel.saveItem();
        if(mItemNavigator != null) {
            mItemNavigator.onPositiveButtonClicked("New item added: " + mItemModel.getItem().getName());
        }
    }

    public void cancel(){
        if(mItemNavigator != null) {
            mItemNavigator.onNegativeButtonPressed();
        }
    }

    public final ObservableField<String> itemName = new ObservableField<>("");

    public void destroy() {
        mItemNavigator = null;
    }
}
