package com.zhjydy.model.data;

import com.zhjydy.model.entity.TokenInfo;
import com.zhjydy.model.net.WebCall;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class AppData {


    private static AppData appdata;
    public AppData()
    {
    }


    public static AppData getInstance()
    {
        if (appdata == null)
        {
            appdata = new AppData();
        }
        return appdata;
    }

    public void initData() {
        DicData.getInstance().init();
    }
    private TokenInfo mToken;
    public void setToken(TokenInfo token) {
        mToken = token;
    }
    public TokenInfo getToken() {
        return mToken;
    }


}
