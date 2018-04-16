package com.wuhan.gallery.view.record;

import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.MBaseAdapter;

import java.util.List;

class RecyclerGridAdapter extends MBaseAdapter<String> {
    private final static int sDimension10DP = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 10,
            GalleryApplication.getContext().getResources().getDisplayMetrics()
    );

    RecyclerGridAdapter(@NonNull List<String> datas) {
        super(datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_record_grid_image, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(R.id.tag_view_holder, holder);

            int displayWidth = parent.getContext().getResources().getDisplayMetrics().widthPixels;
            int itemWidth = (displayWidth-sDimension10DP*4)/3;
            ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
            layoutParams.height = itemWidth;
            layoutParams.width  = itemWidth;
            holder.mImageView.setLayoutParams(layoutParams);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.tag_view_holder);
        }
        String url = getItem(position);
        Glide.with(parent).load(url).into(holder.mImageView);
        return convertView;
    }

    static class ViewHolder {
        ImageView mImageView;

        public ViewHolder(View v) {
            mImageView = v.findViewById(R.id.image_view);
        }
    }
}
