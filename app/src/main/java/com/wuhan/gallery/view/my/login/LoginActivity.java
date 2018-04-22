package com.wuhan.gallery.view.my.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseActivity;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.net.NetObserver;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.MainActivity;
import com.wuhan.gallery.view.comm.LoadingDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {
    private EditText mUserNameEt;
    private EditText mPasswordEt;

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mUserNameEt = findViewById(R.id.user_name_ed);
        mPasswordEt = findViewById(R.id.user_password_ed);
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        findViewById(R.id.register_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void initView() {
        findViewById(R.id.loginback_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void login() {
        String name = mUserNameEt.getText().toString();
        String password = mPasswordEt.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("gallery", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("password", password);
            editor.apply();
            editor.commit();

            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this);
            }
            SingletonNetServer.INSTANCE.getUserServer().login(name, password)
                    .compose(this.<NetworkDataBean<UserBean>>bindToLifecycle())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NetObserver<NetworkDataBean<UserBean>>(mLoadingDialog) {
                        @Override
                        public void onNext(NetworkDataBean<UserBean> userBeanNetworkDataBean) {
                            if (userBeanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                                UserBean data = userBeanNetworkDataBean.getData();
                                GalleryApplication.getContext().setUserBean(data);
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, userBeanNetworkDataBean.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
