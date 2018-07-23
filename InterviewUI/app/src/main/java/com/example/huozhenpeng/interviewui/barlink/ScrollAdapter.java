package com.example.huozhenpeng.interviewui.barlink;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.huozhenpeng.interviewui.FloatUtil;
import com.example.huozhenpeng.interviewui.R;

import java.math.BigDecimal;
import java.util.List;

public class ScrollAdapter extends BaseAdapter {
	private Context context;
	private List<AccountAsset> datas;

	public ScrollAdapter(Context context, List<AccountAsset> datas) {
		this.context = context;
		this.datas = datas;
	}

	public ScrollAdapter() {
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_scroll, null);
			viewHolder=new ViewHolder();
			viewHolder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_icon);
			viewHolder.tv_type= (TextView) convertView.findViewById(R.id.tv_type);
			viewHolder.tv_percent= (TextView) convertView.findViewById(R.id.tv_percent);
			viewHolder.tv_profit= (TextView) convertView.findViewById(R.id.tv_profit);
			viewHolder.tv_totalasset= (TextView) convertView.findViewById(R.id.tv_totalasset);
			viewHolder.rl_background= (RelativeLayout) convertView.findViewById(R.id.rl_background);
			viewHolder.view=convertView.findViewById(R.id.v_devider);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder= (ViewHolder) convertView.getTag();
		}
		viewHolder.rl_background.setBackgroundColor(Color.parseColor("#ffffff"));
		viewHolder.view.setBackgroundColor(Color.parseColor("#dcdcdc"));
		AccountAsset accountAsset=datas.get(position);
		switch (accountAsset.getAssetType())
		{
			case 1://银行存款
				viewHolder.tv_type.setText("银行存款");
				viewHolder.iv_icon.setImageResource(R.mipmap.account_cunkuan);
				break;
			case 2://p2p
				viewHolder.tv_type.setText("P2P");
				viewHolder.iv_icon.setImageResource(R.mipmap.account_ptwop);
				break;
			case 3://宝宝类
				viewHolder.tv_type.setText("宝宝类");
				viewHolder.iv_icon.setImageResource(R.mipmap.account_baobao);
				break;
			case 4://基金
				viewHolder.tv_type.setText("基金");
				viewHolder.iv_icon.setImageResource(R.mipmap.account_fund);
				break;
			case 5://股票
				viewHolder.tv_type.setText("股票");
				viewHolder.iv_icon.setImageResource(R.mipmap.account_stock);
				break;
			case 6://银行理财
				viewHolder.tv_type.setText("银行理财");
				viewHolder.iv_icon.setImageResource(R.mipmap.account_licai);
				break;
			case 7://信托
				viewHolder.tv_type.setText("信托/资管");
				viewHolder.iv_icon.setImageResource(R.mipmap.account_xintuo);
				break;
			case 8://自定义资产
				viewHolder.tv_type.setText("其他资产");
				viewHolder.iv_icon.setImageResource(R.mipmap.account_other);
				break;
			default:
				viewHolder.tv_type.setText(context.getString(R.string.default_value));
				viewHolder.iv_icon.setImageResource(R.mipmap.account_other);
				break;
		}
//		80c49c
		viewHolder.tv_profit.setTextColor(Color.parseColor("#333333"));
		try
		{
			if(accountAsset.getGroupYesterdayProfit()>0) {
				viewHolder.tv_profit.setTextColor(Color.parseColor("#e65452"));
				viewHolder.tv_profit.setText("+"+ FloatUtil.toTwoDianStringSeparator(accountAsset.getGroupYesterdayProfit()));
			}
			else if(accountAsset.getGroupYesterdayProfit()<0)
			{
				viewHolder.tv_profit.setTextColor(Color.parseColor("#80c49c"));
				viewHolder.tv_profit.setText(FloatUtil.toTwoDianStringSeparator(accountAsset.getGroupYesterdayProfit()));
			}
			else
			{
				viewHolder.tv_profit.setText(FloatUtil.toTwoDianStringSeparator(accountAsset.getGroupYesterdayProfit()));
			}
		}
		catch (Exception e)
		{
			viewHolder.tv_profit.setText(accountAsset.getGroupYesterdayProfit()+"");
		}

		try
		{
			viewHolder.tv_totalasset.setText(FloatUtil.toTwoDianStringSeparator(accountAsset.getGroupYesterdayAsset()));
		}
		catch (Exception e)
		{
			viewHolder.tv_totalasset.setText(accountAsset.getGroupYesterdayAsset()+"");
		}
		viewHolder.tv_percent.setText(new BigDecimal(accountAsset.getPercent()+"").movePointRight(2).setScale(2)+"%");
		return convertView;
	}


	public class ViewHolder
	{
		private ImageView iv_icon;
		private TextView tv_type,tv_percent,tv_profit,tv_totalasset;
		private RelativeLayout rl_background;
		private View view;
	}


}
