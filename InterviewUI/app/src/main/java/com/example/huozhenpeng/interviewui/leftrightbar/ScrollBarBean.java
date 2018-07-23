package com.example.huozhenpeng.interviewui.leftrightbar;

import java.io.Serializable;
import java.util.List;

/**
 * 作者 huozhenpeng
 * 日期 2018/3/13
 * 邮箱 huohacker@sina.com
 */

public class ScrollBarBean implements Serializable {

    private double total;
    private List<ScrollPerBarBean> lists;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<ScrollPerBarBean> getLists() {
        return lists;
    }

    public void setLists(List<ScrollPerBarBean> lists) {
        this.lists = lists;
    }
}
