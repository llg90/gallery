package com.wuhan.gallery.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseFragment;
import com.wuhan.gallery.base.BaseFragmentActivity;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.net.NetObserver;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.home.HomeFragment;
import com.wuhan.gallery.view.my.MyFragment;
import com.wuhan.gallery.view.my.login.LoginActivity;
import com.wuhan.gallery.view.record.RecordFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseFragmentActivity {
    private BaseFragment mSelectedFragment;
    private BaseFragment[] mFragments = new BaseFragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContentFragment();
        initBottomBar();
        login();
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
                mSelectedFragment.setUserVisibleHint(false);
                mFragments[position].setUserVisibleHint(true);
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

    private void login() {
        SharedPreferences sharedPreferences = getSharedPreferences("gallery", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", null);
        String pass = sharedPreferences.getString("password", null);

        if (name != null && pass != null) {
            SingletonNetServer.INSTANCE.getUserServer().login(name, pass)
                    .compose(this.<NetworkDataBean<UserBean>>bindToLifecycle())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NetObserver<NetworkDataBean<UserBean>>() {
                        @Override
                        public void onNext(NetworkDataBean<UserBean> userBeanNetworkDataBean) {
                            if (userBeanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                                UserBean data = userBeanNetworkDataBean.getData();
                                GalleryApplication.setUserBean(data);
                            } else {
                                Toast.makeText(MainActivity.this, userBeanNetworkDataBean.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
