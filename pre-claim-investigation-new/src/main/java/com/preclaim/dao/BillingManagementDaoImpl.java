package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.models.BillManagement;
import com.preclaim.models.BillManagementList;
import com.preclaim.models.Location;

public class BillingManagementDaoImpl implements BillingManagementDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
		@Override
		public List<BillManagementList> billingEnquiryPendingList() 
		{
			String sql="SELECT * FROM case_lists a, case_movement b where a.caseId = b.caseId and a.caseStatus = 'Closed'";
		 
			return template.query(sql, (ResultSet rs, int rowNum) -> {
				
				BillManagementList billManagementList = new BillManagementList();
				billManagementList.setSrNo(rowNum + 1);
				billManagementList.setCaseID(rs.getInt("caseId"));
				billManagementList.setPolicyNumber(rs.getString("policyNumber"));
				billManagementList.setInitimationType(rs.getString("intimationType"));
				billManagementList.setInvestigationId(rs.getInt("investigationId"));
				billManagementList.setSupervisorID(rs.getString("fromId"));
				billManagementList.setSupervisorName(rs.getString("createdBy"));
				billManagementList.setCharges(rs.getDouble("fees"));
				return billManagementList;
			});
		}
		
		@Override
		public List<BillManagementList> billingEnquiryPendingList(Integer list) 
		{
			String sql="SELECT * FROM case_lists a, case_movement b where a.caseId = b.caseId and a.caseStatus = 'Closed' and b.caseId in('"+list+"')";
		 
			return template.query(sql, (ResultSet rs, int rowNum) -> {
				
				BillManagementList billManagementList = new BillManagementList();
				billManagementList.setSrNo(rowNum + 1);
				billManagementList.setCaseID(rs.getInt("caseId"));
				billManagementList.setPolicyNumber(rs.getString("policyNumber"));
				billManagementList.setInitimationType(rs.getString("intimationType"));
				billManagementList.setInvestigationId(rs.getInt("investigationId"));
				billManagementList.setSupervisorID(rs.getString("fromId"));
				billManagementList.setSupervisorName(rs.getString("createdBy"));
				billManagementList.setCharges(rs.getDouble("fees"));
				return billManagementList;
			});
		}


}
