package com.wuhan.gallery.view.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.constant.ImageTypeEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;


public class HomeFragment extends BaseLazyLoadFragment {
    private TabLayout mTabLayoutView;
    private ViewPager mContentPagerView;

    //ImageTypeEnum调用数据
    EnumSet<ImageTypeEnum> typeEnums = EnumSet.allOf(ImageTypeEnum.class);
    private String[] mTabTexts = new String[typeEnums.size() + 1];     // {"首页","明星", "风景","动漫","剧照","用户专区"};
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
      //  mContentPagerView.setCurrentItem(0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mContentPagerView.setCurrentItem(0);
    }

    private void initTabText() {
        int i = 1;
        mTabTexts[0] = "首页";
        for (ImageTypeEnum type : typeEnums){
            mTabTexts[i] = type.getName().toString();
            i++;
        }
    }

    @Override
    protected void initView(View contentView) {
        initTabText();
        mTabLayoutView = contentView.findViewById(R.id.tab_layout_view);
        mContentPagerView = contentView.findViewById(R.id.content_pager_view);

        for (int count = 0, length = mTabTexts.length; count < length; count++) {
            mTabLayoutView.addTab(mTabLayoutView.newTab().setText(mTabTexts[count]));
            if (count == 0) {
                mFragments.add(new HostFragment());
            } else {
                ImageTypeEnum imageType = ImageTypeEnum.getOfValue(count);
                int imageTypeValue = imageType == null ? 0 : imageType.getValue();
                BaseLazyLoadFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", imageTypeValue);
                fragment.setArguments(bundle);
                mFragments.add(fragment);
            }
        }

        //为ViewPager设置适配器
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

        //将TagLayout与ViewPager相关联
        mTabLayoutView.setupWithViewPager(mContentPagerView);
    }
}
