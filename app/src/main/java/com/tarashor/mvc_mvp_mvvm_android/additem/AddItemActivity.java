package com.tarashor.mvc_mvp_mvvm_android.additem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
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

    private ItemController mController;

    private static final String ITEM_EXTRA = "new_item_extra";
    private EditText mNameEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ItemModel itemModel = new ItemModel(LocalDatasource.getInstance());
        mController = new ItemController(this, itemModel);

        mNameEditText = findViewById(R.id.name_text);
        mNameEditText.setText(itemModel.getItemName());

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.saveItem(getTextNameField());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController = null;
    }

    private String getTextNameField() {
        String itemName = "";
        Editable nameEditable = mNameEditText.getText();
        if (nameEditable != null){
            itemName = nameEditable.toString();
        }
        return itemName;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            mController.cancel();
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

    public static Item parseItem(Intent data) {
        if (data != null){
            return (Item) data.getSerializableExtra(ITEM_EXTRA);
        }
        return null;
    }

    private static Intent createResultData(Item item) {
        Intent intent = new Intent();
        intent.putExtra(ITEM_EXTRA, item);
        return intent;
    }

    @Override
    public void positiveButtonClicked(ItemModel mItemModel) {
        setResult(Activity.RESULT_OK, createResultData(mItemModel.getItem()));
        finish();
    }

    @Override
    public void negativeButtonPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
