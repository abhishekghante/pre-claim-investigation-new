package com.preclaim.dao;

import java.util.List;

import com.preclaim.models.CaseCategory;
import com.preclaim.models.CaseCategoryList;

public interface CaseCategoryDao {
	
	String add_caseCategory(CaseCategory caseCategory);
	List<CaseCategoryList> caseCategory_list(int status);
	String deleteCaseCategory(int caseCategoryId);
	String updateCaseCategory(int caseCategoryId, String caseStatus, String caseCategory, String username);
	String updateCaseCategoryStatus(int caseCategoryId,int status, String username);
	List<CaseCategory> getActiveCaseCategory();
	List<String> getActiveCaseCategoryStringList();
	List<String> getCaseCategoryListByStatus(String case_status);
}
