package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.ExpertDetailContract;
import com.zhjydy.presenter.contract.OrderDetailContract;
import com.zhjydy.presenter.presenterImp.ExpertDetailPresenterImp;
import com.zhjydy.view.adapter.ExperDetaiCommentListAdapter;
import com.zhjydy.view.avtivity.IntentKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class OrderDetailFragment extends PageImpBaseFragment implements OrderDetailContract.View {


    private OrderDetailContract.Presenter mPresenter;
    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_details;
    }

    @Override
    protected void afterViewCreate() {

    }

    @Override
    public void update(Map<String, Object> info) {

    }
}
