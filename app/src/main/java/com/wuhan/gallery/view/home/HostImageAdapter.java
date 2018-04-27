package com.wuhan.gallery.view.home;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.wuhan.gallery.R;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.net.SingletonNetServer;

import java.util.List;

import static com.wuhan.gallery.GalleryApplication.getContext;

class HostImageAdapter extends RecyclerView.Adapter<HostImageAdapter.ViewHolder> {

    private Fragment mFragment;
    private List<ImageBean> mImageUrls;
    private OnItemClickListener mOnClickListener;

    interface  OnItemClickListener {
        void OnItemClick(View itemView, int position);
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    HostImageAdapter(Fragment fragment, List<ImageBean> imageUrls) {
        mFragment = fragment;
        mImageUrls = imageUrls;
    }

    @NonNull
    @Override
    public HostImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_home_host_image, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull HostImageAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(R.id.tag_view_holder_position, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    int position = (int) v.getTag(R.id.tag_view_holder_position);
                    mOnClickListener.OnItemClick(v, position);
                }
            }
        });

        int roundingRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, Resources.getSystem().getDisplayMetrics());
/*        Glide.with(mFragment).load(mImageUrls.get(position))
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundingRadius)))
                .into(holder.mImageView);*/


        String url = SingletonNetServer.sIMAGE_SERVER_HOST + mImageUrls.get(position).getImageurl();
        Glide.with(mFragment).load(url)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundingRadius)))
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mImageUrls == null ? 0 : mImageUrls.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        CheckBox mClickButton;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView     = itemView.findViewById(R.id.image_view);
            mClickButton = itemView.findViewById(R.id.click_button);
        }
    }
}
