package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.CustomMethods;
import com.preclaim.models.InvestigationType;
import com.preclaim.models.InvestigationTypeList;

public class InvestigationTypeDaoImpl implements InvestigationTypeDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String addInvestigationType(InvestigationType investigationType) {

		try {
			String sql = "INSERT INTO investigation_type(investigationType, createdBy, createdDate"
					+ ", updatedDate, updatedBy, status) values(?, ?, getdate(), getdate(), '', ?)";
			this.template.update(sql,investigationType.getInvestigationType(), 
					investigationType.getCreatedBy(), 0);
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
	public List<InvestigationTypeList> getInvestigationList(int status) {
		String query = "";
		if (status == 0)
			query = "SELECT * FROM investigation_type WHERE status = " + status;
		else
			query = "select * from investigation_type where status = 1 or status = 2";
		return template.query(query, (ResultSet rs, int rowNum) -> {
			InvestigationTypeList investigationTypeList = new InvestigationTypeList();
			investigationTypeList.setSrNo(rowNum + 1);
			investigationTypeList.setInvestigationId(rs.getInt("investigationId"));
			investigationTypeList.setInvestigationType(rs.getString("investigationType"));
			investigationTypeList.setStatus(rs.getInt("status"));
			return investigationTypeList;
		});
	}
	
	@Override
	public List<InvestigationType> getActiveInvestigationList() {
		String query = "SELECT * FROM investigation_type WHERE status = 1";
		return template.query(query, (ResultSet rs, int rowNum) -> {
			InvestigationType investigationTypeList = new InvestigationType();
			investigationTypeList.setInvestigationId(rs.getInt("investigationId"));
			investigationTypeList.setInvestigationType(rs.getString("investigationType"));
			return investigationTypeList;
		});
	}

	public String updateInvestigationTypeStatus(int investigationId, String username, int status) 
	{
		try 
		{
			String sql = "UPDATE investigation_type SET status = ?, updatedDate = getdate(),"
					+ " updatedBy = ? where investigationId = ?";
			this.template.update(sql, status, username, investigationId);
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
	public String deleteInvestigationType(int investigationId) {
		try 
		{
			String sql = "DELETE FROM investigation_type WHERE investigationId = ?";
			this.template.update(sql,investigationId);
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
	public String updateInvestigationType(String investigationType, String username, int investigationId) 
	{
		try
		{
			String sql = "UPDATE investigation_type SET investigationType = ?, updatedDate = getdate(), "
					+ "updatedBy = ? where investigationId = ?";
			template.update(sql, investigationType, username, investigationId);
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
	public List<String> getActiveInvestigationStringList() {
		String query = "SELECT * FROM investigation_type WHERE status = 1";
		return template.query(query, (ResultSet rs, int rowNum) -> {
			return rs.getString("investigationType");
		});
	}
	
	@Override
	public HashMap<Integer,String> getActiveInvestigationMapping() {
		String query = "SELECT * FROM investigation_type WHERE status = 1";
		return template.query(query, (ResultSet rs, int rowNum) -> {
			HashMap<Integer,String> mapping = new HashMap<Integer,String>();
			mapping.put(rs.getInt("investigationId"), rs.getString("investigationType"));
			while(rs.next())
				mapping.put(rs.getInt("investigationId"), rs.getString("investigationType"));
			return mapping;
		}).get(0);
	}

	@Override
	public String getInvestigationById(int investigationId) {
		String sql = "SELECT investigationType from investigation_type where investigationId = " 
					+ investigationId;
		return template.queryForObject(sql, String.class);
	}
}
