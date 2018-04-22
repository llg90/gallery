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
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.net.NetObserver;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.comm.ImageDetailsActivity;
import com.wuhan.gallery.view.comm.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TabFragment extends BaseLazyLoadFragment {
    private SmartRefreshLayout mSmartRefreshLayout;
    private List<ImageBean> mImageData = new ArrayList<>();
    private TabImageAdapter mTabImageAdapter;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void getData() {
        Bundle bundle = getArguments();
        if (bundle == null) return;
        int imageType = getArguments().getInt("type", 0);
        SingletonNetServer.INSTANCE.getImageServer().getImagesByType(imageType)
                .compose(this.<NetworkDataBean<List<ImageBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<NetworkDataBean<List<ImageBean>>>(mLoadingDialog) {
                    @Override
                    public void onNext(NetworkDataBean<List<ImageBean>> listNetworkDataBean) {
                        if (listNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                            List<ImageBean> data = listNetworkDataBean.getData();
                            mImageData.addAll(data);
                            mTabImageAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initView(View convertView) {
        mLoadingDialog = new LoadingDialog(getContext());
        mSmartRefreshLayout    = convertView.findViewById(R.id.refresh_layout);
        GridView imageGridView = convertView.findViewById(R.id.image_grid_view);

        mTabImageAdapter = new TabImageAdapter(this, mImageData);
        imageGridView.setAdapter(mTabImageAdapter);
        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<ImageBean> imageBeans = new ArrayList<>();
                imageBeans.add(mImageData.get(position));
                Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                intent.putParcelableArrayListExtra("images", imageBeans);
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
