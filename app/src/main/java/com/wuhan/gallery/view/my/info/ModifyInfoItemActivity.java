package com.wuhan.gallery.view.my.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseActivity;

public class ModifyInfoItemActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info_item);
        String itemName = getIntent().getStringExtra("name");
        String itemHint = getIntent().getStringExtra("hint");

        TextView itemNameText = findViewById(R.id.item_name_text);
        EditText itemEditText = findViewById(R.id.item_edit_text);

        itemNameText.setText(itemName);
        itemEditText.setHint(itemHint);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
