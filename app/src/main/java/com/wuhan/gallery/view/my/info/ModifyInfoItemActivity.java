package com.wuhan.gallery.view.my.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ModifyInfoItemActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info_item);
        final int    itemType = getIntent().getIntExtra("type", 0);
        String itemName = getIntent().getStringExtra("name");
        String itemHint = getIntent().getStringExtra("hint");

        TextView itemNameText = findViewById(R.id.item_name_text);
        final EditText itemEditText = findViewById(R.id.item_edit_text);

        itemNameText.setText(itemName);
        itemEditText.setHint(itemHint);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = itemEditText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(ModifyInfoItemActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserBean userBean = GalleryApplication.getUserBean();
                int    id    = userBean.getId();
                String name  = userBean.getUsername();
                String phone = userBean.getTelephone();
                String email = userBean.getEmail();
                switch (itemType) {
                    case 0:name  = text;break;
                    case 1:phone = text;break;
                    case 2:email = text;break;
                }

                LoadingDialog loadingDialog = new LoadingDialog(ModifyInfoItemActivity.this);
                SingletonNetServer.INSTANCE.getUserServer().upUserInfo(id, name, phone, email)
                        .compose(ModifyInfoItemActivity.this.<NetworkDataBean<UserBean>>bindToLifecycle())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new NetObserver<NetworkDataBean<UserBean>>(loadingDialog) {
                            @Override
                            public void onNext(NetworkDataBean<UserBean> userBeanNetworkDataBean) {
                                if (userBeanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                                    UserBean data = userBeanNetworkDataBean.getData();
                                    GalleryApplication.getContext().setUserBean(data);
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
