package com.wuhan.gallery.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liligang on 2018/3/25.
 */

public abstract class BaseLazyLoadFragment extends BaseFragment {
    protected boolean isVisible = false;    //当前Fragment是否可见
    private boolean isPrepared = false;     //是否与View建立起映射关系
    private boolean isFirst = true; //用是否是第一次加载数据

    private View mConvertView;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mConvertView == null) {
            mConvertView = inflater.inflate(getLayoutId(), container, false);
        }
        initView(mConvertView);
        isPrepared = true;
        lazyLoadData();
        return mConvertView;
    }

    protected void lazyLoadData() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        getData();
        isFirst = false;
    }

    protected abstract void getData();
    protected abstract @LayoutRes int getLayoutId();
    protected abstract void initView(View convertView);
}
