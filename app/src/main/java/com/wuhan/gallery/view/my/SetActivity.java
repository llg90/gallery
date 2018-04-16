package com.wuhan.gallery.view.my;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wuhan.gallery.R;
import com.wuhan.gallery.base.BaseActivity;
import com.wuhan.gallery.utils.DataCleanUtils;

public class SetActivity extends BaseActivity {
    private TextView mCacheText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        mCacheText = findViewById(R.id.cache_text);
        TextView versionText = findViewById(R.id.version_text);

        try {
            PackageInfo packageInfo = getApplicationContext()
                    .getPackageManager().getPackageInfo(getPackageName(), 0);

            mCacheText.setText(DataCleanUtils.getTotalCacheSize(getBaseContext()));
            versionText.setText(packageInfo.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.back_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.clear_cache_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.check_version_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.logout_button).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_button:
                    finish();
                    break;
                case R.id.clear_cache_button:
                    DataCleanUtils.clearCache(getBaseContext());
                    mCacheText.setText("0KB");
                    break;
                case R.id.check_version_button:
                    break;
                case R.id.logout_button:
                    break;
            }
        }
    };
}
