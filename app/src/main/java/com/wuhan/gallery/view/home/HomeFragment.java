package com.wuhan.gallery.view.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseLazyLoadFragment;
import com.wuhan.gallery.constant.ImageTypeEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends BaseLazyLoadFragment {
    private TabLayout mTabLayoutView;
    private ViewPager mContentPagerView;

    private String[] mTabTexts = new String[] {"首页","明星", "风景","动漫","剧照" };
    private List<BaseLazyLoadFragment> mFragments = new ArrayList<>();

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onResume() {
        super.onResume();
      //  mContentPagerView.setCurrentItem(0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mContentPagerView.setCurrentItem(0);
    }

    @Override
    protected void initView(View contentView) {
        mTabLayoutView = contentView.findViewById(R.id.tab_layout_view);
        mContentPagerView = contentView.findViewById(R.id.content_pager_view);

        for (int count = 0, length = mTabTexts.length; count < length; count++) {
            mTabLayoutView.addTab(mTabLayoutView.newTab().setText(mTabTexts[count]));
            if (count == 0) {
                mFragments.add(new HostFragment());
            } else {
                ImageTypeEnum imageType = ImageTypeEnum.getOfValue(count);
                int imageTypeValue = imageType == null ? 0 : imageType.getValue();
                BaseLazyLoadFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", imageTypeValue);
                fragment.setArguments(bundle);
                mFragments.add(fragment);
            }
        }

        //为ViewPager设置适配器
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

        //将TagLayout与ViewPager相关联
        mTabLayoutView.setupWithViewPager(mContentPagerView);
    }
}




//
//    Bitmap bitmap = loadBitmapFromView(mCacheView.get(mSelectPosition));
//                if (bitmap == null){
//                        Toast.makeText(ImageDetailsActivity.this, "bitmap_1为空", Toast.LENGTH_SHORT).show();
//                        }
//                        String imageFilePath = saveBitmap(getBaseContext(), bitmap);
//                        if (imageFilePath != null){
//                        Toast.makeText(getApplicationContext(), "图片保存至" + imageFilePath, Toast.LENGTH_SHORT).show();
//                        }else{
//                        Toast.makeText(getApplicationContext(), "图片保存失败", Toast.LENGTH_SHORT).show();
//                        }
////                String imageurl = SingletonNetServer.sIMAGE_SERVER_HOST + mImageBeans.get(mSelectPosition).getImageurl();
////                download(imageurl);
//                        reserve_tv.setVisibility(View.INVISIBLE);
//                        if (mCacheView.get(mSelectPosition) == null){
//                        Toast.makeText(ImageDetailsActivity.this, "为空", Toast.LENGTH_SHORT).show();
//

//
//    private Bitmap loadBitmapFromView(View v) {
//        int w = v.getWidth();
//        int h = v.getHeight();
//        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bmp);
//
//        //c.drawColor(Color.WHITE);
//        /** 如果不设置canvas画布为白色，则生成透明 */
//
//        v.layout(0, 0, w, h);
//        v.draw(c);
//
//        return bmp;
//    }
//
//    public static String saveBitmap(Context context, Bitmap mBitmap) {
//        String savePath;
//        File filePic;
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            savePath = SD_PATH;
//        } else {
//            savePath = context.getApplicationContext().getFilesDir()
//                    .getAbsolutePath()
//                    + IN_PATH;
//        }
//        try {
//            filePic = new File(savePath + UUID.randomUUID().toString() + ".jpg");
//            if (!filePic.exists()) {
//                filePic.getParentFile().mkdirs();
//                filePic.createNewFile();
//            }
//            FileOutputStream fos = new FileOutputStream(filePic);
//            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }
//
//        return filePic.getAbsolutePath();
//    }






















