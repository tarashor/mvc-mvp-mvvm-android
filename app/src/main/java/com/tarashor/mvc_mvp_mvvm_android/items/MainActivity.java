package com.tarashor.mvc_mvp_mvvm_android.items;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.tarashor.mvc_mvp_mvvm_android.additem.AddItemActivity;
import com.tarashor.mvc_mvp_mvvm_android.databinding.ActivityAdditemBinding;
import com.tarashor.mvc_mvp_mvvm_android.databinding.ActivityMainBinding;
import com.tarashor.mvc_mvp_mvvm_android.datasource.LocalDatasource;
import com.tarashor.mvc_mvp_mvvm_android.login.LoginActivity;
import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.datasource.UserPreferences;

public class MainActivity extends AppCompatActivity implements IItemsNavigator {

    private static final int ADD_ITEM_REQUEST_CODE = 1;

    private ItemsViewModel mItemsViewModel;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItemsViewModel = new ItemsViewModel(LocalDatasource.getInstance(), UserPreferences.getInstance(this));
        mItemsViewModel.setItemsNavigator(this);

        mActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mActivityMainBinding.setViewModel(mItemsViewModel);

        setSupportActionBar(mActivityMainBinding.toolbar);

        ItemsFragment itemsFragment = (ItemsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (itemsFragment != null) {
            itemsFragment.setViewModel(mItemsViewModel);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mItemsViewModel.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ITEM_REQUEST_CODE){
            mItemsViewModel.handlerAddItemResult(resultCode, AddItemActivity.parseMessage(data));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mItemsViewModel.logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mItemsViewModel = null;
    }

    public static void start(Context context) {
        if (context != null){
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void startLogin() {
        LoginActivity.start(MainActivity.this);
        finish();
    }

    @Override
    public void startAddItem() {
        AddItemActivity.start(MainActivity.this, ADD_ITEM_REQUEST_CODE);
    }

    @Override
    public void notifyItemAdded(String message) {
        Snackbar.make(mActivityMainBinding.fab, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
