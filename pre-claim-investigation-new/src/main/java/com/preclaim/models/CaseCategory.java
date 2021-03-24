package com.preclaim.models;

public class CaseCategory {
	
	private int caseCategoryId;
	private String caseStatus;
	private String caseCategory;
	private String createdBy;
	private String updatedBy;
	private int status;
	
	public CaseCategory() {
		this.caseStatus = "";
		this.caseCategory = "";
		this.createdBy = "";
		this.updatedBy = "";
		this.status = 0;
		this.caseCategoryId = 0;
	}

	public int getCaseCategoryId() {
		return caseCategoryId;
	}

	public void setCaseCategoryId(int caseCategoryId) {
		this.caseCategoryId = caseCategoryId;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getCaseCategory() {
		return caseCategory;
	}

	public void setCaseCategory(String caseCategory) {
		this.caseCategory = caseCategory;
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
		return "CaseCategory [caseCategoryId=" + caseCategoryId + ", caseStatus=" + caseStatus + ", caseCategory="
				+ caseCategory + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", status=" + status + "]";
	}
	
}
