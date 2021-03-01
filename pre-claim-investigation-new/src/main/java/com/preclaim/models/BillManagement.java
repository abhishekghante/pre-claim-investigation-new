package com.preclaim.models;

public class BillManagement {

	private long caseID;
	private String policyNumber;
	private String investigationType;
	private String initimationType;
	private int supervisorID;
	private String SupervisorName;
	private double Charges;
	private String createdBy;
	private String createdDate;
	private String updatedDate;
	private String updatedBy;

	public BillManagement() {

		this.caseID = 0;
		this.policyNumber = "";
		this.investigationType = "";
		this.initimationType = "";
		this.supervisorID = 0;
		this.SupervisorName = "";
		this.Charges = 0;
		this.createdBy = "";
		this.createdDate = "";
		this.updatedDate = "";
		this.updatedBy = "";

	}

	public long getCaseID() {
		return caseID;
	}

	public void setCaseID(long caseID) {
		this.caseID = caseID;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getInvestigationType() {
		return investigationType;
	}

	public void setInvestigationType(String investigationType) {
		this.investigationType = investigationType;
	}

	public String getInitimationType() {
		return initimationType;
	}

	public void setInitimationType(String initimationType) {
		this.initimationType = initimationType;
	}

	public int getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(int supervisorID) {
		this.supervisorID = supervisorID;
	}

	public String getSupervisorName() {
		return SupervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		SupervisorName = supervisorName;
	}

	public double getCharges() {
		return Charges;
	}

	public void setCharges(double charges) {
		Charges = charges;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "BillManagement [caseID=" + caseID + ", policyNumber=" + policyNumber + ", investigationType="
				+ investigationType + ", initimationType=" + initimationType + ", supervisorID=" + supervisorID
				+ ", SupervisorName=" + SupervisorName + ", Charges=" + Charges + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + "]";
	}

}
