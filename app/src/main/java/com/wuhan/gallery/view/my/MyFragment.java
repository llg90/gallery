package com.wuhan.gallery.view.my;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends BaseLazyLoadFragment {
    private ImageView mUserBackgroundImageView;
    private ImageView mUserIconImageView;
    private TextView  mUserNameTextView;
    private LinearLayout mSetButton;

    private List<String> mCollectImageUrls = new ArrayList<>();
    private MyFragmentImageAdapter myFragmentImageAdapter;
    private RecyclerView mCollectRecyclerView;


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
                    .load(SingletonNetServer.sIMAGE_SERVER_HOST + userBean.getIcon())
                    .apply(new RequestOptions().circleCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mUserIconImageView);
            mUserNameTextView.setText(userBean.getUsername());

        }


        mCollectImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523471754721&di=ee50059c20a127535f3f4d216caaee6b&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2F9825bc315c6034a84e310db2c713495409237635.jpg");
//        mCollectImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523472017828&di=739f16b500e2832880621a58b5ccad80&imgtype=0&src=http%3A%2F%2Fimg1qn.moko.cc%2F2017-02-18%2F5ef4f3a3-8a8f-467e-befb-42e5d909ecce.jpg");
//        mCollectImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473066906&di=384be74479df524095d705084ef035c4&imgtype=0&src=http%3A%2F%2Fimgphoto.gmw.cn%2Fattachement%2Fjpg%2Fsite2%2F20160525%2Feca86bd9dc4718af3fd92e.jpg");
//        mCollectImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473083050&di=4cbed09a785cdc1345ddc83cdf3fccf5&imgtype=0&src=http%3A%2F%2Fwww.hinews.cn%2Fpic%2F0%2F18%2F18%2F24%2F18182489_999915.jpg");
//        mCollectImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473103543&di=43e6e704a7ec8d0b8f8b1355da50a780&imgtype=0&src=http%3A%2F%2Fwww.sinaimg.cn%2Fdy%2Fslidenews%2F4_img%2F2013_27%2F704_1015455_128499.jpg");
//        mCollectImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473120590&di=1ef8eba39675245f88c75eda99ce1763&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201504%2F12%2F20150412H1731_RvmAa.thumb.700_0.jpeg");

        myFragmentImageAdapter.notifyDataSetChanged();
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

        mCollectRecyclerView = convertView.findViewById(R.id.collect_recycler_view);

        Drawable decorationDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            decorationDrawable = getResources().getDrawable(R.drawable.my_image_divider, getContext().getTheme());
        } else {
            decorationDrawable = getResources().getDrawable(R.drawable.my_image_divider);
        }
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL);
        itemDecoration.setDrawable(decorationDrawable);


        myFragmentImageAdapter = new MyFragmentImageAdapter(this, mCollectImageUrls);
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mCollectRecyclerView.addItemDecoration(itemDecoration);
        mCollectRecyclerView.setAdapter(myFragmentImageAdapter);


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
