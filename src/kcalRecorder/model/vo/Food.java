package kcalRecorder.model.vo;

import java.io.Serializable;

public class Food implements Serializable{
	private int f_no;
	private int kcalPerOneHundred;	
	private String name;
	public Food() {
		f_no = -1;
	}
	
	public Food(int kcalPerOneHundred, String name) {
		super();
		this.kcalPerOneHundred = kcalPerOneHundred;
		this.name = name;
		f_no = -1;
	}
	
	public int getF_no() {
		return f_no;
	}

	public void setF_no(int f_no) {
		this.f_no = f_no;
	}

	public int calcTotalKcal(int kcalPerOneHundred,double size) {
		return (int)((double)this.kcalPerOneHundred *  size);
	}
	public int getKcalPerOneHundred() {
		return kcalPerOneHundred;
	}
	public void setKcalPerOneHundred(int kcalPerOneHundred) {
		this.kcalPerOneHundred = kcalPerOneHundred;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}