package com.tarashor.mvc_mvp_mvvm_android.items;

import android.databinding.DataBindingUtil;
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
import com.tarashor.mvc_mvp_mvvm_android.databinding.FragmentMainBinding;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemsFragment extends Fragment {
    private ItemsViewModel mViewModel;
    private FragmentMainBinding mFragmentMainBinding;


    public ItemsFragment() {
    }

    public void setViewModel(ItemsViewModel itemsViewModel){
        mViewModel = itemsViewModel;
        mFragmentMainBinding.setViewModel(mViewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false);

        mFragmentMainBinding.setViewModel(mViewModel);

        mFragmentMainBinding.itemsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mFragmentMainBinding.itemsList.setAdapter(new ItemsAdapter());

        return mFragmentMainBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.refreshItems();
    }


    public static class SwipeCallback extends ItemTouchHelper.SimpleCallback{

        private RemoveListener mRemoveListener;

        public SwipeCallback(RemoveListener removeListener) {
            super(0, ItemTouchHelper.LEFT);
            mRemoveListener = removeListener;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (mRemoveListener != null){
                mRemoveListener.onRemovedOnPosition(viewHolder.getAdapterPosition());
            }
        }
    }

    public interface RemoveListener{
        void onRemovedOnPosition(int position);
    }
}
