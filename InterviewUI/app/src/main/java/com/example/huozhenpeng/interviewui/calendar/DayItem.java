package com.example.huozhenpeng.interviewui.calendar;
 
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.huozhenpeng.interviewui.R;

/**
 * Created by ${huozhenpeng} on 17/3/27.
 * Company : www.miduo.com
 */
 
public class DayItem extends View {
 
    private int width;
    private int height;
    private TextPaint textPaint;
    private Paint pointPaint;
 
    private Paint circlePaint;
    private int radius;
 
    private int circleWidth;
    private CalendarActivity context;
    public DayItem(Context context) {
        this(context,null);
    }
 
    public DayItem(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }
 
    public DayItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context= (CalendarActivity) context;
        init();
    }
 
    private void init()
    {
        textPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(context.getResources().getDimension(R.dimen.px2sp_24));
        pointPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.parseColor("#369ed2"));
        radius= (int) context.getResources().getDimension(R.dimen.px2dp_6);
        pointPaint.setStrokeWidth(radius);
 
        circlePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.parseColor("#ff0000"));
        circleWidth= (int) context.getResources().getDimension(R.dimen.px2dp_2);
        circlePaint.setStrokeWidth(circleWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
 
    }
 
    private int baseY;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
 
        //绘制文字
        // 计算Baseline绘制的Y坐标
        baseY = (int) ((height/ 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
 
        canvas.drawText(dateBean.getDate(), width/2, baseY, textPaint);
 
        //绘制标记
 
        if(dateBean.isNeedFlag()) {
            canvas.drawCircle(width / 2, height - radius, radius, pointPaint);
        }
 
        //绘制红色圆环
        if(dateBean.isToday())
        {
            if(width>height)
            {
                canvas.drawCircle(width/2,height/2,height/2-circleWidth/2,circlePaint);
            }
            else
            {
                canvas.drawCircle(width/2,height/2,width/2-circleWidth/2,circlePaint);
            }
 
        }
 
    }
 
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
    }
 
    private DateBean dateBean;
    public  void setData(DateBean dateBean)
    {
        this.dateBean=dateBean;
    }
 
 
    public DateBean getData()
    {
        return this.dateBean;
    }
 
}
