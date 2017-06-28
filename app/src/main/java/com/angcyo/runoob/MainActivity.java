package com.angcyo.runoob;

import android.Manifest;
import android.content.Intent;

import com.angcyo.runoob.iview.LauncherUIView;
import com.angcyo.runoob.iview.MainUIView;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.base.UILayoutActivity;

public class MainActivity extends UILayoutActivity {

    @Override
    protected void onLoadView(Intent intent) {
        checkPermissions();
    }

    @Override
    protected String[] needPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        };
    }

    @Override
    protected void onLoadViewAfterPermission(Intent intent) {
        super.onLoadViewAfterPermission(intent);
        if (BuildConfig.DEBUG) {
            startIView(new MainUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_START));
        } else {
            startIView(new LauncherUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_START));
        }
    }
}
