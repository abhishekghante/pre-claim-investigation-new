package com.preclaim.models;

public class BillManagementList {

	private int srNo;
	private long caseID;
	private String policyNumber;
	private String investigationType;
	private String initimationType;
	private String supervisorID;
	private String SupervisorName;
	private double Charges;

	public BillManagementList() {

		this.srNo = 0;
		this.caseID = 0;
		this.policyNumber = "";
		this.investigationType = "";
		this.initimationType = "";
		this.supervisorID = "";
		this.SupervisorName = "";
		this.Charges = 0;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
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

	public String getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(String supervisorID) {
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

	@Override
	public String toString() {
		return "BillManagementList [srNo=" + srNo + ", caseID=" + caseID + ", policyNumber=" + policyNumber
				+ ", investigationId=" + investigationType + ", initimationType=" + initimationType + ", supervisorID="
				+ supervisorID + ", SupervisorName=" + SupervisorName + ", Charges=" + Charges + "]";
	}

}
