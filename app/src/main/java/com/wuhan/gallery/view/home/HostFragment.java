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

import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.bean.HostDataBean;
import com.wuhan.gallery.bean.ImageBean;
import com.wuhan.gallery.bean.NetworkDataBean;
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
    private ArrayList<ImageBean> mLeaderBoardData = new ArrayList<>();
    private HostImageAdapter mLeaderBoardAdapter;

    private ArrayList<ImageBean> mLikeListData = new ArrayList<>();
    private HostImageAdapter mLikeListAdapter;

    private Banner mBanner;
    private ArrayList<ImageBean> mCircleImageUrlData = new ArrayList<>();

    private LoadingDialog mLoadingDialog;

    @Override
    protected void getData() {
/*
        mLeaderBoardData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523471754721&di=ee50059c20a127535f3f4d216caaee6b&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2F9825bc315c6034a84e310db2c713495409237635.jpg");
        mLeaderBoardData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523472017828&di=739f16b500e2832880621a58b5ccad80&imgtype=0&src=http%3A%2F%2Fimg1qn.moko.cc%2F2017-02-18%2F5ef4f3a3-8a8f-467e-befb-42e5d909ecce.jpg");
        mLeaderBoardData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473066906&di=384be74479df524095d705084ef035c4&imgtype=0&src=http%3A%2F%2Fimgphoto.gmw.cn%2Fattachement%2Fjpg%2Fsite2%2F20160525%2Feca86bd9dc4718af3fd92e.jpg");
        mLeaderBoardData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473083050&di=4cbed09a785cdc1345ddc83cdf3fccf5&imgtype=0&src=http%3A%2F%2Fwww.hinews.cn%2Fpic%2F0%2F18%2F18%2F24%2F18182489_999915.jpg");
        mLeaderBoardData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473103543&di=43e6e704a7ec8d0b8f8b1355da50a780&imgtype=0&src=http%3A%2F%2Fwww.sinaimg.cn%2Fdy%2Fslidenews%2F4_img%2F2013_27%2F704_1015455_128499.jpg");
        mLeaderBoardData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473120590&di=1ef8eba39675245f88c75eda99ce1763&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201504%2F12%2F20150412H1731_RvmAa.thumb.700_0.jpeg");


        mLikeListData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473370717&di=f35e71642ab80f2832c7c7b65a6c8386&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201605%2F08%2F20160508154653_AQavc.png");
        mLikeListData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473389143&di=c70bea1fee94e9cf561c27696b2a602a&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fa9d3fd1f4134970a35788b9597cad1c8a7865d04.jpg");
        mLikeListData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524068141&di=f65d5e727d1d3c38afed003196b19dd0&imgtype=jpg&er=1&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201504%2F20%2F20150420H3909_hGtBL.jpeg");
        mLikeListData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473478109&di=584e790a0107e39d7b45642b23a47211&imgtype=0&src=http%3A%2F%2Fwww.71lady.com%2Fuploads%2Fallimg%2F140812%2F2-140Q2162642.jpg");
        mLikeListData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473495261&di=c625fa6fdb38eb7c1e63081f059c2591&imgtype=0&src=http%3A%2F%2Fimg3.yxlady.com%2Fyl%2FUploadFiles_5361%2F2014063%2F2014060323325904.jpg");
        mLikeListData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473519623&di=5da8e7cadcec118dfb5ff7a5675c0617&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D3e3a0afb31d12f2ece05ae687fc3d5ff%2F8eac339b033b5bb58c3c4bd231d3d539b700bc4f.jpg");
        mLikeListData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473546429&di=f6c64b913ef18967b810ce7c202b8f5d&imgtype=0&src=http%3A%2F%2Fww1.sinaimg.cn%2Forj480%2F9b3dfba1gw1f3a8plwqtej20qo13kti7.jpg");

        mCircleImageUrlData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523471754721&di=ee50059c20a127535f3f4d216caaee6b&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2F9825bc315c6034a84e310db2c713495409237635.jpg");
        mCircleImageUrlData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523472017828&di=739f16b500e2832880621a58b5ccad80&imgtype=0&src=http%3A%2F%2Fimg1qn.moko.cc%2F2017-02-18%2F5ef4f3a3-8a8f-467e-befb-42e5d909ecce.jpg");
        mCircleImageUrlData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473066906&di=384be74479df524095d705084ef035c4&imgtype=0&src=http%3A%2F%2Fimgphoto.gmw.cn%2Fattachement%2Fjpg%2Fsite2%2F20160525%2Feca86bd9dc4718af3fd92e.jpg");
        mCircleImageUrlData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523473083050&di=4cbed09a785cdc1345ddc83cdf3fccf5&imgtype=0&src=http%3A%2F%2Fwww.hinews.cn%2Fpic%2F0%2F18%2F18%2F24%2F18182489_999915.jpg");

        mLeaderBoardAdapter.notifyDataSetChanged();
        mLikeListAdapter.notifyDataSetChanged();
        mBanner.setImages(mCircleImageUrlData);
        mBanner.start();
*/

        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }

        SingletonNetServer.INSTANCE.getImageServer().clicktopload()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<NetworkDataBean<HostDataBean>>() {
                    @Override
                    public void onNext(NetworkDataBean<HostDataBean> hostDataBeanNetworkDataBean) {
                        if (hostDataBeanNetworkDataBean.getStatus().equals(SingletonNetServer.SUCCESS)) {
                            HostDataBean data = hostDataBeanNetworkDataBean.getData();
                            List<ImageBean> leader = data.getClicklist();
                            List<ImageBean> like   = data.getMaxlist();
                            if (leader != null) {
                                mLeaderBoardData.addAll(leader);
                                mLeaderBoardAdapter.notifyDataSetChanged();
                            }

                            if (like != null) {
                                mLikeListData.addAll(like);
                                mLikeListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_host;
    }

    @Override
    protected void initView(View convertView) {
        Drawable decorationDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            decorationDrawable = getResources().getDrawable(R.drawable.host_image_divider, getContext().getTheme());
        } else {
            decorationDrawable = getResources().getDrawable(R.drawable.host_image_divider);
        }

        RecyclerView leaderBoardListView = convertView.findViewById(R.id.leader_board_list_view);
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
//                intent.putStringArrayListExtra("urls", (ArrayList<String>) mLeaderBoardData);
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(itemView, "image"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        leaderBoardListView.setAdapter(mLeaderBoardAdapter);

        RecyclerView likeListView = convertView.findViewById(R.id.like_list_view);
        likeListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        likeListView.addItemDecoration(itemDecoration);
        mLikeListAdapter = new HostImageAdapter(this, mLikeListData);
        mLikeListAdapter.setOnClickListener(new HostImageAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int position) {
                Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                intent.putExtra("position", position);
//                intent.putStringArrayListExtra("urls", (ArrayList<String>) mLikeListData);
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(itemView, "image"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        likeListView.setAdapter(mLikeListAdapter);

        mBanner = convertView.findViewById(R.id.banner_view);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.Stack);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
//                ArrayList<String> urls = new ArrayList<>();
//                urls.add(mCircleImageUrlData.get(position));
                //intent.putStringArrayListExtra("urls", urls);
                ArrayList<ImageBean> imageBeans = new ArrayList<>();
                imageBeans.add(mCircleImageUrlData.get(position));
                intent.putParcelableArrayListExtra("images", imageBeans);

                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create((View)mBanner, "image"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });

    }



}
