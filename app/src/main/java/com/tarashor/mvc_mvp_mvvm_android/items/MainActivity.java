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
import com.tarashor.mvc_mvp_mvvm_android.login.LoginActivity;
import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.UserPreferences;
import com.tarashor.mvc_mvp_mvvm_android.data.Item;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_ITEM_REQUEST_CODE = 1;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!UserPreferences.getInstance(this).isUserLoggedIn()){
            LoginActivity.start(this);
            finish();
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemActivity.start(MainActivity.this, ADD_ITEM_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ITEM_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                Item newItem = AddItemActivity.getItem(data);
                MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                mainActivityFragment.addItem(newItem);
                Snackbar.make(mFab, newItem.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
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
            UserPreferences.getInstance(MainActivity.this).setUserLoggedIn(false);
            LoginActivity.start(MainActivity.this);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context) {
        if (context != null){
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
}
