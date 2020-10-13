package kcalRecorder.model.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class Foods implements Serializable{
	private int mNo;
	private int fNo;
	private double amount;
	public int getmNo() {
		return mNo;
	}
	public void setmNo(int mNo) {
		this.mNo = mNo;
	}
	public int getfNo() {
		return fNo;
	}
	public void setfNo(int fNo) {
		this.fNo = fNo;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Foods() {
		super();
	}
	public Foods(int mNo, int fNo, int amount) {
		super();
		this.mNo = mNo;
		this.fNo = fNo;
		this.amount = amount;
	}
	
}