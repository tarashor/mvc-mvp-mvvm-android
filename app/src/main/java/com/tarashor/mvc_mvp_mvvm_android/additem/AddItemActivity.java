package com.tarashor.mvc_mvp_mvvm_android.additem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.tarashor.mvc_mvp_mvvm_android.R;
import com.tarashor.mvc_mvp_mvvm_android.data.Item;
import com.tarashor.mvc_mvp_mvvm_android.datasource.LocalDatasource;


public class AddItemActivity extends AppCompatActivity implements IItemView {

    private ItemPresenter mPresenter;

    private static final String NEW_ITEM_MESSAGE_EXTRA = "new_item_extra";
    private EditText mNameEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNameEditText = findViewById(R.id.name_text);

        mPresenter = new ItemPresenter(LocalDatasource.getInstance());
        mPresenter.setItemView(this);

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveItem();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            mPresenter.cancel();
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
    public String getTextNameField() {
        String itemName = "";
        Editable nameEditable = mNameEditText.getText();
        if (nameEditable != null){
            itemName = nameEditable.toString();
        }
        return itemName;
    }

    @Override
    public void setTextFieldName(String name) {
        mNameEditText.setText(name);
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
