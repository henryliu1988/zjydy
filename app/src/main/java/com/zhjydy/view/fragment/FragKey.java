package com.zhjydy.view.fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/5 0005.
 */
public class FragKey {

    public static final int search_expert_fragment = 1001;
    public static final int search_info_fragment = 1002;

    public static final Map<Integer,String> FragMap = new HashMap<>();
    static
    {
        FragMap.put(search_expert_fragment, "search_expert_fragment");
        FragMap.put(search_info_fragment, "search_info_fragment");

    }
}
