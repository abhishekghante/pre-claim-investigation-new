package com.preclaim.models;

public class CaseStatusList {

	private int srNo;
	private String caseStatus;
	private String createdDate;
	private int status;
	private int caseStatusId;

	public CaseStatusList() {

		this.srNo = 0;
		this.caseStatus = "";
		this.createdDate = "";
		this.status = 0;
		this.caseStatusId = 0;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCaseStatusId() {
		return caseStatusId;
	}

	public void setCaseStatusId(int caseStatusId) {
		this.caseStatusId = caseStatusId;
	}

	@Override
	public String toString() {
		return "CaseStatusList [srNo=" + srNo + ", caseStatus=" + caseStatus + ", createdDate=" + createdDate
				+ ", status=" + status + ", caseStatusId=" + caseStatusId + "]";
	}

}
