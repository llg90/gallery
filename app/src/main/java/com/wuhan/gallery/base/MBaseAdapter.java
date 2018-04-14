package com.wuhan.gallery.base;

import android.support.annotation.NonNull;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author: 李利刚
 * @E-mail: lgzc_work@163.com
 * @date: 2018-04-12 14:26
 * @describe:
 */
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
