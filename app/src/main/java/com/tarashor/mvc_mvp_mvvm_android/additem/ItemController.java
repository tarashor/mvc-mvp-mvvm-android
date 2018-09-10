package com.tarashor.mvc_mvp_mvvm_android.additem;

public class ItemController {
    private final IItemView mItemView;
    private final ItemModel mItemModel;

    public ItemController(IItemView itemView, ItemModel itemModel) {
        mItemView = itemView;
        mItemModel = itemModel;
    }

    public void saveItem() {
        mItemModel.setItemName(mItemView.getTextNameField());
        mItemView.onPositiveButtonClicked();
    }

    public void cancel(){
        mItemView.onNegativeButtonPressed();
    }

    public ItemModel getModel() {
        return mItemModel;
    }
}
