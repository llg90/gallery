package com.wuhan.gallery.view.record;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.RecordImageBean;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.constant.ImageStatusEnum;
import com.wuhan.gallery.net.NetObserver;
import com.wuhan.gallery.net.SingletonNetServer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RecordFragment extends BaseLazyLoadFragment {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mImageRecyclerView;

    //存放数据源
    private List<RecordImageBean> mRecordImageBeans = new ArrayList<>();
    private RecordImageRecyclerAdapter mRecordImageRecyclerAdapter;

    @Override
    protected void getData() {
        UserBean userBean = GalleryApplication.getUserBean();
        if (userBean == null) return;

        SingletonNetServer.INSTANCE.getImageServer().getImageBrowse(userBean.getId(), ImageStatusEnum.BROWSE.getValue())
                .compose(this.<NetworkDataBean<List<ImageBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<NetworkDataBean<List<ImageBean>>>() {
                    @Override
                    public void onNext(NetworkDataBean<List<ImageBean>> listNetworkDataBean) {
                        List<ImageBean> data = listNetworkDataBean.getData();
                        if (listNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS) && data != null && !data.isEmpty()) {
                            mRecordImageBeans.clear();
                            String time = data.get(0).getAddtime();
                            int position = 1;
                            mRecordImageBeans.add(new RecordImageBean(0,time, null));
                            mRecordImageBeans.add(new RecordImageBean(1,null, new ArrayList<ImageBean>()));
                            for (ImageBean item : data) {
                                if (item.getAddtime().equals(time)) {
                                    mRecordImageBeans.get(position).getUrls().add(item);
                                } else {
                                    time = item.getAddtime();
                                    mRecordImageBeans.add(new RecordImageBean(0,time, null));
                                    mRecordImageBeans.add(new RecordImageBean(1,null, new ArrayList<ImageBean>()));
                                    position += 2;
                                    mRecordImageBeans.get(position).getUrls().add(item);
                                }
                            }
                            mRecordImageRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getData();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_record;
    }

    @Override
    protected void initView(View contentView) {
        mRefreshLayout = contentView.findViewById(R.id.refresh_layout);
        mImageRecyclerView = contentView.findViewById(R.id.image_recycler_view);

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
            }
        });

        mRecordImageRecyclerAdapter = new RecordImageRecyclerAdapter(mRecordImageBeans);
        mImageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mImageRecyclerView.setAdapter(mRecordImageRecyclerAdapter);
    }
}
