package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.CustomMethods;
import com.preclaim.models.BillManagementList;

public class BillingManagementDaoImpl implements BillingManagementDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	InvestigationTypeDao investigationDao;

	@Autowired
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
		@Override
		public List<BillManagementList> billPaymentList() 
		{
			HashMap<Integer, String> investigationType = investigationDao.getActiveInvestigationMapping();
			String sql = 
					"SELECT * FROM case_lists a ,"
					+ "(select * from ("
					+ "select a.caseId, a.toId from audit_case_movement a, ("
					+ "select caseId, max(updatedDate) as updatedDate from audit_case_movement where user_role = 'AGNSUP' "
					+ "group by caseId) b where a.caseId = b.caseId and a.updatedDate = b.updatedDate) a, admin_user b "
					+ "where a.toId = b.username) b where  a.caseId = b.caseId and a.caseStatus = 'Closed' and a.paymentApproved = ''";
					
			return template.query(sql, (ResultSet rs, int rowNum) -> {
				BillManagementList billManagementList = new BillManagementList();
				billManagementList.setSrNo(rowNum + 1);
				billManagementList.setCaseID(rs.getInt("caseId"));
				billManagementList.setPolicyNumber(rs.getString("policyNumber"));
				billManagementList.setInitimationType(rs.getString("intimationType"));
				billManagementList.setInvestigationType(investigationType.get(rs.getInt("investigationId")));
				billManagementList.setSupervisorID(rs.getString("username"));
				billManagementList.setSupervisorName(rs.getString("full_name"));
				billManagementList.setCharges(rs.getDouble("fees"));
				return billManagementList;
			});
		}
		
		@Override
		public Map<Integer, Object[]> billPaymentList(String list) 
		{
			HashMap<Integer, String> investigationType = investigationDao.getActiveInvestigationMapping();
			String sql = "SELECT * FROM case_lists a ,"
					+ "(select * from ("
					+ "select a.caseId, a.toId from audit_case_movement a, ("
					+ "select caseId, max(updatedDate) as updatedDate from audit_case_movement where user_role = 'AGNSUP' "
					+ "group by caseId) b where a.caseId = b.caseId and a.updatedDate = b.updatedDate) a, admin_user b "
					+ "where a.toId = b.username) b where  a.caseId = b.caseId and a.caseStatus = 'Closed' and a.paymentApproved = ''"
					+ " and a.caseId in('" + list + "')";
		 
			Map<Integer, Object[]> paidcases = new HashMap<Integer, Object[]>();
			paidcases.put(0, new Object[] {"Sr No", "Case ID", "Policy Number", "Investigation Type", 
				"Intimation Type", "Insured Name", "Insured DOB", "Insured DOD", "Insured Address",
				"Nominee Name", "Nominee Contact Number", "Nominee Address", "Sum Assured",
				"Longitude", "Latitude", "Case Description", "Captured Date",
				"Agency Supervisor", "Fees"});
			
			return template.query(sql,  
					(ResultSet rs, int rowNum) ->
					{
						int i = 1;
						paidcases.put(i, new Object[] {
								i,rs.getLong("caseId"), rs.getString("policyNumber"),
								investigationType.get(rs.getInt("investigationId")), 
								rs.getString("intimationType"), rs.getString("insuredName"), 
								rs.getString("insuredDOB"), rs.getString("insuredDOD"), 
								rs.getString("insured_address"), rs.getString("nominee_Name"), 
								rs.getString("nominee_ContactNumber"), rs.getString("nominee_address"), 
								rs.getString("sumAssured"),rs.getString("longitude"), 
								rs.getString("latitude"), rs.getString("case_description"), 
								rs.getString("capturedDate"), rs.getString("full_name"), 
								rs.getFloat("fees") 
						});
						while(rs.next())
						{
							paidcases.put(i, new Object[] {
									i,rs.getLong("caseId"), rs.getString("policyNumber"),
									investigationType.get(rs.getInt("investigationId")), 
									rs.getString("intimationType"), rs.getString("insuredName"), 
									rs.getString("insuredDOB"), rs.getString("insuredDOD"), 
									rs.getString("insured_address"), rs.getString("nominee_Name"), 
									rs.getString("nominee_ContactNumber"), rs.getString("nominee_address"), 
									rs.getString("sumAssured"),rs.getString("longitude"), 
									rs.getString("latitude"), rs.getString("case_description"), 
									rs.getString("capturedDate"), rs.getString("full_name"), 
									rs.getFloat("fees") 
							});
							i++;
						}
						return paidcases;
					}).get(0);
		}
		
		@Override
		public String UpdateFees(String list, String userId) {
			try
			{
				String sql = "UPDATE case_lists  SET paymentApproved = 'Fees Paid', "
						+ "updatedDate = getDate(), updatedBy = ?  WHERE caseId in ("+ list +")";
				this.template.update(sql, userId);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				CustomMethods.logError(e);
				return e.getMessage();
			}
			return "****";
		}
		
		@Override
		public List<BillManagementList> billEnquiryList() 
		{
			HashMap<Integer, String> investigationType = investigationDao.getActiveInvestigationMapping();
			String sql = 
					"SELECT * FROM case_lists a ,"
					+ "(select * from ("
					+ "select a.caseId, a.toId from audit_case_movement a, ("
					+ "select caseId, max(updatedDate) as updatedDate from audit_case_movement where "
					+ "user_role = 'AGNSUP' "
					+ "group by caseId) b where a.caseId = b.caseId and a.updatedDate = b.updatedDate) a, admin_user b "
					+ "where a.toId = b.username) b where  a.caseId = b.caseId and a.caseStatus = 'Closed' and "
					+ "a.paymentApproved <> ''";
		 
			return template.query(sql, (ResultSet rs, int rowNum) -> {
				BillManagementList billManagementList = new BillManagementList();
				billManagementList.setSrNo(rowNum + 1);
				billManagementList.setCaseID(rs.getInt("caseId"));
				billManagementList.setPolicyNumber(rs.getString("policyNumber"));
				billManagementList.setInitimationType(rs.getString("intimationType"));
				billManagementList.setInvestigationType(investigationType.get(rs.getInt("investigationId")));
				billManagementList.setSupervisorID(rs.getString("username"));
				billManagementList.setSupervisorName(rs.getString("full_name"));
				billManagementList.setCharges(rs.getDouble("fees"));
				return billManagementList;
			});
		}
		
		
		
		



}
