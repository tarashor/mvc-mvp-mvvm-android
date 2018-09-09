package com.tarashor.mvc_mvp_mvvm_android.data;

import com.tarashor.mvc_mvp_mvvm_android.datasource.DatabaseDatasource;

import java.util.ArrayList;
import java.util.List;

public class Dao {
    private static final int N = 10;

    private List<Item> cache;

    private Dao(){
        cache = new ArrayList<>();
        init();
    }

    private static volatile Dao INSTANCE = null;


    public static Dao getInstance() {
        if (INSTANCE == null) {
            synchronized (DatabaseDatasource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Dao();
                }
            }
        }
        return INSTANCE;
    }

    private void init() {
        for (int i = 0; i < N; i++ ){
            Item item = new Item();
            item.setName("Item = " + i);
            cache.add(item);
        }
    }

    public List<Item> getItems() {
        return cache;
    }

    public void insertItem(Item item) {
        cache.add(item);
    }

    public void removeItem(Item removedItem) {
        cache.remove(removedItem);
    }
}
