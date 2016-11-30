package com.zhjydy.presenter.contract;

import android.media.session.MediaSession;

import com.zhjydy.model.entity.TokenInfo;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MineInfoContract {

    interface View extends BaseView<Presenter>
    {
        void updateInfo(TokenInfo info);
    }

    interface Presenter extends BasePresenter
    {
    }
}
