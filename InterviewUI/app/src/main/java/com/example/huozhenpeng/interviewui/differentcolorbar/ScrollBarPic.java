package com.example.huozhenpeng.interviewui.differentcolorbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.huozhenpeng.interviewui.R;


/**
 * 在viewpager中直接放自定义View时，测量的问题？？？(这里没有处理测量逻辑，强制其高度与viewpager保持一致，宽度采用屏幕宽度)
 * 
 * @author huozhenpeng
 * 
 */
public class ScrollBarPic extends View implements Runnable {
	private int barPadding;// 柱子之间的间距
	private int barbottom;// 柱子的底端位置
	private int bartop;// 柱子的顶端位置
	private int perBarWidth;// 每个柱子的宽度
	// private int width;// 控件的宽度
	private int height;// 控件的高度
	private int defaultHeight;// 默认高度
	private NSingleGainResult scrollBarBean;
	private Paint barPaint;// 柱状图画笔
	private Paint barPaintTeam;// 团队奖励柱状图画笔
	private Paint barPaintSea;// 海外产品柱状图画笔
	private Paint barPaintInsure;// 保险产品柱状图画笔
	private Paint barPaintInvest;// 投资产品柱状图画笔
	private Paint barPaintfund;//基金画笔
	private Paint allowancePaint;//管理津贴画笔
	private Paint textPaint;
	private TextPaint textPaint1;// 灰色画笔
	private TextPaint textPaint2;// 蓝色画笔
	private FontMetrics metrics3;
	private FontMetrics metrics2;
	private int baseline3;
	private int baseline2;
	private LinearGradient linearGradient;
	private int blueDelta = 0;
	private boolean flag = false;
	private int w_screen;// 屏幕宽度
	private int h_screen_5;// 出去barbottom之外，五分之一控件高度
	private int textLeftMargin;// 刻度文字左边间距
	private int leftLinePadding;// 竖直刻度据左边间距
	private int deltaLength;// 绘制三角形使用
	private TextPaint textPaint3;// 气泡文字画笔
	private Paint paint;// 气泡背景画笔
	private Path path;
	private RectF bubbleRect;// 放置气泡文字测量结果
	private float textLength;
	private int bubbleTopPadding;// 控制气泡上下间距，左右间距通过给传进来的描述文字中加空格来控制
	private int bubblemargin;// 气泡距离柱子顶端的距离
	private ScrollBarPicListener listener;
	private Canvas mCanvas;
	private Bitmap bitmap;

	private int marginRignt;//viewpager 左margin
	private int marginLeft;// viewpager 右margin

	public ScrollBarPic(Context context) {
		this(context, null);
	}

