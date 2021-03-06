package com.wuhan.gallery.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseLazyLoadFragment extends BaseFragment {
    protected boolean isVisible = false;    //当前Fragment是否可见
    private boolean isPrepared = false;     //是否与View已经建立起映射关系
    private boolean isFirst = true; //用是否是第一次加载数据

    private View mContentView;

    //设定可见/不可见的状态
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser){
            isVisible = true;
            lazyLoadData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), container, false);
        }
        initView(mContentView);
        isPrepared = true;
        lazyLoadData();
        return mContentView;
    }

    protected void lazyLoadData() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }//已经加载布局文件、可见、第一次加载数据
        getData();                  //完成第一次加载
        isFirst = false;
    }

    //加载要显示的数据
    protected abstract void getData();
    //让布局中的view与fragment中的变量建立起映射
    protected abstract @LayoutRes int getLayoutId();
    //加载布局文件
    protected abstract void initView(View contentView);
}







