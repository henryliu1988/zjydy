package com.zhjydy.app;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhjydy.model.data.AppDataManager;
import com.zhjydy.util.ImageUtils;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class zhjApplication extends Application {

    private static zhjApplication instance;

    public static zhjApplication getInstance() {
        return instance;
    }


    public Context getContext() {
        return this.getApplicationContext();
    }

    {
        PlatformConfig.setWeixin("wx82bb63a1065fc8a0", "0072626f46d0bca81968ce5752950fbf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        UMShareAPI.get(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void init() {
        instance = this;
        ImageUtils.getInstance().initImageLoader();
        AppDataManager.getInstance().initData();
    }

}
