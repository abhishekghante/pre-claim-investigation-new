package com.preclaim.models;

public class CaseHistory {

	private long caseId;
	private String fromId;
	private String fromUserName;
	private String role;
	private String toId;
	private String caseStatus;
	private String remarks;
	private String createdDate;
	private String updatedDate;

	public CaseHistory() {
		this.caseId = 0;
		this.fromId = "";
		this.fromUserName = "";
		this.role = "";
		this.toId = "";
		this.caseStatus = "";
		this.remarks = "";
		this.createdDate = "";
		this.updatedDate = "";
	}

	public CaseHistory(long caseId, String fromId, String role, String toId, String caseStatus, String remarks) {
		super();
		this.caseId = caseId;
		this.fromId = fromId;
		this.role = role;
		this.toId = toId;
		this.caseStatus = caseStatus;
		this.remarks = remarks;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId2) {
		this.caseId = caseId2;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "caseMovement [caseId=" + caseId + ", fromId=" + fromId + ", toId=" + toId + ", caseStatus=" + caseStatus
				+ ", remarks=" + remarks + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}

	
}
