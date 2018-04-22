package com.wuhan.gallery.view.home;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.MBaseAdapter;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.net.SingletonNetServer;

import java.util.List;

class TabImageAdapter extends MBaseAdapter<ImageBean> {
    private Fragment mFragment;

    TabImageAdapter(Fragment fragment, @NonNull List<ImageBean> datas) {
        super(datas);
        mFragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_home_tab_image, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(R.id.tag_view_holder, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.tag_view_holder);
        }

        String url = SingletonNetServer.sIMAGE_SERVER_HOST + getItem(position).getImageurl();
        Glide.with(mFragment).load(url).into(holder.image);
        return convertView;
    }

    static class ViewHolder {
        ImageView image;

        ViewHolder(View v) {
            image = v.findViewById(R.id.image_view);
        }
    }
}
