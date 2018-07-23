package com.example.huozhenpeng.interviewui.leftrightbar;
 
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.MToast;
import com.example.huozhenpeng.interviewui.R;


/**
 * 单日收益
 * 
 * @author huozhenpeng
 * 
 */
public class SingleDayGainActivity extends Activity implements
		View.OnClickListener {
 
	private TextView title_txt;
	private ImageView left_img;
	private TextView right_txt;
	private ListView listview;
	private SingleGainAdapter adapter;
	private List<SingleGainBean> datas;
	private ScrollBarPic scrollBarPic;
	private ScrollBarBean scrollBarBean ;
	private ArrayList<ScrollPerBarBean> lists;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_singledaygain);
		initView();
		initEvent();
		initData();
	}
 
	private void initData() {
		title_txt.setText("单日收益");
		right_txt.setText("累计收益");
		scrollBarPic = (ScrollBarPic) this.findViewById(R.id.scrollBarPic);
		scrollBarBean = new ScrollBarBean();
		scrollBarBean.setTotal(100);
		lists = new ArrayList<ScrollPerBarBean>();
		lists.add(new ScrollPerBarBean("￥3,000.00", 0, "10-3"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 80, "10-4"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 90, "10-5"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 10, "10-6"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 100, "10-7"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-8"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-9"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-11"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-12"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-13"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-14"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-15"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-16"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-17"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-18"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-19"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-20"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-21"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 55, "10-22"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 56, "10-23"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 80, "10-24"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 90, "10-25"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-26"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 30, "10-27"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 20, "10-28"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 10, "10-29"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "10-30"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 80, "10-31"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 70, "11-1"));
		lists.add(new ScrollPerBarBean("￥3,000.00", 50, "11-2"));
		scrollBarBean.setLists(lists);
 
		for (int i = 0; i < 4; i++) {
			datas.add(new SingleGainBean(22.34f,
					"米多利产品米多利产品米多利产品米多利产品米多利产品米多利产品"));
		}
		adapter.notifyDataSetChanged();
		
	}
 
	private void initEvent() {
		title_txt.setOnClickListener(this);
		left_img.setOnClickListener(this);
		scrollBarPic.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						scrollBarPic.setDatas(scrollBarBean);
					}
				});
		scrollBarPic.setOnClickListener(new ScrollBarPic.OnClickListener() {
 
			@Override
			public void onclick(int i) {
				MToast.showToast(SingleDayGainActivity.this, ""+lists.get(i).getDate());
			}
		});
	}
 
	private void initView() {
		title_txt = (TextView) this.findViewById(R.id.title_txt);
		left_img = (ImageView) this.findViewById(R.id.left_img);
		right_txt = (TextView) this.findViewById(R.id.right_txt);
		right_txt.setVisibility(View.INVISIBLE);
		listview = (ListView) this.findViewById(R.id.listview);
		scrollBarPic=(ScrollBarPic) this.findViewById(R.id.scrollBarPic);
		datas = new ArrayList<SingleGainBean>();
		adapter = new SingleGainAdapter(datas, this);
		listview.setAdapter(adapter);
	}
 
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_img:
			finish();
			break;
		case R.id.right_txt:
			break;
 
		default:
			break;
		}
	}
}
