package com.example.huozhenpeng.interviewui.differentcolorbar;

import java.math.BigDecimal;

public class IncomeUser {
	private String id;
	private BigDecimal amount;
	private String username;
	private String color;
	private String ictype;
	// 客户拓展(1),引荐FA(2),引荐FA审核中(3),米多奖励(4),其他(5);
	private String sourceType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIctype() {
		return ictype;
	}

	public void setIctype(String ictype) {
		this.ictype = ictype;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
}
