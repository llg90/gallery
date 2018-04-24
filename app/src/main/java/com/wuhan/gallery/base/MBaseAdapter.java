package com.wuhan.gallery.base;

import android.support.annotation.NonNull;
import android.widget.BaseAdapter;

import java.util.List;


public abstract class MBaseAdapter<T> extends BaseAdapter {
    private List<T> mDatas;

    public MBaseAdapter(@NonNull List<T> datas) {
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
