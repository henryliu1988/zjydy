package com.zhjydy.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;

import java.util.List;
import java.util.Map;

public class HorizontalScrollViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Map<String, Object>> mUrls;

    public HorizontalScrollViewAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public HorizontalScrollViewAdapter(Context context, List<Map<String, Object>> urls) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mUrls = urls;
    }


    public int getCount() {
        return mUrls.size();
    }

    public Object getItem(int position) {
        return mUrls.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.horizontal_image_view, parent, false);
            viewHolder.mImg = (ImageView) convertView
                    .findViewById(R.id.image);
            viewHolder.mText = (TextView) convertView
                    .findViewById(R.id.text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageUtils.getInstance().displayFromRemote(Utils.toString(mUrls.get(position).get("url")), viewHolder.mImg);
        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView mText;
    }

}  
