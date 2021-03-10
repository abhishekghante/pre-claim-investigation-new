package com.preclaim.dao;

import java.util.List;
import java.util.Map;

import com.preclaim.models.BillManagementList;

public interface BillingManagementDao {

	List<BillManagementList> billPaymentList();
	Map<Integer, Object[]> billPaymentList(String values);
	String UpdateFees(String values, String userID);
	List<BillManagementList> billEnquiryList();
}
