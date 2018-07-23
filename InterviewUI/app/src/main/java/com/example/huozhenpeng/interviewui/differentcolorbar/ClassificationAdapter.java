package com.example.huozhenpeng.interviewui.differentcolorbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.huozhenpeng.interviewui.FloatUtil;
import com.example.huozhenpeng.interviewui.R;

import java.util.List;

public class ClassificationAdapter extends BaseAdapter {

    private List<ProductIncomeType> list = null;
    private Context mContext;

    public ClassificationAdapter(Context mContext, List<ProductIncomeType> productLst) {
        this.mContext = mContext;
        this.list = productLst;
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
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(
                R.layout.item_income, null);
        ImageView iv_icon= (ImageView) convertView.findViewById(R.id.iv_icon);
        TextView tv_protype= (TextView) convertView.findViewById(R.id.tv_protype);
        TextView tv_holdorder= (TextView) convertView.findViewById(R.id.tv_holdorder);
        TextView tv_totalorder= (TextView) convertView.findViewById(R.id.tv_totalorder);
        TextView tv_income= (TextView) convertView.findViewById(R.id.tv_income);
        TextView tv_totalincome= (TextView) convertView.findViewById(R.id.tv_totalincome);
        LinearLayout ll_invest= (LinearLayout) convertView.findViewById(R.id.ll_invest);
        TextView tv_incometip= (TextView) convertView.findViewById(R.id.tv_incometip);
        TextView tv_totalincometip= (TextView) convertView.findViewById(R.id.tv_totalincometip);
        TextView tv_tip= (TextView) convertView.findViewById(R.id.tv_text);
        ProductIncomeType productIncomeType=list.get(position);
        try{
            tv_income.setText(FloatUtil.toTwoDianStringSeparator(Double.parseDouble(productIncomeType.getProfit())));
        }
        catch (Exception e)
        {
            tv_income.setText(productIncomeType.getProfit());
        }
        try
        {
            tv_totalincome.setText(FloatUtil.toTwoDianStringSeparator(Double.parseDouble(productIncomeType.getTotalProfit())));
        }
        catch (Exception e)
        {
            tv_totalincome.setText(productIncomeType.getTotalProfit());
        }
        tv_holdorder.setText(productIncomeType.getHoldOrder());
        tv_totalorder.setText(productIncomeType.getTotalOrder());
        switch (productIncomeType.getType())
        {
            case 0://投资
                iv_icon.setImageResource(R.mipmap.insure_icon);
                tv_protype.setText("投资产品");
                tv_totalincometip.setText("折标后业绩");
                break;
            case 1://保险
                iv_icon.setImageResource(R.mipmap.insure_icon);
                tv_protype.setText("保险产品");
                tv_totalincometip.setText("总保费");
                break;
            case 2://海外
                iv_icon.setImageResource(R.mipmap.insure_icon);
                tv_protype.setText("海外产品");
                tv_totalincometip.setText("总成交额");
                break;
            default:
                break;

        }
        return convertView;
    }
}
