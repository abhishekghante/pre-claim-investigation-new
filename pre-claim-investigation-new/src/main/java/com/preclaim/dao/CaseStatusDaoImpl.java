package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.CustomMethods;
import com.preclaim.models.CaseStatus;
import com.preclaim.models.CaseStatusList;

public class CaseStatusDaoImpl implements CaseStatusDao {

	@Autowired
	DataSource datasource;

	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String add_caseStatus(CaseStatus caseStatus) {
		try 
		{
			String caseStatusCheck = "select count(*) from case_status where caseStatus='" 
					+ caseStatus.getCaseStatus() + "'";
			int caseStatusCount = this.template.queryForObject(caseStatusCheck, Integer.class);
			/* System.out.println(intimationType.toString()); */
			if (caseStatusCount == 0) 
			{
				String query = "INSERT INTO case_status(caseStatus, createdBy, createdDate, updatedDate, "
						+ "updatedBy, status) values(?, ? , getdate(), getdate(), ?, ?)";
				template.update(query, caseStatus.getCaseStatus(), caseStatus.getCreatedBy(), 
						caseStatus.getUpdatedBy(), caseStatus.getStatus());
			}
			else
				return "Case Status already exists";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return e.getMessage();
		}
		return "****";
	}

	@Override
	public List<CaseStatusList> caseStatus_list(int status) {
		String query = "";		
		if(status == 0) 
			query = "SELECT * FROM case_status WHERE status = " + status;
		else 
			query = "SELECT * FROM case_status WHERE status = 1 or status = 2";
		return this.template.query(query, (ResultSet rs, int rowNum) -> {
			CaseStatusList caseStatusList = new CaseStatusList();
			caseStatusList.setCaseStatusId(rs.getInt("caseStatusId"));
			caseStatusList.setSrNo(rowNum + 1);
			caseStatusList.setCaseStatus(rs.getString("caseStatus"));
			caseStatusList.setCreatedDate(rs.getString("createdDate"));
			caseStatusList.setStatus(rs.getInt("status"));
			return caseStatusList;
		});
	}

	@Override
	public String deleteCaseStatus(int caseStatusId) {
		try 
		{
			String query = "DELETE FROM case_status WHERE caseStatusId = ?";
			template.update(query, caseStatusId);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
		return "****";
	}

	@Override
	public String updateCaseStatus(int caseStatusId, String caseStatus, String username) {
		try {
			String CaseStatusCheck = "select count(*) from case_status where caseStatus='" 
					+ caseStatus + "' and caseStatusId <> " + caseStatusId;
			int CaseStatusCount = this.template.queryForObject(CaseStatusCheck, Integer.class);
			if(CaseStatusCount > 0)
				return "Case Status already exists";
			String sql = "UPDATE case_status SET caseStatus = ? , updatedDate = getdate(), "
					+ "updatedBy = ? WHERE caseStatusId = ?";
			template.update(sql, caseStatus, username, caseStatusId);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
		return "****";
	}

	@Override
	public String updateStatus(int caseStatusId, int status, String username) {
		try 
		{
			String sql="UPDATE case_status SET status = ?, updatedDate = getdate(), updatedBy = ? "
					+ "WHERE caseStatusId = ?";       
			template.update(sql, status, username, caseStatusId);	
           return  "****";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
	}
		
	@Override
	public List<CaseStatus> getActiveCaseStatus()
	{
		String sql = "SELECT * FROM case_status where status = 1";
		return template.query(sql,
				(ResultSet rs, int rowNum) ->
				{
				
					CaseStatus caseStatus = new CaseStatus();
					caseStatus.setCaseStatusId(rs.getInt("caseStatusId"));
					caseStatus.setCaseStatus(rs.getString("caseStatus"));
					return caseStatus;
				}
				);
	}

	@Override
	public List<String> getActiveCaseStatusStringList() {
		String sql = "SELECT * FROM case_status where status = 1";
		return template.query(sql, (ResultSet rs, int rowNum) ->
				{return rs.getString("caseStatus");});
	}
	

}
