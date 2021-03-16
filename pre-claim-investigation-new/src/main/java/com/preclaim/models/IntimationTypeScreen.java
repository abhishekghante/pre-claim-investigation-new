package com.preclaim.models;

public class IntimationTypeScreen {

	private String IntimationType;
	private int clean;
	private int notClean;
	private int piv;
	private int wip;
	private int total;
	private float notCleanRate;

	public IntimationTypeScreen() {
		IntimationType = "";
		clean = 0;
		notClean = 0;
		notCleanRate = 0;
		piv = 0;
	}

	public IntimationTypeScreen(String IntimationType, int clean, int notClean) {
		super();
		this.IntimationType = IntimationType;
		this.clean = clean;
		this.notClean = notClean;
		this.total = clean + notClean;
		this.notCleanRate = notClean*100/total;
	}

	public String getIntimationType() {
		return IntimationType;
	}

	public void setIntimationType(String intimationType) {
		IntimationType = intimationType;
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

	public int getPiv() {
		return piv;
	}

	public void setPiv(int piv) {
		this.piv = piv;
	}

	public int getWip() {
		return wip;
	}

	public void setWip(int wip) {
		this.wip = wip;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public float getNotCleanRate() {
		return notCleanRate;
	}

	public void setNotCleanRate(float notCleanRate) {
		this.notCleanRate = notCleanRate;
	}

}
