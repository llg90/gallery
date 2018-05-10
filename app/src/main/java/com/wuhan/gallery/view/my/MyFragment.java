package com.wuhan.gallery.view.my;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.constant.ImageStatusEnum;
import com.wuhan.gallery.constant.ImageTypeEnum;
import com.wuhan.gallery.net.NetObserver;
import com.wuhan.gallery.net.SimplifyObserver;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.comm.GlideEngine;
import com.wuhan.gallery.view.comm.ImageDetailsActivity;
import com.wuhan.gallery.view.comm.LoadingDialog;
import com.wuhan.gallery.view.my.info.UserInfoActivity;
import com.wuhan.gallery.view.my.login.LoginActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends BaseLazyLoadFragment {
    //固定背景图
    private ImageView mUserBackgroundImageView;
    //用户头像
    private ImageView mUserIconImageView;
    //用户昵称
    private TextView  mUserNameTextView;
    private LinearLayout mSetBtn;
    private View mUploadBtn;

    private ArrayList<ImageBean> mCollectImageData = new ArrayList<>();
    private ImageAdapter mCollectImageAdapter;
    private RecyclerView mCollectRecyclerView;

    private ArrayList<ImageBean> mRecordImageData = new ArrayList<>();
    private ImageAdapter mRecordImageAdapter;
    private RecyclerView mRecordRecyclerView;

    private LoadingDialog mLoadingDialog;
    private static final int REQUEST_CODE_CHOOSE = 10;
    @Override
    protected void getData() {
        String backgroundUrl = "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png";
        Glide.with(this).load(backgroundUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mUserBackgroundImageView);
        mUserNameTextView.setText("请登录");

        UserBean userBean = GalleryApplication.getUserBean();
        int id = userBean == null ? 0 : userBean.getId();
        if (userBean != null){
            Glide.with(this)
                    .load(SingletonNetServer.sIMAGE_SERVER_HOST + userBean.getIcon())
                    .apply(new RequestOptions().circleCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mUserIconImageView);
            mUserNameTextView.setText(userBean.getUsername());
        }
        else{
            Glide.with(this)
                    .load(R.mipmap.icon_user)
                    .apply(new RequestOptions().circleCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mUserIconImageView);
        }

        SingletonNetServer.INSTANCE.getImageServer().getImageBrowse(id, ImageStatusEnum.COLLECTION.getValue())
                .compose(this.<NetworkDataBean<List<ImageBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<NetworkDataBean<List<ImageBean>>>() {
                    @Override
                    public void onNext(NetworkDataBean<List<ImageBean>> listNetworkDataBean) {
                        if (listNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                            List<ImageBean> data = listNetworkDataBean.getData();
                            mCollectImageData.clear();
                            mCollectImageData.addAll(data);
                            mCollectImageAdapter.notifyDataSetChanged();
                        }
                    }
                });

        SingletonNetServer.INSTANCE.getImageServer().getImageBrowse(id, ImageStatusEnum.BROWSE.getValue())
                .compose(this.<NetworkDataBean<List<ImageBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<NetworkDataBean<List<ImageBean>>>() {
                    @Override
                    public void onNext(NetworkDataBean<List<ImageBean>> listNetworkDataBean) {
                        if (listNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                            List<ImageBean> data = listNetworkDataBean.getData();
                            mRecordImageData.clear();
                            mRecordImageData.addAll(data);
                            mRecordImageAdapter.notifyDataSetChanged();
                        }
                    }
                });


    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View convertView) {
        mUserBackgroundImageView = convertView.findViewById(R.id.user_background_image_view);
        mUserIconImageView = convertView.findViewById(R.id.user_icon_image);
        mUserNameTextView  = convertView.findViewById(R.id.user_name_text);
        mSetBtn = convertView.findViewById(R.id.set_button);
        mUploadBtn = convertView.findViewById(R.id.upload_button);

        mCollectRecyclerView = convertView.findViewById(R.id.collect_recycler_view);
        mRecordRecyclerView = convertView.findViewById(R.id.record_recycler_view);

        //分隔区域
//        Drawable decorationDrawable;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            decorationDrawable = getResources().getDrawable(R.drawable.my_image_divider, getContext().getTheme());
//        } else {
//            decorationDrawable = getResources().getDrawable(R.drawable.my_image_divider);
//        }
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL);
//        itemDecoration.setDrawable(decorationDrawable);


        mCollectImageAdapter = new ImageAdapter(this, mCollectImageData);
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       // mCollectRecyclerView.addItemDecoration(itemDecoration);
        //设置点击监听
        mCollectImageAdapter.setOnClickListener(new ImageAdapter.OnItemClickListener(){

            @Override
            public void OnItemClick(View itemView, int position) {
                Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("images", mCollectImageData);

                //将界面中itemView与新界面元素相关联
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                getActivity(), Pair.create(itemView, "image"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        mCollectRecyclerView.setAdapter(mCollectImageAdapter);

        mRecordImageAdapter = new ImageAdapter(this, mRecordImageData);
        mRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //mRecordRecyclerView.addItemDecoration(itemDecoration);
        mRecordImageAdapter.setOnClickListener(new ImageAdapter.OnItemClickListener(){

            @Override
            public void OnItemClick(View itemView, int position) {
                Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("images", mRecordImageData);

                //将界面中itemView与新界面元素相关联
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                getActivity(), Pair.create(itemView, "image"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        mRecordRecyclerView.setAdapter(mRecordImageAdapter);

        initListener();
    }

    private void initListener() {
        mUserIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = GalleryApplication.getUserBean();
                if (userBean == null) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                }

            }
        });

        mSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SetActivity.class));
            }
        });

        RxView.clicks(mUploadBtn).compose(new RxPermissions(getActivity())
                .ensure(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return aBoolean ? Observable.just(true) : new RxPermissions(getActivity()).request(
                                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    }
                }).subscribe(new SimplifyObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (GalleryApplication.getUserBean() == null)
                        {
                            Toast.makeText(getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
                        }
                        else if (aBoolean){
                            Matisse.from(MyFragment.this)
                                    .choose(MimeType.allOf())
                                    .countable(true)
                                    .maxSelectable(1)
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true,"com.gallery.fileprovider"))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .forResult(REQUEST_CODE_CHOOSE);
                          //  Log.d("getActivity()","竟如金妮妮妮妮妮您地产商你对此苏宁彩电生产商的措施调查");
                        } else {
                            Toast.makeText(getContext(), "需要相机权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> images = Matisse.obtainResult(data);
            if (images != null && !images.isEmpty()) {
                Uri iconUri = images.get(0);
                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cur = getContext().getContentResolver().query(iconUri, projection, null, null, null);
                cur.moveToFirst();

                String path = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DATA));
                File iconFile = new File(path);
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("picture", iconFile.getName(), RequestBody.create(MediaType.parse("image/*"), iconFile));
                builder.addFormDataPart("userid", String.valueOf(GalleryApplication.getUserBean().getId()));
                builder.addFormDataPart("type", String.valueOf(ImageTypeEnum.UserUpload.getValue()));

                if (mLoadingDialog == null){
                    mLoadingDialog = new LoadingDialog(getContext());
                }
                SingletonNetServer.INSTANCE.getImageServer().uploadImage(builder.build())
                        .compose(this.<NetworkDataBean<Boolean>>bindToLifecycle())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new NetObserver<NetworkDataBean<Boolean>>(mLoadingDialog) {

                            @Override
                            public void onNext(NetworkDataBean<Boolean> booleanNetworkDataBean) {
                                if (booleanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)){
                                    Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
}
