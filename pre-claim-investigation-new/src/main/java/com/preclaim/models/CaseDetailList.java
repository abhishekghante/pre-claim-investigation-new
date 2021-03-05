package com.preclaim.models;

public class CaseDetailList {

	private int srNo;
	private long caseId;
	private String policyNumber;
	private String insuredName;
	private int investigationCategoryId;
	private String investigationCategory;
	private String intimationType;
	private double sumAssured;
	private int assigneeId;
	private String assigneeName;
	private String caseStatus;
	private double fees;
	private String caseType;
	private String caseSubStatus;

	public CaseDetailList() {
		srNo = 0;
		caseId = 0;
		policyNumber = "";
		insuredName = "";
		investigationCategoryId = 0;
		investigationCategory = "";
		intimationType = "";
		sumAssured = 0;
		assigneeId = 0;
		assigneeName = "";
		caseStatus = "";
		fees = 0;
		caseType = "";
		caseSubStatus = "";

	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public int getInvestigationCategoryId() {
		return investigationCategoryId;
	}

	public void setInvestigationCategoryId(int investigationCategoryId) {
		this.investigationCategoryId = investigationCategoryId;
	}

	public String getInvestigationCategory() {
		return investigationCategory;
	}

	public void setInvestigationCategory(String investigationCategory) {
		this.investigationCategory = investigationCategory;
	}

	public String getIntimationType() {
		return intimationType;
	}

	public void setIntimationType(String intimationType) {
		this.intimationType = intimationType;
	}

	public double getSumAssured() {
		return sumAssured;
	}

	public void setSumAssured(double sumAssured) {
		this.sumAssured = sumAssured;
	}

	public int getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseSubStatus() {
		return caseSubStatus;
	}

	public void setCaseSubStatus(String caseSubStatus) {
		this.caseSubStatus = caseSubStatus;
	}

}
