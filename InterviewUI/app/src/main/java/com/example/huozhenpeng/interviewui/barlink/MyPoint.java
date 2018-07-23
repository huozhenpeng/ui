package com.example.huozhenpeng.interviewui.barlink;

import java.io.Serializable;

public class MyPoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyPoint() {
		// TODO Auto-generated constructor stub
	}

	private double x1;
	private double x2;
	private double x3;

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getX3() {
		return x3;
	}

	public void setX3(double x3) {
		this.x3 = x3;
	}

	public void set(double x1, double x2, double x3)
	{
		this.x1=x1;
		this.x2=x2;
		this.x3=x3;
	}

	@Override
	public String toString() {
		return "MyPoint [x1=" + x1 + ", x2=" + x2 + ", x3=" + x3 + "]";
	}
	
	

}
