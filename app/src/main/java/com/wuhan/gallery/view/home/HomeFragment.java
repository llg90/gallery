package com.wuhan.gallery.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 李利刚
 * @E-mail: lgzc_work@163.com
 * @date: 2018-04-11 17:05
 * @describe:
 */
public class HomeFragment extends BaseLazyLoadFragment {
    private TabLayout mTabLayoutView;
    private ViewPager mContentPagerView;

    private String[] mTabTexts = new String[] {"首页","明星", "风景","动漫","剧照", "测试" };
    private List<BaseLazyLoadFragment> mFragments = new ArrayList<>();

    private final static List<ArrayList<String>> mImageUrls = new ArrayList<>();
    static {
        mImageUrls.add(new ArrayList<>(Arrays.asList(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524122786&di=36a5f3864591ddad94a591954648c6eb&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F121114%2F240498-1211141U24824.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063807&di=088df43aebf13eaa732507035567e9ae&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F121119%2F240509-12111919121974.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063807&di=092ceae48ad66ce26dfb7ab0cdb80b0d&imgtype=0&src=http%3A%2F%2Fpic8.nipic.com%2F20100622%2F4445776_212046052394_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063807&di=96213598320151288b18b65a23bde161&imgtype=0&src=http%3A%2F%2Fpic2015.5442.com%2F2016%2F0518%2F21%2F8.jpg%2521960.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063806&di=a4270bdbdb2527071ade3bf510059bcc&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2014%2F067%2F3I26385RL51U.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063806&di=65e653df620050d521e84abf0ae4c7de&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F20170803%2F0Gvp6hGAlV154.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063805&di=5794a5390e95d56c1123634ca3a8e087&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F37d12f2eb9389b50c0c9264e8735e5dde7116efc.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063805&di=7a9a73cf48f498cb38e8eac1877e011d&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F121119%2F240509-12111914422314.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063805&di=b299e225db08d0bd7af4c6d807563042&imgtype=0&src=http%3A%2F%2Fatt.kidblog.cn%2Fxm%2F201406%2F8%2F2436402_1402216504Y8YB.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528063804&di=016e984316966ab389d18128effdd076&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F121114%2F240498-12111414560353.jpg")));

        mImageUrls.add(new ArrayList<>(Arrays.asList(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524123022&di=a68b1b9627fac84a2e14ebc16df4d9c1&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fbf096b63f6246b60553a62a0e1f81a4c510fa22a.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528300353&di=c31558662622cd4592f478d2d99599ea&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F120105%2F2014-120105115U136.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528300352&di=5d53ac9457ab7ce935d6e25e428d45cf&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F8b82b9014a90f6037cb445933312b31bb151edda.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528300352&di=611e4307bb6a748de7ce879e335fcccd&imgtype=0&src=http%3A%2F%2Fpic15.nipic.com%2F20110803%2F7180732_211822337168_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528300351&di=a66ddd855ddb01ae03575d6195164710&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3Dc9d9632d69d0f703f2bf9d9c61823451%2Feaf81a4c510fd9f994e060532e2dd42a2834a410.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528300351&di=99380167fd960b9711448d43a4e13997&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F1%2F580ed3a031ea7.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528300351&di=6ca0be4e2f197eeb7ade3637155d6434&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fb21c8701a18b87d6232299000d0828381f30fd48.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528300351&di=db2e585bb538690f00841e79641cce68&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F279759ee3d6d55fb945b460267224f4a20a4dd70.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528300351&di=89f5899fc0098235cf09b9af804c92b2&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fcdbf6c81800a19d8a1af34d139fa828ba71e46b1.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528300351&di=404230973385c7bcc48d52d17cdcf2ca&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fd8f9d72a6059252db773ccd03e9b033b5ab5b9fa.jpg")));

        mImageUrls.add(new ArrayList<>(Arrays.asList(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524123151&di=76c9abc43e11e7d529811f77bf8fb12c&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fa50f4bfbfbedab646acef914fd36afc378311ea1.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528429549&di=fb12d0333c730a3f9b3e39d6ed516823&imgtype=0&src=http%3A%2F%2Fpic40.nipic.com%2F20140427%2F12728082_192158112000_2.png",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528429548&di=9034ca2e6edf7580b377e06b79791860&imgtype=0&src=http%3A%2F%2Fpic8.nipic.com%2F20100716%2F5372314_005516302742_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528429548&di=4d6cad88d78ef1d3e7b6fdf87b059338&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F8cb1cb13495409238658178c9758d109b3de490a.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528429548&di=b54732598182834d9f4ecbb3493562b0&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201208%2F23%2F20120823222234_nJfNh.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528429548&di=5f87db71030f6b5c5515e08a77b81b9b&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F08f790529822720e7f9f138e73cb0a46f21fab75.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528429548&di=57d30f3661138cde158a69aff98f36e2&imgtype=0&src=http%3A%2F%2Fp1.gexing.com%2Fshaitu%2F20120917%2F2243%2F505736f78ae54_600x.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528429547&di=79f57390417fcca8e624de276e726e9b&imgtype=0&src=http%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fbizhi%2Fipad3%2F2015%2F0410%2F20150410090620913_ipad3.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528429547&di=600082811bbf022493f99ee99327fd41&imgtype=0&src=http%3A%2F%2Fa3.topitme.com%2F7%2F31%2F3d%2F1121055191cfe3d317o.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528429546&di=0c357e7a2fdaaceaf4f73ae695d2973c&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201412%2F27%2F20141227233320_hZn5t.jpeg")));

        mImageUrls.add(new ArrayList<>(Arrays.asList(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604222&di=5c881478dde3b31da748bef0e86da91c&imgtype=0&src=http%3A%2F%2Fimg5.mtime.cn%2Fpi%2F2016%2F12%2F07%2F165449.80897500_1000X1000.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604221&di=18a18c741497a204484e68b8dda5c9f3&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2F2015%2Fw18%2F15%2Fd%2F41.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604220&di=b61af9b7df993aaec9eab60e33640b33&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2F9825bc315c6034a8d7259f7bca13495408237600.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604220&di=370acc8642f0223fc589ce412d1eb496&imgtype=0&src=http%3A%2F%2Fp4.qhmsg.com%2Ft019bc9b2c5b5f4af8f.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604220&di=d65d92c469423f3231e8a8932acb0091&imgtype=0&src=http%3A%2F%2Fimg17.3lian.com%2Fd%2Ffile%2F201702%2F24%2F9eeaa57b00a4ddd117a945b65c7d267d.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604219&di=0a7b4614597e14167ae4887774175687&imgtype=0&src=http%3A%2F%2Fi1.qhmsg.com%2Ft01f1cfb233f3b1a32b.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604218&di=a27680ee8c18cad64ba51462730391aa&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2F11%2F1111%2F111109%2F11110929_1200x1000_0.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604218&di=12c7d520bc81fbc37990edeaa3c0c8f6&imgtype=0&src=http%3A%2F%2Fimg1.mtime.cn%2Fpi%2Fd%2F2009%2F39%2F2009921233613.27344705_1000X1000.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604217&di=5ead4b7f483ceede5e78f0df24f3feb4&imgtype=0&src=http%3A%2F%2Fimages2.china.com%2Ffun%2Fzh_cn%2Fmovie%2Fnews%2F205%2F20140718%2F18642434_2014071810110314707800.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604216&di=2ce31d4d9cd08ebf29fdd219548edb54&imgtype=0&src=http%3A%2F%2Fimg31.mtime.cn%2Fpi%2F2016%2F04%2F14%2F150040.82462098_1000X1000.jpg")));

        mImageUrls.add(new ArrayList<>(Arrays.asList(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604222&di=5c881478dde3b31da748bef0e86da91c&imgtype=0&src=http%3A%2F%2Fimg5.mtime.cn%2Fpi%2F2016%2F12%2F07%2F165449.80897500_1000X1000.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604221&di=18a18c741497a204484e68b8dda5c9f3&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2F2015%2Fw18%2F15%2Fd%2F41.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604220&di=b61af9b7df993aaec9eab60e33640b33&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2F9825bc315c6034a8d7259f7bca13495408237600.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604220&di=370acc8642f0223fc589ce412d1eb496&imgtype=0&src=http%3A%2F%2Fp4.qhmsg.com%2Ft019bc9b2c5b5f4af8f.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604220&di=d65d92c469423f3231e8a8932acb0091&imgtype=0&src=http%3A%2F%2Fimg17.3lian.com%2Fd%2Ffile%2F201702%2F24%2F9eeaa57b00a4ddd117a945b65c7d267d.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604219&di=0a7b4614597e14167ae4887774175687&imgtype=0&src=http%3A%2F%2Fi1.qhmsg.com%2Ft01f1cfb233f3b1a32b.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604218&di=a27680ee8c18cad64ba51462730391aa&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2F11%2F1111%2F111109%2F11110929_1200x1000_0.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604218&di=12c7d520bc81fbc37990edeaa3c0c8f6&imgtype=0&src=http%3A%2F%2Fimg1.mtime.cn%2Fpi%2Fd%2F2009%2F39%2F2009921233613.27344705_1000X1000.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604217&di=5ead4b7f483ceede5e78f0df24f3feb4&imgtype=0&src=http%3A%2F%2Fimages2.china.com%2Ffun%2Fzh_cn%2Fmovie%2Fnews%2F205%2F20140718%2F18642434_2014071810110314707800.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523528604216&di=2ce31d4d9cd08ebf29fdd219548edb54&imgtype=0&src=http%3A%2F%2Fimg31.mtime.cn%2Fpi%2F2016%2F04%2F14%2F150040.82462098_1000X1000.jpg")));


    }


    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View convertView) {
        mTabLayoutView = convertView.findViewById(R.id.tab_layout_view);
        mContentPagerView = convertView.findViewById(R.id.content_pager_view);

        for (int count=0, length = mTabTexts.length; count<length; count++) {
            mTabLayoutView.addTab(mTabLayoutView.newTab().setText(mTabTexts[count]));
            if (count == 0) {
                mFragments.add(new HostFragment());
            } else {
                BaseLazyLoadFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("urls", mImageUrls.get(count-1));
                fragment.setArguments(bundle);
                mFragments.add(fragment);
            }
        }

        mContentPagerView.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTexts[position];
            }
        });

        mTabLayoutView.setupWithViewPager(mContentPagerView);
    }
}
