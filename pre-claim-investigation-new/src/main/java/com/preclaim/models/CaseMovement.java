package com.preclaim.models;

public class CaseMovement {

	private long caseId;
	private String fromId;
	private String toId;
	private String caseStatus;
	private String remarks;
	private String createdDate;
	private String updatedDate;
	private String user_role;
	private String zone;

	public CaseMovement() {
		this.caseId = 0;
		this.fromId = "";
		this.toId = "";
		this.caseStatus = "";
		this.remarks = "";
		this.createdDate = "";
		this.updatedDate = "";
		this.user_role = "";
		this.zone = "";
	}

	public CaseMovement(long caseId, String fromId, String toId, String caseStatus, String remarks, String user_role) {
		super();
		this.caseId = caseId;
		this.fromId = fromId;
		this.toId = toId;
		this.caseStatus = caseStatus;
		this.remarks = remarks;
		this.user_role =user_role;
		
	}

	public CaseMovement(long caseId, String fromId, String toId, String caseStatus, String remarks, String createdDate,
			String updatedDate, String user_role, String zone) {
		super();
		this.caseId = caseId;
		this.fromId = fromId;
		this.toId = toId;
		this.caseStatus = caseStatus;
		this.remarks = remarks;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.user_role = user_role;
		this.zone = zone;
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

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	@Override
	public String toString() {
		return "CaseMovement [caseId=" + caseId + ", fromId=" + fromId + ", toId=" + toId + ", caseStatus=" + caseStatus
				+ ", remarks=" + remarks + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", user_role=" + user_role + ", zone=" + zone + "]";
	}

}
