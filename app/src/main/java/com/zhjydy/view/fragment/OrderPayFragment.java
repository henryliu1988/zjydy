package com.zhjydy.view.fragment;

import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.presenter.contract.OrderCancelContract;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class OrderPayFragment extends PageImpBaseFragment implements OrderCancelContract.View {
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void afterViewCreate() {

    }

    @Override
    public void refreshView() {

    }

    @Override
    public void cancelResult(boolean result) {

    }

    @Override
    public void updateCancelResonList(ArrayList<NormalPickViewData> resons) {

    }

    @Override
    public void setPresenter(OrderCancelContract.Presenter presenter) {

    }
}
