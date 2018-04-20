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
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseActivity;
import com.wuhan.gallery.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

public class ImageDetailsActivity extends BaseActivity {
    private int mPosition;
    private List<ImageBean> mImageBeans;
    private ArrayList<String> mUrls;
    private SparseArray<View> mCacheView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        Intent intent = getIntent();
        if (intent != null) {
            mPosition = intent.getIntExtra("position", 0);
            mUrls = intent.getStringArrayListExtra("urls");
            mImageBeans = intent.getParcelableArrayListExtra("images");
            mCacheView = new SparseArray<>(mUrls.size());
        } else {
            finish();
        }

        ViewPager viewPager = findViewById(R.id.view_pager);
//        CirclePageIndicator indicator = findViewById(R.id.indicator);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mUrls==null?0:mUrls.size();
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
                                finishAfterTransition();
                            } else {
                                finish();
                            }
                        }
                    });
                    Picasso.get().load(mUrls.get(position)).into(view);
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

/*        if (mUrls.isEmpty() || mUrls.size() == 1) {
            indicator.setVisibility(View.GONE);
        } else {
            indicator.setVisibility(View.VISIBLE);
            indicator.setViewPager(viewPager);
        }*/
        viewPager.setCurrentItem(mPosition);
    }
}
