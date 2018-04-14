package com.wuhan.gallery.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseFragment;
import com.wuhan.gallery.base.BaseFragmentActivity;
import com.wuhan.gallery.view.home.HomeFragment;
import com.wuhan.gallery.view.my.MyFragment;
import com.wuhan.gallery.view.record.RecordFragment;

public class MainActivity extends BaseFragmentActivity {
    private BaseFragment mSelectedFragment;
    private BaseFragment[] mFragments = new BaseFragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContentFragment();
        initBottomBar();
    }

    private void initBottomBar() {
        BottomBar bottomBar = findViewById(R.id.bottom_bar_view);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                int position = 0;
                switch (tabId) {
                    case R.id.menu_home:  position = 0;break;
                    case R.id.menu_record:position = 1;break;
                    case R.id.menu_my:    position = 2;break;
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(mSelectedFragment);
                fragmentTransaction.show(mFragments[position]);
                fragmentTransaction.commit();
                mSelectedFragment = mFragments[position];
            }
        });
    }

    private void initContentFragment() {
        mFragments[0] = new HomeFragment();
        mFragments[1] = new RecordFragment();
        mFragments[2] = new MyFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content_view, mFragments[0]);
        fragmentTransaction.add(R.id.frame_content_view, mFragments[1]);
        fragmentTransaction.add(R.id.frame_content_view, mFragments[2]);
        fragmentTransaction.show(mFragments[0]);
        fragmentTransaction.hide(mFragments[1]);
        fragmentTransaction.hide(mFragments[2]);
        fragmentTransaction.commit();
        mSelectedFragment = mFragments[0];
    }

}
