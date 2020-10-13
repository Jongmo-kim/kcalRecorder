package kcalRecorder.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Meal implements Serializable{
	private int mNo;
	private Date date;
	private int uNo;
	public int getmNo() {
		return mNo;
	}
	public void setmNo(int mNo) {
		this.mNo = mNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getuNo() {
		return uNo;
	}
	public void setuNo(int uNo) {
		this.uNo = uNo;
	}
	public Meal(int mNo, Date date, int uNo) {
		super();
		this.mNo = mNo;
		this.date = date;
		this.uNo = uNo;
	}
	public Meal() {
		super();
	}
	
}