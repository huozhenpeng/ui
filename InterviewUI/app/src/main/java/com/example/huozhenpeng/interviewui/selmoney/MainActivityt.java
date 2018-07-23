package com.example.huozhenpeng.interviewui.selmoney;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.MToast;
import com.example.huozhenpeng.interviewui.R;

import java.util.ArrayList;
import java.util.List;
 
public class MainActivityt extends Activity {
 
	private ListView listview;
	private List<Integer> lists = new ArrayList<Integer>();
	private ListAdaptera adapter;
	private int position;
	private int top;
	private int itemHeight;
	private int height;
	private int deltaItemNum;// 差距条数
	private int remainder;// 余数
	// 全部以万为单位
	private int startMoney = 5;// 起投金额
	private int deltaMoney = 1;// 递增金额
	private int canInvestMoney = 1097;// 可投金额
	// 补一个头部
	private LinearLayout ll_head;
	// 补一个footer
	private LinearLayout ll_footer;
	// 静止之后实际的position
	private int actualPosition;
 
	@TargetApi(19)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selmoney);
		itemHeight = (int) getResources().getDimension(R.dimen.px2dp_26);
		height = (int) getResources().getDimension(R.dimen.px2dp_880);
		initHead();
		initFooter();
		// 算出总共有多少个实际的格子（可以滑动到中间位置上的）
		for (int i = startMoney; i <= canInvestMoney; i += deltaMoney) {
			lists.add(i);
		}
		adapter = new ListAdaptera(this, lists);
		listview = (ListView) this.findViewById(R.id.listview);
		listview.addHeaderView(ll_head);
		listview.addFooterView(ll_footer);
		listview.setAdapter(adapter);
		listview.setOnItemSelectedListener(new OnItemSelectedListener() {
 
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
 
			}
 
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
 
			}
		});
		listview.setOnScrollListener(new OnScrollListener() {
 
			@SuppressLint("NewApi")
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case SCROLL_STATE_FLING:// 手指离开屏幕后，惯性滑动
 
					break;
				case SCROLL_STATE_IDLE:// 滑动后静止
					position = listview.getFirstVisiblePosition();// 第几个item
					top = getViewByPosition(position, listview).getTop();
					if (position == 0) {
						if (top == 0 || -top <= itemHeight / 2)// 定位到起投金额
						{
//                                listview.setSelectionFromTop(1, (height - itemHeight) / 2);
							//滑动次数多了会崩
//                                listview.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        listview.smoothScrollToPositionFromTop(1, (height - itemHeight) / 2,500);
//                                    }
//                                });
							myRunnable.setPosition(1);
							myRunnable.setOffset((height - itemHeight) / 2);
							listview.post(myRunnable);
							actualPosition = 0;
						} else {
//                                listview.setSelectionFromTop(-(top + itemHeight / 2) / itemHeight + 2,
//                                        (height - itemHeight) / 2);
//                                listview.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        listview.smoothScrollToPositionFromTop(-(top + itemHeight / 2) / itemHeight + 2,
//                                                (height - itemHeight) / 2,500);
//
//                                    }
//                                });
							myRunnable.setPosition(-(top + itemHeight / 2) / itemHeight + 2);
							myRunnable.setOffset((height - itemHeight) / 2);
							listview.post(myRunnable);
							actualPosition = -(top + itemHeight / 2) / itemHeight + 1;
						}
					} else {
						deltaItemNum = (height / 2 - (itemHeight + top)) / itemHeight;
//                            listview.setSelectionFromTop(position + deltaItemNum + 1, (height - itemHeight) / 2);
//                            listview.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    listview.smoothScrollToPositionFromTop(position + deltaItemNum + 1, (height - itemHeight) / 2,500);
//                                }
//                            });
						myRunnable.setPosition(position + deltaItemNum + 1);
						myRunnable.setOffset((height - itemHeight) / 2);
						listview.post(myRunnable);
						actualPosition = position + deltaItemNum;
					}
					MToast.showToast(MainActivityt.this,
							lists.get(actualPosition) + "万");
//					showHighLight(actualPosition, listview);
					break;
					// 手指在屏幕上滑动
				case SCROLL_STATE_TOUCH_SCROLL:
					break;
 
				default:
					break;
				}
 
			}
 
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
 
			}
		});
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
 
	public View getViewByPosition(int pos, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition
				+ listView.getChildCount() - 1;
 
		if (pos < firstListItemPosition || pos > lastListItemPosition) {
			return listView.getAdapter().getView(pos, null, listView);
		} else {
			final int childIndex = pos - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}
 
	/**
	 * 添加辅助头部
	 */
	private void initHead() {
		ll_head = new LinearLayout(this);
		ll_head.setOrientation(LinearLayout.VERTICAL);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(
				AbsListView.LayoutParams.MATCH_PARENT,
				(height - itemHeight) / 2);
		ll_head.setLayoutParams(params);
		ll_head.addView(new View(this), 0, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, itemHeight / 2));
		int total = (height - itemHeight) / 2 / itemHeight + 1;
		View view = null;
		for (int i = 1; i <= total; i++) {
			if (i % 5 == 0) {
				view = LayoutInflater.from(this).inflate(R.layout.item_list3,
						null);
			} else {
				view = LayoutInflater.from(this).inflate(R.layout.item_list2,
						null);
			}
			ll_head.addView(view, 0);
		}
 
	}
 
	/**
	 * 添加辅助头部
	 */
	private void initFooter() {
		ll_footer = new LinearLayout(this);
		ll_footer.setOrientation(LinearLayout.VERTICAL);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(
				AbsListView.LayoutParams.MATCH_PARENT,
				(height - itemHeight) / 2);
		ll_footer.setLayoutParams(params);
		ll_footer.addView(new View(this), 0, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, itemHeight / 2));
		int total = (height - itemHeight) / 2 / itemHeight + 1;
		View view = null;
		for (int i = 1; i <= total; i++) {
 
			view = LayoutInflater.from(this).inflate(R.layout.item_list2, null);
			ll_footer.addView(view, 0);
		}
 
	}
 
	private void showHighLight(int pos, ListView listview) {
		View view = getViewByPosition(pos + 1, listview);
		TextView tv_left = (TextView) view.findViewById(R.id.tv_left);
		TextView tv_right = (TextView) view.findViewById(R.id.tv_right);
		if (tv_left != null) {
			tv_left.setTextColor(Color.parseColor("#fe7800"));
			tv_right.setTextColor(Color.parseColor("#fe7800"));
		}
	}

	private MyRunnable myRunnable = new MyRunnable();

	class MyRunnable implements Runnable {

		private int position;
		private int offset;

		public void setPosition(int position) {
			this.position = position;
		}

		public void setOffset(int offset) {
			this.offset = offset;
		}

		@Override
		public void run() {
			listview.smoothScrollToPositionFromTop(position, offset, 500);
		}
	}
 
}
