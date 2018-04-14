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

import java.util.List;

/**
 * @author: 李利刚
 * @E-mail: lgzc_work@163.com
 * @date: 2018-04-12 14:25
 * @describe:
 */
class TabImageAdapter extends MBaseAdapter<String> {
    private Fragment mFragment;

    TabImageAdapter(Fragment fragment, @NonNull List<String> datas) {
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

        String url = getItem(position);
        Glide.with(mFragment).load(url).into(holder.image);
        return convertView;
    }

    static class ViewHolder {
        ImageView image;

        public ViewHolder(View v) {
            image = v.findViewById(R.id.image_view);
        }
    }
}
