package com.example.huozhenpeng.interviewui.linepic;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.example.huozhenpeng.interviewui.JsonUtils;
import com.example.huozhenpeng.interviewui.R;
import com.example.huozhenpeng.interviewui.StringUtil;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/4/25.
 * 汇率曲线页面
 */

public class ExchangeCurveActivity extends Activity implements View.OnClickListener {
    private String exchangeCode1, exchangeCode2;
    //    private ExchangeCurrentBean exchangeCurrentBean;
    private TextView code_txt1, code_txt2;
    private TextView exchange_money_txt, time_txt;
    private ImageView leftImg;
    private TextView titleTxt;
    private CircleImageView current_img2, current_img1;

    private TextView tv_onemonth;
    private TextView tv_twomonth;
    private TextView tv_threemonth;
    private TextView tv_oneyear;

    private int width1_4;// 四分之一屏幕宽度（除去左右margin）

    private View tipsview;

    private ExchangeLinePic exchangelinepic;


    private PopupWindow mPop;

    private ImageView conversion_img;


    //popupwindow距离圆环中心向上，向右偏移的距离
    private int delta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initEvents();
        loadData();
    }


    private int width;

    protected void initViews() {
        setContentView(R.layout.activity_exchange_curve);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        width1_4 = (int) (dm.widthPixels - (2 * getResources().getDimension(R.dimen.px2dp_30))) / 4;
        leftImg = (ImageView) findViewById(R.id.left_img);
        titleTxt = (TextView) findViewById(R.id.title);
        tv_onemonth = (TextView) findViewById(R.id.tv_onemonth);
        tv_twomonth = (TextView) findViewById(R.id.tv_twomonth);
        tv_threemonth = (TextView) findViewById(R.id.tv_threemonth);
        tv_oneyear = (TextView) findViewById(R.id.tv_oneyear);
        tipsview = findViewById(R.id.tipsview);

        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) tipsview.getLayoutParams();
        if(params==null)
        {
            params=new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.px2dp_36),(int)getResources().getDimension(R.dimen.px2dp_4));

        }
        params.setMargins((int)((width1_4-getResources().getDimension(R.dimen.px2dp_36))/2+getResources().getDimension(R.dimen.px2dp_30)),0,0,0);
        tipsview.setLayoutParams(params);
        exchangelinepic = (ExchangeLinePic) findViewById(R.id.exchangelinepic);
        code_txt1 = (TextView) findViewById(R.id.code_txt1);
        code_txt2 = (TextView) findViewById(R.id.code_txt2);
        exchange_money_txt = (TextView) findViewById(R.id.exchange_money_txt);
        time_txt = (TextView) findViewById(R.id.time_txt);
        current_img1 = (CircleImageView) findViewById(R.id.current_img1);
        current_img2 = (CircleImageView) findViewById(R.id.current_img2);
        delta = (int) getResources().getDimension(R.dimen.px2dp_20);

        conversion_img = (ImageView) findViewById(R.id.iv_conversion);
        initPopupWindow();
    }

    protected void initEvents() {
        leftImg.setOnClickListener(this);
        tv_onemonth.setOnClickListener(this);
        tv_twomonth.setOnClickListener(this);
        tv_threemonth.setOnClickListener(this);
        tv_oneyear.setOnClickListener(this);
        conversion_img.setOnClickListener(this);
        exchangelinepic.setCallBackInterface(new ExchangeLinePic.CallBackInterface() {
            @Override
            public void movePosition(int index, float X, float Y, float total) {
                CurveBean curveBean = lists.get(index);
                if (!mPop.isShowing()) {
                    if ((int) X > width - popupWidth/2) {
                        mPop.showAsDropDown(exchangelinepic, width - popupWidth, (int) -(total - Y + popupHeight + delta));
                    } else {
                        mPop.showAsDropDown(exchangelinepic, (int) X -popupWidth/2, (int) -(total - Y + popupHeight + delta));
                    }

                } else {
                    if ((int) X > width - popupWidth/2) {
                        mPop.update(exchangelinepic, width - popupWidth, (int) -(total - Y + popupHeight + delta), popupWidth, popupHeight);
                    } else {
                        mPop.update(exchangelinepic, (int) X -popupWidth/2, (int) -(total - Y + popupHeight + delta), popupWidth, popupHeight);
                    }
                }
                tv_data.setText(curveBean.getClose());
                tv_date.setText(curveBean.getDay());
            }

            @Override
            public void dismiss() {

            }
        });
    }



    protected void loadData() {
        Message message=Message.obtain();
        message.what=0xa1;
        message.obj=DataSource.getInstance().month;
        handler.sendMessageDelayed(message,200);

    }





    private List<CurveBean> lists;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0xa1:
                    String result = (String) msg.obj;
                    if (TextUtils.isEmpty(result)) {
                        return;
                    }
                    if (!StringUtil.isEmpty(result)) {
                        JSONObject jo = null;
                        try {
                            jo = new JSONObject(result);
                            int state = jo.getInt("state");
                            String resultMsg = jo.optString("msg");
                            String data = jo.optString("data");
                            if (state == 1) {
                                lists = JsonUtils.toBean(data, new TypeToken<List<CurveBean>>() {
                                }.getType());
                                if (lists == null || lists.size() == 0) {
                                    time_txt.setText(getResources().getString(R.string.default_value));
                                    exchange_money_txt.setText(getResources().getString(R.string.default_value));
                                    return;
                                }
                                showCurve();
                            } else {
                            }
                        } catch (Exception e) {
                        }
                    } else {
                    }
                    break;
                case 0xa2:
                    break;
            }
        }
    };

    private void showCurve() {
        time_txt.setText(StringUtil.showStringContent(lists.get(lists.size() - 1).getDay()));
        exchange_money_txt.setText(StringUtil.showStringContent(lists.get(lists.size() - 1).getClose()));
        //获取最小值和最大值
        float min = Float.parseFloat(lists.get(0).getClose());
        float max = Float.parseFloat(lists.get(0).getClose());
        for (int i = 0; i < lists.size(); i++) {
            CurveBean curveBean = lists.get(i);
            float temp = Float.parseFloat(curveBean.getClose());
            if (temp < min) {
                min = temp;
            }
            if (temp > max) {
                max = temp;
            }
            curveBean.setActualData(temp);
        }
        exchangelinepic.setDescriptionList(null);
        if (max > min) {
            exchangelinepic.setYMax(max - min, min);
        } else {
            exchangelinepic.setYMax(min, min / 2.0f);
        }
        exchangelinepic.setData(lists,popupHeight);
        new Thread(exchangelinepic).start();
        exchangelinepic.setVetticalDesciptions(null);
    }


    private int id = R.id.tv_onemonth;
    private boolean flag = true;
    private String interval = "m1";

    @Override
    public void onClick(View v) {
        Message message=Message.obtain();
        message.what=0xa1;
        switch (v.getId()) {
            case R.id.left_img:
                finish();
                break;
            case R.id.tv_onemonth:
                if (id == R.id.tv_onemonth) {
                    return;
                }
                id = R.id.tv_onemonth;
                changeLocation(0, tipsview);
                lastPosition = 0;
                interval = "m1";
                message.obj=DataSource.getInstance().month;
                handler.sendMessage(message);
                break;
            case R.id.tv_twomonth:
                if (id == R.id.tv_twomonth) {
                    return;
                }
                id = R.id.tv_twomonth;
                changeLocation(1, tipsview);
                lastPosition = width1_4;
                interval = "m3";
                message.obj=DataSource.getInstance().threemonth;
                handler.sendMessage(message);
                break;
            case R.id.tv_threemonth:
                if (id == R.id.tv_threemonth) {
                    return;
                }
                id = R.id.tv_threemonth;
                changeLocation(2, tipsview);
                lastPosition = width1_4 * 2;
                interval = "m6";
                message.obj=DataSource.getInstance().sixmonth;
                handler.sendMessage(message);
                break;
            case R.id.tv_oneyear:
                if (id == R.id.tv_oneyear) {
                    return;
                }
                id = R.id.tv_oneyear;
                changeLocation(3, tipsview);
                lastPosition = width1_4 * 3;
                interval = "y1";
                message.obj=DataSource.getInstance().year;
                handler.sendMessage(message);
                break;
        }
        if(mPop!=null&&mPop.isShowing())
        {
            mPop.dismiss();
        }

    }

    private int lastPosition;

    /**
     * 修改指示view的位置(这个)
     */
    private void changeLocation(int index, View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",
                lastPosition, width1_4 * index);
        animator.setDuration(500).start();
    }


    private View menuView;
    private TextView tv_data;
    private TextView tv_date;
    private int popupWidth;
    private int popupHeight;

    /**
     * 是相对于view的右下角的
     */
    public void initPopupWindow() {
        if (mPop == null) {
            menuView = LayoutInflater.from(this).inflate(R.layout.curvepopupwindow, null, true);
            tv_data = (TextView) menuView.findViewById(R.id.tv_data);
            tv_date = (TextView) menuView.findViewById(R.id.tv_date);

            menuView.measure(View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.AT_MOST));
            popupWidth = measureLayout(menuView);
            mPop = new PopupWindow(menuView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPop.setFocusable(false);
        }
    }


    private int measureLayout(View view) {
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            view.measure(View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.AT_MOST));
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY));
        }
        popupHeight = view.getMeasuredHeight();// popupHeight最终用来控制popupwindow的竖直位置
        return view.getMeasuredWidth();
    }
}
