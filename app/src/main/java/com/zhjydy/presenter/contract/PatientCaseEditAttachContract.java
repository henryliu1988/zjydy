

package com.zhjydy.presenter.contract;

import android.content.Context;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface PatientCaseEditAttachContract {

    interface View extends BaseView<Presenter>
    {
        void sumbitOk();
    }

    interface Presenter extends BasePresenter
    {
        void submitMsg(HashMap<String,Object> params,List<Map<String,Object>> files,Context context,int type);
    }
}
