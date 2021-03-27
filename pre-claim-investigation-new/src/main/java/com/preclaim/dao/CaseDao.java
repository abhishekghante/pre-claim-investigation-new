package com.preclaim.dao;

import java.util.List;

import com.preclaim.models.CaseDetailList;
import com.preclaim.models.CaseDetails;
import com.preclaim.models.CaseSubStatus;
import com.preclaim.models.UserDetails;

public interface CaseDao {
	
	String addBulkUpload(String filename, UserDetails fromUser, String user_role,String assigneeId);
	long addcase(CaseDetails casedetail);
	String deleteCase(int caseId);
	CaseDetails getCaseDetail(long caseID);
	String updateCaseDetails(CaseDetails case_details);
	List<CaseDetails> getCaseList();
	List<CaseDetailList> getPendingCaseList(String user_role,String zone, String username);
	List<CaseDetailList> getAssignedCaseList(String username);
	List<UserDetails> getUserListByRole(String role_code);
	List<CaseDetails> getLiveCaseList(String username);
    void getExcelMail(String zone);
    String updateCaseTypeAndSubType(CaseDetails casedetail);
    String bulkUpdateCaseTypeAndSubType(CaseDetails casedetail,String list);
    CaseSubStatus getCaseStatus(String user_role, int level);
    boolean checkPolicyNumber(String policyNumber);
}
