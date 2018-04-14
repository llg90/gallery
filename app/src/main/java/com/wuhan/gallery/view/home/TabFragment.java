package com.wuhan.gallery.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.view.comm.ImageDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends BaseLazyLoadFragment {
    private SmartRefreshLayout mSmartRefreshLayout;
    private List<String> mImageUrls = new ArrayList<>();
    private TabImageAdapter mTabImageAdapter;

    @Override
    protected void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<String> urls = getArguments().getStringArrayList("urls");
            mImageUrls.addAll(urls);
            mTabImageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initView(View convertView) {
        mSmartRefreshLayout    = convertView.findViewById(R.id.refresh_layout);
        GridView imageGridView = convertView.findViewById(R.id.image_grid_view);

        mTabImageAdapter = new TabImageAdapter(this, mImageUrls);
        imageGridView.setAdapter(mTabImageAdapter);
        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> urls = new ArrayList<>();
                urls.add(mImageUrls.get(position));
                Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                intent.putStringArrayListExtra("urls", urls);
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(view,"image"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
            }
        });
    }
}
