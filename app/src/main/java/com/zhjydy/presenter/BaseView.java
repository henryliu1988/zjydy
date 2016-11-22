package com.zhjydy.presenter;

import android.content.Context;

/**
 * Created by admin on 2016/8/1.
 */
public interface BaseView<T> {

    void setPresenter(T presenter);
    void refreshView();
    Context getContext();
}
