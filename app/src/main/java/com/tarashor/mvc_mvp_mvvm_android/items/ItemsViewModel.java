package com.tarashor.mvc_mvp_mvvm_android.items;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.Nullable;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IDataSource;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IUserPreferences;

import java.util.ArrayList;
import java.util.List;


public class ItemsViewModel {

    private final IUserPreferences mUserPreferences;
    private final ItemsModel mModel;

    private IItemsNavigator mItemsNavigator;

    public ItemsViewModel(IDataSource dataSource, IUserPreferences userPreferences) {
        mModel = new ItemsModel(dataSource);
        mUserPreferences = userPreferences;
    }

    public void start() {
        if (!mUserPreferences.isUserLoggedIn()){
            mItemsNavigator.startLogin();
        }
        refreshItems();
    }

    public void setItemsNavigator(IItemsNavigator mainView){
        mItemsNavigator = mainView;
    }

    public void logout() {
        mUserPreferences.setUserLoggedIn(false);
        mItemsNavigator.startLogin();
    }

    public void addNewItem() {
        mItemsNavigator.startAddItem();
    }


    public void removeItemByPosition(int position) {
        mModel.removeItemByPosition(position);
        refreshItems();
    }


    public void refreshItems() {
        mModel.refreshItems();
        items.clear();
        items.addAll(mModel.getItems());
    }


    public void handlerAddItemResult(int resultCode, String message) {
        if (resultCode == Activity.RESULT_OK) {
            mItemsNavigator.notifyItemAdded(message);
            refreshItems();
        }
    }

    public final ObservableList<Item> items = new ObservableArrayList<>();



}
