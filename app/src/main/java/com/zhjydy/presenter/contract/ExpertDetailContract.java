package com.zhjydy.presenter.contract;

import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface ExpertDetailContract {

    interface View extends BaseView<Presenter> {
        void updateExpertInfos(Map<String, Object> expertInfo);

        void updateComments(List<Map<String, Object>> comments);
    }

    interface Presenter extends BasePresenter {
        void markExpert();

        void subscribeExpert();

        void makeNewComment(String commentId);

        void saveExpert(String id);
    }
}
