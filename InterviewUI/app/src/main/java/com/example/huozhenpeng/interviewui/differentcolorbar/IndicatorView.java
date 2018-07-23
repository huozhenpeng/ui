package com.example.huozhenpeng.interviewui.differentcolorbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;

import com.example.huozhenpeng.interviewui.R;


/**
 *
 * @author huozhenpeng
 *
 */
public class IndicatorView extends View {

	private ViewPager viewPager;
	private int width;//控件宽度
	private int height;//控件高度
	private int radius;//小圆半径
	private float margin;
	private Paint paint;
	private int index;
	private int innerMargin;//两个小圆之间的间隔
	public MyIncomeActivity activity;

	public IndicatorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public IndicatorView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public IndicatorView(Context context) {
		this(context, null);
	}

	@SuppressWarnings("deprecation")
	public void setViewPager(ViewPager viewPager,MyIncomeActivity activity) {
		this.viewPager = viewPager;
		this.activity=activity;
		this.viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				((ScrollBarPic)(IndicatorView.this.viewPager.getChildAt(position))).clearData();
				IndicatorView.this.activity.setLabelText(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,//当前页面偏移的百分比
									   int positionOffsetPixels) {//当前页面偏移的像素位置
				index=position;
				margin= positionOffset;
				invalidate();
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	private void init() {
		paint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
		paint.setColor(Color.BLUE);
		radius=(int) getContext().getResources().getDimension(R.dimen.px2dp_8);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//根据项目实际要求，传过来的mode应该都是EXACTLY,不处理测量逻辑了
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width=w;
		height=h;
		innerMargin=(w-6*radius)/2;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		canvas.drawCircle(radius, height/2, radius, paint);
		canvas.drawCircle(width/2, height/2, radius, paint);
		canvas.drawCircle(width-radius, height/2, radius, paint);
		paint.setStyle(Style.FILL);
		canvas.drawCircle(radius+(innerMargin+2*radius)*(margin+index), height/2, radius, paint);
	}

}
