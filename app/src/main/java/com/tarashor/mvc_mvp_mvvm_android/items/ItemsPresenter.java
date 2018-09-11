package com.tarashor.mvc_mvp_mvvm_android.items;

import android.app.Activity;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IDataSource;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IUserPreferences;
import com.tarashor.mvc_mvp_mvvm_android.datasource.LocalDatasource;

import java.util.ArrayList;


public class ItemsPresenter {
    private final IUserPreferences mUserPreferences;
    private final ItemsModel mModel;

    private IMainView mMainView;

    private IItemsView mItemsView;


    public ItemsPresenter(IDataSource dataSource, IUserPreferences userPreferences) {
        mModel = new ItemsModel(dataSource);
        mUserPreferences = userPreferences;
    }

    public void start() {
        if (!mUserPreferences.isUserLoggedIn()){
            mMainView.startLogin();
        }
        mModel.refreshItems();
        refreshItemsView();

    }

    public void setMainView(IMainView mainView){
        mMainView = mainView;
    }

    public void setItemsView(IItemsView itemsView) {
        this.mItemsView = itemsView;
    }


    public void logout() {
        mUserPreferences.setUserLoggedIn(false);
        mMainView.startLogin();
    }

    public void addNewItem() {
        mMainView.startAddItem();
    }


    public void removeItemByPosition(int position) {
        mModel.removeItemByPosition(position);
        refreshItemsView();
    }


    public void refreshItems() {
        mModel.refreshItems();
        refreshItemsView();
    }

    private void refreshItemsView(){
        if (mItemsView != null){
            mItemsView.showItems(new ArrayList<>(mModel.getItems()));
        }
    }

    public void handlerAddItemResult(int resultCode, String message) {
        if (resultCode == Activity.RESULT_OK) {
            mMainView.notifyItemAdded(message);
            refreshItems();
        }
    }


}
