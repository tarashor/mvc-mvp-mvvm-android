package com.tarashor.mvc_mvp_mvvm_android.items;

import android.content.Context;
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

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemsFragment extends Fragment implements IItemsView {
    private ItemsController mController;
    private RecyclerView mItemsRecyckerView;
    private ItemsAdapter mAdapter;

    public ItemsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IMainView){
            mController = ((IMainView)context).getController();
            mController.setItemsView(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController.setItemsView(null);
        mController = null;
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
                mController.removeItemByPosition(viewHolder.getAdapterPosition());
            }
        });
        itemTouchhelper.attachToRecyclerView(mItemsRecyckerView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mController.refreshItems();
    }


    @Override
    public void showModel(ItemsModel model) {
        mAdapter.submitList(new ArrayList<>(model.getItems()));
    }
}
