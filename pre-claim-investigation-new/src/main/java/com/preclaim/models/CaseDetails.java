package com.preclaim.models;

public class CaseDetails {

	private long caseId;
	private String policyNumber;
	private int investigationId;
	private String investigationCategory;
	private String insuredName;
	private String insuredDOD;
	private String insuredDOB;
	private double sumAssured;
	private String intimationType;
	private int locationId;
	private String claimantCity;
	private String claimantZone;
	private String claimantState;
	private String caseStatus;
	private String nominee_name;
	private String nomineeContactNumber;
	private String nominee_address;
	private String insured_address;
	private String case_description;
	private String longitude;
	private String latitude;
	private String pdf1FilePath;
	private String pdf2FilePath;
	private String pdf3FilePath;
	private String audioFilePath;
	private String videoFilePath;
	private String signatureFilePath;
	private String capturedDate;
	private String approvedStatus;
	private String assignerRole;
	private String assignerName;
	private String assignerStatus;
	private String assignerRemarks;
	private String createdBy;
	private String createdDate;
	private String updatedDate;
	private String updatedBy;

	public CaseDetails() {
		caseId = 0;
		policyNumber = "";
		investigationId = 0;
		insuredName = "";
		insuredDOD = "";
		insuredDOB = "";
		sumAssured = 0;
		intimationType = "";
		locationId = 0;
		claimantCity = "";
		claimantState = "";
		claimantZone = "";
		caseStatus = "";
		nominee_name = "";
		nomineeContactNumber = "";
		nominee_address = "";
		insured_address = "";
		case_description = "";
		longitude = "";
		latitude = "";
		pdf1FilePath = "";
		pdf2FilePath = "";
		pdf3FilePath = "";
		audioFilePath = "";
		videoFilePath = "";
		signatureFilePath = "";
		assignerRole = "";
		assignerName = "";
		assignerStatus = "";
		assignerRemarks = "";
		capturedDate = "";
		createdBy = "";
		createdDate = "";
		updatedDate = "";
		updatedBy = "";
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

	public int getInvestigationId() {
		return investigationId;
	}

	public void setInvestigationId(int investigationId) {
		this.investigationId = investigationId;
	}

	public String getInvestigationCategory() {
		return investigationCategory;
	}

	public void setInvestigationCategory(String investigationCategory) {
		this.investigationCategory = investigationCategory;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getInsuredDOD() {
		return insuredDOD;
	}

	public void setInsuredDOD(String insuredDOD) {
		this.insuredDOD = insuredDOD;
	}

	public String getInsuredDOB() {
		return insuredDOB;
	}

	public void setInsuredDOB(String insuredDOB) {
		this.insuredDOB = insuredDOB;
	}

	public double getSumAssured() {
		return sumAssured;
	}

	public void setSumAssured(double sumAssured) {
		this.sumAssured = sumAssured;
	}

	public String getIntimationType() {
		return intimationType;
	}

	public void setIntimationType(String intimationType) {
		this.intimationType = intimationType;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getClaimantCity() {
		return claimantCity;
	}

	public void setClaimantCity(String claimantCity) {
		this.claimantCity = claimantCity;
	}

	public String getClaimantZone() {
		return claimantZone;
	}

	public void setClaimantZone(String claimantZone) {
		this.claimantZone = claimantZone;
	}

	public String getClaimantState() {
		return claimantState;
	}

	public void setClaimantState(String claimantState) {
		this.claimantState = claimantState;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getNominee_name() {
		return nominee_name;
	}

	public void setNominee_name(String nominee_name) {
		this.nominee_name = nominee_name;
	}

	public String getNomineeContactNumber() {
		return nomineeContactNumber;
	}

	public void setNomineeContactNumber(String nomineeContactNumber) {
		this.nomineeContactNumber = nomineeContactNumber;
	}

	public String getNominee_address() {
		return nominee_address;
	}

	public void setNominee_address(String nominee_address) {
		this.nominee_address = nominee_address;
	}

	public String getInsured_address() {
		return insured_address;
	}

	public void setInsured_address(String insured_address) {
		this.insured_address = insured_address;
	}

	public String getCase_description() {
		return case_description;
	}

	public void setCase_description(String case_description) {
		this.case_description = case_description;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getPdf1FilePath() {
		return pdf1FilePath;
	}

	public void setPdf1FilePath(String pdf1FilePath) {
		this.pdf1FilePath = pdf1FilePath;
	}

	public String getPdf2FilePath() {
		return pdf2FilePath;
	}

	public void setPdf2FilePath(String pdf2FilePath) {
		this.pdf2FilePath = pdf2FilePath;
	}

	public String getPdf3FilePath() {
		return pdf3FilePath;
	}

	public void setPdf3FilePath(String pdf3FilePath) {
		this.pdf3FilePath = pdf3FilePath;
	}

	public String getAudioFilePath() {
		return audioFilePath;
	}

	public void setAudioFilePath(String audioFilePath) {
		this.audioFilePath = audioFilePath;
	}

	public String getVideoFilePath() {
		return videoFilePath;
	}

	public void setVideoFilePath(String videoFilePath) {
		this.videoFilePath = videoFilePath;
	}

	public String getSignatureFilePath() {
		return signatureFilePath;
	}

	public void setSignatureFilePath(String signatureFilePath) {
		this.signatureFilePath = signatureFilePath;
	}

	public String getCapturedDate() {
		return capturedDate;
	}

	public void setCapturedDate(String capturedDate) {
		this.capturedDate = capturedDate;
	}

	public String getApprovedStatus() {
		return approvedStatus;
	}

	public void setApprovedStatus(String approvedStatus) {
		this.approvedStatus = approvedStatus;
	}

	public String getAssignerRole() {
		return assignerRole;
	}

	public void setAssignerRole(String assignerRole) {
		this.assignerRole = assignerRole;
	}

	public String getAssignerName() {
		return assignerName;
	}

	public String getAssignerStatus() {
		return assignerStatus;
	}

	public void setAssignerStatus(String assignerStatus) {
		this.assignerStatus = assignerStatus;
	}

	public String getAssignerRemarks() {
		return assignerRemarks;
	}

	public void setAssignerRemarks(String assignerRemarks) {
		this.assignerRemarks = assignerRemarks;
	}

	public void setAssignerName(String assignerName) {
		this.assignerName = assignerName;
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
		return "CaseDetails [caseId=" + caseId + ", policyNumber=" + policyNumber + ", investigationId="
				+ investigationId + ", investigationCategory=" + investigationCategory + ", insuredName=" + insuredName
				+ ", insuredDOD=" + insuredDOD + ", insuredDOB=" + insuredDOB + ", sumAssured=" + sumAssured
				+ ", intimationType=" + intimationType + ", locationId=" + locationId + ", claimantCity=" + claimantCity
				+ ", claimantZone=" + claimantZone + ", claimantState=" + claimantState + ", caseStatus=" + caseStatus
				+ ", nominee_name=" + nominee_name + ", nomineeContactNumber=" + nomineeContactNumber
				+ ", nominee_address=" + nominee_address + ", insured_address=" + insured_address
				+ ", case_description=" + case_description + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", pdf1FilePath=" + pdf1FilePath + ", pdf2FilePath=" + pdf2FilePath + ", pdf3FilePath=" + pdf3FilePath
				+ ", audioFilePath=" + audioFilePath + ", videoFilePath=" + videoFilePath + ", signatureFilePath="
				+ signatureFilePath + ", capturedDate=" + capturedDate + ", approvedStatus=" + approvedStatus
				+ ", assignerRole=" + assignerRole + ", assignerName=" + assignerName + ", assignerStatus="
				+ assignerStatus + ", assignerRemarks=" + assignerRemarks + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + "]";
	}
}

