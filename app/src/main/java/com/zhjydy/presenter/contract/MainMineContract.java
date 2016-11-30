package com.zhjydy.presenter.contract;

import android.content.Context;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainMineContract {

    interface MainMineView extends BaseView<MainMinePresenter>
    {
    }

    interface MainMinePresenter extends BasePresenter
    {
        void loadIdentifyInfo();
        Map<String,Object> getIdentifyInfo(Context context);
    }
}
