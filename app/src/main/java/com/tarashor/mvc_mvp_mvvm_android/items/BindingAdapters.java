package com.tarashor.mvc_mvp_mvvm_android.items;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;

import java.util.ArrayList;
import java.util.List;

public class BindingAdapters {
    public static final int EMPTY_STRING_ID = 0;

    @BindingAdapter("items")
    public static void setItems(RecyclerView listView, List<Item> items) {
        ItemsAdapter adapter = (ItemsAdapter) listView.getAdapter();
        if (adapter != null)
        {
            adapter.submitList(new ArrayList<>(items));
        }
    }

    @BindingAdapter("contacts")
    public static void setItems(AutoCompleteTextView view, List<String> items) {
        if (view.getAdapter() instanceof ArrayAdapter) {
            ArrayAdapter<? super String> adapter = (ArrayAdapter<? super String>) view.getAdapter();
            if (adapter != null) {
                adapter.clear();
                if (items != null) adapter.addAll(items);
            }
        }
    }

    @BindingAdapter("onRemoveItem")
    public static void setOnRemoveItemListener(RecyclerView recyclerView, ItemsFragment.RemoveListener removeListener) {
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(new ItemsFragment.SwipeCallback(removeListener));
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @BindingAdapter("errorText")
    public static void setErrorMessage(EditText view, int errorMessageId) {
        String errorMessage = null;
        if (errorMessageId != EMPTY_STRING_ID){
            errorMessage = view.getContext().getString(errorMessageId);
            view.requestFocus();
        }
        view.setError(errorMessage);
    }
}
