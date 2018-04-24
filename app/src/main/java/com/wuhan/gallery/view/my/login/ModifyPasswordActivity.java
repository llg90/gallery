package com.wuhan.gallery.view.my.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseActivity;

public class ModifyPasswordActivity extends BaseActivity {

    private EditText pwd_ed;
    private EditText repwd_ed;
    private TextView confirm_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        pwd_ed = (EditText)findViewById(R.id.old_password);
        repwd_ed = (EditText)findViewById(R.id.new_password);
        String pwd = pwd_ed.getText().toString();
        String repwd = repwd_ed.getText().toString();

        confirm_tv = (TextView)findViewById(R.id.confirm_repwd_botton);
        confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (GalleryApplication.getUserBean().get.equals(pwd)){
//
//                }
                //else if ()
            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
