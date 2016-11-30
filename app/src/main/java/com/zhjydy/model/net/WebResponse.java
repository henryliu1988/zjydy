package com.zhjydy.model.net;

import android.text.TextUtils;

/**
 * Created by admin on 2016/7/29.
 */
public class WebResponse
{
    private int mError;
    private String mInfo;
    private String mData;
    private String funName;

    public WebResponse()
    {
        mError = -1;
        mInfo = "";
        mData = "";
    }

    public WebResponse(int erro, String info, String data)
    {
        this.mError = erro;
        this.mInfo = info;
        this.mData = data;
    }

    public int getError()
    {
        return mError;
    }

    public void setError(int error)
    {
        this.mError = error;
    }

    public String getInfo()
    {
        return mInfo;
    }

    public void setInfo(String info)
    {
        this.mInfo = info;
    }

    public String getData()
    {
        return mData;
    }

    public void setData(String data)
    {
        String tmp = data.replace("\r", "\\r");
        tmp = tmp.replace("\n", "\\n");
        this.mData = tmp;
    }

    public String getFuncName()
    {
        return funName;
    }

    public void setFuncName(String input)
    {
        this.funName = input;
    }

    public boolean isEmptyData()
    {
        if (TextUtils.isEmpty(mData) || "anyType{}".equals(mData))
        {
            return true;
        }
        return false;

    }
}