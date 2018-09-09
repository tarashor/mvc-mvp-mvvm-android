package com.tarashor.mvc_mvp_mvvm_android.additem;

public class ItemController {
    private final IItemView mItemView;
    private final ItemModel mItemModel;

    public ItemController(IItemView itemView, ItemModel itemModel) {
        mItemView = itemView;
        mItemModel = itemModel;
    }

    public void saveItem(String itemName) {
        mItemModel.setItemName(itemName);
        mItemModel.saveItem();
        mItemView.positiveButtonClicked(mItemModel);
    }

    public void cancel(){
        mItemView.negativeButtonPressed();
    }
}
