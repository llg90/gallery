package com.wuhan.gallery.net;

import android.app.Dialog;
import android.widget.Toast;

import com.wuhan.gallery.GalleryApplication;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class NetObserver<T> implements Observer<T> {
    private Dialog mLoadingDialog;

    public NetObserver() {
        super();
    }

    public NetObserver(Dialog loadingDialog) {
        super();
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(GalleryApplication.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
