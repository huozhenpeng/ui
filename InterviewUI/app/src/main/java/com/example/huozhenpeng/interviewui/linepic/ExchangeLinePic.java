package com.example.huozhenpeng.interviewui.linepic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader.TileMode;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.example.huozhenpeng.interviewui.R;

import java.util.ArrayList;
import java.util.List;

public class ExchangeLinePic extends View implements Runnable {
    private int vHeight;
    private int vWidth;
    private Paint pathPaint;// 路径画笔，就是折线
    private Path path;// line路径
    private Path regionPath;// 闭合区域路径
    private Paint regionPaint;// 区域画笔，画渐变区域
    private Paint verticalLinePaint;// 跟随手指移动的线的画笔
    private Paint circlePaint;// 绘制小圆环画笔
    private Paint circlePoint;// 绘制小圆点画笔
    private List<CurveBean> originalData;
    private List<Float> XList = new ArrayList<Float>();
    private float surplusHeight;// 预留高度，用来显示底部刻度
    private float topsurplusHeight;// 顶部预留高度，为纵轴文字高度的一半
    private float total;// 代表竖直最大刻度
    // ---------------------------------------------------------
    private TextPaint verticalTextPaint;
    private Paint horiPaint;// 横轴刻度线画笔
    private TextPaint vertiPaint;// 纵轴文字画笔
    private Paint fillCirclePaint;//实心圆画笔
    private Paint flagPaint;


    private Paint backPaint;

    //跟ExchangeCurveActivity中的delta的取值相同
    private int delta;

