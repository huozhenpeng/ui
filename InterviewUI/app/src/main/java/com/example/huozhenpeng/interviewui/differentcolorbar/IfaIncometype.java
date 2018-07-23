package com.example.huozhenpeng.interviewui.differentcolorbar;

import java.io.Serializable;

/**
 * Created by ${huozhenpeng} on 16/10/12.
 * Company : www.miduo.com
 */
public class IfaIncometype implements Serializable{
   /*
     * insTotalOrderCount : 22
     * osTotalOrderCount : 0
     * insTotalIncome : 261,357,113.53
     * investIncome : 3,252.53
     * insHasOrderCount : 8
     * investHasOrderCount : 8
     * investTotalIncome : 357,113.53
     * investTotalOrderCount : 22
     * osTotalIncome : 0.00
     * overseasIncome : 0.00
     * osHasOrderCount : 0
     * insIncome : 43,252.53
     */

    private String insTotalOrderCount;
    private String osTotalOrderCount;
    private String insTotalIncome;
    private String investIncome;
    private String insHasOrderCount;
    private String investHasOrderCount;
    private String investTotalIncome;
    private String investTotalOrderCount;
    private String osTotalIncome;
    private String overseasIncome;
    private String osHasOrderCount;
    private String insIncome;

    public String getInsTotalOrderCount() {
        return insTotalOrderCount;
    }

    public void setInsTotalOrderCount(String insTotalOrderCount) {
        this.insTotalOrderCount = insTotalOrderCount;
    }

    public String getOsTotalOrderCount() {
        return osTotalOrderCount;
    }

    public void setOsTotalOrderCount(String osTotalOrderCount) {
        this.osTotalOrderCount = osTotalOrderCount;
    }

    public String getInsTotalIncome() {
        return insTotalIncome;
    }

    public void setInsTotalIncome(String insTotalIncome) {
        this.insTotalIncome = insTotalIncome;
    }

    public String getInvestIncome() {
        return investIncome;
    }

    public void setInvestIncome(String investIncome) {
        this.investIncome = investIncome;
    }

    public String getInsHasOrderCount() {
        return insHasOrderCount;
    }

    public void setInsHasOrderCount(String insHasOrderCount) {
        this.insHasOrderCount = insHasOrderCount;
    }

    public String getInvestHasOrderCount() {
        return investHasOrderCount;
    }

    public void setInvestHasOrderCount(String investHasOrderCount) {
        this.investHasOrderCount = investHasOrderCount;
    }

    public String getInvestTotalIncome() {
        return investTotalIncome;
    }

    public void setInvestTotalIncome(String investTotalIncome) {
        this.investTotalIncome = investTotalIncome;
    }

    public String getInvestTotalOrderCount() {
        return investTotalOrderCount;
    }

    public void setInvestTotalOrderCount(String investTotalOrderCount) {
        this.investTotalOrderCount = investTotalOrderCount;
    }

    public String getOsTotalIncome() {
        return osTotalIncome;
    }

    public void setOsTotalIncome(String osTotalIncome) {
        this.osTotalIncome = osTotalIncome;
    }

    public String getOverseasIncome() {
        return overseasIncome;
    }

    public void setOverseasIncome(String overseasIncome) {
        this.overseasIncome = overseasIncome;
    }

    public String getOsHasOrderCount() {
        return osHasOrderCount;
    }

    public void setOsHasOrderCount(String osHasOrderCount) {
        this.osHasOrderCount = osHasOrderCount;
    }

    public String getInsIncome() {
        return insIncome;
    }

    public void setInsIncome(String insIncome) {
        this.insIncome = insIncome;
    }

    @Override
    public String toString() {
        return "IfaIncometype{" +
                "insTotalOrderCount=" + insTotalOrderCount +
                ", osTotalOrderCount=" + osTotalOrderCount +
                ", insTotalIncome='" + insTotalIncome + '\'' +
                ", investIncome='" + investIncome + '\'' +
                ", insHasOrderCount=" + insHasOrderCount +
                ", investHasOrderCount=" + investHasOrderCount +
                ", investTotalIncome='" + investTotalIncome + '\'' +
                ", investTotalOrderCount=" + investTotalOrderCount +
                ", osTotalIncome='" + osTotalIncome + '\'' +
                ", overseasIncome='" + overseasIncome + '\'' +
                ", osHasOrderCount=" + osHasOrderCount +
                ", insIncome='" + insIncome + '\'' +
                '}';
    }
}
