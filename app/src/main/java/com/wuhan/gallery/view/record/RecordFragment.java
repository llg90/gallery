package com.wuhan.gallery.view.record;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.bean.RecordImageBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 李利刚
 * @E-mail: lgzc_work@163.com
 * @date: 2018-04-11 17:06
 * @describe:
 */
public class RecordFragment extends BaseLazyLoadFragment {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mImageRecyclerView;

    private List<RecordImageBean> mRecordImageBeans = new ArrayList<>();
    private RecordImageRecyclerAdapter mRecordImageRecyclerAdapter;

    @Override
    protected void getData() {
        List<String> list = Arrays.asList(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524122786&di=36a5f3864591ddad94a591954648c6eb&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F121114%2F240498-1211141U24824.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063807&di=088df43aebf13eaa732507035567e9ae&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F121119%2F240509-12111919121974.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063807&di=092ceae48ad66ce26dfb7ab0cdb80b0d&imgtype=0&src=http%3A%2F%2Fpic8.nipic.com%2F20100622%2F4445776_212046052394_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063807&di=96213598320151288b18b65a23bde161&imgtype=0&src=http%3A%2F%2Fpic2015.5442.com%2F2016%2F0518%2F21%2F8.jpg%2521960.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063806&di=a4270bdbdb2527071ade3bf510059bcc&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2014%2F067%2F3I26385RL51U.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063806&di=65e653df620050d521e84abf0ae4c7de&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F20170803%2F0Gvp6hGAlV154.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063805&di=5794a5390e95d56c1123634ca3a8e087&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F37d12f2eb9389b50c0c9264e8735e5dde7116efc.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063805&di=7a9a73cf48f498cb38e8eac1877e011d&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F121119%2F240509-12111914422314.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063805&di=b299e225db08d0bd7af4c6d807563042&imgtype=0&src=http%3A%2F%2Fatt.kidblog.cn%2Fxm%2F201406%2F8%2F2436402_1402216504Y8YB.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063804&di=016e984316966ab389d18128effdd076&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F121114%2F240498-12111414560353.jpg");

        mRecordImageBeans.add(new RecordImageBean(0,"2018-04-16", null));
        mRecordImageBeans.add(new RecordImageBean(1,null, list));
        mRecordImageBeans.add(new RecordImageBean(0,"2018-04-15", null));
        mRecordImageBeans.add(new RecordImageBean(1,null, list));
        mRecordImageBeans.add(new RecordImageBean(0,"2018-04-13", null));
        mRecordImageBeans.add(new RecordImageBean(1,null, list));
        mRecordImageRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_record;
    }

    @Override
    protected void initView(View convertView) {
        mRefreshLayout = convertView.findViewById(R.id.refresh_layout);
        mImageRecyclerView = convertView.findViewById(R.id.image_recycler_view);

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