    public ExchangeLinePic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ExchangeLinePic(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ExchangeLinePic(Context context) {
        this(context, null);
    }

    private int popHeight;
    public void setData(List<CurveBean> list,int popHeight) {
        this.popHeight=popHeight;
        isTouch=true;
        right=0;
        XList.clear();
        this.originalData = list;
        int length = list.size();
        float radius=largeRadius + getResources().getDimension(R.dimen.px2dp_4);
        for (int i = 0; i < length; i++) {
            XList.add((vWidth - maxLength - 2*radius) * 1.0f / (length - 1) * i
                    + maxLength+radius);
        }


        CurveBean curveBean = null;
        for (int i = 0; i < length; i++) {
            curveBean = originalData.get(i);
            curveBean.setActualData(curveBean.getActualData() - YMin);
            // 这个12.5是纵轴范围总和，到时候根据项目需要动态计算（7.5+5.0）
            curveBean.setActualHeight(total - (total - topsurplusHeight)
                    * curveBean.getActualData() / (YMax));
        }

        path.reset();
        regionPath.reset();
        path.moveTo(XList.get(0), originalData.get(0).getActualHeight());
        regionPath.moveTo(XList.get(0), originalData.get(0).getActualHeight());
        for (int i = 1; i < length; i++) {
            path.lineTo(XList.get(i), originalData.get(i).getActualHeight());
            regionPath.lineTo(XList.get(i), originalData.get(i)
                    .getActualHeight());
        }
        regionPath.lineTo(XList.get(length - 1), total);
        regionPath.lineTo(maxLength, total);
        regionPath.lineTo(XList.get(0), total);
        regionPath.close();
        invalidate();
    }


    @Override
    public void run() {
        try {
            while (right<=vWidth)
            {
                right+=5;
                Thread.sleep(5);
                postInvalidate();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        vHeight = h;
        vWidth = w-2;
        LinearGradient gradient = new LinearGradient(0, vHeight, 0, 0,
                Color.parseColor("#00ffffff"), Color.parseColor("#6629bbea"),
                TileMode.REPEAT);
//        regionPaint.setShader(gradient);
        regionPaint.setColor(Color.parseColor("#3fc9dffe"));
    }

    private DashPathEffect effects;

    private void init() {
        delta= (int) getResources().getDimension(R.dimen.px2dp_20);
        surplusHeight = getResources().getDimension(R.dimen.px2dp_30);
        verticalLength = getResources().getDimension(R.dimen.px2dp_15);
        largeRadius=getResources().getDimension(R.dimen.px2dp_8);
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Style.STROKE);// 不设置Style为STROKE，DashPathEffect不起作用
        pathPaint.setStrokeWidth(getResources().getDimension(R.dimen.px2dp_2));
        regionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        regionPaint.setStyle(Style.FILL);
        backPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setStyle(Style.FILL);
        backPaint.setColor(Color.parseColor("#e5e5e5"));
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Style.STROKE);
        circlePaint.setStrokeWidth(5);
        circlePoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePoint.setStrokeWidth(10);
        horiPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        horiPaint.setPathEffect(effects);
        horiPaint.setColor(Color.parseColor("#666666"));
        verticalLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        verticalLinePaint.setColor(Color.BLACK);
        verticalTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        verticalTextPaint.setTextSize(getResources().getDimension(
                R.dimen.px2dp_15));
        verticalTextPaint.setColor(Color.parseColor("#666666"));
        flagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        flagPaint.setColor(Color.parseColor("#369ed2"));
        flagPaint.setStrokeWidth(getResources().getDimension(R.dimen.px2dp_1));

        path = new Path();
        regionPath = new Path();
        pathPaint.setColor(Color.parseColor("#369ed2"));
        circlePaint.setColor(Color.parseColor("#29bbea"));
        circlePoint.setColor(Color.parseColor("#29bbea"));
        vertiPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        vertiPaint.setTextSize(getResources().getDimension(R.dimen.px2dp_15));
        vertiPaint.setColor(Color.parseColor("#666666"));
        fillCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillCirclePaint.setColor(Color.parseColor("#ffffff"));
        fillCirclePaint.setStyle(Style.FILL);

    }

    private float largeRadius = 15f;
    private float verticalDelta = 0;
    private float offsetDistance;
    private float verticalLength;
    private int right=0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#ffffff"));
       /* for(int i=0;i<6;i++)
        {
            canvas.drawLine(vWidth*1.0f/6*i,0,vWidth*1.0f/6*i,vHeight*1.0f,backPaint);
        }*/
        canvas.drawPath(regionPath, regionPaint);
        canvas.save();
        canvas.clipRect(0,0,right,vHeight);

        if (originalData != null) {
            verticalDelta = (total - topsurplusHeight)
                    / (descriptionLists.size() - 1);
            for (int i = 0; i < descriptionLists.size(); i++) {
                // 绘制横轴标识
                // 绘制横轴刻度线
                if (i == 0) {
                    horiPaint.setColor(Color.parseColor("#666666"));
                    horiPaint.setPathEffect(null);
                }
                else {
                    horiPaint.setColor(Color.parseColor("#cccccc"));
                    horiPaint.setPathEffect(effects);
                }
                canvas.drawLine(maxLength, total - verticalDelta * i, vWidth - largeRadius, total
                        - verticalDelta * i, horiPaint);
                canvas.drawText(descriptionLists.get(i), 0, total - verticalDelta
                        * i + offsetDistance, verticalTextPaint);
            }
            // 绘制纵轴刻度标识
            horiPaint.setPathEffect(null);
            for (int i = 0; i < verticalDesciptions.size(); i++) {

                // 绘制纵轴文字
                if (i == 0) {
                    vertiPaint.setTextAlign(Align.LEFT);
                    // 绘制纵轴线
                    canvas.drawLine(XList.get(0), total, XList.get(0), total
                            + verticalLength, horiPaint);
                    canvas.drawText(verticalDesciptions.get(i), XList.get(0), total
                                    + verticalLength + (horiTextHeight - horiOffsetDistance),
                            vertiPaint);
                } else if (i == 2) {
                    vertiPaint.setTextAlign(Align.RIGHT);
                    // 绘制纵轴线
                    canvas.drawLine(XList.get(XList.size() - 1), total, XList.get(XList.size() - 1), total
                            + verticalLength, horiPaint);
                    canvas.drawText(verticalDesciptions.get(i), XList.get(XList.size() - 1), total
                                    + verticalLength + (horiTextHeight - horiOffsetDistance),
                            vertiPaint);
                } else {
                    vertiPaint.setTextAlign(Align.CENTER);
                    // 绘制纵轴线
                    canvas.drawLine(XList.get(XList.size() % 2 == 0 ? (XList.size() / 2 - 1) : (XList.size() / 2)), total, XList.get(XList.size() % 2 == 0 ? (XList.size() / 2 - 1) : (XList.size() / 2)), total
                            + verticalLength, horiPaint);
                    canvas.drawText(verticalDesciptions.get(i), XList.get(XList.size() % 2 == 0 ? (XList.size() / 2 - 1) : (XList.size() / 2)), total
                                    + verticalLength + (horiTextHeight - horiOffsetDistance),
                            vertiPaint);
                }

            }

            // 绘制折线和渐变区域
            canvas.drawPath(path, pathPaint);
            if (!isTouch) {
                canvas.drawLine(X, topsurplusHeight-delta, X, total, flagPaint);
                //canvas.drawLine(X, topsurplusHeight, X, total, flagPaint);
                //canvas.drawLine(XList.get(0), Y, XList.get(XList.size() - 1), Y, flagPaint);
                canvas.drawCircle(X, Y, largeRadius + getResources().getDimension(R.dimen.px2dp_4), flagPaint);
                canvas.drawCircle(X, Y, largeRadius, fillCirclePaint);
            }
            //默认情况下，pop停留在最后
            /*if (isTouch) {
                canvas.drawCircle(XList.get(XList.size() - 1), originalData.get(XList.size() - 1).getActualHeight(), largeRadius + getResources().getDimension(R.dimen.px2dp_4), flagPaint);
                canvas.drawCircle(XList.get(XList.size() - 1), originalData.get(XList.size() - 1).getActualHeight(), largeRadius, fillCirclePaint);
                if(this.callBackInterface!=null)
                {
                   // this.callBackInterface.movePosition(XList.size()-1,XList.get(XList.size()-1),originalData.get(XList.size() - 1).getActualHeight(),total);
                    this.callBackInterface.movePosition(XList.size()-1,XList.get(XList.size()-1),topsurplusHeight,total);
                }
            }*/
        }
        canvas.restore();

    }

