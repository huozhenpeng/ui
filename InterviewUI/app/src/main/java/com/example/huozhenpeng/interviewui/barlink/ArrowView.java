package com.example.huozhenpeng.interviewui.barlink;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.huozhenpeng.interviewui.R;


public class ArrowView extends View {
	private Paint paint;
	private Paint virtualPaint;
	private int paintWidth = 2;
	private int width;
	private int height;
	private int delta ;
	private String color;
	private String virtualColor;
	private Path path;

	private Context context;
	public ArrowView(Context context) {
		this(context, null);
	}

	public ArrowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ArrowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
		init();
	}

	public void init() {
		delta= (int) context.getResources().getDimension(R.dimen.px2dp_20);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		paint.setStrokeWidth(paintWidth);

		virtualPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		virtualPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		path=new Path();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (TextUtils.isEmpty(color)) {
			paint.setColor(Color.parseColor("#ffffff"));
		} else {
			paint.setColor(Color.parseColor(color));
		}
		if(TextUtils.isEmpty(virtualColor))
		{
			virtualPaint.setColor(Color.parseColor("#ffffff"));
		}
		else
		{
			virtualPaint.setColor(Color.parseColor(virtualColor));
		}
		path.reset();
		path.moveTo(0,height * 1.0f / 4);
		path.lineTo(width * 1.0f / 2 - delta,height * 1.0f / 4);
		path.lineTo( width * 1.0f / 2,height * 1.0f / 6);
		path.lineTo(width * 1.0f / 2 + delta,
				height * 1.0f / 4);
		path.lineTo(width,height * 1.0f / 4);
		path.lineTo(width,height);
		path.lineTo(0,height);
		path.close();
		canvas.drawPath(path,virtualPaint);

		canvas.drawLine(0, height * 1.0f / 4, width * 1.0f / 2 - delta,
				height * 1.0f / 4, paint);
		canvas.drawLine(width * 1.0f / 2 - delta, height * 1.0f / 4,
				width * 1.0f / 2, height * 1.0f / 6, paint);
		canvas.drawLine(width * 1.0f / 2, height * 1.0f / 6, width * 1.0f / 2 + delta,
				height * 1.0f / 4, paint);
		canvas.drawLine(width * 1.0f / 2 + delta, height * 1.0f / 4, width,
				height * 1.0f / 4, paint);
		canvas.drawLine(width, height * 1.0f / 4, width, height, paint);
//		canvas.drawLine(width, height, 0, height, paint);//底边线
		canvas.drawLine(0, height, 0, height * 1.0f / 4, paint);


	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
	}

	public void setColor(String color) {
		this.color=color;
		invalidate();

	}

	public void setVirtualColor(String virtualColor) {
		this.virtualColor=virtualColor;
		invalidate();

	}

}
