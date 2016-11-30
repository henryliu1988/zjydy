package com.zhjydy.model.entity;

import android.text.TextUtils;

import com.zhjydy.model.data.AppData;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class TokenInfo {
    String id;
    String mobile;
    String nickname;
    String loginTime;
    String collectExperts;
    String collectNews;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }


    public String getCollectExperts() {
        return collectExperts;
    }

    public void setCollectExperts(String collectExperts) {
        this.collectExperts = collectExperts;
    }

    public String getCollectNews() {
        return collectNews;
    }

    public void setCollectNews(String collectNews) {
        this.collectNews = collectNews;
    }

    public List<String> getCollectExpertList(){
        String collect = getCollectExperts();
        List<String> coList = new ArrayList<String>();
        if (!TextUtils.isEmpty(collect)) {
            coList = Arrays.asList(collect.split(","));
        }
        return coList;
    }
    public List<String> getCollectNewsList(){
        String news = getCollectNews();
        List<String> list = new ArrayList<String>();
        if (!TextUtils.isEmpty(news)) {
            list = Arrays.asList(news.split(","));
        }
        return list;
    }

    public void setCollectExpertAsList(List<String> collect) {
        String str = "";
        if (collect != null && collect.size() > 0) {
            for (int i = 0 ; i < collect.size() ; i ++) {
                str += collect.get(i);
                if (i < collect.size() -1)
                str +=",";
            }
        }
        setCollectExperts(str);
    }

    public void setCollectNewAsList(List<String> news) {
        String str = "";
        if (news != null && news.size() > 0) {
            for (int i = 0 ; i < news.size() ; i ++) {
                str += news.get(i);
                if (i < news.size() -1)
                    str +=",";
            }
        }
        setCollectNews(str);
    }
}