    private float X;
    private boolean isTouch = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        X = event.getX();
        getRegionY(X);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = false;
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                //控制下横轴滑动范围
                if(XList!=null&&XList.size()>1) {
                    if (X <= XList.get(0)) {
                        X = XList.get(0);
                    } else if (X >= XList.get(XList.size() - 1)) {
                        X = XList.get(XList.size() - 1);
                    }
                    getIndex(X);
                }
                break;
            case MotionEvent.ACTION_UP:
                /*isTouch=true;
                if(this.callBackInterface!=null)
                {
                    this.callBackInterface.dismiss();
                }*/
                //手指放开后，popupwindow恢复到最后位置上
                /*isTouch = true;
                if(this.callBackInterface!=null)
                {
                    //this.callBackInterface.movePosition(XList.size()-1,X,Y,total);
                    this.callBackInterface.movePosition(XList.size()-1,X,topsurplusHeight,total);
                }*/
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    private float Y;

    private void getRegionY(float x) {
        for (int i = 0; i < XList.size() - 1; i++) {
            if (x == XList.get(i) || x == XList.get(i + 1)
                    || (x > XList.get(i) && x < XList.get(i + 1))) {
                float X1 = XList.get(i);
                float Y1 = total - originalData.get(i).getActualHeight();
                float X2 = XList.get(i + 1);
                float Y2 = total - originalData.get(i + 1).getActualHeight();
                Y = total - (Y2 - Y1) / (X2 - X1) * x + (Y2 * X1 - Y1 * X2)
                        / (X2 - X1);
            }
        }

    }

    private List<String> descriptionLists = new ArrayList<String>();
    private float maxLength = 0;// 记录纵坐标的最大长度

    public void setDescriptionList(List<String> descriptionLists) {
        if(descriptionLists==null)
        {
            total=vHeight;
            topsurplusHeight=getResources().getDimension(R.dimen.px2dp_120);
            return;
        }
        this.descriptionLists = descriptionLists;
        String maxString = "";
        for (int i = 0; i < descriptionLists.size(); i++) {
            if (descriptionLists.get(i).length() > maxString.length()) {
                maxString = descriptionLists.get(i);
            }
        }
        maxLength = verticalTextPaint.measureText(maxString);
        topsurplusHeight = (verticalTextPaint.descent() - verticalTextPaint
                .ascent()) / 2;
        total = vHeight - surplusHeight;
        offsetDistance = (-verticalTextPaint.descent() - verticalTextPaint
                .ascent()) / 2;
    }

    private List<String> verticalDesciptions = new ArrayList<String>();

    private float horiTextHeight;
    private float horiOffsetDistance;

    public void setVetticalDesciptions(List<String> verticalDesciptions) {
        if(verticalDesciptions==null) {
            return;
        }
        this.verticalDesciptions = verticalDesciptions;
        horiTextHeight = (vertiPaint.descent() - vertiPaint.ascent());
        horiOffsetDistance = (-vertiPaint.descent() - vertiPaint.ascent()) / 2;
    }

    private float YMax;
    private float YMin;

    public void setYMax(float yMax, float yMin) {
        YMax = yMax;
        YMin = yMin;
    }

    public interface CallBackInterface
    {
        void movePosition(int index,float X,float Y,float total);
        void dismiss();
    }
    private CallBackInterface callBackInterface;
    public void setCallBackInterface(CallBackInterface callBackInterface)
    {
        this.callBackInterface=callBackInterface;
    }
    private void getIndex(float x)
    {
        int index=0;
        float temp=(XList.get(1)-XList.get(0))/2;
        for(int i=0;i<XList.size();i++)
        {
            if(i==0)
            {
                if(x<=XList.get(0)+temp)
                {
                    index=i;
                    break;
                }
            }
            if(x>=XList.get(i)-temp&&x<=XList.get(i)+temp)
            {
                index=i;
                break;
            }
            if(i==XList.get(XList.size()-1))
            {
                if(x>=XList.get(XList.size()-1)-temp)
                {
                    index=i;
                    break;
                }
            }
        }

        if(this.callBackInterface!=null)
        {
            /**
             * 需求已改，y轴固定
             */
            //this.callBackInterface.movePosition(index,X,Y,total);
            this.callBackInterface.movePosition(index,X,topsurplusHeight,total);
        }
    }


}
