package com.tarashor.mvc_mvp_mvvm_android.items;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.data.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemsFragment extends Fragment implements IItemsView {
    private ItemsPresenter mPresenter;
    private RecyclerView mItemsRecyckerView;
    private ItemsAdapter mAdapter;

    public ItemsFragment() {
    }

    public void setPresenter(ItemsPresenter itemsPresenter){
        mPresenter = itemsPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mItemsRecyckerView = rootView.findViewById(R.id.items_list);
        mItemsRecyckerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ItemsAdapter();
        mItemsRecyckerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mPresenter.removeItemByPosition(viewHolder.getAdapterPosition());
            }
        });
        itemTouchhelper.attachToRecyclerView(mItemsRecyckerView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refreshItems();
    }


    @Override
    public void showItems(List<Item> items) {
        mAdapter.submitList(items);
    }
}
