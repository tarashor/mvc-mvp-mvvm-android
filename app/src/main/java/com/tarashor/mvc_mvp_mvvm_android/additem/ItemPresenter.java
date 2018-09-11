package com.tarashor.mvc_mvp_mvvm_android.additem;

import com.tarashor.mvc_mvp_mvvm_android.datasource.LocalDatasource;

public class ItemPresenter {
    private IItemView mItemView;
    private final ItemModel mItemModel;

    public ItemPresenter(LocalDatasource dataSource) {
        mItemModel = new ItemModel(dataSource);
    }

    public void setItemView(IItemView itemView){
        mItemView = itemView;
    }

    public void start(){
        if (mItemView == null) return;
        if (!mItemModel.isNewItem()) {
            mItemView.setTextFieldName(mItemModel.getItem().getName());
        }
    }

    public void saveItem() {
        mItemModel.setItemName(mItemView.getTextNameField());
        mItemModel.saveItem();
        mItemView.onPositiveButtonClicked("New item added: " + mItemModel.getItem().getName());
    }

    public void cancel(){
        mItemView.onNegativeButtonPressed();
    }
}
