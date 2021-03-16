package com.preclaim.dao;

import java.util.HashMap;
import java.util.List;

import com.preclaim.models.RegionwiseList;
import com.preclaim.models.TopInvestigatorList;
import com.preclaim.models.VendorwiseList;

public interface ReportDao {

	List<String> getRegion();
	HashMap<String, String> getVendor();
	List<String> getIntimationType();
    List<TopInvestigatorList> getTopInvestigatorList(String startDate, String endDate);
	List<RegionwiseList> getRegionwiseList(String region, String startDate, String endDate);
    HashMap<String, Integer> getIntimationTypeList(String intimationType, String startDate, String endDate);
    List<VendorwiseList> getVendorwistList(String vendorName, String startDate, String endDate);
}
