package com.wuhan.gallery.view.my.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.wuhan.gallery.view.my.MyFragment;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity {
    private EditText usernameEt;
    private EditText emailEt;
    private EditText telephoneEt;
    private EditText passwordEt;
    private EditText repasswordEt;
    private TextView registerBtn;

    private LoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        usernameEt = (EditText)findViewById(R.id.user_name);
        emailEt = (EditText)findViewById(R.id.user_email);
        telephoneEt = (EditText)findViewById(R.id.user_telephone);
        passwordEt = (EditText)findViewById(R.id.user_password);
        repasswordEt = (EditText)findViewById(R.id.user_re_password);
        registerBtn = (TextView) findViewById(R.id.user_registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void initView() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void register(){
        String name = usernameEt.getText().toString();
        String email = emailEt.getText().toString();
        String telephone = telephoneEt.getText().toString();
        String pwd = passwordEt.getText().toString();
        String repwd = repasswordEt.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
        } else if(!pwd.equals(repwd)){
            Toast.makeText(this, "两次密码不符", Toast.LENGTH_SHORT).show();
        }else{
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this);
            }

            SingletonNetServer.INSTANCE.getUserServer().register(name, pwd, email, telephone)
                    .compose(this.<NetworkDataBean<Boolean>>bindToLifecycle())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NetObserver<NetworkDataBean<Boolean>>(mLoadingDialog){

                        @Override
                        public void onNext(NetworkDataBean<Boolean> booleanNetworkDataBean) {
                            if (booleanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)){
                                Toast.makeText(RegisterActivity.this, booleanNetworkDataBean.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, booleanNetworkDataBean.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
