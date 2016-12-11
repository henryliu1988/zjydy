package com.zhjydy.presenter;

interface RefreshWithData extends RefreshListener {
    void onRefreshWithData(int key, Object data);
}