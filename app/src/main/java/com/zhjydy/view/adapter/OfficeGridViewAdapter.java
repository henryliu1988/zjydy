package com.zhjydy.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/30 0030.
 */
public class OfficeGridViewAdapter extends ListViewAdapter<Map<String, Object>> {
   final static  int []colors = {
            Color.parseColor("#5D76F4"),
            Color.parseColor("#AFC853"),
            Color.parseColor("#4FB5DD"),
            Color.parseColor("#E6BB2E"),
            Color.parseColor("#4ED2AB"),
            Color.parseColor("#E25365"),
            Color.parseColor("#7DCF60"),
            Color.parseColor("#C95582"),
            Color.parseColor("#5D76F4"),
            Color.parseColor("#AFC853"),
            Color.parseColor("#E6BB2E"),
            Color.parseColor("#4ED2AB"),
            Color.parseColor("#E25365")
    };
    public OfficeGridViewAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.gridview_office_item_layout);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //初始化ViewHolder,使用通用的ViewHolder，一行代码就搞定ViewHolder的初始化咯
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);//layoutId就是单个item的布局
        String officeName = Utils.toString(getItem(position).get("name"));
        ((TextView) holder.getView(R.id.office_text)).setText(officeName);
        if (position < colors.length){
            ((TextView) holder.getView(R.id.office_text)).setBackgroundColor(colors[position]);
        } else {
            ((TextView) holder.getView(R.id.office_text)).setBackgroundColor(colors[position%(colors.length -1)]);
        }

        return holder.getConvertView(); //这一行的代码要注意了
    }

    @Override
    public void convert(ViewHolder holder, Map<String, Object> data) {
    }
}
