package com.wuhan.gallery.view.my;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.comm.LoadingDialog;
import com.wuhan.gallery.view.my.info.UserInfoActivity;
import com.wuhan.gallery.view.my.login.LoginActivity;

/**
 * @author: 李利刚
 * @E-mail: lgzc_work@163.com
 * @date: 2018-04-11 17:05
 * @describe:
 */
public class MyFragment extends BaseLazyLoadFragment {
    private ImageView mUserBackgroundImageView;
    private ImageView mUserIconImageView;
    private TextView  mUserNameTextView;
    private LinearLayout mSetButton;

    @Override
    protected void getData() {

        String backgroundUrl = "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png";
        Glide.with(this).load(backgroundUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mUserBackgroundImageView);
        mUserNameTextView.setText("请登录");

        UserBean userBean = GalleryApplication.getContext().getUserBean();
        if (userBean != null) {
            Glide.with(this).load(backgroundUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mUserBackgroundImageView);

            Glide.with(this)
                    .load(SingletonNetServer.sIMAGE_SERVER_HOST+userBean.getIcon())
                    .apply(new RequestOptions().circleCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mUserIconImageView);

            mUserNameTextView.setText(userBean.getUsername());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View convertView) {
        mUserBackgroundImageView = convertView.findViewById(R.id.user_background_image_view);
        mUserIconImageView = convertView.findViewById(R.id.user_icon_image);
        mUserNameTextView  = convertView.findViewById(R.id.user_name_text);
        mSetButton = convertView.findViewById(R.id.set_button);
        initListener();
    }

    private void initListener() {
        mUserIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = GalleryApplication.getContext().getUserBean();
                if (userBean == null) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                }

            }
        });

        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SetActivity.class));
            }
        });
    }
}
