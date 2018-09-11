package com.tarashor.mvc_mvp_mvvm_android.items;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;

import java.util.List;

interface IItemsView {
    void showItems(List<Item> items);
}
