package com.zhjydy.app;

import android.app.Application;
import android.content.Context;

import com.tendcloud.tenddata.TCAgent;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class ZhApplication extends Application {

    private static ZhApplication instance;
    public static ZhApplication getInstance()
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
        TCAgent.LOG_ON = true;
        TCAgent.init(this);
        TCAgent.setReportUncaughtExceptions(true);
    }

}
