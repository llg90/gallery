package com.wuhan.gallery.view.comm;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wuhan.gallery.R;

public class LoadingDialog extends Dialog {
    private AnimationDrawable mAnimationDrawable;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_loading);
////加载过程不可中断
//        setCancelable(false);
//        setCanceledOnTouchOutside(false);
//        Window window = getWindow();
//        ImageView imageView = findViewById(R.id.image_view);
//        mAnimationDrawable = (AnimationDrawable) imageView.getDrawable();
//        if (window != null) {
//            WindowManager.LayoutParams layoutParams = window.getAttributes();
//            layoutParams.gravity = Gravity.CENTER;
//            layoutParams.dimAmount = 0.5f;
//            layoutParams.alpha = 1.0f;
//            window.setAttributes(layoutParams);
//        }
        Window window = getWindow();
        ImageView imageview = findViewById(R.id.image_view);
        mAnimationDrawable = (AnimationDrawable)imageview.getDrawable();

        if (window != null){
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.dimAmount = 0.5f;
            layoutParams.alpha = 1.0f;
            window.setAttributes(layoutParams);

        }


    }

    @Override
    public void show() {
        if (!isShowing()) {
            mAnimationDrawable.start();
        }
        super.show();
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            mAnimationDrawable.stop();
        }
        super.dismiss();
    }
}
