package com.preclaim.models;

public class CaseStatus {
	
	private int caseStatusId;
	private String caseStatus;
	private String createdBy;
	private String updatedBy;
	private int status;
	
	public CaseStatus() {
		this.caseStatus = "";
		this.createdBy = "";
		this.updatedBy = "";
		this.status = 0;
		this.caseStatusId = 0;
	}

	public int getCaseStatusId() {
		return caseStatusId;
	}

	public void setCaseStatusId(int caseStatusId) {
		this.caseStatusId = caseStatusId;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CaseStatus [caseStatusId=" + caseStatusId + ", caseStatus=" + caseStatus + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", status=" + status + "]";
	}

	
}
