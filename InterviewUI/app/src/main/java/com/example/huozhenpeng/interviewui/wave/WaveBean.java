package com.example.huozhenpeng.interviewui.wave;
 
import java.io.Serializable;
 
public class WaveBean implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;
	private int data;
 
	public String getText() {
		return text;
	}
 
	public void setText(String text) {
		this.text = text;
	}
 
	public int getData() {
		return data;
	}
 
	public void setData(int data) {
		this.data = data;
	}
 
	@Override
	public String toString() {
		return "WaveBean [text=" + text + ", data=" + data + "]";
	}
 
	public WaveBean(String text, int data) {
		super();
		this.text = text;
		this.data = data;
	}
	
 
}
