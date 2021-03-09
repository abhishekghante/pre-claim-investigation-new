package com.preclaim.models;

public class TopInvestigatorList {
	
	private String investigator;
	private int clean;
	private int notClean;
	private int total;
	private float notCleanRate;
	
	public TopInvestigatorList()
	{
		investigator = "";
		clean = 0;
		notClean = 0;
		notCleanRate = 0;
	}
		
	public TopInvestigatorList(String investigator, int clean, int notClean) {
		super();
		this.investigator = investigator;
		this.clean = clean;
		this.notClean = notClean;
		this.total = clean + notClean;
		this.notCleanRate = notClean*100/total;
	}

	public String getInvestigator() {
		return investigator;
	}
	public int getClean() {
		return clean;
	}
	public int getNotClean() {
		return notClean;
	}
	public int getTotal() {
		return total;
	}
	public float getNotCleanRate() {
		return notCleanRate;
	}

}
