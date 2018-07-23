package com.example.huozhenpeng.interviewui.differentcolorbar;

import java.io.Serializable;

/**
 * Created by ${huozhenpeng} on 16/10/12.
 * Company : www.miduo.com
 */
public class ProductIncomeType implements Serializable{
    private int type;//0 投资产品     1 保险产品      2 海外产品
    private String holdOrder;
    private String totalOrder;
    private String profit;
    private String totalProfit;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHoldOrder() {
        return holdOrder;
    }

    public void setHoldOrder(String holdOrder) {
        this.holdOrder = holdOrder;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Override
    public String toString() {
        return "ProductIncomeType{" +
                "type=" + type +
                ", holdOrder='" + holdOrder + '\'' +
                ", totalOrder='" + totalOrder + '\'' +
                ", profit='" + profit + '\'' +
                ", totalProfit='" + totalProfit + '\'' +
                '}';
    }

    public ProductIncomeType(int type, String totalProfit, String profit, String totalOrder, String holdOrder) {
        this.type = type;
        this.totalProfit = totalProfit;
        this.profit = profit;
        this.totalOrder = totalOrder;
        this.holdOrder = holdOrder;
    }
}
