package com.example.huozhenpeng.interviewui.leftrightbar;
 
import java.util.List;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.R;

/**
 * 但是收益listview的adapter
 * @author huozhenpeng
 *
 */
public class SingleGainAdapter extends BaseAdapter {
 
	private List<SingleGainBean> lists;
	private Context context;
 
	public SingleGainAdapter(List<SingleGainBean> lists, Context context) {
		super();
		this.lists = lists;
		this.context = context;
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
		ViewHolder viewholder=null;
		if(convertView==null)
		{
			convertView=LayoutInflater.from(context).inflate(R.layout.item_singlegain, null);
			viewholder=new ViewHolder();
			viewholder.tv_money=(TextView) convertView.findViewById(R.id.tv_money);
			viewholder.tv_proname=(TextView) convertView.findViewById(R.id.tv_proname);
			convertView.setTag(viewholder);
		}
		else
		{
			viewholder=(ViewHolder) convertView.getTag();
		}
		viewholder.tv_money.setText(lists.get(position).getMoney()+"");
		viewholder.tv_proname.setText(lists.get(position).getProductName());
		return convertView;
	}
 
	class ViewHolder {
		TextView tv_proname;
		TextView tv_money;
	}
 
}
