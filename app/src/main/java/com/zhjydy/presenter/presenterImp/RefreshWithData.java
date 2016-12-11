package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.RefreshListener;

interface RefreshWithData extends RefreshListener{
        void onRefreshWithData(int key,Object data);
    }