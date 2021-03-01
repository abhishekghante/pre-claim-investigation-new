package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.CustomMethods;
import com.preclaim.models.CaseHistory;
import com.preclaim.models.CaseMovement;

public class Case_movementDaoImpl implements Case_movementDao {

	private static final Logger LOGGER = Logger.getLogger(Case_movementDao.class);
	
	@Autowired
	DataSource datasource;

	@Autowired
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String CreatecaseMovement(CaseMovement caseMovement) {
		try
		{
		   String query="INSERT INTO case_movement(caseId, fromID, toId, caseStatus, remarks, "
		   		+ "createdDate, updatedDate) values(?, ?, ?, ?, ?, getdate(), getdate()) ";
		   this.template.update(query,caseMovement.getCaseId(), caseMovement.getFromId(), 
				   caseMovement.getToId(), caseMovement.getCaseStatus(), caseMovement.getRemarks());
		 
		   query="INSERT INTO audit_case_movement(caseId, fromID, toId, caseStatus, remarks, "
		   		+ "createdDate, updatedDate) values(?, ?, ?, ?, ?, getdate(), getdate()) ";
		   this.template.update(query,caseMovement.getCaseId(), caseMovement.getFromId(), 
				   caseMovement.getToId(),caseMovement.getCaseStatus(), caseMovement.getRemarks());
			
	    }
		catch(Exception e) 
		{	
			e.printStackTrace();
			LOGGER.error(caseMovement.toString());
			CustomMethods.logError(e);
			return e.getMessage();	
		}
		return "****";
	}

	@Override
	public CaseMovement getCaseById(long caseId) {
		String sql = "SELECT * FROM case_movement where caseId = ?";	
		return template.query(sql, new Object[] {caseId}, (ResultSet rs, int rowNum) -> {
			CaseMovement case_movement = new CaseMovement();
			case_movement.setCaseId(caseId);
			case_movement.setFromId(rs.getString("fromId"));
			case_movement.setToId(rs.getString("toId"));
			case_movement.setCaseStatus(rs.getString("caseStatus"));
			case_movement.setRemarks(rs.getString("Remarks"));
			return case_movement;
		}).get(0);
	}

	@Override
	public String updateCaseMovement(CaseMovement caseMovement) {
		try
		{
		   String query="UPDATE case_movement SET fromID = ?, toId = ?, caseStatus = ?, remarks = ?, "
		   		+ "updatedDate = getdate() where caseId = ?";
		   this.template.update(query,caseMovement.getFromId(), caseMovement.getToId(), 
				   caseMovement.getCaseStatus(),caseMovement.getRemarks(), caseMovement.getCaseId());
		 
		   query = "INSERT INTO audit_case_movement(caseId, fromID, toId, caseStatus, remarks, "
		   		+ "createdDate, updatedDate) "
		   		+ "values(?, ?, ?, ?, ?, getdate(), getdate()) ";
		   this.template.update(query, caseMovement.getCaseId(), caseMovement.getFromId(), 
				   caseMovement.getToId(), caseMovement.getCaseStatus(), caseMovement.getRemarks());
			
		   if(caseMovement.getCaseStatus().equals("Closed"))
		   {
			   query = "UPDATE case_lists SET caseStatus = ?, updatedBy = ?, updatedDate = getDate() "
			   		+ "where caseId = ?";
			   template.update(query,caseMovement.getCaseStatus(), caseMovement.getFromId(), 
					   caseMovement.getCaseId());
		   }
		   
	    }
		catch(Exception e) 
		{	
			e.printStackTrace();
			LOGGER.error(caseMovement.toString());
			CustomMethods.logError(e);
			return e.getMessage();
		}		
		return "****";
	}

	@Override
	public List<CaseHistory> getCaseMovementHistory(long caseId) {
		String sql = "SELECT * FROM audit_case_movement where caseId = ? order by createdDate";	
		List<CaseHistory> case_details = template.query(sql, new Object[] {caseId}, 
				(ResultSet rs, int rowNum) -> 
		{
			CaseHistory case_history = new CaseHistory();
			case_history.setCaseId(caseId);
			case_history.setFromId(rs.getString("fromId"));
			case_history.setToId(rs.getString("toId"));
			case_history.setCaseStatus(rs.getString("caseStatus"));
			case_history.setRemarks(rs.getString("Remarks"));
			case_history.setCreatedDate(rs.getString("createdDate"));
			case_history.setUpdatedDate(rs.getString("updatedDate"));
			return case_history;
		});
		
		for(int i = 0; i < case_details.size(); i++)
		{
			sql = "SELECT full_name from admin_user where username = '" + case_details.get(i).getFromId() 
					+ "'";
			case_details.get(i).setFromUserName(template.queryForObject(sql, String.class));
		}
		
		for(int i = 0; i < case_details.size(); i++)
		{
			sql = "SELECT role from admin_user a, user_role b where a.role_name = b.role_code and "
					+ "a.username = '" + case_details.get(i).getFromId() + "'";
			case_details.get(i).setRole(template.queryForObject(sql, String.class));
		}
		
		return case_details;
	}


}
