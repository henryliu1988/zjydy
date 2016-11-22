package com.zhjydy.model.entity;

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
}
