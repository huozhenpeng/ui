package com.example.huozhenpeng.interviewui.barlink;

import java.io.Serializable;

/**
 * Created by ${huozhenpeng} on 16/12/8.
 * Company : www.miduo.com
 */

public class AccountAsset implements Serializable {

    /**
     * groupYesterdayAsset : 3768960
     * groupYesterdayProfit : 0
     * percent : 1
     * assetType : 3
     * 资产类别(1-银行存款,2-P2P,3-宝宝类，4-基金，5-股票，6-银行理财产品，7-信托、资管，8-自定义资产) ,
     *
     * p2p: #fa582c   #fbe8dd
     * 股票： #da403b  #f9e5e4
     * 理财： #875fd7   #ece8fa
     * 信托： #3e9ecc   #e1f1f7
     * 存款： #2baff4   #dff5fb
     * 其他资产： #9bca53    #f0f8e4
     * 基金： #fed119   #fef9de
     * 宝宝： #eca026  #fbf2de
     *
     */

    private double groupYesterdayAsset;
    private double groupYesterdayProfit;
    private double percent;
    private int assetType;
    private MyPoint point;//只是记录两个点（并非x和y）
    private int degrees;


    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public MyPoint getPoint() {
        return point;
    }

    public void setPoint(MyPoint point) {
        this.point = point;
    }

    public double getGroupYesterdayAsset() {
        return groupYesterdayAsset;
    }

    public void setGroupYesterdayAsset(double groupYesterdayAsset) {
        this.groupYesterdayAsset = groupYesterdayAsset;
    }

    public double getGroupYesterdayProfit() {
        return groupYesterdayProfit;
    }

    public void setGroupYesterdayProfit(double groupYesterdayProfit) {
        this.groupYesterdayProfit = groupYesterdayProfit;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getAssetType() {
        return assetType;
    }

    public void setAssetType(int assetType) {
        this.assetType = assetType;
    }

    public String getColor()
    {
        String color="#efefef";
        switch (assetType)
        {
            case 1://银行存款
                color="#faa97c";
                break;
            case 2://p2p
                color="#957cfb";
                break;
            case 3://宝宝类
                color="#93d28b";
                break;
            case 4://基金
                color="#fdcf00";
                break;
            case 5://股票
                color="#ff82ae";
                break;
            case 6://银行理财
                color="#6aceee";
                break;
            case 7://信托
                color="#60b0f5";
                break;
            case 8://自定义资产
                color="#5ed9a2";
                break;
        }
        return color;

    }
    public String getVirtualColor()
    {
        String color="#efefef";
        switch (assetType)
        {
            case 1:
                color="#fef3ec";
                break;
            case 2:
                color="#efecfe";
                break;
            case 3:
                color="#eef9ee";
                break;
            case 4:
                color="#fff9d8";
                break;
            case 5:
                color="#ffedf3";
                break;
            case 6:
                color="#e8f9fc";
                break;
            case 7:
                color="#e7f4ff";
                break;
            case 8:
                color="#e7faf1";
                break;
        }
        return color;
    }

    @Override
    public String toString() {
        return "AccountAsset{" +
                "groupYesterdayAsset=" + groupYesterdayAsset +
                ", groupYesterdayProfit=" + groupYesterdayProfit +
                ", percent=" + percent +
                ", assetType=" + assetType +
                '}';
    }
}
