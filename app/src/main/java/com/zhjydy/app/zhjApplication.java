package com.zhjydy.app;

import android.app.Application;
import android.content.Context;

import com.tendcloud.tenddata.TCAgent;
import com.zhjydy.util.ImageUtils;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class zhjApplication extends Application {

    private static zhjApplication instance;
    public static zhjApplication getInstance()
    {
        return instance;
    }
    public Context getContext()
    {
        return this.getApplicationContext();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        init();
    }
    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    private void init()
    {
        instance = this;
        ImageUtils.getInstance().initImageLoader();
    }

}
