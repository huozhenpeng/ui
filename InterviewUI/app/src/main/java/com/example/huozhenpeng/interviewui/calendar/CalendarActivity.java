package com.example.huozhenpeng.interviewui.calendar;
 
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
 
public class CalendarActivity extends AppCompatActivity {
 
    private ViewPager viewPager;
    private CalendarAdapger calendarAdapger;
    private List<viewPagerItem> lists;
 
    private TextView tv_tip;
 
 
 
    private float lastValue;
    private boolean isRightToLeft = false;  //从右向左滑动
    private boolean isLeftToRight = false;  //从左向右滑动
 
    private String currentDate;
 
    private DayItem oldDayItem;
 
    private List<String> eventList;
 
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
 
        eventList=new ArrayList<>();
        eventList.add("20170308");//实际上是四月八号的事件
        eventList.add("20170303");
        eventList.add("20161222");
        eventList.add("20170403");
        eventList.add("20170508");
        eventList.add("20170512");
 
        viewPager= (ViewPager) findViewById(R.id.viewpater);
        tv_tip= (TextView) findViewById(R.id.tv_tip);
        lists=new ArrayList<>();
        Calendar calendar= Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int week=calendar.get(Calendar.DAY_OF_WEEK);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        currentDate=""+year+(month<10?"0"+month:month+"")+(day<10?"0"+day:day+"");
        int lastDay=getLastDay(calendar);
        String[] nums=makeViewpagerItem(day,week,lastDay,-1,month,year);
 
        tv_tip.setText(year+"年"+(month+1)+"月");
 
 
        //计算下一页数据
        if(TextUtils.isEmpty(nums[1])||Integer.parseInt(nums[1])==lastDay)
        {
            //下一月份
            //判断是不是该到下一年
            if(month==11)
            {
                //取下一年
                calendar.set(year+1,0,1);
            }
            else
            {
                calendar.set(year,month+1,1);
 
 
            }
        }
        else
        {
            //本月
            calendar.set(year,month,Integer.parseInt(nums[1])+1);
 
        }
        int day1=calendar.get(Calendar.DAY_OF_MONTH);
        int week1=calendar.get(Calendar.DAY_OF_WEEK);
        int month1=calendar.get(Calendar.MONTH);
        int year1=calendar.get(Calendar.YEAR);
        int lastDay1=getLastDay(calendar);
        String[] nums1=makeViewpagerItem(day1,week1,lastDay1,-1,month1,year1);
 
