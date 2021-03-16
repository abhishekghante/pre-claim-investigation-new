package com.preclaim.models;

public class VendorwiseList {
	
	private String month;
	private int clean;
	private int notClean;
	private int cleanRate;
	private int notCleanRate;
	private int total;
	
	public VendorwiseList()
	{
		month = "";
		clean = 0;
		notClean = 0;
		cleanRate = 0;
		notCleanRate = 0;
		total = 0;
	}
	
	public VendorwiseList(String month, int clean, int notClean)
	{
		this.month = month;
		this.clean = clean;
		this.notClean = notClean;
		total = clean + notClean;
		cleanRate = clean*100/total;
		notCleanRate = notClean*100/total;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getClean() {
		return clean;
	}
	public void setClean(int clean) {
		this.clean = clean;
	}
	public int getNotClean() {
		return notClean;
	}
	public void setNotClean(int notClean) {
		this.notClean = notClean;
	}
	public int getCleanRate() {
		return cleanRate;
	}
	public void setCleanRate(int cleanRate) {
		this.cleanRate = cleanRate;
	}
	public int getNotCleanRate() {
		return notCleanRate;
	}
	public void setNotCleanRate(int notCleanRate) {
		this.notCleanRate = notCleanRate;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

}
