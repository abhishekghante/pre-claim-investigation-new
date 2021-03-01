package com.preclaim.dao;

import java.util.HashMap;
import java.util.List;

import com.preclaim.models.InvestigationType;
import com.preclaim.models.InvestigationTypeList;

public interface InvestigationTypeDao {
	
	String addInvestigationType(InvestigationType investigationType);
	String updateInvestigationType(String investigationType, String username, int investigationId);	
	List<InvestigationTypeList> getInvestigationList(int status);
    String updateInvestigationTypeStatus(int status, String username, int investigationId);
    String deleteInvestigationType(int investigationId);
    List<InvestigationType> getActiveInvestigationList();
    List<String> getActiveInvestigationStringList();
	HashMap<Integer, String> getActiveInvestigationMapping();
	String getInvestigationById(int investigationId);
    
}
