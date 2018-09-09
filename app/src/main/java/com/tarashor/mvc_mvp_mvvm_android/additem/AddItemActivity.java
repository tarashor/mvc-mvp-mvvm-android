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

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class AddItemActivity extends AppCompatActivity {

    private static final String ITEM_EXTRA = "new_item_extra";
    private Item mItem  = null;
    private EditText mNameEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mItem == null){
            mItem = new Item();
        }

        mNameEditText = findViewById(R.id.name_text);
        mNameEditText.setText(mItem.getName());

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = "";
                Editable nameEditable = mNameEditText.getText();
                if (nameEditable != null){
                    itemName = nameEditable.toString();
                }
                mItem.setName(itemName);
                setResult(Activity.RESULT_OK, createResultData(mItem));
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            setResult(Activity.RESULT_CANCELED);
            finish();
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

    public static Item getItem(Intent data) {
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
}
