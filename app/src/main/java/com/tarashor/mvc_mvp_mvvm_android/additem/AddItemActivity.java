package com.tarashor.mvc_mvp_mvvm_android.additem;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.databinding.ActivityAdditemBinding;
import com.tarashor.mvc_mvp_mvvm_android.datasource.LocalDatasource;


public class AddItemActivity extends AppCompatActivity implements IItemNavigator {

    private static final String NEW_ITEM_MESSAGE_EXTRA = "new_item_extra";
    private AddItemViewModel mAddItemViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAdditemBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_additem);
        mAddItemViewModel = new AddItemViewModel(LocalDatasource.getInstance(), this);
        binding.setViewModel(mAddItemViewModel);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddItemViewModel.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            mAddItemViewModel.cancel();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Activity activity, int requestCode){
        if (activity != null){
            Intent intent = new Intent(activity, AddItemActivity.class);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    private static Intent createResultData(String messageToShow) {
        Intent intent = new Intent();
        intent.putExtra(NEW_ITEM_MESSAGE_EXTRA, messageToShow);
        return intent;
    }

    @Override
    public void onPositiveButtonClicked(String messageToShow) {
        setResult(Activity.RESULT_OK, createResultData(messageToShow));
        finish();
    }

    @Override
    public void onNegativeButtonPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public static String parseMessage(Intent data) {
        if (data != null){
            return data.getStringExtra(NEW_ITEM_MESSAGE_EXTRA);
        }
        return null;
    }
}
