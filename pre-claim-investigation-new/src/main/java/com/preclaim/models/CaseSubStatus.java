package com.preclaim.models;

public class CaseSubStatus {

	private long id;
	private String user_role;
	private String Case_status;
	private String caseSubStatus;
	private int level;

	public CaseSubStatus() {
		id = 0;
		user_role = "";
		Case_status = "";
		level = 0;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getCase_status() {
		return Case_status;
	}

	public void setCase_status(String case_status) {
		Case_status = case_status;
	}

	public String getCaseSubStatus() {
		return caseSubStatus;
	}

	public void setCaseSubStatus(String caseSubStatus) {
		this.caseSubStatus = caseSubStatus;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "CaseSubStatus [id=" + id + ", user_role=" + user_role + ", Case_status=" + Case_status
				+ ", caseSubStatus=" + caseSubStatus + ", level=" + level + "]";
	}

}
