package com.example.huozhenpeng.interviewui.calendar;
 
import android.text.TextUtils;
 
import java.io.Serializable;
 
/**
 * Created by ${huozhenpeng} on 17/3/27.
 * Company : www.miduo.com
 */
 
public class DateBean implements Serializable {
 
    private String  date;
    private boolean needFlag;
    private boolean isToday;
 
    private String month;
 
    private String year;
 
    private String strDate;
 
    public String getStrDate() {
        strDate = year + month + date;
        return strDate;
    }
 
    public String getYear() {
        return year;
    }
 
    public void setYear(String year) {
        this.year = year;
    }
 
    public String getMonth() {
        return month;
    }
 
    public void setMonth(String month) {
        this.month = month;
    }
 
    public boolean isToday() {
        return isToday;
    }
 
    public void setToday(boolean today) {
        isToday = today;
    }
 
 
 
    public DateBean(String date, boolean needFlag,String month,String year)
    {
        this.date=date;
        this.needFlag=needFlag;
        this.month=month;
        this.year=year;
    }
    public String getDate() {
        return date;
    }
 
    public void setDate(String date) {
        this.date = date;
    }
 
    public boolean isNeedFlag() {
        return needFlag;
    }
 
    public void setNeedFlag(boolean needFlag) {
        this.needFlag = needFlag;
    }
 
 
}