        //计算前一页数据
        if(TextUtils.isEmpty(nums[0])||Integer.parseInt(nums[0])==0)
        {
            //前一月份
            //判断是不是该到前一年
            if(month==0)
            {
                //取上一年
                calendar.set(year-1,11,1);
                int week2=calendar.get(Calendar.DAY_OF_WEEK);
                int lastDay2=getLastDay(calendar);
                int date=lastDay2-(week2-1);
                calendar.set(year-1,11,date);
            }
            else
            {
                calendar.set(Calendar.MONTH,month-1);
                int lastDay2=getLastDay(calendar);
                int week2=calendar.get(Calendar.DAY_OF_WEEK);
                int  date=lastDay2-(week2-1);
                calendar.set(year,month-1,date);
 
            }
        }
        else
        {
            //本月
            calendar.set(year,month,Integer.parseInt(nums[0])-1);
 
        }
        day=calendar.get(Calendar.DAY_OF_MONTH);
        week=calendar.get(Calendar.DAY_OF_WEEK);
        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);
        lastDay=getLastDay(calendar);
        makeViewpagerItem(day,week,lastDay,0,month,year);
 
 
 
        calendarAdapger=new CalendarAdapger(lists,this);
        viewPager.setAdapter(calendarAdapger);
        viewPager.setCurrentItem(Integer.MAX_VALUE/2+1);
 
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset == 0.0){
                    return;
                }
                if (lastValue > positionOffset) {
                    // 递减，从左向右滑动
                    isRightToLeft = false;
                    isLeftToRight = true;
                } else if (lastValue < positionOffset) {
                    // 递增，从右向左滑动
                    isRightToLeft = true;
                    isLeftToRight = false;
                } else if (lastValue == positionOffset) {
                    isLeftToRight = isRightToLeft = false;
                }
                lastValue = positionOffset;
 
            }
 
            @Override
            public void onPageSelected(int position) {
                viewPagerItem viewPagerItem=lists.get(position%3);
                tv_tip.setText(viewPagerItem.getYear()+"年"+(viewPagerItem.getMonth()+1)+"月");
 
                if(isRightToLeft)//右边滑动
                {
                    String[] nums=viewPagerItem.getNums();
                    int month=viewPagerItem.getMonth();
                    int year=viewPagerItem.getYear();
                    int lastDay=viewPagerItem.getLastDay();
                    Calendar calendar=Calendar.getInstance();
                    //计算下一页数据
                    if(TextUtils.isEmpty(nums[1])||Integer.parseInt(nums[1])==lastDay)
                    {
                        //下一月份
                        //判断是不是该到下一年
                        if(month==11)
                        {
                            //取下一年
                            calendar.set(year+1,0,1);
                        }
                        else
                        {
                            calendar.set(year,month+1,1);
                        }
                    }
                    else
                    {
                        //本月
                        calendar.set(year,month,Integer.parseInt(nums[1])+1);
 
                    }
                    int day1=calendar.get(Calendar.DAY_OF_MONTH);
                    int week1=calendar.get(Calendar.DAY_OF_WEEK);
                    int month1=calendar.get(Calendar.MONTH);
                    int year1=calendar.get(Calendar.YEAR);
                    int lastDay1=getLastDay(calendar);
                    makeViewpagerItem(day1,week1,lastDay1,month1,year1,lists.get((position-2)%3));
                    calendarAdapger.notifyDataSetChanged();
 
                }
                else//左边滑动
                {
                    String[] nums=viewPagerItem.getNums();
                    int month=viewPagerItem.getMonth();
                    int year=viewPagerItem.getYear();
                    Calendar calendar=Calendar.getInstance();
                    //计算前一页数据
                    if(TextUtils.isEmpty(nums[0])||Integer.parseInt(nums[0])==0)
                    {
                        //前一月份
                        //判断是不是该到前一年
                        if(month==0)
                        {
                            //取上一年
 
 
                            calendar.set(year-1,11,1);
                            int week=calendar.get(Calendar.DAY_OF_WEEK);
                            int lastDay=getLastDay(calendar);
                            int date=lastDay-(week-1);
                            calendar.set(year-1,11,date);
                        }
                        else
                        {
                            calendar.set(Calendar.MONTH,month-1);
                            int lastDay=getLastDay(calendar);
                            int week=calendar.get(Calendar.DAY_OF_WEEK);
                            int  date=lastDay-(week-1);
                            calendar.set(year,month-1,date);
 
                        }
                    }
                    else
                    {
                        //本月
                        calendar.set(year,month,Integer.parseInt(nums[0])-1);
 
                    }
                    int  day1=calendar.get(Calendar.DAY_OF_MONTH);
                    int  week1=calendar.get(Calendar.DAY_OF_WEEK);
                    int month1=calendar.get(Calendar.MONTH);
                    int year1=calendar.get(Calendar.YEAR);
                    int lastDay1=getLastDay(calendar);
                    makeViewpagerItem(day1,week1,lastDay1,month1,year1,lists.get((position+2)%3));
                    calendarAdapger.notifyDataSetChanged();
                }
 
            }
 
            @Override
            public void onPageScrollStateChanged(int state) {
 
            }
        });
 
 
 
    }
 
 
    public DayItem getOldDayItem() {
        return oldDayItem;
    }
 
    public void setOldDayItem(DayItem oldDayItem) {
        this.oldDayItem = oldDayItem;
    }
 
    /**
     * 返回第一个数和最后一个数
     * @param parameterday
     * @param week
     * @param lastDay
     * @return
     */
    private String[] makeViewpagerItem(int parameterday,int week,int lastDay,int flag,int month,int year)
    {
        int day=parameterday;
        List<DateBean> dateBeens=new ArrayList<>();
        for(int i=week;i>0;i--)
        {
            if(day<1)
            {
                dateBeens.add(0,new DateBean("",false,month<10?"0"+month:month+"",year+""));
            }
            else
            {
                dateBeens.add(0,new DateBean(day<10?"0"+day:day+"",false,month<10?"0"+month:month+"",year+""));
            }
            day--;
        }
 
        day=parameterday;
        for(int i=week+1;i<=7;i++)
        {
            day++;
            if(day>lastDay)
            {
                dateBeens.add(new DateBean("",false,month<10?"0"+month:month+"",year+""));
            }
            else
            {
                dateBeens.add(new DateBean(day<10?"0"+day:day+"",false,month<10?"0"+month:month+"",year+""));
            }
        }
        //给"今天"做标识
        for(int i=0;i<dateBeens.size();i++)
        {
            DateBean  dateBean=dateBeens.get(i);
 
            if(currentDate.equals(dateBean.getStrDate()))
            {
                dateBean.setToday(true);
            }
        }
 
        viewPagerItem viewPagerItem=new viewPagerItem(this);
        String [] num=new String[2];
        num[0]=dateBeens.get(0).getDate();
        num[1]=dateBeens.get(6).getDate();
        viewPagerItem.setDay(parameterday);
        viewPagerItem.setMonth(month);
        viewPagerItem.setYear(year);
        viewPagerItem.setNums(num);
 
        viewPagerItem.setData(dateBeens);
 
        if(flag==0)
        {
            lists.add(0,viewPagerItem);
        }
        else
        {
            lists.add(viewPagerItem);
        }
        return num;
    }
 
    private int getLastDay(Calendar calendar)
    {
        //得到月末
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
 
 
    private String[] makeViewpagerItem(int parameterday,int week,int lastDay,int month,int year,viewPagerItem viewPagerItem)
    {
        int day=parameterday;
        List<DateBean> dateBeens=new ArrayList<>();
        for(int i=week;i>0;i--)
        {
            if(day<1)
            {
                dateBeens.add(0,new DateBean("",false,month<10?"0"+month:month+"",year+""));
            }
            else
            {
                dateBeens.add(0,new DateBean(day<10?"0"+day:day+"",false,month<10?"0"+month:month+"",year+""));
            }
            day--;
        }
 
        day=parameterday;
        for(int i=week+1;i<=7;i++)
        {
            day++;
            if(day>lastDay)
            {
                dateBeens.add(new DateBean("",false,month<10?"0"+month:month+"",year+""));
            }
            else
            {
                dateBeens.add(new DateBean(day<10?"0"+day:day+"",false,month<10?"0"+month:month+"",year+""));
            }
        }
        String [] num=new String[2];
        num[0]=dateBeens.get(0).getDate();
        num[1]=dateBeens.get(6).getDate();
        viewPagerItem.setDay(parameterday);
        viewPagerItem.setMonth(month);
        viewPagerItem.setYear(year);
        viewPagerItem.setNums(num);
 
        viewPagerItem.setData(dateBeens);
        return num;
    }
 
    public String getCurrentDate() {
        return currentDate;
    }
 
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
 
    public List<String> getEventList() {
        return eventList;
    }
 
    public void setEventList(List<String> eventList) {
        this.eventList = eventList;
    }
}
