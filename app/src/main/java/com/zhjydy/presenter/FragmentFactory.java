package com.zhjydy.presenter;

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
        }
      return null;
    }
}
