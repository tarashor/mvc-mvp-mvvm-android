package com.tarashor.mvc_mvp_mvvm_android.items;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.tarashor.mvc_mvp_mvvm_android.additem.AddItemActivity;
import com.tarashor.mvc_mvp_mvvm_android.datasource.LocalDatasource;
import com.tarashor.mvc_mvp_mvvm_android.login.LoginActivity;
import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.datasource.UserPreferences;
import com.tarashor.mvc_mvp_mvvm_android.data.Item;

public class MainActivity extends AppCompatActivity implements IMainView {

    private static final int ADD_ITEM_REQUEST_CODE = 1;

    private ItemsController mController;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ItemsModel itemsModel = new ItemsModel(LocalDatasource.getInstance());
        mController = new ItemsController(this, itemsModel, UserPreferences.getInstance(this));
        mController.checkIfUserLoggedIn();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.addNewItem();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ITEM_REQUEST_CODE){
            mController.handlerAddItemResult(resultCode, AddItemActivity.parseItem(data));
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
            mController.logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController = null;
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
    public ItemsController getController() {
        return mController;
    }

    @Override
    public void notifyItemAdded(Item newItem) {
        Snackbar.make(mFab, newItem.getName(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
