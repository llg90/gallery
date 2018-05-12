package com.wuhan.gallery.view.my.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.wuhan.gallery.view.comm.LoadingDialog;
import com.wuhan.gallery.view.my.login.LoginActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ModifyPasswordActivity extends BaseActivity {

    private EditText pwd_ed;
    private EditText repwd_ed;
    private TextView confirm_tv;

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        pwd_ed = (EditText)findViewById(R.id.old_password);
        repwd_ed = (EditText)findViewById(R.id.new_password);

        confirm_tv = (TextView)findViewById(R.id.confirm_repwd_botton);
        confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = pwd_ed.getText().toString();
                String repwd = repwd_ed.getText().toString();
                String username = GalleryApplication.getUserBean().getUsername().toString();
                SingletonNetServer.INSTANCE.getUserServer().pwdUpdate(username, pwd, repwd)
                        .compose(ModifyPasswordActivity.this.<NetworkDataBean<UserBean>>bindToLifecycle())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new NetObserver<NetworkDataBean<UserBean>>(mLoadingDialog) {
                            @Override
                            public void onNext(NetworkDataBean<UserBean> userBeanNetworkDataBean) {
                                if (userBeanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                                    UserBean data = userBeanNetworkDataBean.getData();
                                    GalleryApplication.getContext().setUserBean(data);
                                    Toast.makeText(ModifyPasswordActivity.this,
                                            userBeanNetworkDataBean.getMessage(),Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                                else{
                                    Toast.makeText(ModifyPasswordActivity.this,
                                            userBeanNetworkDataBean.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
