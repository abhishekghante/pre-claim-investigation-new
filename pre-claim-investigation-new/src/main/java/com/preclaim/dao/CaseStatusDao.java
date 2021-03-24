package com.preclaim.dao;

import java.util.List;

import com.preclaim.models.CaseStatus;
import com.preclaim.models.CaseStatusList;

public interface CaseStatusDao {
	
	String add_caseStatus(CaseStatus caseStatus);
	List<CaseStatusList> caseStatus_list(int status);
	String deleteCaseStatus(int caseStatusId);
	String updateCaseStatus(int caseStatusId, String caseStatus, String username);
	String updateStatus(int caseStatusId,int status, String username);
	List<CaseStatus> getActiveCaseStatus();
	List<String> getActiveCaseStatusStringList();
}
