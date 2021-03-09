package com.preclaim.dao;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.models.UserDetails;

public class DashboardDaoImpl implements DashboardDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	JdbcTemplate template;
	
	@Autowired
	UserDAO userdao;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public HashMap<String, Integer> getCaseCount(UserDetails user) {
		
		HashMap<String, Integer> dashboardCount = new HashMap<String, Integer>();
		List<String> permission = userdao.retrievePermission(user.getAccount_type());
		
		int count = 0;
		String sql = ""; 
		if(permission.contains("dashboard/new"))
		{
			sql = "SELECT count(*) from case_movement where toId = ''";
			count = template.queryForObject(sql, Integer.class);
			dashboardCount.put("New Cases", count);
		}
		
		if(permission.contains("dashboard/assigned"))
		{
			if(user.getAccount_type().equals("SUPADM"))
				sql = "SELECT count(*) from case_movement where user_role = 'AGNSUP'";
			else
				sql = "SELECT count(*) from case_movement where toId = '" + user.getUsername() + "'";
			count = template.queryForObject(sql, Integer.class);
			dashboardCount.put("Assigned Cases", count);
		}
		
		if(permission.contains("dashboard/piv"))
		{
			if(user.getAccount_type().equals("SUPADM"))
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " a.intimationType in ('PIV', 'PIRV', 'LIVE') and b.user_role = 'INV'";
			else
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " a.intimationType in ('PIV', 'PIRV', 'LIVE') and b.toId = '" + user.getUsername() + "'";
			count = template.queryForObject(sql, Integer.class);
			dashboardCount.put("PIV/PIRV/LIVE Pending", count);
		}
		
		if(permission.contains("dashboard/cdp"))
		{
			if(user.getAccount_type().equals("SUPADM"))
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " a.intimationType = 'CDP' and b.user_role = 'INV'";
			else
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " a.intimationType = 'CDP' and b.toId = '" + user.getUsername() + "'";
			count = template.queryForObject(sql, Integer.class);
			dashboardCount.put("Claim Document pickup / Claim Investigation", count);
		}
		
		if(permission.contains("dashboard/review"))
		{
			if(user.getAccount_type().equals("SUPADM"))
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " a.case_description <> '' and b.user_role = 'AGNSUP'";
			else
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " a.case_description <> '' and b.toId = '" + user.getUsername() + "'";
			count = template.queryForObject(sql, Integer.class);
			dashboardCount.put("Review Report", count);
		}
		
		if(permission.contains("dashboard/active"))
		{
			if(user.getAccount_type().equals("SUPADM"))
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " b.user_role = 'CLAIMS'";
			else
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " b.toId = '" + user.getUsername() + "'";
			count = template.queryForObject(sql, Integer.class);
			dashboardCount.put("Active Cases", count);
		}
		
		if(permission.contains("dashboard/wip"))
		{
			if(user.getAccount_type().equals("SUPADM"))
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " b.user_role = 'UW'";
			else
				sql = "SELECT count(*) from case_lists a, case_movement b where a.caseId = b.caseId and "
						+ " b.toId = '" + user.getUsername() + "'";
			count = template.queryForObject(sql, Integer.class);
			dashboardCount.put("WIP", count);
		}
		if(permission.contains("dashboard/billing"))
		{
			sql = "SELECT count(*) from case_lists where caseStatus = 'Closed' and paymentApproved = ''";
			count = template.queryForObject(sql, Integer.class);
			dashboardCount.put("Investigation Billing", count);
		}
		return dashboardCount;
	}

}
