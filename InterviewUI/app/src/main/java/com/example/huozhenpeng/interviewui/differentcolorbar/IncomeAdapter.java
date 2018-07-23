package com.example.huozhenpeng.interviewui.differentcolorbar;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.FloatUtil;
import com.example.huozhenpeng.interviewui.R;
import com.example.huozhenpeng.interviewui.StringUtil;


public class IncomeAdapter extends BaseAdapter {

	private List<IncomeUser> list = null;
	private Context mContext;

	public IncomeAdapter(Context mContext, List<IncomeUser> incomeLst) {
		this.mContext = mContext;
		this.list = incomeLst;
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		IncomeUser input = list.get(position);
		// TextView name_txt, type_txt, amout_txt;
		// View line;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.income_item, parent,false);
			viewHolder.name_txt = (TextView) convertView.findViewById(R.id.name_txt);
			viewHolder.type_txt = (TextView) convertView.findViewById(R.id.type_txt);
			viewHolder.amout_txt = (TextView) convertView.findViewById(R.id.amout_txt);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (input != null) {
			viewHolder.name_txt.setText(StringUtil.showStringContent(input.getUsername()));
			viewHolder.amout_txt.setText(FloatUtil.toTwoDianStringSeparator(input.getAmount().doubleValue()));
			viewHolder.type_txt.setText(StringUtil.showStringContent(input.getIctype()));
		}
		return convertView;

	}

	final static class ViewHolder {
		TextView name_txt, type_txt, amout_txt;
	}

	public void dataChange(List<IncomeUser> inputLst) {
		this.list = inputLst;
		notifyDataSetChanged();
	}

}
