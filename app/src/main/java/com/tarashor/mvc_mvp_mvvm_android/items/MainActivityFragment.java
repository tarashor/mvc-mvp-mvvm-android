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
import com.tarashor.mvc_mvp_mvvm_android.datasource.DataSource;
import com.tarashor.mvc_mvp_mvvm_android.datasource.DatabaseDatasource;
import com.tarashor.mvc_mvp_mvvm_android.items.ItemsAdapter;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private RecyclerView mItemsView;
    private ItemsAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mItemsView = rootView.findViewById(R.id.items_list);
        mItemsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ItemsAdapter();
        mItemsView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Item removedItem = mAdapter.removeItemByPosition(viewHolder.getAdapterPosition());
                DatabaseDatasource.getInstance().removeItem(removedItem);
            }
        });
        itemTouchhelper.attachToRecyclerView(mItemsView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseDatasource.getInstance().getItems(new DataSource.LoadItemsCallback() {
            @Override
            public void onTasksLoaded(List<Item> items) {
                mAdapter.refreshItems(items);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void addItem(Item newItem) {
        DatabaseDatasource.getInstance().saveItem(newItem);
        mAdapter.addItem(newItem);
    }
}
