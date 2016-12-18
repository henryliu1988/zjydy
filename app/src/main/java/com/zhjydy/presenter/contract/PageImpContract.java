package com.zhjydy.presenter.contract;

import android.content.Intent;

import com.zhjydy.presenter.BaseView;

/**
 * Created by admin on 2016/8/9.
 */
public interface PageImpContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter {
        void onActivityResult(int requestCode, int resultCode, Intent data);

    }
}
