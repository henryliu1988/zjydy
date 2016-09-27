package com.zhjydy.model.data;

import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.NormalDicItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class DicData {

    public static DicData instance = new DicData();

    public static DicData getInstance() {
        return instance;
    }


    public void init() {

    }

    public List<NormalDicItem> getOffice() {
        return new ArrayList<>();
    }

    public List<NormalDicItem> getProfession() {
        return new ArrayList<>();
    }

    public List<District> getDistrict() {
        return new ArrayList<>();
    }
}
