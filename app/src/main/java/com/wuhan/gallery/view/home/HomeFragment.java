package com.wuhan.gallery.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.constant.ImageTypeEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends BaseLazyLoadFragment {
    private TabLayout mTabLayoutView;
    private ViewPager mContentPagerView;

    private String[] mTabTexts = new String[] {"首页","明星", "风景","动漫","剧照" };
    private List<BaseLazyLoadFragment> mFragments = new ArrayList<>();

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onResume() {
        super.onResume();
        mContentPagerView.setCurrentItem(0);
    }

    @Override
    protected void initView(View convertView) {
        mTabLayoutView = convertView.findViewById(R.id.tab_layout_view);
        mContentPagerView = convertView.findViewById(R.id.content_pager_view);

        for (int count=0, length = mTabTexts.length; count<length; count++) {
            mTabLayoutView.addTab(mTabLayoutView.newTab().setText(mTabTexts[count]));
            if (count == 0) {
                mFragments.add(new HostFragment());
            } else {
                ImageTypeEnum imageType = ImageTypeEnum.getOfValue(count);
                int imageTypeValue = imageType==null?0:imageType.getValue();
                BaseLazyLoadFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", imageTypeValue);
                fragment.setArguments(bundle);
                mFragments.add(fragment);
            }
        }

        mContentPagerView.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTexts[position];
            }
        });

        mTabLayoutView.setupWithViewPager(mContentPagerView);
    }
}
