package com.example.huozhenpeng.interviewui.barlink;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.huozhenpeng.interviewui.R;


/**
 * 
 * @author huozhenpeng
 * 
 */
public class RotationView extends android.support.v7.widget.AppCompatImageView {
	private int width;
	private int height;
	private Paint paint;
	private Paint virtualPaint;
	private RectF rectf;
	private int paintWidth;
	private int margin;
	private double startAngle;// 记录开始角度
	private Matrix matrix;
	private double totalRotation;
	private double tempRotation;
	private int padding;// 设置padding或者leftpadding
	private boolean isFirst = true;
	private Bitmap imageScaled;
	private Canvas mCanvas;
	private TextPaint textpaint;
	private int textsize = 50;
	private FontMetrics fontMetrics;
	private List<AccountAsset> datas;
	private AccountAsset accountAsset;
	public  int currentIndex = 0;
	private Context context;

	public RotationView(Context context) {
		this(context, null);
	}

	public RotationView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RotationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
		init();
	}

	public void init() {
		this.setScaleType(ScaleType.MATRIX);
		paintWidth=(int) context.getResources().getDimension(R.dimen.px2dp_100);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		paint.setStrokeWidth(paintWidth);
		paint.setStyle(Style.STROKE);
		
		virtualPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		virtualPaint.setStrokeWidth(paintWidth);
		virtualPaint.setStyle(Style.FILL);
		textpaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		textpaint.setTextSize(textsize);
		textpaint.setTextAlign(Align.CENTER);
		if (matrix == null) {
			matrix = new Matrix();
		} else {
			matrix.reset();
		}
		setImageMatrix(matrix);
		
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(heightMeasureSpec, heightMeasureSpec);//6.0系统不起作用,所以宽度也写死
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
		padding = getPaddingLeft();
		margin = padding + paintWidth / 2;
		rectf = new RectF(margin, margin, width - margin, height - margin);
		imageScaled = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		mCanvas = new Canvas();
		mCanvas.setBitmap(imageScaled);
		fontMetrics = textpaint.getFontMetrics();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		if (datas != null) {
//			textpaint.setColor(Color.parseColor(datas.get(currentIndex)
//					.getColor()));
//			canvas.drawText(
//					datas.get(currentIndex).getShowData(),
//					width / 2,
//					height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2,
//					textpaint);
//		}

	}

	/**
	 * 得到（x,y）点的象限值
	 * 
	 * @return quadrant 1,2,3 or 4
	 */
	private static int getQuadrant(double x, double y) {
		if (x >= 0) {
			return y >= 0 ? 1 : 4;
		} else {
			return y >= 0 ? 2 : 3;
		}
	}

	/**
	 * 得到（x,y）点的角度
	 */
	private double getAngle(double x, double y) {
		x = x - (width / 2d);
		y = height - y - (height / 2d);// （就是wheelHeight/2-y，由于变为数学中的坐标系，所以相当于取了一下反）

		switch (getQuadrant(x, y)) {
		case 1:
			return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
		case 2:
			return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
		case 3:
			return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
		case 4:
			return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
		default:
			return 0;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startAngle = parseFormat(getAngle(event.getX(), event.getY()));
			break;

		case MotionEvent.ACTION_MOVE:
			if(datas!=null&&datas.size()>1)
			{
				double currentAngle = parseFormat(getAngle(event.getX(), event.getY()));
				Log.e("abc",currentAngle+"");
				double deltaDegrees=startAngle - currentAngle;
				totalRotation=totalRotation%360;
				//1. 控制角度旋转
				if(totalRotation<0)
				{
					totalRotation = 360 + tempRotation;
				}
				tempRotation=(totalRotation+deltaDegrees)%360;
				if (tempRotation < 0) {
					tempRotation = 360 + tempRotation;
				}
				if(datas.get(0).getPoint().getX3()<=tempRotation&&tempRotation<=(int)datas.get(1).getPoint().getX3())
				{
					rotateWheel((float) (startAngle - currentAngle));

				}
				else if(tempRotation>datas.get(1).getPoint().getX3())
				{
					rotateWheel((float)((datas.get(1).getPoint().getX3())-totalRotation));
				}
				//2.   360度旋转
//				rotateWheel((float) (startAngle - currentAngle));
				startAngle = currentAngle;

			}
			break;

		case MotionEvent.ACTION_UP:
			totalRotation = totalRotation % 360;
			if (totalRotation < 0) {
				totalRotation = 360 + totalRotation;
			}
			MyPoint point = null;
			for (int i = 0; i < datas.size(); i++) {
				point = datas.get(i).getPoint();
				if (point.getX1() < totalRotation
						&& totalRotation < point.getX2()) {
					rotateWheel((float) (point.getX3() - totalRotation));
					currentIndex = i;
				}

			}

			break;
		}

		return true;
		
	}

	/**
	 * 要旋转的角度,滑动扇形旋转
	 * 
	 * @param degrees
	 */
	public void rotateWheel(float degrees) {
		matrix.postRotate(degrees, width / 2, height / 2);
		setImageMatrix(matrix);
		totalRotation = parseFormat(totalRotation + degrees);
		//让listview联动
		if(onListViewMoveListener!=null)
		{
			double tempTotalRotation = totalRotation % 360;
			if (tempTotalRotation < 0) {
				tempTotalRotation = 360 + tempTotalRotation;
			}
			onListViewMoveListener.onMove(tempTotalRotation);
		}
		
	}
	/**
	 * 要旋转的角度,滑动listview调用
	 * 
	 * @param degrees
	 */
	public void rotateWheel3(float degrees) {
		matrix.postRotate(degrees, width / 2, height / 2);
		setImageMatrix(matrix);
		totalRotation = totalRotation + degrees;
	}
	
	
	
	/**
	 * 要旋转的角度
	 * 
	 * @param degrees
	 */
	public void rotateWheel2(float degrees) {
		matrix.postRotate(degrees, width / 2, height / 2);
		setImageMatrix(matrix);
	}

	/**
	 * 设置数据,寻找规律
	 * 
	 * degree x1 x2 x3
	 * 
	 * 0 90 0 90 45
	 * 
	 * 1 60 300 360 330
	 * 
	 * 2 50 250 300 275
	 * 
	 * 3 160 90 250 170
	 * 
	 * 
	 * @param datas
	 */
	public void setData(List<AccountAsset> datas) {
		this.datas = datas;
		// 为point属性赋值
		for (int i = 0; i < datas.size(); i++) {
			MyPoint point = new MyPoint();
			double x1 = 0;
			double x2 = 0;
			double x3 = 0;
			if (i == 0) {
				x1 = 0;
				x2 = datas.get(i).getDegrees();
				x3 = parseFormat(x2 / 2.0);
				point.set(x1, x2, x3);
			} else {
				for (int j = i + 1; j < datas.size(); j++) {
					x1 += datas.get(j).getDegrees();
				}
				x3 = parseFormat(x1 + datas.get(i).getDegrees() / 2.0
						+ datas.get(0).getDegrees());
				x1 += datas.get(0).getDegrees();
				x2 = x1 + datas.get(i).getDegrees();
				point.set(x1, x2, x3);
			}
			datas.get(i).setPoint(point);
		}


		init();
		if (isFirst) {
			if (datas != null) {
				int sumDegrees = 0;
				for (int i = 0; i < datas.size(); i++) {
					accountAsset = datas.get(i);
					paint.setColor(Color.parseColor(accountAsset.getColor()));
					virtualPaint.setColor(Color.parseColor(accountAsset.getVirtualColor()));
					mCanvas.drawArc(rectf, sumDegrees, accountAsset.getDegrees(),
							false, paint);
					mCanvas.drawArc(rectf, sumDegrees, accountAsset.getDegrees(),
							true, virtualPaint);

					sumDegrees += accountAsset.getDegrees();
				}
			}
		}
		isFirst = false;
		setImageBitmap(imageScaled);
		if (datas != null) {
			rotateWheel2((float)parseFormat(90-datas.get(0).getDegrees()/2));//初始旋转，让第一个元素位于底部
		}
		totalRotation=parseFormat(datas.get(0).getDegrees()/2);
	}
	public int getTotalDegrees()
	{
		return (int) totalRotation;
	}
	
	private OnListViewMoveListener onListViewMoveListener;
	public interface OnListViewMoveListener
	{
		void onMove(double degrees);
	}
	/**
	 * 控制listview联动
	 */
	public void setListViewMoveListener(OnListViewMoveListener onListViewMoveListener)
	{
		this.onListViewMoveListener=onListViewMoveListener;
	}
	
	private ListView listview;
	public void setListView(ListView listview)
	{
		this.listview=listview;
	}
	
	public double getTotalRotation()
	{
		double tempTotalRotation = totalRotation % 360;
		if (tempTotalRotation < 0) {
			tempTotalRotation = 360 + tempTotalRotation;
		}
		return tempTotalRotation;
	}

	public double parseFormat(double value)
	{
		return Double.parseDouble(String.format("%.1f", value));
	}

}
