package com.wuhan.gallery.view.my;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.my.info.UserInfoActivity;
import com.wuhan.gallery.view.my.login.LoginActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        String iconUrl = "http://img3.duitang.com/uploads/item/201605/08/20160508154653_AQavc.png";
        Glide.with(this).load(backgroundUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mUserBackgroundImageView);

        Glide.with(this)
                .load(iconUrl)
                .apply(new RequestOptions().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mUserIconImageView);
        mUserNameTextView.setText("测试用户");

        SingletonNetServer.INSTANCE.getUserServer().login("test", "123456")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NetworkDataBean<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NetworkDataBean<UserBean> userBeanNetworkDataBean) {
                        UserBean data = userBeanNetworkDataBean.getData();
                        mUserNameTextView.setText(data.getName());
                        Glide.with(MyFragment.this)
                                .load(data.getIconUrl())
                                .apply(new RequestOptions().circleCrop())
                                .into(mUserIconImageView);

                        Glide.with(MyFragment.this)
                                .load(data.getbUrl())
                                .into(mUserBackgroundImageView);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
                startActivity(new Intent(getContext(), LoginActivity.class));
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
