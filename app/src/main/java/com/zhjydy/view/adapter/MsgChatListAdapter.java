package com.zhjydy.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.entity.NormalDicItem;
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
        int imageId = Utils.toInteger(data.get("image"));
        String title = Utils.toString(data.get("title"));
        String content = Utils.toString(data.get("content"));
        String time = Utils.toString(data.get("time"));
        if (imageId > 0) {
            ImageView imageView = (ImageView) holder.getView(R.id.image);
            ImageUtils.getInstance().displayFromDrawable(imageId, imageView);
            ((TextView) holder.getView(R.id.msg_title)).setText(title);
            ((TextView) holder.getView(R.id.msg_content)).setText(content);
            if (!TextUtils.isEmpty(time)) {
                ((TextView) holder.getView(R.id.msg_time)).setText(time);

            }
        }
    }
}
