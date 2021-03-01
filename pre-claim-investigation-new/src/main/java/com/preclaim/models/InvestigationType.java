package com.preclaim.models;

public class InvestigationType {
	private int investigationId;
	private String investigationType;
	private String createdBy;
	private String updatedBy;
	private int status;
	
	public InvestigationType() {
	
		investigationId = 0;
		investigationType = "";
		createdBy = "";
		updatedBy = "";
		status = 0;
	}
	
	public InvestigationType(int investigationId, String investigationType) {
		
		this.investigationId = investigationId;
		this.investigationType = investigationType;
		createdBy = "";
		updatedBy = "";
		status = 0;
	}

	public int getInvestigationId() {
		return investigationId;
	}

	public void setInvestigationId(int investigationId) {
		this.investigationId = investigationId;
	}

	public String getInvestigationType() {
		return investigationType;
	}

	public void setInvestigationType(String investigationType) {
		this.investigationType = investigationType;
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
		return "InvestigationType [investigationId=" + investigationId + ", investigationType=" + investigationType
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", status=" + status + "]";
	}

	
}
