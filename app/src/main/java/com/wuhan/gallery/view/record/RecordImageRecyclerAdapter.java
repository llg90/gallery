package com.wuhan.gallery.view.record;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.RecordImageBean;

import java.util.List;

public class RecordImageRecyclerAdapter extends RecyclerView.Adapter {
    private List<RecordImageBean> mRecordImageBeans;
    private final static int sDimension10DP = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 10,
            GalleryApplication.getContext().getResources().getDisplayMetrics()
    );

    public RecordImageRecyclerAdapter(@NonNull List<RecordImageBean> list) {
        this.mRecordImageBeans = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            return new TextViewHolder(
                    LayoutInflater.from(parent.getContext()).
                            inflate(R.layout.recycler_record_text, parent, false)
            );
        } else {
            return new ImageGridViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recycler_record_grid, parent, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).textView.setText(mRecordImageBeans.get(position).getTime());
        } else if (holder instanceof ImageGridViewHolder) {
            List<ImageBean> urls = mRecordImageBeans.get(position).getUrls();
            GridView gridView = ((ImageGridViewHolder) holder).mGridView;
            int displayWidth = gridView.getContext().getResources().getDisplayMetrics().widthPixels;
            int lineNumber = urls.size()%3==0?urls.size()/3:urls.size()/3+1;
            int itemWidth = (displayWidth-sDimension10DP*4)/3;
            int gridHeight = itemWidth*lineNumber + sDimension10DP*(lineNumber+1);
            ViewGroup.LayoutParams lp = gridView.getLayoutParams();
            lp.height = gridHeight;
            gridView.setLayoutParams(lp);

            RecyclerGridAdapter adapter = new RecyclerGridAdapter(urls);
            gridView.setAdapter(adapter);
        } else {
            throw new NullPointerException("no recycler view type");
        }
    }

    @Override
    public int getItemCount() {
        return mRecordImageBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mRecordImageBeans.get(position).getType();
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        TextViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

    static class ImageGridViewHolder extends RecyclerView.ViewHolder {
        GridView mGridView;

        ImageGridViewHolder(View itemView) {
            super(itemView);
            mGridView = itemView.findViewById(R.id.image_grid_view);
        }
    }
}
