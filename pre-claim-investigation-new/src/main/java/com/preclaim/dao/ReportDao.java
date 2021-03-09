package com.preclaim.dao;

import java.util.HashMap;
import java.util.List;

import com.preclaim.models.TopInvestigatorList;

public interface ReportDao {

	List<String> getRegion();
	HashMap<String, String> getVendor();
	List<TopInvestigatorList> getTopInvestigatorList(String startDate, String endDate);
	
}
