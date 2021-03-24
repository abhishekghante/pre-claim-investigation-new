package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.CustomMethods;
import com.preclaim.models.CaseCategory;
import com.preclaim.models.CaseCategoryList;

public class CaseCategoryDaoImpl implements CaseCategoryDao {

	@Autowired
	DataSource datasource;

	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String add_caseCategory(CaseCategory caseCategory) {
		try 
		{
			String caseCategoryCheck = "select count(*) from case_category where "
					+ "caseStatus = '" + caseCategory.getCaseStatus() + "' and "
					+ "caseCategory = '" + caseCategory.getCaseCategory() + "'";
			int caseCategoryCount = this.template.queryForObject(caseCategoryCheck, Integer.class);
			/* System.out.println(intimationType.toString()); */
			if (caseCategoryCount == 0) 
			{
				String query = "INSERT INTO case_category(caseStatus, caseCategory, createdBy, createdDate, updatedDate, "
						+ "updatedBy, status) values(?, ?, ? , getdate(), getdate(), ?, ?)";
				template.update(query, caseCategory.getCaseStatus(), caseCategory.getCaseCategory(), caseCategory.getCreatedBy(), 
						caseCategory.getUpdatedBy(), caseCategory.getStatus());
			}
			else
				return "Case Category already exists";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return e.getMessage();
		}
		return "****";
	}

	@Override
	public List<CaseCategoryList> caseCategory_list(int status) {
		String query = "";		
		if(status == 0) 
			query = "SELECT * FROM case_category WHERE status = " + status;
		else 
			query = "SELECT * FROM case_category WHERE status = 1 or status = 2";
		return this.template.query(query, (ResultSet rs, int rowNum) -> {
			CaseCategoryList caseCategoryList = new CaseCategoryList();
			caseCategoryList.setCaseCategoryId(rs.getInt("caseCategoryId"));
			caseCategoryList.setSrNo(rowNum + 1);
			caseCategoryList.setCaseStatus(rs.getString("caseStatus"));
			caseCategoryList.setCaseCategory(rs.getString("caseCategory"));
			caseCategoryList.setCreatedDate(rs.getString("createdDate"));
			caseCategoryList.setStatus(rs.getInt("status"));
			return caseCategoryList;
		});
	}

	@Override
	public String deleteCaseCategory(int caseCategoryId) {
		try 
		{
			String query = "DELETE FROM case_category WHERE caseCategoryId = ?";
			template.update(query, caseCategoryId);
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
	public String updateCaseCategory(int caseCategoryId, String caseStatus, String caseCategory, 
			String username) {
		try 
		{
			String caseCategoryCheck = "select count(*) from case_category where "
					+ "caseStatus = '" + caseStatus + "' and "
					+ "caseCategory = '" + caseCategory + "'";
			int caseCategoryCount = this.template.queryForObject(caseCategoryCheck, Integer.class);
			/* System.out.println(intimationType.toString()); */
			if (caseCategoryCount == 0) 
				return "Case Category already exists";
			String sql = "UPDATE case_category SET caseCategory = ? , updatedDate = getdate(), "
					+ "updatedBy = ? WHERE caseCategoryId = ?";
			template.update(sql, caseCategory, username, caseCategoryId);
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
	public String updateCaseCategoryStatus(int caseCategoryId, int status, String username) {
		try 
		{
			String sql="UPDATE case_category SET status = ?, updatedDate = getdate(), updatedBy = ? "
					+ "WHERE caseCategoryId = ?";       
			template.update(sql, status, username, caseCategoryId);	
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
	public List<CaseCategory> getActiveCaseCategory()
	{
		String sql = "SELECT * FROM case_category where status = 1";
		return template.query(sql,
				(ResultSet rs, int rowNum) ->
				{
				
					CaseCategory caseCategory = new CaseCategory();
					caseCategory.setCaseCategoryId(rs.getInt("caseCategoryId"));
					caseCategory.setCaseStatus(rs.getString("caseStatus"));
					caseCategory.setCaseCategory(rs.getString("caseCategory"));
					return caseCategory;
				}
				);
	}

	@Override
	public List<String> getActiveCaseCategoryStringList() {
		String sql = "SELECT * FROM case_category where status = 1";
		return template.query(sql, (ResultSet rs, int rowNum) ->
				{return rs.getString("caseCategory");});
	}
	

}
