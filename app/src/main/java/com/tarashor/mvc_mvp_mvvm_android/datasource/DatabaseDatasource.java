package com.tarashor.mvc_mvp_mvvm_android.datasource;

import com.tarashor.mvc_mvp_mvvm_android.data.Item;

public class DatabaseDatasource implements DataSource {

    private static volatile DatabaseDatasource INSTANCE;
    private final Dao mDao;


    // Prevent direct instantiation.
    private DatabaseDatasource() {
        mDao = Dao.getInstance();
    }

    public static DatabaseDatasource getInstance() {
        if (INSTANCE == null) {
            synchronized (DatabaseDatasource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DatabaseDatasource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getItems(LoadItemsCallback callback) {
        if (callback != null) {
            if (mDao != null && mDao.getItems() != null) {
                callback.onTasksLoaded(mDao.getItems());
            } else {
                callback.onDataNotAvailable();
            }
        }
    }

    @Override
    public void saveItem(Item item) {
        if (mDao != null){
            mDao.insertItem(item);
        }
    }

    @Override
    public void removeItem(Item removedItem) {
        if (mDao != null){
            mDao.removeItem(removedItem);
        }
    }
}
