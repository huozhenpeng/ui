package com.example.huozhenpeng.interviewui.selmoney;
 
import java.util.List;
 
import android.content.Context;
import android.content.ClipData.Item;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.R;

/**
 * 立即投资页面刻度 本来觉得不用复用了，结果发现会卡死，还是得复用
 * 
 * @author huozhenpeng
 * 
 */
public class ListAdaptera extends BaseAdapter {
 
	private Context context;
	private List<Integer> lists;
	private static final int TYPE_ITEM_FIRST = 0;
	private static final int TYPE_ITEM_SECOND = 1;
	private static final int TYPE_ITEM_THREE = 2;
 
	public ListAdaptera(Context context, List<Integer> lists) {
		this.context = context;
		this.lists = lists;
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
	public int getItemViewType(int position) {
		if (position == 0 || position % 10 == 0) {
			return TYPE_ITEM_FIRST;
		} else if (position % 5 == 0) {
			return TYPE_ITEM_SECOND;
		} else {
			return TYPE_ITEM_THREE;
		}
	}
 
	@Override
	public int getViewTypeCount() {
		return 3;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TYPE_ITEM_FIRST:
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_list, null);
				viewHolder.tv_left = ((TextView) convertView
						.findViewById(R.id.tv_left));
				viewHolder.tv_right = ((TextView) convertView
						.findViewById(R.id.tv_right));
				convertView.setTag(viewHolder);
				break;
			case TYPE_ITEM_SECOND:
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_list3, null);
				break;
			case TYPE_ITEM_THREE:
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_list2, null);
				break;
			default:
				break;
			}
		}
		switch (type) {
		case TYPE_ITEM_FIRST:
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.tv_left.setText(lists.get(position) + "万");
			viewHolder.tv_right.setText(lists.get(position) + "万");
			break;
		case TYPE_ITEM_SECOND:
			break;
		case TYPE_ITEM_THREE:
			break;
		default:
			break;
		}
 
		return convertView;
	}
 
	final static class ViewHolder {
		TextView tv_left, tv_right;
	}
 
}
