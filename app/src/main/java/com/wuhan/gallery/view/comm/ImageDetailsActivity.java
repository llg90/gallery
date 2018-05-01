package com.wuhan.gallery.view.comm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Url;

public class ImageDetailsActivity extends BaseActivity {

    private static final String SD_PATH = "/sdcard/myapp/pic/";
    private static final String IN_PATH = "/myapp/pic/";


    private int mPosition;
    private List<ImageBean> mImageBeans;
    private SparseArray<View> mCacheView;

    private CheckBox mLikeCheckBox;
    private CheckBox mCollectCheckBox;
 //   private CheckBox mDownloadCheckBox;
    private TextView reserve_tv;

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

        mLikeCheckBox    = findViewById(R.id.likes_button);
        mCollectCheckBox = findViewById(R.id.collect_button);
     // mDownloadCheckBox = findViewById(R.id.download_button);
        reserve_tv = findViewById(R.id.reserve_bnt);

        final ViewPager viewPager = findViewById(R.id.view_pager);
        //为viewpager设置适配器
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageBeans == null ? 0 : mImageBeans.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                ImageView view = (PhotoView) mCacheView.get(position);
                if (view == null) {
                    view = new PhotoView(container.getContext());   //图片点击放缩
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
//                    Glide.with(getBaseContext()).load(url)
//                            .into(view);
                    mCacheView.put(position, view);

                    view.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {
                            //String url = SingletonNetServer.sIMAGE_SERVER_HOST + mImageBeans.get(position).getImageurl();
                            reserve_tv.setVisibility(View.VISIBLE);
                            //Toast.makeText(ImageDetailsActivity.this, "已下载", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

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
//      mDownloadCheckBox.setOnCheckedChangeListener(mOnCheckedChangeListener);
        reserve_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String imageurl = SingletonNetServer.sIMAGE_SERVER_HOST + mImageBeans.get(mSelectPosition).getImageurl();

                Bitmap bitmap = loadBitmapFromView(mCacheView.get(mSelectPosition));
//                if (bitmap == null){
//                    Toast.makeText(ImageDetailsActivity.this, "bitmap_1为空", Toast.LENGTH_SHORT).show();
//                }
                String imageFilePath = saveBitmap(getBaseContext(), bitmap);
                if (imageFilePath != null){
                    Toast.makeText(getApplicationContext(), "图片保存至" + imageFilePath, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "图片保存失败", Toast.LENGTH_SHORT).show();
                }

                reserve_tv.setVisibility(View.INVISIBLE);
            }
        });


        //浏览之后提交浏览状态
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
                } else{
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


    private Bitmap loadBitmapFromView(View v) {

        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        //c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

       // v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + UUID.randomUUID().toString() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

}


//测试bitmap老为空？？？？？

//                    //Bitmap bitmap = (mCacheView.get(viewPager.getCurrentItem())).getDrawingCache(true);
//                    Bitmap bitmap = convertViewToBitmap(mCacheView.get(viewPager.getCurrentItem()));
//                    if (bitmap == null){
//                        Toast.makeText(ImageDetailsActivity.this, "bitmap_1为空", Toast.LENGTH_SHORT).show();
//                    }