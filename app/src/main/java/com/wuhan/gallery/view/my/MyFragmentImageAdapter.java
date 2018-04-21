package com.wuhan.gallery.view.my;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wuhan.gallery.R;

import java.util.List;

public class MyFragmentImageAdapter extends RecyclerView.Adapter {
    private Fragment mFragment;
    private List<String> urls;

    public MyFragmentImageAdapter(Fragment fragment, @NonNull List<String> urls) {
        mFragment = fragment;
        this.urls = urls;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_my_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String url = urls.get(position);
        Glide.with(mFragment).load(url).into((ImageView) holder.itemView);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
