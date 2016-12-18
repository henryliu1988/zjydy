package com.zhjydy.presenter;

public interface RefreshWithData extends RefreshListener {
    void onRefreshWithData(int key, Object data);
}