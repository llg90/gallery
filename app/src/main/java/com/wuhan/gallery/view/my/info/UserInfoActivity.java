package com.wuhan.gallery.view.my.info;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseActivity;
import com.wuhan.gallery.base.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.net.NetObserver;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.comm.GlideEngine;
import com.wuhan.gallery.view.my.login.ModifyPasswordActivity;
import com.wuhan.gallery.net.SimplifyObserver;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserInfoActivity extends BaseActivity {
    private ImageView mUserIcon;
    private TextView mNameText;
    private TextView mPhoneText;
    private TextView mEmailText;

    private static final int REQUEST_CODE_CHOOSE = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();

        SingletonNetServer.INSTANCE.getImageServer().getImage("风景")
                .compose(this.<NetworkDataBean<ImageBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<NetworkDataBean<ImageBean>>() {
                    @Override
                    public void onNext(NetworkDataBean<ImageBean> imageBeanNetworkDataBean) {
                        if (imageBeanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                            ImageBean data = imageBeanNetworkDataBean.getData();
                        }
                    }
                });
    }

    private void initView() {
        mUserIcon = findViewById(R.id.user_icon_image);
        mNameText = findViewById(R.id.user_name_text);
        mPhoneText = findViewById(R.id.phone_text);
        mEmailText = findViewById(R.id.email_text);

        UserBean userBean = GalleryApplication.getContext().getUserBean();
        if (userBean != null) {
            String url = SingletonNetServer.sIMAGE_SERVER_HOST + userBean.getIcon();
            Glide.with(this).load(url).apply(new RequestOptions().circleCrop()).into(mUserIcon);

            String name = userBean.getUsername();
            mNameText.setText(name==null?"":name);

            String phone = userBean.getTelephone();
            mPhoneText.setText(phone==null?"":phone);

            String email = userBean.getEmail();
            mEmailText.setText(email==null?"":email);
        }


//        findViewById(R.id.user_icon_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.back_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.user_name_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.phone_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.email_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.password_button).setOnClickListener(mOnClickListener);

        View userIconButton = findViewById(R.id.user_icon_button);
        RxView.clicks(userIconButton)
                .compose(new RxPermissions(this).ensure(
                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return aBoolean ?
                                Observable.just(true) :
                                new RxPermissions(UserInfoActivity.this).request(
                                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                }).subscribe(new SimplifyObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean){
                            Matisse.from(UserInfoActivity.this)
                                    .choose(MimeType.allOf())
                                    .countable(true)
                                    .maxSelectable(1)
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true,"com.gallery.fileprovider"))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .forResult(REQUEST_CODE_CHOOSE);
                        } else {
                            Toast.makeText(UserInfoActivity.this, "需要相机权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> images = Matisse.obtainResult(data);
            if (images != null && !images.isEmpty()) {
                Uri iconUri = images.get(0);
                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cur = managedQuery(iconUri, projection, null, null, null);
                cur.moveToFirst();
                String path = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DATA));
                File iconFile = new File(path);
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("picture", iconFile.getName(), RequestBody.create(MediaType.parse("image/*"), iconFile));
                SingletonNetServer.INSTANCE.getUserServer().upUserIcon(builder.build())
                        .compose(this.<NetworkDataBean<String>>bindToLifecycle())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new NetObserver<NetworkDataBean<String>>() {
                            @Override
                            public void onNext(NetworkDataBean<String> stringNetworkDataBean) {
                                if (stringNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                                    String url = SingletonNetServer.sIMAGE_SERVER_HOST + stringNetworkDataBean.getData();
                                    Glide.with(UserInfoActivity.this).load(url)
                                            .apply(new RequestOptions().circleCrop()).into(mUserIcon);

                                    UserBean userBean = GalleryApplication.getContext().getUserBean();
                                    userBean.setIcon(stringNetworkDataBean.getData());
                                }
                            }
                        });
            }
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.back_button:
                    finish();
                    break;
                case R.id.user_name_button:
                    intent.setClass(UserInfoActivity.this, ModifyInfoItemActivity.class);
                    intent.putExtra("name", "用户名：");
                    intent.putExtra("hint", mNameText.getText().toString());
                    startActivity(intent);
                    break;
                case R.id.phone_button:
                    intent.setClass(UserInfoActivity.this, ModifyInfoItemActivity.class);
                    intent.putExtra("name", "电话号码：");
                    intent.putExtra("hint", mPhoneText.getText().toString());
                    startActivity(intent);
                    break;
                case R.id.email_button:
                    intent.setClass(UserInfoActivity.this, ModifyInfoItemActivity.class);
                    intent.putExtra("name", "邮箱：");
                    intent.putExtra("hint", mEmailText.getText().toString());
                    startActivity(intent);
                    break;
                case R.id.password_button:
                    intent.setClass(UserInfoActivity.this, ModifyPasswordActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };


}
