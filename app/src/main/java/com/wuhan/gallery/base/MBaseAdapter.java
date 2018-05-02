package com.wuhan.gallery.base;

import android.support.annotation.NonNull;
import android.widget.BaseAdapter;

import java.util.List;


//数据类型适配器
public abstract class MBaseAdapter<T> extends BaseAdapter {
    private List<T> mDatas;

    public MBaseAdapter(@NonNull List<T> datas) {
        mDatas = datas;
    }


    //适配器中数据集的数据个数
    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    //获取数据集中与索引对应的数据项
    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }


    //获取指定行的对应ID
    @Override
    public long getItemId(int position) {
        return position;
    }

}
