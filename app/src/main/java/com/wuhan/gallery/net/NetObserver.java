package com.wuhan.gallery.net;

import android.app.Dialog;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.view.comm.LoadingDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class NetObserver<T> implements Observer<T> {
    private Dialog mLoadingDialog;
    private SmartRefreshLayout mSmartRefreshLayout;

    public NetObserver() {
        super();
    }

    public NetObserver(Dialog loadingDialog) {
        super();
        mLoadingDialog = loadingDialog;
    }

    public NetObserver(Dialog loadingDialog, SmartRefreshLayout smartRefreshLayout) {
        super();
        mLoadingDialog = loadingDialog;
        mSmartRefreshLayout = smartRefreshLayout;
    }
    @Override
    public void onSubscribe(Disposable d) {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        if (mSmartRefreshLayout != null) {
            if (mSmartRefreshLayout.getState() == RefreshState.Refreshing) mSmartRefreshLayout.finishRefresh();
            if (mSmartRefreshLayout.getState() == RefreshState.Loading)    mSmartRefreshLayout.finishLoadMore();

        }
        Toast.makeText(GalleryApplication.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        if (mSmartRefreshLayout != null) {
            if (mSmartRefreshLayout.getState() == RefreshState.Refreshing) mSmartRefreshLayout.finishRefresh();
            if (mSmartRefreshLayout.getState() == RefreshState.Loading)    mSmartRefreshLayout.finishLoadMore();

        }
    }
}
