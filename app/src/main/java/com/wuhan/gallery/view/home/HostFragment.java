package com.wuhan.gallery.view.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wuhan.gallery.GalleryApplication;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.bean.HostDataBean;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;
import com.wuhan.gallery.bean.UserBean;
import com.wuhan.gallery.net.NetObserver;
import com.wuhan.gallery.net.SingletonNetServer;
import com.wuhan.gallery.view.comm.ImageDetailsActivity;
import com.wuhan.gallery.view.comm.LoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HostFragment extends BaseLazyLoadFragment {
    //点击榜
    private ArrayList<ImageBean> mLeaderBoardData = new ArrayList<>();
    private HostImageAdapter mLeaderBoardAdapter;

    //猜你喜欢
    private ArrayList<ImageBean> mLikeListData = new ArrayList<>();
    private HostImageAdapter mLikeListAdapter;

    //首页轮播
    private Banner mBanner;
    private List<ImageBean> mBannerImage;
    private ArrayList<String> mBannerImageUrlData = new ArrayList<>();

//    private ArrayList<ImageBean> mBannerData = new ArrayList<>();
//    private HostImageAdapter mBannerAdapter;


    private LoadingDialog mLoadingDialog;

    @Override
    protected void getData() {

        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }

        UserBean userBean = GalleryApplication.getUserBean();
        int id = userBean==null?0:userBean.getId();
        SingletonNetServer.INSTANCE.getImageServer().clicktopload(id)
                .compose(this.<NetworkDataBean<HostDataBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<NetworkDataBean<HostDataBean>>() {
                    @Override
                    public void onNext(NetworkDataBean<HostDataBean> hostDataBeanNetworkDataBean) {
                        if (hostDataBeanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                            HostDataBean data = hostDataBeanNetworkDataBean.getData();
                            List<ImageBean> banner   = data.getMaxlist();
                            List<ImageBean> topclick = data.getClicklist();
                            List<ImageBean> likelist = data.getLikelist();
                            if (banner != null) {
                                mBannerImage = banner;
                                mBannerImageUrlData.clear();
                                for (ImageBean imageBean : banner){
                                    String url = SingletonNetServer.sIMAGE_SERVER_HOST + imageBean.getImageurl();
                                    mBannerImageUrlData.add(url);
                                }
                                mBanner.setImages(mBannerImageUrlData);
                                mBanner.start();
                            }

                            if (topclick != null) {
                                mLeaderBoardData.clear();
                                mLeaderBoardData.addAll(topclick);
                                mLeaderBoardAdapter.notifyDataSetChanged();
                            }

                            if (likelist != null) {
                                mLikeListData.clear();
                                mLikeListData.addAll(likelist);
                                mLikeListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });


    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_host;
    }

    @Override
    protected void initView(View contentView) {
        Drawable decorationDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            decorationDrawable = getResources().getDrawable(R.drawable.host_image_divider, getContext().getTheme());
        } else {
            decorationDrawable = getResources().getDrawable(R.drawable.host_image_divider);
        }

        RecyclerView leaderBoardListView = contentView.findViewById(R.id.leader_board_list_view);
        leaderBoardListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(leaderBoardListView.getContext(), LinearLayoutManager.HORIZONTAL);
        itemDecoration.setDrawable(decorationDrawable);
        leaderBoardListView.addItemDecoration(itemDecoration);
        mLeaderBoardAdapter = new HostImageAdapter(this, mLeaderBoardData);
        mLeaderBoardAdapter.setOnClickListener(new HostImageAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int position) {
                Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("images", mLeaderBoardData);
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(itemView, "image"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        leaderBoardListView.setAdapter(mLeaderBoardAdapter);

        RecyclerView likeListView = contentView.findViewById(R.id.like_list_view);
        likeListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        likeListView.addItemDecoration(itemDecoration);
        mLikeListAdapter = new HostImageAdapter(this, mLikeListData);
        mLikeListAdapter.setOnClickListener(new HostImageAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int position) {
                Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("images", mLikeListData);
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(itemView, "image"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        likeListView.setAdapter(mLikeListAdapter);

        mBanner = contentView.findViewById(R.id.banner_view);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.Stack);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                ArrayList<ImageBean> imageBeans = new ArrayList<>();
                imageBeans.add(mBannerImage.get(position));
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("images", imageBeans);

                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create((View)mBanner, "image"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });

    }
}
