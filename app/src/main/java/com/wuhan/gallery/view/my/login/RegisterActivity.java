package com.wuhan.gallery.view.my.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseActivity;

public class RegisterActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
