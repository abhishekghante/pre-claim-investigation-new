package com.preclaim.models;

public class CaseCategoryList {

	private int srNo;
	private String caseStatus;
	private String caseCategory;
	private String createdDate;
	private int status;
	private int caseCategoryId;

	public CaseCategoryList() {

		this.srNo = 0;
		this.caseStatus = "";
		this.caseCategory = "";
		this.createdDate = "";
		this.status = 0;
		this.caseCategoryId = 0;
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

	public String getCaseCategory() {
		return caseCategory;
	}

	public void setCaseCategory(String caseCategory) {
		this.caseCategory = caseCategory;
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

	public int getCaseCategoryId() {
		return caseCategoryId;
	}

	public void setCaseCategoryId(int caseCategoryId) {
		this.caseCategoryId = caseCategoryId;
	}

	@Override
	public String toString() {
		return "CaseCategoryList [srNo=" + srNo + ", caseStatus=" + caseStatus + ", caseCategory=" + caseCategory
				+ ", createdDate=" + createdDate + ", status=" + status + ", caseCategoryId=" + caseCategoryId + "]";
	}

}
