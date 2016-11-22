package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.ExpertDetailContract;
import com.zhjydy.presenter.presenterImp.ExpertDetailPresenterImp;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.ExperDetaiCommentListAdapter;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.zhview.ScoreView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class ExpertDetailFragment extends PageImpBaseFragment implements ExpertDetailContract.View {

    @BindView(R.id.comment_make_edit)
    EditText commentMakeEdit;
    @BindView(R.id.comment_make_btn)
    TextView commentMakeBtn;
    @BindView(R.id.write_words)
    LinearLayout writeWords;
    @BindView(R.id.bottom_div)
    View bottomDiv;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.depart)
    TextView depart;
    @BindView(R.id.profession)
    TextView profession;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.save_image)
    ImageView saveImage;
    @BindView(R.id.save_text)
    TextView saveText;
    @BindView(R.id.save_layout)
    LinearLayout saveLayout;
    @BindView(R.id.score_image)
    ImageView scoreImage;
    @BindView(R.id.score_text)
    TextView scoreText;
    @BindView(R.id.score_star)
    ScoreView scoreStar;
    @BindView(R.id.reason)
    LinearLayout reason;
    @BindView(R.id.specical)
    LinearLayout specical;
    @BindView(R.id.word_listview)
    ListView wordListview;
    @BindView(R.id.subscribe_expert)
    TextView subscribeExpert;
    @BindView(R.id.reason_tv)
    TextView reasonTv;
    @BindView(R.id.specical_tv)
    TextView specicalTv;
    private ExpertDetailContract.Presenter mPresenter;

    private ExperDetaiCommentListAdapter mCommentListAdapter;
    private View mCommentListHeaderView;

    private String id;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_expert_detail;
    }

    @Override
    protected void afterViewCreate() {
        id = getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(id)) {
            return;
        }
        initCommentListView();
        new ExpertDetailPresenterImp(this, id);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    private void initCommentListView() {
        mCommentListAdapter = new ExperDetaiCommentListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        wordListview.setAdapter(mCommentListAdapter);
        mCommentListHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.listview_expert_comment_header_layout, null);
        wordListview.addHeaderView(mCommentListHeaderView);
    }

    @Override
    public void updateExpertInfos(Map<String, Object> expertInfo) {
        name.setText(Utils.toString(expertInfo.get("realname")));
        depart.setText(Utils.toString(expertInfo.get("office")));
        hospital.setText(Utils.toString(expertInfo.get("hospital")));
        profession.setText(Utils.toString(expertInfo.get("business")));
        reasonTv.setText(Utils.toString(expertInfo.get("reason")));
        specicalTv.setText(Utils.toString(expertInfo.get("adept")));
        ImageUtils.getInstance().displayFromRemote(Utils.toString(expertInfo.get("path")), image);
    }

    @Override
    public void updateComments(List<Map<String, Object>> comments) {
        mCommentListAdapter.refreshData(comments);
    }

    @Override
    public void setPresenter(ExpertDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.comment_make_btn, R.id.title_back, R.id.save_layout, R.id.subscribe_expert})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_make_btn:
                break;
            case R.id.title_back:
                back();
                break;
            case R.id.save_layout:
                mPresenter.saveExpert(id);
                break;
        }
    }
}
