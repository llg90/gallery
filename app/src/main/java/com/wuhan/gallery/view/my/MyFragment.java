package com.wuhan.gallery.view.my;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.constant.ImageStatusEnum;
import com.wuhan.gallery.net.NetObserver;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.my.info.UserInfoActivity;
import com.wuhan.gallery.view.my.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyFragment extends BaseLazyLoadFragment {
    private ImageView mUserBackgroundImageView;
    private ImageView mUserIconImageView;
    private TextView  mUserNameTextView;
    private LinearLayout mSetButton;

    private List<ImageBean> mCollectImageData = new ArrayList<>();
    private ImageAdapter mCollectImageAdapter;
    private RecyclerView mCollectRecyclerView;

    private List<ImageBean> mRecordImageData = new ArrayList<>();
    private ImageAdapter mRecordImageAdapter;
    private RecyclerView mRecordRecyclerView;

    @Override
    protected void getData() {
        String backgroundUrl = "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png";
        Glide.with(this).load(backgroundUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mUserBackgroundImageView);
        mUserNameTextView.setText("请登录");

        UserBean userBean = GalleryApplication.getUserBean();
        if (userBean == null) return;

        Glide.with(this).load(backgroundUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mUserBackgroundImageView);

        Glide.with(this)
                .load(SingletonNetServer.sIMAGE_SERVER_HOST + userBean.getIcon())
                .apply(new RequestOptions().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mUserIconImageView);
        mUserNameTextView.setText(userBean.getUsername());

        SingletonNetServer.INSTANCE.getImageServer().getImageBrowse(userBean.getId(), ImageStatusEnum.COLLECTION.getValue())
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

        SingletonNetServer.INSTANCE.getImageServer().getImageBrowse(userBean.getId(), ImageStatusEnum.BROWSE.getValue())
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
        mSetButton = convertView.findViewById(R.id.set_button);

        mCollectRecyclerView = convertView.findViewById(R.id.collect_recycler_view);
        mRecordRecyclerView = convertView.findViewById(R.id.record_recycler_view);

        Drawable decorationDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            decorationDrawable = getResources().getDrawable(R.drawable.my_image_divider, getContext().getTheme());
        } else {
            decorationDrawable = getResources().getDrawable(R.drawable.my_image_divider);
        }
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL);
        itemDecoration.setDrawable(decorationDrawable);


        mCollectImageAdapter = new ImageAdapter(this, mCollectImageData);
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mCollectRecyclerView.addItemDecoration(itemDecoration);
        mCollectRecyclerView.setAdapter(mCollectImageAdapter);

        mRecordImageAdapter = new ImageAdapter(this, mRecordImageData);
        mRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecordRecyclerView.addItemDecoration(itemDecoration);
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

        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SetActivity.class));
            }
        });
    }
}
