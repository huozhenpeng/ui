package com.example.huozhenpeng.interviewui.wave;
 
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.R;

import java.util.ArrayList;
import java.util.List;
 
 
/**
 * Created by ${huozhenpeng} on 15/9/23.
 * Company : www.miduo.com
 */
public class MyAdapter1 extends BaseAdapter {
 
    private List<WaveBean>  list=new ArrayList<WaveBean>();
    private Context context;
 
 
    public MyAdapter1(List<WaveBean> list,Context context) {
        this.context=context;
        this.list = list;
    }
 
    @Override
    public int getCount() {
        return list.size();
    }
 
    @Override
    public Object getItem(int position) {
        return position;
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
 
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.waveitem,null);
            holder=new ViewHolder();
            holder.textView= (TextView) convertView.findViewById(R.id.textview);
            holder.wavetext=(WaveTextView) convertView.findViewById(R.id.waveText);
            new Thread(holder.wavetext).start();
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).getText());
        if(holder.wavetext==null)
        {
        	Log.e("abc", "nullllllll");
        }
        holder.wavetext.setText(list.get(position).getData());
        return convertView;
    }
 
    class  ViewHolder
    {
        private TextView textView;
        private WaveTextView wavetext;
    }
}
