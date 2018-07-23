package com.example.huozhenpeng.interviewui.leftrightbar;
 
import java.io.Serializable;
 
/**
 * 单日收益页面历史题view的bean对象
 * 
 * @author huozhenpeng
 * 
 */
public class SingleGainBean implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float money;
	private String productName;
 
	public float getMoney() {
		return money;
	}
 
	public void setMoney(float money) {
		this.money = money;
	}
 
	public String getProductName() {
		return productName;
	}
 
	public void setProductName(String productName) {
		this.productName = productName;
	}
 
	@Override
	public String toString() {
		return "SingleGainBean [money=" + money + ", productName="
				+ productName + "]";
	}
 
	public SingleGainBean(float money, String productName) {
		super();
		this.money = money;
		this.productName = productName;
	}
	
 
}
