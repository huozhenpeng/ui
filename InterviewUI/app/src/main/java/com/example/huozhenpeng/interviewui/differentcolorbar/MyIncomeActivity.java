package com.example.huozhenpeng.interviewui.differentcolorbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.FloatUtil;
import com.example.huozhenpeng.interviewui.JsonUtils;
import com.example.huozhenpeng.interviewui.R;
import com.example.huozhenpeng.interviewui.StringUtil;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 新版：“我的收入”页面
 *
 * @author huozhenpeng
 */
public class MyIncomeActivity extends Activity implements android.view.View.OnClickListener {
	private ViewPager viewpager;
	private List<ScrollBarPic> viewList;
	private IndicatorView indicatorview;
	private ListView lv_order;
	private List<ProductIncomeType> orderLst;
	private ClassificationAdapter classificationAdapter;
	private LinearLayout ll_top;
	private TextView tv_title;
	private PopupWindow mPop;
	private NSingleGainResult.ScrollPerBarBean scrollPerBarBean;
	private ScrollBarPic bar;
	private int index;
	private int popupWidth;
	private int popupHeight;
	private int barbottomHeight;// popupwindow距离viewpager最底端的距离
	private int popupXdistance;// popupwindow与柱子的水平间距
	private int popupYdistance;// 用来控制向上滑动时，popupwindow的隐藏与显示
	private int lineDistance;// 刻度间距


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myincome);
		initView();
		initData();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void initView() {
		barbottomHeight = (int) getResources().getDimension(R.dimen.px2dp_30);
		popupXdistance = (int) getResources().getDimension(R.dimen.px2dp_8);
		lineDistance = (int) getResources().getDimension(R.dimen.px2dp_40);

		ll_top = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_myincome_head, null);
		tv_title = (TextView) ll_top.findViewById(R.id.tv_title);
		viewpager = (ViewPager) ll_top.findViewById(R.id.viewpager);
		indicatorview = (IndicatorView) ll_top.findViewById(R.id.indicatorview);
		lv_order = (ListView) this.findViewById(R.id.lv_order);
		// 添加头部
		// addHeadView();
		orderLst = new ArrayList<ProductIncomeType>();

	}

	private void initData() {
		viewList = new ArrayList<>();
		viewpager.setAdapter(pagerAdapter);
		indicatorview.setViewPager(viewpager, this);
		try {
			myIncomeResult=JsonUtils.toBean(result, new TypeToken<MyIncomeResult>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.sendEmptyMessageDelayed(0x01,200);
	}


	private String orderResult="{\n" +
			"  \"data\" : {\n" +
			"    \"insHasOrderCount\" : \"8\",\n" +
			"    \"insIncome\" : \"43,252.53\",\n" +
			"    \"insTotalIncome\" : \"261357,113.53\",\n" +
			"    \"insTotalOrderCount\" : \"22\",\n" +
			"    \"investHasOrderCount\" : \"8\",\n" +
			"    \"investIncome\" : \"3,252.53\",\n" +
			"    \"investTotalIncome\" : \"357,113.53\",\n" +
			"    \"investTotalOrderCount\" : \"22\",\n" +
			"    \"osHasOrderCount\" : \"0\",\n" +
			"    \"osTotalIncome\" : \"0.00\",\n" +
			"    \"osTotalOrderCount\" : \"0\",\n" +
			"    \"overseasIncome\" : \"0.00\"\n" +
			"  },\n" +
			"  \"state\" : 1\n" +
			"}";
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			switch (message.what) {
				case 0x01:
					List<ProductIncomeType> temOrderLst = null;
					IfaIncometype ifaIncometype = null;

					if (StringUtil.isEmpty(orderResult)) {
					} else {
						try {
							JSONObject jo = new JSONObject(orderResult);
							int state = jo.getInt("state");
							if (state == 1) {
								String data = jo.getString("data");
								if (!StringUtil.isEmpty(data)) {
									ifaIncometype = JsonUtils.toBean(data, new TypeToken<IfaIncometype>() {
									}.getType());
								}
							} else {
								String msg = jo.optString("msg");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					//构建集合
					if (ifaIncometype != null) {
						temOrderLst = new ArrayList<>();
						temOrderLst.add(new ProductIncomeType(0, ifaIncometype.getInvestTotalIncome(), ifaIncometype.getInvestIncome(), ifaIncometype.getInvestTotalOrderCount(), ifaIncometype.getInvestHasOrderCount()));
						temOrderLst.add(new ProductIncomeType(1, ifaIncometype.getInsTotalIncome(), ifaIncometype.getInsIncome(), ifaIncometype.getInsTotalOrderCount(), ifaIncometype.getInsHasOrderCount()));
						temOrderLst.add(new ProductIncomeType(2, ifaIncometype.getOsTotalIncome(), ifaIncometype.getOverseasIncome(), ifaIncometype.getOsTotalOrderCount(), ifaIncometype.getOsHasOrderCount()));
					}
					showData(temOrderLst);
					break;
				case 0x02:
					break;
				default:
					break;
			}
		}

	};


	protected void changeVisible() {
		if (ll_top != null && classificationAdapter != null)// 判断sumOrderAdapter是为了在加载数据完成之前不要执行这个方法
		{
			// 控制popupwindow的显示与隐藏
			if (ll_top.getBottom() <= popupYdistance) {
				mPop.dismiss();
			} else {
				if (!mPop.isShowing() && "月收入".equals(tv_title.getText().toString())) {
					initPopupWindow();
					if (this.index <= 3) {
						measureLayout(menuView);
						mPop.showAsDropDown(bar, scrollPerBarBean.getRightX() + popupXdistance, -popupHeight);
					} else {
						popupWidth = measureLayout(menuView);
						mPop.showAsDropDown(bar, scrollPerBarBean.getLeftX() - popupXdistance - popupWidth,
								-popupHeight);
					}
				}
			}
		}
	}

	private MyIncomeResult myIncomeResult;
	private String result="{\n" +
			"  \"data\" : {\n" +
			"    \"allowanceList\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"discountOrder\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"fundCommission\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"groupAward\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"incomeMonth\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 132000,\n" +
			"        \"yearIncomeDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 198000,\n" +
			"        \"yearIncomeDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 276000,\n" +
			"        \"yearIncomeDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 189000,\n" +
			"        \"yearIncomeDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 60000,\n" +
			"        \"yearIncomeDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 480000,\n" +
			"        \"yearIncomeDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"incomeOrder\" : [\n" +
			"      {\n" +
			"        \"halfYearResult\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearResult\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearResult\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearResult\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearResult\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearResult\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"insuranceCommission\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"overseasCommission\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"productCommission\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"reward\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"teamAward\" : [\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 22000,\n" +
			"        \"yearResultDate\" : \"2016-01\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 33000,\n" +
			"        \"yearResultDate\" : \"2015-12\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 46000,\n" +
			"        \"yearResultDate\" : \"2015-11\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 31500,\n" +
			"        \"yearResultDate\" : \"2015-10\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 10000,\n" +
			"        \"yearResultDate\" : \"2015-09\"\n" +
			"      },\n" +
			"      {\n" +
			"        \"halfYearMonthIncome\" : 80000,\n" +
			"        \"yearResultDate\" : \"2015-08\"\n" +
			"      }\n" +
			"    ]\n" +
			"  },\n" +
			"  \"state\" : 1\n" +
			"}";
	/**
	 * 初始化柱状图 刻度间隔为：max/250然后向上取整，再乘以50
	 */
	protected void initBarPic() {
		bar = new ScrollBarPic(MyIncomeActivity.this);
		ArrayList<NSingleGainResult.ScrollPerBarBean> list = new ArrayList<NSingleGainResult.ScrollPerBarBean>();
		ArrayList<String> tips = new ArrayList<String>();
		ArrayList<String> bubbleDescs = new ArrayList<String>();
		if (myIncomeResult.getData() == null) {
			return;
		}
		// 原来的月收入，相当于现在的总收入
		List<MyIncomeResult.DataEntity.IncomeMonthEntity> tempIncomes = myIncomeResult.getData().getIncomeMonth();
		// 原来的奖励
		List<MyIncomeResult.DataEntity.IncomeRewardEntity> tempOrders = myIncomeResult.getData().getImcomeReward();
		// 保险产品
		List<MyIncomeResult.DataEntity.InsuranceCommissionEntity> insuranceList = myIncomeResult.getData().getInsuranceCommission();
		// 米多奖励
		List<MyIncomeResult.DataEntity.IncomeRewardEntity> teamAwardList = myIncomeResult.getData().getReward();
		// 投资产品
		List<MyIncomeResult.DataEntity.ProductCommissionEntity> productCommissionList = myIncomeResult.getData().getProductCommission();
		// 海外产品
		List<MyIncomeResult.DataEntity.OverseasCommissionEntity> overseasList = myIncomeResult.getData().getOverseasCommission();
		//公募基金
		List<MyIncomeResult.DataEntity.FundEntity> fundList = myIncomeResult.getData().getFundCommission();
		//管理津贴
		List<MyIncomeResult.DataEntity.AllowanceEntity> allowanceEntityList=myIncomeResult.getData().getAllowanceList();
		double max = 0;
		for (int i = 0; i < tempIncomes.size(); i++) {
			if (tempIncomes.get(i).getHalfYearMonthIncome() > max) {
				max = tempIncomes.get(i).getHalfYearMonthIncome();
			}
		}
		double tempMax = max;
		if (max > 100)// 防止死循环（处理逻辑和ios一致）
		{
			tempMax = Math.ceil(max / 250) * 50 * 5;

			for (int i = 0; i < tempIncomes.size(); i++) {
//				tips.add(FloatUtil.toStringSeparator(tempMax / 5 * i));
				tips.add("");
			}
			// 和ios商量，最后决定除以4
			tips.add("");
		} else {
			for (int i = 0; i <= tempIncomes.size(); i++) {
//				tips.add(FloatUtil.toStringSeparator(25.0 * i));
				tips.add("");
			}
		}
		for (int i = 0; i < tempIncomes.size(); i++) {
			MyIncomeResult.DataEntity.IncomeMonthEntity incomeMonth = tempIncomes.get(i);
			MyIncomeResult.DataEntity.IncomeRewardEntity rewardEntity = tempOrders.get(i);
			MyIncomeResult.DataEntity.IncomeRewardEntity teamEntity = null;
			if (teamAwardList != null) {
				teamEntity = teamAwardList.get(i);// 团队
			}
			MyIncomeResult.DataEntity.OverseasCommissionEntity overEntity = null;
			if (overseasList != null) {
				overEntity = overseasList.get(i);// 海外
			}
			MyIncomeResult.DataEntity.InsuranceCommissionEntity insureEntity = null;
			if (insuranceList != null) {
				insureEntity = insuranceList.get(i);// 保险
			}
			MyIncomeResult.DataEntity.ProductCommissionEntity productEntity = null;
			if (productCommissionList != null) {
				productEntity = productCommissionList.get(i);// 投资
			}
			MyIncomeResult.DataEntity.FundEntity fundEntity = null;
			if (fundList != null) {
				fundEntity = fundList.get(i);//基金
			}
			MyIncomeResult.DataEntity.AllowanceEntity allowanceEntity=null;
			if(allowanceEntityList!=null)
			{
				allowanceEntity=allowanceEntityList.get(i);
			}
			NSingleGainResult.ScrollPerBarBean temp = new NSingleGainResult.ScrollPerBarBean();
			if (teamEntity != null) {
				temp.setTeamFloat(teamEntity.getHalfYearMonthIncome());
			}
			if (overEntity != null) {
				temp.setOverseaFloat(overEntity.getHalfYearMonthIncome());
			}
			if (insureEntity != null) {
				temp.setInsureFloat(insureEntity.getHalfYearMonthIncome());
			}
			if (productEntity != null) {
				temp.setProductFloat(productEntity.getHalfYearMonthIncome());
			}
			if (fundEntity != null) {
				temp.setFundFloat(fundEntity.getHalfYearMonthIncome());
			}
			if(allowanceEntity!=null)
			{
				temp.setAllowanceFloat(allowanceEntity.getHalfYearMonthIncome());
			}

			try {
				temp.setIcDate(incomeMonth.getYearIncomeDate().split("-")[1] + "月");
			} catch (Exception e) {
				temp.setIcDate(incomeMonth.getYearIncomeDate());
			}
			temp.setIcDateFloat(incomeMonth.getHalfYearMonthIncome());
			list.add(temp);
			bubbleDescs.add("  总收入￥" + FloatUtil.toTwoDianStringSeparator(incomeMonth.getHalfYearMonthIncome()) + "#"
					+ "(奖励" + "￥" + FloatUtil.toTwoDianStringSeparator(rewardEntity.getHalfYearMonthIncome()) + ")  ");
		}
		NSingleGainResult bean = new NSingleGainResult();
		bean.setData(list);
		// bean.setTotal((int) max);
		if (max > 100) {
			// bean.setTotal((int) (tempMax * divisor / 4 * 5));
			bean.setTotal((int) tempMax);
		} else {
			bean.setTotal(125);
		}
		bean.setBubbledesc(bubbleDescs);
		bean.setDescripions(tips);
		bar.setListener(new ScrollBarPicListener() {

			@Override
			public void onclick(NSingleGainResult.ScrollPerBarBean scrollPerBarBean, int index) {
				MyIncomeActivity.this.index = index;
				if (MyIncomeActivity.this.scrollPerBarBean != null) {
					if (scrollPerBarBean.getLeftX() == MyIncomeActivity.this.scrollPerBarBean.getLeftX()) {
						return;
					}
				}
				MyIncomeActivity.this.scrollPerBarBean = scrollPerBarBean;
				initPopupWindow();
				if (index <= 3) {
					measureLayout(menuView);
					mPop.showAsDropDown(bar, scrollPerBarBean.getRightX() + popupXdistance, -popupHeight);
				} else {
					popupWidth = measureLayout(menuView);
					mPop.showAsDropDown(bar, scrollPerBarBean.getLeftX() - popupXdistance - popupWidth, -popupHeight);
				}

			}
		});
		bar.setDatas(bean);
		new Thread(bar).start();
		viewList.add(bar);
		initbar( myIncomeResult.getData().getIncomeOrder(),false);
		initbar(myIncomeResult.getData().getDiscountOrder(),true);
		pagerAdapter.notifyDataSetChanged();

	}

	private void initbar(List<MyIncomeResult.DataEntity.IncomeOrderEntity> incomeOrderEntities, boolean discounted)
	{
		ScrollBarPic bar1 = new ScrollBarPic(MyIncomeActivity.this);
		ArrayList<NSingleGainResult.ScrollPerBarBean> list1 = new ArrayList<>();
		ArrayList<String> tips1 = new ArrayList<String>();
		ArrayList<String> bubbleDescs1 = new ArrayList<String>();
		List<MyIncomeResult.DataEntity.IncomeOrderEntity> tempResult = incomeOrderEntities;
		double max1 = 0;
		for (int i = 0; i < tempResult.size(); i++) {
			if (tempResult.get(i).getHalfYearResult() > max1) {
				max1 = tempResult.get(i).getHalfYearResult();
			}
		}
		double tempMax1 = max1;
		if (max1 > 100) {
			tempMax1 = Math.ceil(max1 / 250) * 50 * 5;
			for (int i = 0; i < tempResult.size(); i++) {
//				tips1.add(FloatUtil.toStringSeparator(tempMax1 / 5 * i));
				tips1.add("");
			}
			tips1.add("");
		} else {
			for (int i = 0; i <= tempResult.size(); i++) {
//				tips1.add(FloatUtil.toStringSeparator(25.0 * i));
				tips1.add("");
			}
		}
		for (int i = 0; i < tempResult.size(); i++) {
			MyIncomeResult.DataEntity.IncomeOrderEntity incomeMonth = tempResult.get(i);
			NSingleGainResult.ScrollPerBarBean temp = new NSingleGainResult.ScrollPerBarBean();
			try {
				temp.setIcDate(incomeMonth.getYearResultDate().split("-")[1] + "月");
			} catch (Exception e) {
				temp.setIcDate(incomeMonth.getYearResultDate());
			}
			temp.setIcDateFloat(incomeMonth.getHalfYearResult());
			list1.add(temp);
			if(discounted)
			{
				bubbleDescs1.add("    折标后业绩￥" + FloatUtil.toTwoDianStringSeparator(incomeMonth.getHalfYearResult()) + "    ");
			}
			else
			{
				bubbleDescs1.add("    折标前业绩￥" + FloatUtil.toTwoDianStringSeparator(incomeMonth.getHalfYearResult()) + "    ");
			}

		}
		NSingleGainResult bean1 = new NSingleGainResult();
		bean1.setData(list1);
		// bean1.setTotal((int) max1);
		if (max1 > 100) {
			// bean1.setTotal((int) (tempMax1 * divisor1 / 4 * 5));
			bean1.setTotal((int) tempMax1);
		} else {
			bean1.setTotal(125);
		}
		bean1.setDescripions(tips1);
		bean1.setBubbledesc(bubbleDescs1);
		bar1.setDatas(bean1);
		new Thread(bar1).start();
		viewList.add(bar1);
	}

	/**
	 * 展示数据
	 *
	 * @param temOrderLst
	 */
	private void showData(List<ProductIncomeType> temOrderLst) {
		if (temOrderLst != null && temOrderLst.size() > 0) {
			orderLst.addAll(temOrderLst);
			if (classificationAdapter == null) {
				classificationAdapter = new ClassificationAdapter(this, orderLst);
				if (myIncomeResult != null) {// 柱状图的初始化放在这儿，能看到动画效果
					initBarPic();
					addHeadView();
				}
				lv_order.setAdapter(classificationAdapter);
			} else {
				classificationAdapter.notifyDataSetChanged();
			}
			handler.sendEmptyMessageDelayed(0x03, 200);
		} else {
			if (classificationAdapter == null)// 为了刷新头部数据（这个是为了即使adaper的数据源为null时，也必须要刷新一下listview，否则头部数据显示不出来）
			{
				classificationAdapter = new ClassificationAdapter(this, orderLst);
				if (myIncomeResult != null) {// 柱状图的初始化放在这儿，能看到动画效果
					initBarPic();
					addHeadView();
					lv_order.setAdapter(classificationAdapter);
					classificationAdapter.notifyDataSetChanged();
				}
			}
			handler.sendEmptyMessageDelayed(0x03, 1000);
		}

	}

	/**
	 * 添加头部
	 */
	private void addHeadView() {
		lv_order.addHeaderView(ll_top);
	}


	PagerAdapter pagerAdapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public int getCount() {

			return viewList.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewList.get(position));

		}

		@Override
		public int getItemPosition(Object object) {

			return super.getItemPosition(object);
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return "title";
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewList.get(position));

			return viewList.get(position);
		}

	};

	@Override
	public void onClick(View v) {

	}

	/**
	 * IndicatorView 回调
	 *
	 * @param index
	 */
	public void setLabelText(int index) {
		switch (index) {
			case 0:
				tv_title.setText("月收入");
				initPopupWindow();
				if (this.index <= 3) {
					measureLayout(menuView);
					mPop.showAsDropDown(bar, scrollPerBarBean.getRightX() + popupXdistance, -popupHeight);
				} else {
					popupWidth = measureLayout(menuView);
					mPop.showAsDropDown(bar, scrollPerBarBean.getLeftX() - popupXdistance - popupWidth, -popupHeight);
				}
				break;
			case 1:
				tv_title.setText("月折标前业绩");
				if (mPop.isShowing()) {
					mPop.dismiss();
				}
				break;
			case 2:
				tv_title.setText("月折标后业绩");
				if (mPop.isShowing()) {
					mPop.dismiss();
				}
				break;
			default:
				break;
		}
	}


	// popupwindow布局
	private TextView tv_totalMoney;// 总收入
	private TextView tv_investproduct;// 投资产品
	private TextView tv_insureproduct;// 保险产品
	private TextView tv_seaproduct;// 海外产品
	private TextView tv_teamreward;// 米多奖励
	private TextView tv_allowance;//管理津贴
	private TextView tv_fund;
	private ViewGroup menuView;
	private LinearLayout ll_invest;
	private LinearLayout ll_insure;
	private LinearLayout ll_oversea;
	private LinearLayout ll_team;
	private LinearLayout ll_fund;
	private LinearLayout ll_allowance;

	/**
	 * 是相对于view的右下角的
	 */
	public void initPopupWindow() {
		if (mPop == null) {
			menuView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.popupwindow, null, true);
			tv_totalMoney = (TextView) menuView.findViewById(R.id.tv_totalMoney);
			tv_investproduct = (TextView) menuView.findViewById(R.id.tv_investproduct);
			tv_insureproduct = (TextView) menuView.findViewById(R.id.tv_insureproduct);
			tv_seaproduct = (TextView) menuView.findViewById(R.id.tv_seaproduct);
			tv_teamreward = (TextView) menuView.findViewById(R.id.tv_teamreward);
			tv_allowance= (TextView) menuView.findViewById(R.id.tv_allowance);
			tv_fund = (TextView) menuView.findViewById(R.id.tv_fund);
			ll_invest = (LinearLayout) menuView.findViewById(R.id.ll_invest);
			ll_insure = (LinearLayout) menuView.findViewById(R.id.ll_insure);
			ll_oversea = (LinearLayout) menuView.findViewById(R.id.ll_oversea);
			ll_team = (LinearLayout) menuView.findViewById(R.id.ll_team);
			ll_fund = (LinearLayout) menuView.findViewById(R.id.ll_fund);
			ll_allowance= (LinearLayout) menuView.findViewById(R.id.ll_allowance);
			menuView.measure(MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.AT_MOST),
					MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.AT_MOST));
			popupWidth = measureLayout(menuView);
			mPop = new PopupWindow(menuView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			mPop.setFocusable(false);
		}

		if (scrollPerBarBean != null) {
			tv_totalMoney.setText("￥" + FloatUtil.toTwoDianStringSeparator(scrollPerBarBean.getIcDateFloat()));
			tv_investproduct.setText("￥" + FloatUtil.toTwoDianStringSeparator(scrollPerBarBean.getProductFloat()));
			tv_insureproduct.setText("￥" + FloatUtil.toTwoDianStringSeparator(scrollPerBarBean.getInsureFloat()));
			tv_seaproduct.setText("￥" + FloatUtil.toTwoDianStringSeparator(scrollPerBarBean.getOverseaFloat()));
			tv_teamreward.setText("￥" + FloatUtil.toTwoDianStringSeparator(scrollPerBarBean.getTeamFloat()));
			tv_fund.setText("￥" + FloatUtil.toTwoDianStringSeparator(scrollPerBarBean.getFundFloat()));
			tv_allowance.setText("￥" + FloatUtil.toTwoDianStringSeparator(scrollPerBarBean.getAllowanceFloat()));
			if (scrollPerBarBean.getProductFloat() == 0)// 投资
			{
				ll_invest.setVisibility(View.GONE);
			} else {
				ll_invest.setVisibility(View.VISIBLE);
			}
			if (scrollPerBarBean.getInsureFloat() == 0)// 保险
			{
				ll_insure.setVisibility(View.GONE);
			} else {
				ll_insure.setVisibility(View.VISIBLE);
			}
			if (scrollPerBarBean.getOverseaFloat() == 0)// 海外
			{
				ll_oversea.setVisibility(View.GONE);
			} else {
				ll_oversea.setVisibility(View.VISIBLE);
			}
			if (scrollPerBarBean.getTeamFloat() == 0)// 米多奖励
			{
				ll_team.setVisibility(View.GONE);
			} else {
				ll_team.setVisibility(View.VISIBLE);
			}
			if (scrollPerBarBean.getFundFloat() == 0)//基金
			{
				ll_fund.setVisibility(View.GONE);
			} else {
				ll_fund.setVisibility(View.VISIBLE);
			}
			if(scrollPerBarBean.getAllowanceFloat()==0)//管理津贴
			{
				ll_allowance.setVisibility(View.GONE);
			}
			else
			{
				ll_allowance.setVisibility(View.VISIBLE);
			}



		}

		if (mPop.isShowing()) {
			mPop.dismiss();
		}
	}

	private int measureLayout(View view) {
		if (android.os.Build.VERSION.SDK_INT >= 18) {
			view.measure(MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.AT_MOST),
					MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.AT_MOST));
		} else {
			view.measure(MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY));
		}
		popupHeight = view.getMeasuredHeight();// popupHeight最终用来控制popupwindow的竖直位置
		if (scrollPerBarBean.getActualHeight() - lineDistance <= popupHeight) {

			popupYdistance = (int) getResources().getDimension(R.dimen.px2dp_130) + popupHeight + lineDistance;
			// 挨着底部显示
			popupHeight = popupHeight + barbottomHeight + lineDistance;

		} else {
			popupYdistance = (int) getResources().getDimension(R.dimen.px2dp_130) + scrollPerBarBean.getActualHeight()
					/ 2 + popupHeight / 2 + lineDistance / 2;
			popupHeight = popupHeight + (scrollPerBarBean.getActualHeight() - lineDistance - popupHeight) / 2
					+ barbottomHeight + lineDistance;
		}
		return view.getMeasuredWidth();
	}

}
