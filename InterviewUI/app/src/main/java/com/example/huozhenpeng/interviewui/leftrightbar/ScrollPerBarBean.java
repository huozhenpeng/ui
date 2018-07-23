package com.example.huozhenpeng.interviewui.leftrightbar;
 
import java.io.Serializable;
/**
 * 
 * @author huozhenpeng
 *
 */
public class ScrollPerBarBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String money;// 金额
	private int ratio;
	private String date;// 日期
	private int actualHeight;// 实际高度
	private boolean flag;// 记录是不是点击状态
	private int leftX, rightX, topY;// 记录左右上边距
 
	public String getMoney() {
		return money;
	}
 
	public void setMoney(String money) {
		this.money = money;
	}
 
	public int getRatio() {
		return ratio;
	}
 
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
 
	public String getDate() {
		return date;
	}
 
	public void setDate(String date) {
		this.date = date;
	}
 
	public ScrollPerBarBean(String money, int ratio, String date) {
		super();
		this.money = money;
		this.ratio = ratio;
		this.date = date;
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
 
	@Override
	public String toString() {
		return "PerBarBean [money=" + money + ", ratio=" + ratio + ", date="
				+ date + ", actualHeight=" + actualHeight + ", flag=" + flag
				+ ", leftX=" + leftX + ", rightX=" + rightX + ", topY=" + topY
				+ "]";
	}
 
}
