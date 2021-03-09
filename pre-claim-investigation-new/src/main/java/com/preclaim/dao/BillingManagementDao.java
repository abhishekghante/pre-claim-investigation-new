package com.preclaim.dao;

import java.util.List;

import com.preclaim.models.BillManagement;
import com.preclaim.models.BillManagementList;

public interface BillingManagementDao {

	List<BillManagementList> billingPaymentPendingList();
	List<BillManagementList> billingPaymentPendingList(Integer list);
	public String UpdateFees(Integer list);
	public List<BillManagementList> billingEnquiryPendingList();
}
