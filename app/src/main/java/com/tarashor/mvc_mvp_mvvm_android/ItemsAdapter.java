package com.tarashor.mvc_mvp_mvvm_android;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private List<Item> mItems = new ArrayList<>();

    public void refreshItems(List<? extends Item> items){
        mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Item item){
        mItems.add(item);
        notifyItemInserted(mItems.size()-1);
    }

    public Item removeItemByPosition(int position) {
        Item removedItem = mItems.remove(position);
        notifyItemRemoved(position);
        return removedItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vh, viewGroup, false);
        return new ItemViewHolder(itemView, (TextView) itemView.findViewById(R.id.item_name_txt));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bindItem(mItems.get(i));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
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