	public ScrollBarPic(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollBarPic(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		marginLeft=marginRignt=(int) getContext().getResources().getDimension(
				R.dimen.px2dp_10);
		deltaLength = (int) getContext().getResources().getDimension(
				R.dimen.px2dp_10);
		height = (int) getContext().getResources().getDimension(
				R.dimen.px2dp_300);
		bubblemargin = (int) getContext().getResources().getDimension(
				R.dimen.px2dp_8);
		bartop = (int) getResources().getDimension(R.dimen.px2dp_70);
		barbottom = (int) getResources().getDimension(R.dimen.px2dp_30);
		h_screen_5 = (height - barbottom - bartop) / 5;
		defaultHeight = (int) getResources().getDimension(R.dimen.px2dp_1);
		barPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		barPaintInsure = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		barPaintInvest = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		barPaintfund = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		barPaintSea = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		barPaintTeam = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		allowancePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		linearGradient = new LinearGradient(0, height, 0, 0, Color.argb(255,
				144, 188, 236), Color.argb(255, 205, 226, 253),
				Shader.TileMode.REPEAT);
		// barPaint.setStrokeCap(Cap.SQUARE);//自己埋了个大炕
		textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		textPaint.setColor(Color.parseColor("#000000"));
		barPaint.setStrokeWidth(perBarWidth);
		barPaint.setColor(Color.parseColor("#a5cffd"));
		textPaint1 = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		textPaint1.setTextSize(getResources().getDimension(R.dimen.px2sp_20));
		textPaint1.setColor(Color.parseColor("#90bcec"));
		textPaint1.setTextAlign(Align.CENTER);
		textPaint2 = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		textPaint2.setTextSize(getResources().getDimension(R.dimen.px2dp_20));
		textPaint2.setColor(Color.parseColor("#90bcec"));
		textPaint2.setTextAlign(Align.RIGHT);
		textPaint3 = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		textPaint3.setTextSize(getResources().getDimension(R.dimen.px2sp_20));
		textPaint3.setColor(Color.parseColor("#333333"));
		paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		paint.setColor(Color.parseColor("#eaf3fe"));
		textLeftMargin = (int) getContext().getResources().getDimension(
				R.dimen.px2dp_10);
		metrics3 = textPaint3.getFontMetrics();
		metrics2 = textPaint2.getFontMetrics();
		baseline3 = (int) ((-metrics3.ascent - metrics3.descent) / 2);
		baseline2 = (int) ((-metrics2.ascent - metrics2.descent) / 2);
		bubbleRect = new RectF();
		path = new Path();
		bubbleTopPadding = (int) getContext().getResources().getDimension(
				R.dimen.px2dp_6);
		mCanvas = new Canvas();
		if (w_screen > 0 && height > 0) {
			bitmap = Bitmap.createBitmap(w_screen, height, Config.ARGB_8888);
			mCanvas.setBitmap(bitmap);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// width = w;
		barPaint.setStrokeWidth(perBarWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (scrollBarBean == null) {
			return;
		}
		NSingleGainResult.ScrollPerBarBean perBarBean = null;
		for (int i = 0; i < scrollBarBean.getData().size(); i++) {
			// 绘制竖直刻度竖直
			if (scrollBarBean.getDescripions().size() > 0
					&& i < scrollBarBean.getData().size() - 1) {
				canvas.drawLine(leftLinePadding, height - barbottom
						- h_screen_5 * i, w_screen - textLeftMargin, height
						- barbottom - h_screen_5 * i, textPaint1);
				if (i != 0) {
					// 第一个刻度不绘制
					canvas.drawText(scrollBarBean.getDescripions().get(i),
							marginLeft+leftLinePadding, height - barbottom - h_screen_5 * i
									+ metrics2.descent, textPaint2);
				}
			}

		}

		// 绘制竖直刻度
		canvas.drawLine(leftLinePadding, height - barbottom, leftLinePadding,
				bartop, textPaint1);
		canvas.drawBitmap(bitmap, 0, 0, null);
		for (int i = 0; i < scrollBarBean.getData().size(); i++) {
			perBarBean = scrollBarBean.getData().get(i);

			// if (perBarBean.isFlag())// 选中状态
			// {
			// barPaint.setShader(null);
			// barPaint.setColor(Color.parseColor("#e7d28c"));
			// textPaint1.setColor(Color.parseColor("#90bcec"));
			//
			// } else {
			// barPaint.setShader(linearGradient);
			// textPaint1.setColor(Color.parseColor("#90bcec"));
			// }
			// 日期
			canvas.drawText(
					perBarBean.getIcDate(),
					marginLeft+leftLinePadding + barPadding / 2 + perBarWidth / 2
							+ (barPadding + perBarWidth) * i,
					height - barbottom
							+ getResources().getDimension(R.dimen.px2dp_20)
							+ baseline2 + defaultHeight, textPaint1);
			barPaint.setColor(Color.parseColor("#a5cffd"));
			// 先绘制预留高度
			canvas.drawLine(leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
					+ (barPadding + perBarWidth) * i, height - barbottom,
					leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
							+ (barPadding + perBarWidth) * i, height
							- barbottom - defaultHeight, barPaint);
			// 绘制柱状图
			if(listener==null)//第二种样式（气泡）
			{
				if(perBarBean.isFlag())
				{
					barPaint.setColor(Color.parseColor("#e7d28c"));
				}
				else
				{
					barPaint.setColor(Color.parseColor("#a5cffd"));
				}
				canvas.drawLine(
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height - barbottom - defaultHeight,
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height
								- barbottom
								- (blueDelta < perBarBean.getActualHeight() ? blueDelta
										: perBarBean.getActualHeight())
								- defaultHeight, barPaint);
			}
			if(listener!=null)//第一种popupwindow
			{
			if (perBarBean.isFlag()) {
				//投资产品
				canvas.drawLine(
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height - barbottom - defaultHeight,
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height
								- barbottom
								- (blueDelta < perBarBean.getActualHeight() ? blueDelta
										: perBarBean.getActualHeight())
								- defaultHeight, barPaintInvest);
				//保险产品
				canvas.drawLine(
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height - barbottom - defaultHeight,
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height
								- barbottom
								- (blueDelta < (perBarBean.getActualHeight()-perBarBean.getProductHeight()) ? blueDelta
										: (perBarBean.getActualHeight()-perBarBean.getProductHeight()))
								- defaultHeight, barPaintInsure);
				//海外产品
				canvas.drawLine(
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height - barbottom - defaultHeight,
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height
								- barbottom
								- (blueDelta < (perBarBean.getFundHeight()+perBarBean.getTeamActualHeight()+perBarBean.getOverseaHeight()+perBarBean.getAllowanceHeight()) ? blueDelta
										: (perBarBean.getFundHeight()+perBarBean.getTeamActualHeight()+perBarBean.getOverseaHeight()+perBarBean.getAllowanceHeight()))
								- defaultHeight, barPaintSea);
				//公募基金
				canvas.drawLine(
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height - barbottom - defaultHeight,
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height
								- barbottom
								- (blueDelta <(perBarBean.getAllowanceHeight()+perBarBean.getTeamActualHeight()+perBarBean.getFundHeight()) ? blueDelta
								: (perBarBean.getAllowanceHeight()+perBarBean.getTeamActualHeight()+perBarBean.getFundHeight()))
								- defaultHeight, barPaintfund);


				//管理津贴
				canvas.drawLine(
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height - barbottom - defaultHeight,
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height
								- barbottom
								- (blueDelta <(perBarBean.getAllowanceHeight()+perBarBean.getTeamActualHeight()) ? blueDelta
								: (perBarBean.getAllowanceHeight()+perBarBean.getTeamActualHeight()))
								- defaultHeight, allowancePaint);

                //米多奖励
				canvas.drawLine(
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height - barbottom - defaultHeight,
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height
								- barbottom
								- (blueDelta <(perBarBean.getTeamActualHeight()) ? blueDelta
								: (perBarBean.getTeamActualHeight()))
								- defaultHeight, barPaintTeam);


			} else {
				canvas.drawLine(
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height - barbottom - defaultHeight,
						leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
								+ (barPadding + perBarWidth) * i,
						height
								- barbottom
								- (blueDelta < perBarBean.getActualHeight() ? blueDelta
										: perBarBean.getActualHeight())
								- defaultHeight, barPaint);
			}
			}
			// 绘制日期
			// canvas.drawText(perBarBean.getDate(), barPadding / 2 +
			// perBarWidth
			// / 2 + (barPadding + perBarWidth) * i, height - barbottom
			// - perBarBean.getActualHeight() - baseline1 - defaultHeight,
			// textPaint1);

		}
		canvas.drawBitmap(bitmap, 0, 0, null);

		// 绘制气泡
		if (scrollBarBean.getBubbledesc() != null) {
			for (int i = 0; i < scrollBarBean.getBubbledesc().size(); i++) {
				perBarBean = scrollBarBean.getData().get(i);
				path.reset();
				path.moveTo(leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
						+ (barPadding + perBarWidth) * i, height - barbottom
						- perBarBean.getActualHeight() - defaultHeight
						- bubblemargin);
				path.lineTo(leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
						+ (barPadding + perBarWidth) * i - deltaLength, height
						- barbottom - perBarBean.getActualHeight()
						- defaultHeight - deltaLength - bubblemargin);
				path.lineTo(leftLinePadding+marginLeft + barPadding / 2 + perBarWidth / 2
						+ (barPadding + perBarWidth) * i + deltaLength, height
						- barbottom - perBarBean.getActualHeight()
						- defaultHeight - deltaLength - bubblemargin);
				path.close();
				textLength = textPaint3.measureText(scrollBarBean
						.getBubbledesc().get(i));
				textPaint3.setColor(Color.parseColor("#333333"));
				if (perBarBean.isFlag() && listener == null) {// 根据listener来区分显示气泡的样式
					if (perBarBean.getLeftX() < textLength / 2) {
						// 从左边开始绘制气泡
						bubbleRect.left = textLeftMargin;
						bubbleRect.right = bubbleRect.left + textLength;
						bubbleRect.bottom = height - barbottom
								- perBarBean.getActualHeight() - defaultHeight
								- deltaLength - bubblemargin;
						bubbleRect.top = bubbleRect.bottom
								- (metrics3.bottom - metrics3.top) - 2
								* bubbleTopPadding;
						canvas.drawPath(path, paint);
						canvas.drawRoundRect(bubbleRect, defaultHeight,
								defaultHeight, paint);
						textPaint3.setTextAlign(Align.LEFT);
						if (scrollBarBean.getBubbledesc().get(i).contains("#")) {
							canvas.drawText(
									scrollBarBean.getBubbledesc().get(i)
											.split("#")[0],
									textLeftMargin,
									bubbleRect.top
											+ (bubbleRect.bottom - bubbleRect.top)
											/ 2 + baseline3, textPaint3);
							textPaint3.setColor(Color.parseColor("#777777"));
							canvas.drawText(
									scrollBarBean.getBubbledesc().get(i)
											.split("#")[1],
									textLeftMargin
											+ textPaint3
											.measureText(scrollBarBean
													.getBubbledesc()
													.get(i).split("#")[0]),
									bubbleRect.top
											+ (bubbleRect.bottom - bubbleRect.top)
											/ 2 + baseline3, textPaint3);
						} else {
							canvas.drawText(scrollBarBean.getBubbledesc()
									.get(i), textLeftMargin, bubbleRect.top
									+ (bubbleRect.bottom - bubbleRect.top) / 2
									+ baseline3, textPaint3);
						}
					} else if (w_screen - perBarBean.getRightX() < textLength / 2) {
						// 从右边开始绘制气泡
						bubbleRect.right = w_screen - textLeftMargin;
						bubbleRect.left = w_screen - textLength;
						bubbleRect.bottom = height - barbottom
								- perBarBean.getActualHeight() - defaultHeight
								- deltaLength - bubblemargin;
						bubbleRect.top = bubbleRect.bottom
								- (metrics3.bottom - metrics3.top) - 2
								* bubbleTopPadding;
						canvas.drawPath(path, paint);
						canvas.drawRoundRect(bubbleRect, defaultHeight,
								defaultHeight, paint);
						textPaint3.setTextAlign(Align.RIGHT);
						if (scrollBarBean.getBubbledesc().get(i).contains("#")) {
							textPaint3.setColor(Color.parseColor("#777777"));
							canvas.drawText(scrollBarBean.getBubbledesc()
									.get(i).split("#")[1], w_screen
									- textLeftMargin, bubbleRect.top
									+ (bubbleRect.bottom - bubbleRect.top) / 2
									+ baseline3, textPaint3);
							textPaint3.setColor(Color.parseColor("#333333"));
							canvas.drawText(
									scrollBarBean.getBubbledesc().get(i)
											.split("#")[0],
									w_screen
											- textLeftMargin
											- textPaint3
											.measureText(scrollBarBean
													.getBubbledesc()
													.get(i).split("#")[1]),
									bubbleRect.top
											+ (bubbleRect.bottom - bubbleRect.top)
											/ 2 + baseline3, textPaint3);
						} else {
							canvas.drawText(
									scrollBarBean.getBubbledesc().get(i),
									w_screen - textLeftMargin,
									bubbleRect.top
											+ (bubbleRect.bottom - bubbleRect.top)
											/ 2 + baseline3, textPaint3);
						}
					} else {
						// 从中间开始绘制气泡
						bubbleRect.left = perBarBean.getLeftX() + perBarWidth
								/ 2 - textLength / 2;
						bubbleRect.right = bubbleRect.left + textLength;
						bubbleRect.bottom = height - barbottom
								- perBarBean.getActualHeight() - defaultHeight
								- deltaLength - bubblemargin;
						bubbleRect.top = bubbleRect.bottom
								- (metrics3.bottom - metrics3.top) - 2
								* bubbleTopPadding;
						canvas.drawPath(path, paint);
						canvas.drawRoundRect(bubbleRect, defaultHeight,
								defaultHeight, paint);
						if (scrollBarBean.getBubbledesc().get(i).contains("#")) {
							textPaint3.setTextAlign(Align.LEFT);
							canvas.drawText(
									scrollBarBean.getBubbledesc().get(i)
											.split("#")[0],
									perBarBean.getLeftX()
											+ perBarWidth
											/ 2
											- textPaint3
											.measureText(scrollBarBean
													.getBubbledesc()
													.get(i)) / 2,
									bubbleRect.top
											+ (bubbleRect.bottom - bubbleRect.top)
											/ 2 + baseline3, textPaint3);
							textPaint3.setColor(Color.parseColor("#777777"));
							canvas.drawText(
									scrollBarBean.getBubbledesc().get(i)
											.split("#")[1],
									perBarBean.getLeftX()
											+ perBarWidth
											/ 2
											- textPaint3
											.measureText(scrollBarBean
													.getBubbledesc()
													.get(i))
											/ 2
											+ textPaint3
											.measureText(scrollBarBean
													.getBubbledesc()
													.get(i).split("#")[0]),
									bubbleRect.top
											+ (bubbleRect.bottom - bubbleRect.top)
											/ 2 + baseline3, textPaint3);
						} else {
							textPaint3.setTextAlign(Align.CENTER);
							canvas.drawText(scrollBarBean.getBubbledesc()
									.get(i), perBarBean.getLeftX()
									+ perBarWidth / 2, bubbleRect.top
									+ (bubbleRect.bottom - bubbleRect.top) / 2
									+ baseline3, textPaint3);
						}
					}

				}
			}
		}
		super.onDraw(canvas);
	}

	/**
	 * 得到柱子之间的宽度（父容器使用）
	 * 
	 * @return
	 */
	public int getPadding() {
		return barPadding;
	}

	/**
	 * 得到每个柱子的宽度（父容器使用）
	 * 
	 * @return
	 */
	public int getPerbarWidth() {
		return perBarWidth;
	}

	public void setDatas(NSingleGainResult scrollBarBean) {
		this.scrollBarBean = scrollBarBean;
		DisplayMetrics dm = getResources().getDisplayMetrics();
		w_screen = dm.widthPixels;
		init();
		if (scrollBarBean != null && scrollBarBean.getData().size() != 0) {
			NSingleGainResult.ScrollPerBarBean perBarBean = null;
			for (int i = 0; i < scrollBarBean.getData().size(); i++) {
				perBarBean = scrollBarBean.getData().get(i);
				perBarBean
						.setActualHeight((int) (perBarBean.getIcDateFloat()
								* (height - barbottom - bartop - defaultHeight) / scrollBarBean
								.getTotal()));
				// 设置 投资，保险，海外，团队的实际高度
				// 团队
				perBarBean.setTeamActualHeight((int) ((perBarBean.getTeamFloat()
                                        * perBarBean.getActualHeight() / perBarBean
                                        .getIcDateFloat())+0.5));
				// 海外
				perBarBean.setOverseaHeight((int) ((perBarBean.getOverseaFloat()
                                        * perBarBean.getActualHeight() / perBarBean
                                        .getIcDateFloat())+0.5));
				// 保险
				perBarBean.setInsureHeight((int) ((perBarBean.getInsureFloat()
                                        * perBarBean.getActualHeight() / perBarBean
                                        .getIcDateFloat())+0.5));
				// 投资
				perBarBean.setProductHeight((int) ((perBarBean.getProductFloat()
                                        * perBarBean.getActualHeight() / perBarBean
                                        .getIcDateFloat())+0.5));
				//基金
				perBarBean.setFundHeight((int) ((perBarBean.getFundFloat()
                                        * perBarBean.getActualHeight() / perBarBean
                                        .getIcDateFloat())+0.5));
				//管理津贴
				perBarBean.setAllowanceHeight((int) ((perBarBean.getAllowanceFloat()
                                        * perBarBean.getActualHeight() / perBarBean
                                        .getIcDateFloat())+0.5));
				if (!perBarBean.isFlag()) {
					if (i == scrollBarBean.getData().size() - 1) {
						perBarBean.setFlag(true);
					}
				}
			}
			String max = "";
			for (int i = 0; i < scrollBarBean.getDescripions().size(); i++)// 找出最长的字符串并测量其长度
			{
				if (scrollBarBean.getDescripions().get(i).length() > max
						.length()) {
					max = scrollBarBean.getDescripions().get(i);
				}

			}
			leftLinePadding = (int) (textPaint1.measureText(max) + getContext()
					.getResources().getDimension(R.dimen.px2dp_20));
			perBarWidth = (int) getContext().getResources().getDimension(
					R.dimen.px2dp_30);
			barPadding = (int) ((w_screen-marginLeft-marginRignt
					- leftLinePadding - 6 * perBarWidth) / 6.0);
		}
		barPaintInsure.setStrokeWidth(perBarWidth);
		barPaintInvest.setStrokeWidth(perBarWidth);
		barPaintfund.setStrokeWidth(perBarWidth);
		barPaintSea.setStrokeWidth(perBarWidth);
		barPaintTeam.setStrokeWidth(perBarWidth);
		allowancePaint.setStrokeWidth(perBarWidth);
		barPaintTeam.setColor(Color.parseColor("#ab53f2"));
		barPaintSea.setColor(Color.parseColor("#ffa14f"));
		barPaintInsure.setColor(Color.parseColor("#f6c54c"));
		barPaintInvest.setColor(Color.parseColor("#3abbee"));
		barPaintfund.setColor(Color.parseColor("#aecc6d"));
		allowancePaint.setColor(Color.parseColor("#f05385"));
		NSingleGainResult.ScrollPerBarBean perBarBean = null;
		for (int i = 0; i < scrollBarBean.getData().size(); i++) {
			perBarBean = scrollBarBean.getData().get(i);
			// 设置左边界
			perBarBean.setLeftX(marginLeft+leftLinePadding + barPadding / 2 + perBarWidth / 2
					+ (barPadding + perBarWidth) * i - perBarWidth / 2);
			// 设置右边界
			perBarBean.setRightX(marginLeft+leftLinePadding + barPadding / 2 + perBarWidth / 2
					+ (barPadding + perBarWidth) * i + perBarWidth / 2);
			// 设置上边界
			perBarBean.setTopY(height - perBarBean.getActualHeight()
					- barbottom - defaultHeight);
		}
		if (listener != null) {
			listener.onclick(perBarBean, scrollBarBean.getData().size() - 1);
		}
		clearData();
		invalidate();

	}

	int x = 0;
	int y = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = (int) event.getX();
			y = (int) event.getY();
			clickcallback();
			break;
		case MotionEvent.ACTION_MOVE:// 放在viewpager中，move事件和up事件都监听不到
			break;
		case MotionEvent.ACTION_UP:
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	public interface OnClickListener {
		void onclick(NSingleGainResult.ScrollPerBarBean perBarBean);
	}

	public void resetState() {
		NSingleGainResult.ScrollPerBarBean perBarBean = null;
		for (int i = 0; i < scrollBarBean.getData().size(); i++) {
			perBarBean = scrollBarBean.getData().get(i);
			perBarBean.setFlag(false);
		}
		if (perBarBean.isFlag()) {
			flag = true;
		}
		invalidate();
	}

	public void clickcallback() {
		NSingleGainResult.ScrollPerBarBean perBarBean = null;
		for (int i = 0; i < scrollBarBean.getData().size(); i++) {
			perBarBean = scrollBarBean.getData().get(i);
			if (perBarBean.getLeftX() < x && x < perBarBean.getRightX()
			// && y > perBarBean.getTopY() && y < height) {//控制点击高度的，不需要可以不写
					&& y < height) {
				perBarBean.setFlag(true);
				for (int j = 0; j < scrollBarBean.getData().size(); j++) {
					if (i != j) {
						scrollBarBean.getData().get(j).setFlag(false);
					}
				}
				if (listener != null) {
					listener.onclick(perBarBean, i);
				}
				invalidate();
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			if (flag) {
				blueDelta += 2;
			}

			try {
				postInvalidate();
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 清除数据
	 */
	public void clearData() {
		flag = true;
		blueDelta = 0;

	}

	public void setListener(ScrollBarPicListener listener) {
		this.listener = listener;
	}

}
