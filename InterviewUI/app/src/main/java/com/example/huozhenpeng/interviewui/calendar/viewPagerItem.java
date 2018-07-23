package com.example.huozhenpeng.interviewui.calendar;
 
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
 
import java.util.List;
 
/**
 * Created by ${huozhenpeng} on 17/3/27.
 * Company : www.miduo.com
 */
 
public class viewPagerItem extends LinearLayout implements View.OnClickListener{
    private CalendarActivity context;
    private List<DateBean> lists;
    private int day;
    private int week;
    private int month;
    private int year;
    private int lastDay;
    private String[] nums;
 
    private List<Long> events;
 
 
 
 
    public List<DateBean> getLists() {
        return lists;
    }
 
    public void setLists(List<DateBean> lists) {
        this.lists = lists;
    }
 
    public int getDay() {
        return day;
    }
 
    public void setDay(int day) {
        this.day = day;
    }
 
    public int getWeek() {
        return week;
    }
 
    public void setWeek(int week) {
        this.week = week;
    }
 
    public int getMonth() {
        return month;
    }
 
    public void setMonth(int month) {
        this.month = month;
    }
 
    public int getYear() {
        return year;
    }
 
    public void setYear(int year) {
        this.year = year;
    }
 
    public int getLastDay() {
        return lastDay;
    }
 
    public void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }
 
    public String[] getNums() {
        return nums;
    }
 
    public void setNums(String[] nums) {
        this.nums = nums;
    }
 
    public viewPagerItem(Context context) {
        this(context,null);
    }
 
    public viewPagerItem(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }
 
    public viewPagerItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context= (CalendarActivity) context;
    }
    public void setData(List<DateBean> lists)
    {
        this.lists=lists;
        removeAllViews();
        for(int i=0;i<lists.size();i++)
        {
            DayItem dayItem=new DayItem(context);
            dayItem.setData(lists.get(i));
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight=1;
            dayItem.setLayoutParams(layoutParams);
            addView(dayItem);
            dayItem.setOnClickListener(this);
            dayItem.setTag(lists.get(i));
            DateBean dateBean=lists.get(i);
            if(context.getCurrentDate().equals(dateBean.getStrDate()))
            {
                context.setOldDayItem(dayItem);
                dateBean.setToday(true);
            }
            else
            {
                dateBean.setToday(false);
            }
            if(context.getEventList()!=null)
            {
                if (context.getEventList().contains(dateBean.getStrDate()))
                {
                    dateBean.setNeedFlag(true);
                }
            }
 
 
        }
    }
 
 
    @Override
    public void onClick(View v) {
 
        DateBean dateBean= (DateBean) v.getTag();
        Toast.makeText(context,dateBean.getDate(),Toast.LENGTH_SHORT).show();
        ((DateBean) v.getTag()).setToday(true);
        context.setCurrentDate(dateBean.getStrDate());
        v.invalidate();
        context.getOldDayItem().getData().setToday(false);
        context.getOldDayItem().invalidate();
 
        context.setOldDayItem((DayItem) v);
    }
}
