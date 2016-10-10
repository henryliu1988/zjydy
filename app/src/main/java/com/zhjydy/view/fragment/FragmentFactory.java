package com.zhjydy.view.fragment;

import com.zhjydy.presenter.contract.PageImpContract;
import com.zhjydy.presenter.presenterImp.PageImpPresenter;
import com.zhjydy.view.fragment.FragKey;
import com.zhjydy.view.fragment.SearchExpertFragment;
import com.zhjydy.view.fragment.SearchInfoFragment;
import com.zhjydy.view.fragment.StatedFragment;

/**
 * Created by admin on 2016/8/9.
 */
public class FragmentFactory
{
    public static StatedFragment createFragment(int key)
    {
        switch (key){
            case FragKey.search_expert_fragment:
                return new SearchExpertFragment();
            case FragKey.search_info_fragment:
                return new SearchInfoFragment();
            case FragKey.detail_info_fragment:
                return new InfoDetailFragment();
            case FragKey.detail_expert_fragment:
                return new ExpertDetailFragment();
            case FragKey.detail_order_fragment:
                return new OrderDetailFragment();
            case FragKey.pay_password_change_fragment:
                return new PayPasswordChangeFragment();
            case FragKey.phone_num_change_fragment:
                return new PhoneNumChangeFragment();
            case FragKey.common_fragment:
                return new CommonFragment();
            case FragKey.identify_info_fragment:
                return new IdentityIndfoFragment();
            case FragKey.mine_info_fragment:
                return new MineIndoFragment();
            case FragKey.account_safe_fragment:
                return new AccountSafeFragment();
            case FragKey.msg_all_fragment:
                return new MsgAllListFragment();
            case FragKey.password_change_fragment:
                return new PasswordChangeFragment();
            case FragKey.patient_case_fragment:
                return new PatientCaseFragment();
            case FragKey.patient_case_detail_fragment:
                return new PatientCaseDetailFragment();
            case FragKey.patient_case_edit_fragment:
                return new PatientCaseEditFragment();
        }
      return null;
    }
}
