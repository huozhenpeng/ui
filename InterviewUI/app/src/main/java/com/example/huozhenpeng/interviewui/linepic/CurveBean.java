package com.example.huozhenpeng.interviewui.linepic;

import java.io.Serializable;

/**
 * Created by ${huozhenpeng} on 17/4/25.
 * Company : www.miduo.com
 */

public class CurveBean implements Serializable {

    /**
     * day : 2016-04-26
     * close : 886.3405
     */

    private String day;
    private String close;
    private float actualHeight;
    private float actualData;

    public CurveBean(String day, String close) {
        this.day = day;
        this.close = close;
    }

    public CurveBean() {
    }

    public float getActualData() {
        return actualData;
    }

    public void setActualData(float actualData) {
        this.actualData = actualData;
    }

    public float getActualHeight() {
        return actualHeight;
    }

    public void setActualHeight(float actualHeight) {
        this.actualHeight = actualHeight;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "CurveBean{" +
                "day='" + day + '\'' +
                ", close='" + close + '\'' +
                ", actualHeight=" + actualHeight +
                ", actualData=" + actualData +
                '}';
    }
}
