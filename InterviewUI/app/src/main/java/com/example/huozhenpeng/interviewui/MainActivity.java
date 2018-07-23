package com.example.huozhenpeng.interviewui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.barlink.AccountStewardActivity;
import com.example.huozhenpeng.interviewui.calendar.CalendarActivity;
import com.example.huozhenpeng.interviewui.differentcolorbar.MyIncomeActivity;
import com.example.huozhenpeng.interviewui.interactive.InteractiveActivity;
import com.example.huozhenpeng.interviewui.leftrightbar.SingleDayGainActivity;
import com.example.huozhenpeng.interviewui.linepic.ExchangeCurveActivity;
import com.example.huozhenpeng.interviewui.pulldownrefresh.PullDownActivity;
import com.example.huozhenpeng.interviewui.selmoney.MainActivityt;
import com.example.huozhenpeng.interviewui.wave.MainActivityc;

/**
 * 作者 huozhenpeng
 * 日期 2018/7/20
 * 邮箱 huohacker@sina.com
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_lrbar;
    private TextView tv_sel;
    private TextView tv_wave;
    private TextView tv_pie;
    private TextView tv_refresh;
    private TextView tv_calendar;
    private TextView tv_barpie;
    private TextView tv_linepie;
    private TextView tv_ftop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_lrbar=this.findViewById(R.id.tv_lrbar);
        tv_lrbar.setOnClickListener(this);

        tv_sel=this.findViewById(R.id.tv_sel);
        tv_sel.setOnClickListener(this);

        tv_wave=this.findViewById(R.id.tv_wave);
        tv_wave.setOnClickListener(this);

        tv_pie=this.findViewById(R.id.tv_pie);
        tv_pie.setOnClickListener(this);

        tv_refresh=this.findViewById(R.id.tv_refresh);
        tv_refresh.setOnClickListener(this);

        tv_calendar=this.findViewById(R.id.tv_calendar);
        tv_calendar.setOnClickListener(this);

        tv_barpie=this.findViewById(R.id.tv_barpie);
        tv_barpie.setOnClickListener(this);

        tv_linepie=this.findViewById(R.id.tv_linepie);
        tv_linepie.setOnClickListener(this);

        tv_ftop=this.findViewById(R.id.tv_ftop);
        tv_ftop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_lrbar:
                Intent intent1=new Intent(MainActivity.this, SingleDayGainActivity.class);
                startActivity(intent1);
                break;

            case R.id.tv_sel:
                Intent intent2=new Intent(MainActivity.this, MainActivityt.class);
                startActivity(intent2);
                break;

            case R.id.tv_wave:
                Intent intent3=new Intent(MainActivity.this, MainActivityc.class);
                startActivity(intent3);
                break;

            case R.id.tv_pie:
                Intent intent4=new Intent(MainActivity.this, AccountStewardActivity.class);
                startActivity(intent4);
                break;

            case R.id.tv_refresh:
                Intent intent5=new Intent(MainActivity.this, PullDownActivity.class);
                startActivity(intent5);
                break;

            case R.id.tv_calendar:
                Intent intent6=new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent6);
                break;

            case R.id.tv_barpie:
                Intent intent7=new Intent(MainActivity.this, MyIncomeActivity.class);
                startActivity(intent7);
                break;

            case R.id.tv_linepie:
                Intent intent8=new Intent(MainActivity.this, ExchangeCurveActivity.class);
                startActivity(intent8);
                break;

            case R.id.tv_ftop:
                Intent intent9=new Intent(MainActivity.this, InteractiveActivity.class);
                startActivity(intent9);
                break;



        }

    }
}
