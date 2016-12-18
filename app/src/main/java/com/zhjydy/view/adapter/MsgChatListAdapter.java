package com.zhjydy.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class MsgChatListAdapter extends ListViewAdapter<Map<String, Object>> {


    public MsgChatListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_msg_item_layout);
    }

    @Override
    public void convert(ViewHolder holder, Map<String, Object> data) {
        String photo = Utils.toString(data.get("url"));
        String name = Utils.toString(data.get("sendname"));
        String content = Utils.toString(data.get("content"));
        String time = DateUtil.getTimeDiffDayCurrent(Utils.toLong(data.get("addtime")));
        ;
        ImageView imageView = (ImageView) holder.getView(R.id.image);
        ((TextView) holder.getView(R.id.msg_title)).setText(name);
        ((TextView) holder.getView(R.id.msg_content)).setText(content);
        if (!TextUtils.isEmpty(time)) {
            ((TextView) holder.getView(R.id.msg_time)).setText(time);
        }
        if (!TextUtils.isEmpty(photo)) {
            ImageUtils.getInstance().displayFromRemote(photo, imageView);
        } else {
            ImageUtils.getInstance().displayFromDrawable(R.mipmap.photo, imageView);
        }
    }
}
