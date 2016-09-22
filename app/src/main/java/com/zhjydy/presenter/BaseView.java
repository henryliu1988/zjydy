package com.zhjydy.presenter;

/**
 * Created by admin on 2016/8/1.
 */
public interface BaseView<T> {

    void setPresenter(T presenter);
    void refreshView();
}
