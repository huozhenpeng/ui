package com.example.huozhenpeng.interviewui.differentcolorbar;

import java.io.Serializable;
import java.util.List;

public class NSingleGainResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private List<ScrollPerBarBean> data;
    private List<String> descripions;
    private List<String> bubbledesc;
    private int total;
    public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}


    public List<String> getBubbledesc() {
		return bubbledesc;
	}
	public void setBubbledesc(List<String> bubbledesc) {
		this.bubbledesc = bubbledesc;
	}
	public List<String> getDescripions() {
		return descripions;
	}
	public void setDescripions(List<String> descripions) {
		this.descripions = descripions;
	}
	public void setData(List<ScrollPerBarBean> data) {
        this.data = data;
    }
    public List<ScrollPerBarBean> getData() {
        return data;
    }

    public static class ScrollPerBarBean {
        /**
         * icDate : 2015-11-24
         * icDateFloat : 54.795
         * amount : 5.4795
         * "halfYearResult": 460000,
         * "yearResultDate": "2015-11"
         */

        private String icDate;
        private double icDateFloat;
        private double amount;
    	private int actualHeight;// 实际高度
    	private boolean flag;// 记录是不是点击状态
    	private int leftX, rightX, topY;// 记录左右上边距
    	private double teamFloat;//团队
    	private int teamActualHeight;//团队实际高度
    	private double overseaFloat;//海外
    	private int overseaHeight;//海外实际高度
    	private double insureFloat;//保险
    	private int insureHeight;//保险实际高度
    	private double productFloat;//投资
    	private int productHeight;//投资实际高度
		private double fundFloat;//基金
		private int fundHeight;
		private double allowanceFloat;//管理津贴
		private int allowanceHeight;//管理津贴实际高度

		public double getAllowanceFloat() {
			return allowanceFloat;
		}

		public void setAllowanceFloat(double allowanceFloat) {
			this.allowanceFloat = allowanceFloat;
		}

		public int getAllowanceHeight() {
			return allowanceHeight;
		}

		public void setAllowanceHeight(int allowanceHeight) {
			this.allowanceHeight = allowanceHeight;
		}

		public double getFundFloat() {
			return fundFloat;
		}

		public void setFundFloat(double fundFloat) {
			this.fundFloat = fundFloat;
		}

		public int getFundHeight() {
			return fundHeight;
		}

		public void setFundHeight(int fundHeight) {
			this.fundHeight = fundHeight;
		}

		public double getTeamFloat() {
			return teamFloat;
		}

		public void setTeamFloat(double teamFloat) {
			this.teamFloat = teamFloat;
		}

		public int getTeamActualHeight() {
			return teamActualHeight;
		}

		public void setTeamActualHeight(int teamActualHeight) {
			this.teamActualHeight = teamActualHeight;
		}

		public double getOverseaFloat() {
			return overseaFloat;
		}

		public void setOverseaFloat(double overseaFloat) {
			this.overseaFloat = overseaFloat;
		}

		public int getOverseaHeight() {
			return overseaHeight;
		}

		public void setOverseaHeight(int overseaHeight) {
			this.overseaHeight = overseaHeight;
		}

		public double getInsureFloat() {
			return insureFloat;
		}

		public void setInsureFloat(double insureFloat) {
			this.insureFloat = insureFloat;
		}

		public int getInsureHeight() {
			return insureHeight;
		}

		public void setInsureHeight(int insureHeight) {
			this.insureHeight = insureHeight;
		}

		public double getProductFloat() {
			return productFloat;
		}

		public void setProductFloat(double productFloat) {
			this.productFloat = productFloat;
		}

		public int getProductHeight() {
			return productHeight;
		}

		public void setProductHeight(int productHeight) {
			this.productHeight = productHeight;
		}

		public int getActualHeight() {
			return actualHeight;
		}

		public void setActualHeight(int actualHeight) {
			this.actualHeight = actualHeight;
		}

		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		public int getLeftX() {
			return leftX;
		}

		public void setLeftX(int leftX) {
			this.leftX = leftX;
		}

		public int getRightX() {
			return rightX;
		}

		public void setRightX(int rightX) {
			this.rightX = rightX;
		}

		public int getTopY() {
			return topY;
		}

		public void setTopY(int topY) {
			this.topY = topY;
		}

		public void setIcDate(String icDate) {
            this.icDate = icDate;
        }

        public void setIcDateFloat(double icDateFloat) {
            this.icDateFloat = icDateFloat;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }


        public String getIcDate() {
            return icDate;
        }

        public double getIcDateFloat() {
            return icDateFloat;
        }

        public double getAmount() {
            return amount;
        }


    }

}
