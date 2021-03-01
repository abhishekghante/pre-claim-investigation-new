package com.preclaim.dao;

import java.util.List;

import com.preclaim.models.IntimationType;
import com.preclaim.models.IntimationTypeList;

public interface IntimationTypeDao {
	
	String add_intimationType(IntimationType intimationType);
	List<IntimationTypeList> intimationType_list(int status);
	String deleteIntimationType(int intimationId);
	String updateIntimationType(int intimationId, String intimationType, String username);
	String updateIntimationTypeStatus(int intimationId,int status, String username);
	List<IntimationType> getActiveIntimationType();
	List<String> getActiveIntimationTypeStringList();
}
