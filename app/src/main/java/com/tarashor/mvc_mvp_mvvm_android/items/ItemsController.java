package com.tarashor.mvc_mvp_mvvm_android.items;

import android.app.Activity;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IDataSource;
import com.tarashor.mvc_mvp_mvvm_android.datasource.IUserPreferences;


public class ItemsController {
    private final IUserPreferences mUserPreferences;
    private final IMainView mMainView;

    private final ItemsModel mModel;

    private IItemsView mItemsView;


    public ItemsController(IMainView mainView, ItemsModel itemsModel, IUserPreferences userPreferences) {
        mMainView = mainView;
        mModel = itemsModel;
        mUserPreferences = userPreferences;
    }

    public void logout() {
        mUserPreferences.setUserLoggedIn(false);
        mMainView.startLogin();
    }

    public void addNewItem() {
        mMainView.startAddItem();
    }

    public void checkIfUserLoggedIn() {
        if (!mUserPreferences.isUserLoggedIn()){
            mMainView.startLogin();
        }
    }

    public void setItemsView(IItemsView itemsView) {
        this.mItemsView = itemsView;
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
            mItemsView.showModel(mModel);
        }
    }

    public void handlerAddItemResult(int resultCode, Item item) {
        if (resultCode == Activity.RESULT_OK){
            if (item != null){
                mModel.addItem(item);
                mMainView.notifyItemAdded(item);
            }
            refreshItems();
        }
    }
}
