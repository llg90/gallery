package com.wuhan.gallery.view.my;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wuhan.gallery.R;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.comm.ImageDetailsActivity;

import java.util.List;

class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Fragment mFragment;
    private List<ImageBean> urls;

    private OnItemClickListener mOnClickListener;
    interface  OnItemClickListener {
        void OnItemClick(View itemView, int position);
    }
    public void setOnClickListener(OnItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }


    ImageAdapter(Fragment fragment, @NonNull List<ImageBean> urls) {
        mFragment = fragment;
        this.urls = urls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_my_image, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = SingletonNetServer.sIMAGE_SERVER_HOST + urls.get(position).getImageurl();
        Glide.with(mFragment).load(url).into(holder.mImageView);
       // Glide.with(mFragment).load(url).into((ImageView) holder.itemView);

        holder.itemView.setTag(R.id.tag_view_position, position);
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int position = (int) v.getTag(R.id.tag_view_position);

                mOnClickListener.OnItemClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.fragment_my_imageView);
        }
    }
}
