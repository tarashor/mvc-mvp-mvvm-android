package com.tarashor.mvc_mvp_mvvm_android.items;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;

import java.util.List;

public class BindingAdapters {
    @BindingAdapter("items")
    public static void setItems(RecyclerView listView, List<Item> items) {
        ItemsAdapter adapter = (ItemsAdapter) listView.getAdapter();
        if (adapter != null)
        {
            adapter.submitList(items);
        }
    }

    @BindingAdapter("onRemoveItem")
    public static void setOnRemoveItemListener(RecyclerView recyclerView, ItemsFragment.RemoveListener removeListener) {
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(new ItemsFragment.SwipeCallback(removeListener));
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
