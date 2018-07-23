package com.example.huozhenpeng.interviewui.interactive;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.R;

import java.util.List;
 
/**
 * Created by ${huozhenpeng} on 17/6/29.
 * Company : www.miduo.com
 */
 
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<String> lists;
    public MyAdapter(Context context, List<String> lists)
    {
        this.context=context;
        this.lists=lists;
 
    }
    @Override
    public int getCount() {
        return lists.size();
    }
 
    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.item_n,null);
        TextView textView= (TextView) convertView.findViewById(R.id.text);
        textView.setText(lists.get(position));
        return convertView;
    }
}
