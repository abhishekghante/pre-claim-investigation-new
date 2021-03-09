package com.preclaim.models;

public class RegionwiseList {
	
	private String investigator;
	private int clean;
	private int notClean;
	private int piv;
	private int wip;
	private int total;
	private float notCleanRate;
	
	public RegionwiseList()
	{
		investigator = "";
		clean = 0;
		notClean = 0;
		notCleanRate = 0;
		piv = 0;
	}
		
	public RegionwiseList(String investigator, int clean, int notClean, int piv, int wip) {
		super();
		this.investigator = investigator;
		this.clean = clean;
		this.notClean = notClean;
		this.piv = piv;
		this.wip = wip;
		this.total = clean + notClean + piv + wip;
		this.notCleanRate = notClean *100 / total;
	}

	public String getInvestigator() {
		return investigator;
	}

	public void setInvestigator(String investigator) {
		this.investigator = investigator;
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
