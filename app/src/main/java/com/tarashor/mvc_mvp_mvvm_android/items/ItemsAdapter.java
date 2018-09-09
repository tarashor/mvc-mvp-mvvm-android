package com.tarashor.mvc_mvp_mvvm_android.items;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.data.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends ListAdapter<Item, ItemsAdapter.ItemViewHolder> {
    private static final DiffUtil.ItemCallback<Item> diffCallback = new DiffUtil.ItemCallback<Item>(){
        @Override
        public boolean areItemsTheSame(@NonNull Item item, @NonNull Item t1) {
            return item == t1;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item item, @NonNull Item t1) {
            return item.getName().equals(t1.getName());
        }
    };


    public ItemsAdapter() {
        super(diffCallback);
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vh, viewGroup, false);
        return new ItemViewHolder(itemView, (TextView) itemView.findViewById(R.id.item_name_txt));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bindItem(getItem(i));
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameView;
        public ItemViewHolder(@NonNull View itemView, TextView itemNameView) {
            super(itemView);
            this.itemNameView = itemNameView;
        }

        public void bindItem(Item item){
            itemNameView.setText(item.getName());
        }
    }
}
