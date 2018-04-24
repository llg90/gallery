package com.wuhan.gallery.view.comm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseActivity;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.constant.ImageStatusEnum;
import com.wuhan.gallery.net.NetObserver;
import com.wuhan.gallery.net.SingletonNetServer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ImageDetailsActivity extends BaseActivity {
    private int mPosition;
    private List<ImageBean> mImageBeans;
    private SparseArray<View> mCacheView;

    private CheckBox mLikeCheckBox;
    private CheckBox mCollectCheckBox;

    private int mSelectPosition;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        Intent intent = getIntent();
        if (intent != null) {
            mPosition = intent.getIntExtra("position", 0);
            mImageBeans = intent.getParcelableArrayListExtra("images");
            mCacheView = new SparseArray<>(mImageBeans.size());
        } else {
            finish();
        }

        ViewPager viewPager = findViewById(R.id.view_pager);
        mLikeCheckBox    = findViewById(R.id.likes_button);
        mCollectCheckBox = findViewById(R.id.collect_button);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageBeans==null?0:mImageBeans.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView view = (PhotoView) mCacheView.get(position);
                if (view == null) {
                    view = new PhotoView(container.getContext());
                    view.setScaleType(ImageView.ScaleType.CENTER);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                finishAfterTransition();            //点击图片周围，退回
                            } else {
                                finish();
                            }
                        }
                    });

                    String url = SingletonNetServer.sIMAGE_SERVER_HOST + mImageBeans.get(position).getImageurl();
                    Picasso.get().load(url).into(view);
                    mCacheView.put(position, view);
                }
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });

        mSelectPosition = mPosition;
        viewPager.setCurrentItem(mPosition);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mLikeCheckBox.setChecked(false);
                mCollectCheckBox.setChecked(false);
                mSelectPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mLoadingDialog = new LoadingDialog(this);
        mLikeCheckBox.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mCollectCheckBox.setOnCheckedChangeListener(mOnCheckedChangeListener);

        UserBean userBean = GalleryApplication.getUserBean();
        if (userBean != null) {
            int userId  = userBean.getId();
            int imageId = mImageBeans.get(mSelectPosition).getId();
            int status = ImageStatusEnum.BROWSE.getValue();
            SingletonNetServer.INSTANCE.getImageServer().setImageStatus(userId, imageId, status)
                    .compose(ImageDetailsActivity.this.<NetworkDataBean<Boolean>>bindToLifecycle())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NetObserver<NetworkDataBean<Boolean>>(mLoadingDialog) {

                        @Override
                        public void onNext(NetworkDataBean<Boolean> booleanNetworkDataBean) {
                            if (!booleanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                                Toast.makeText(ImageDetailsActivity.this, booleanNetworkDataBean.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                UserBean userBean = GalleryApplication.getUserBean();
                if (userBean == null) {
                    buttonView.setChecked(false);
                    Toast.makeText(ImageDetailsActivity.this, "你还未登录", Toast.LENGTH_SHORT).show();
                } else {
                    int userId  = userBean.getId();
                    int imageId = mImageBeans.get(mSelectPosition).getId();
                    int status = buttonView.getId() ==
                            R.id.likes_button? ImageStatusEnum.CLICK.getValue():ImageStatusEnum.COLLECTION.getValue();
                    SingletonNetServer.INSTANCE.getImageServer().setImageStatus(userId, imageId, status)
                            .compose(ImageDetailsActivity.this.<NetworkDataBean<Boolean>>bindToLifecycle())
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new NetObserver<NetworkDataBean<Boolean>>(mLoadingDialog) {

                                @Override
                                public void onNext(NetworkDataBean<Boolean> booleanNetworkDataBean) {
                                    if (!booleanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                                        Toast.makeText(ImageDetailsActivity.this, booleanNetworkDataBean.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        }
    };
}
