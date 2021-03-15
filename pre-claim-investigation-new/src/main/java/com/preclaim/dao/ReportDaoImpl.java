package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.CustomMethods;
import com.preclaim.models.RegionwiseList;
import com.preclaim.models.TopInvestigatorList;

public class ReportDaoImpl implements ReportDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<String> getRegion() {
		try
		{
			String sql = "select DISTINCT state from location_lists";
			return template.query(sql, (ResultSet rs, int row) -> {
				return rs.getString("state");
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public HashMap<String, String> getVendor() {
		try
		{
			String sql = "select * from admin_user where role_name = 'AGNSUP'";
			return template.query(sql, (ResultSet rs, int row) -> 
			{
				HashMap<String, String> vendor = new HashMap<String, String>();
				vendor.put(rs.getString("username"), rs.getString("full_name"));
				while(rs.next())
					vendor.put(rs.getString("username"), rs.getString("full_name"));
				return vendor;
			}).get(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public List<TopInvestigatorList> getTopInvestigatorList(String startDate, String endDate) {
		
		String user_lists = "";
		// Main Query - Query to get Top 15 Investigators
		List<String> investigator_list = new ArrayList<String>();
		String sql = "SELECT TOP 15 b.username, count(*) as grandTotal FROM case_lists a ,"
				+ "(SELECT TOP 1 a.caseId, b.* FROM  audit_case_movement a, admin_user b "
				+ "where a.user_role = 'AGNSUP' and a.toId = b.username "
				+ "order by a.updatedDate desc) b "
				+ "where a.caseId = b.caseId and a.caseSubStatus IN ('Clean','Not-Clean') "
				+ "group by b.username order by count(*) desc";
		
		user_lists = template.query(sql, 
				(ResultSet rs, int rowNum) -> 
				{
					String userId = "";
					do 
					{
						investigator_list.add(rs.getString("username"));
						userId +=  "'" + rs.getString("username") + "',";
					}
					while(rs.next());			
					return userId;
					
				}).get(0);
		
		user_lists = user_lists.substring(0, user_lists.length() - 1);
		System.out.println(user_lists);
		
		//Query 2 - Query to categorize Clean, Not Clean 
		
		HashMap<String, Integer> clean = new HashMap<String, Integer>();
		HashMap<String, Integer> notClean = new HashMap<String, Integer>();
		
		sql = "SELECT b.username, a.caseSubStatus, count(*) as substatusTotal FROM case_lists a ,"
				+ "(SELECT TOP 1 a.caseId, b.* FROM  audit_case_movement a, admin_user b "
				+ "where a.user_role = 'AGNSUP' and a.toId = b.username "
				+ "order by a.updatedDate desc) b "
				+ "where a.caseId = b.caseId and b.username in ( " + user_lists +  ") and "
				+ "a.caseSubStatus IN ('Clean','Not-Clean') "
				+ "group by b.username, a.caseSubStatus order by count(*) desc";
		
		template.query(sql,(ResultSet rs, int rowNum) -> {
			do 
			{
				if(rs.getString("caseSubStatus").equals("Clean"))
					clean.put(rs.getString("username"),rs.getInt("substatusTotal"));
				
				else if(rs.getString("caseSubStatus").equals("Not-Clean"))
					notClean.put(rs.getString("username"),rs.getInt("substatusTotal"));
			}while(rs.next());
			
			return "";
		});

		//Query 3 - Query to map username & fullname 
		
		HashMap<String, String> user_mapping = new HashMap<String, String>();
		sql = "SELECT * FROM admin_user b where username in ( " + user_lists +  ")";
		template.query(sql , (ResultSet rs, int rowNum) -> {
			do
			{
				user_mapping.put(rs.getString("username"),rs.getString("full_name"));
			}while(rs.next());
			return user_mapping;
		});
		List<TopInvestigatorList> investigator = new ArrayList<TopInvestigatorList>();
		int cleanCount = 0;
		int NotCleanCount = 0;
		int totalCleanCount = 0;
		int totalNotCleanCount = 0;
		for(String user: investigator_list)
		{
			cleanCount = clean.get(user) == null ? 0 : clean.get(user);
			NotCleanCount = notClean.get(user) == null ? 0 : notClean.get(user);
			investigator.add(new TopInvestigatorList(user_mapping.get(user), cleanCount, NotCleanCount));
			totalCleanCount += cleanCount; 
			totalNotCleanCount += NotCleanCount;
		}
		investigator.add(new TopInvestigatorList("Total", totalCleanCount, totalNotCleanCount));
		
		return investigator;
	}
	
	@Override
	public List<RegionwiseList> getRegionwiseList(String region, String startDate, String endDate) {
		
		String user_lists = "";
		List<String> investigator_list = new ArrayList<String>();
		String sql = "SELECT b.username, count(*) as grandTotal FROM case_lists a, "
				+ "(SELECT TOP 1 a.caseId, b.* FROM  audit_case_movement a, admin_user b "
				+ "where a.user_role = 'AGNSUP' and a.toId = b.username and b.state = '" + region + "' "
				+ "order by a.updatedDate desc) b where a.caseId = b.caseId and "
				+ "a.caseStatus IN ('Closed','WIP') group by b.username order by count(*) desc";
		
		System.out.println(sql);
		
		user_lists = template.query(sql, 
				(ResultSet rs, int rowNum) -> 
				{
					String userId = "";
					do 
					{
						investigator_list.add(rs.getString("username"));
						userId +=  "'" + rs.getString("username") + "',";
					}
					while(rs.next());			
					return userId;
					
				}).get(0);
		
		user_lists = user_lists.substring(0, user_lists.length() - 1);
		System.out.println(user_lists);
		
		HashMap<String, Integer> clean = new HashMap<String, Integer>();
		HashMap<String, Integer> notClean = new HashMap<String, Integer>();
		HashMap<String, Integer> pivstopped = new HashMap<String, Integer>();
		HashMap<String, Integer> wip = new HashMap<String, Integer>();
		
		sql = "SELECT b.username, a.caseSubStatus , count(*) as substatusTotal FROM case_lists a, "
				+ "(SELECT TOP 1 a.caseId, b.* FROM  audit_case_movement a, admin_user b "
				+ "where a.user_role = 'AGNSUP' and a.toId = b.username and "
				+ "b.username in (" + user_lists + ") and b.state = '" + region + "' "
				+ "order by a.updatedDate desc) b where a.caseId = b.caseId and "
				+ "a.caseStatus = 'Closed' group by b.username, a.caseSubStatus order by count(*) desc";
		
		System.out.println(sql);
		
		template.query(sql,(ResultSet rs, int rowNum) -> {
			do 
			{
				if(rs.getString("caseSubStatus").equals("Clean"))
					clean.put(rs.getString("username"),rs.getInt("substatusTotal"));
				
				else if(rs.getString("caseSubStatus").equals("Not-Clean"))
					notClean.put(rs.getString("username"),rs.getInt("substatusTotal"));
				
				else if(rs.getString("caseSubStatus").equals("PIV Stopped"))
					pivstopped.put(rs.getString("username"),rs.getInt("substatusTotal"));
			}while(rs.next());
			
			return "";
		});
		
		sql = "SELECT b.username, count(*) as statusTotal FROM case_lists a, "
				+ "(SELECT TOP 1 a.caseId, b.* FROM  audit_case_movement a, admin_user b "
				+ "where a.user_role = 'AGNSUP' and a.toId = b.username and "
				+ "b.username in (" + user_lists + ") and b.state = '" + region + "' "
				+ "order by a.updatedDate desc) b where a.caseId = b.caseId and "
				+ "a.caseStatus = 'WIP' group by b.username order by count(*) desc";
		
		System.out.println(sql);
		
		template.query(sql,(ResultSet rs, int rowNum) -> {
			do 
			{
				wip.put(rs.getString("username"),rs.getInt("statusTotal"));
			}while(rs.next());
			
			return "";
		});
		
		HashMap<String, String> user_mapping = new HashMap<String, String>();
		sql = "SELECT * FROM admin_user b where username in ( " + user_lists +  ")";
		template.query(sql , (ResultSet rs, int rowNum) -> {
			do
			{
				user_mapping.put(rs.getString("username"),rs.getString("full_name"));
			}while(rs.next());
			return user_mapping;
		});
		List<RegionwiseList> regionwise = new ArrayList<RegionwiseList>();
		for(String user: investigator_list)
		{
			regionwise.add(new RegionwiseList(user_mapping.get(user),
					clean.get(user) == null ? 0 : clean.get(user),
					notClean.get(user) == null ? 0 : notClean.get(user),
					pivstopped.get(user) == null ? 0 : pivstopped.get(user),
					wip.get(user) == null ? 0 : wip.get(user)));
		}
		
		/*
		regionwise.add(new RegionwiseList("Fourth Force Survelliance Indo Pvt. Ltd",344,67,17,104));
		regionwise.add(new RegionwiseList("N.S Advisory Services",264,71,6,45));
		regionwise.add(new RegionwiseList("NSA",143,10,14,104));
		regionwise.add(new RegionwiseList("OURS",104,1,8,54));
		regionwise.add(new RegionwiseList("Shilpi",83,13,6,62));
		regionwise.add(new RegionwiseList("Findcentric cornerstone",37,2,3,14));
		regionwise.add(new RegionwiseList("Shakti Services",15,8,2,4));
		regionwise.add(new RegionwiseList("SMA E Expert",2,2,0,1));
		regionwise.add(new RegionwiseList("Internal",0,0,4,0));
		regionwise.add(new RegionwiseList("Ajoy Ghosh",2,1,0,0));
		regionwise.add(new RegionwiseList("Iservze",0,0,0,2));
		regionwise.add(new RegionwiseList("Deo Investigators",1,0,0,0));
		regionwise.add(new RegionwiseList("Jp Case",0,0,0,1));
		regionwise.add(new RegionwiseList("SMA EXPERTS",0,1,0,0));
		regionwise.add(new RegionwiseList("Pranab Kumar Nath",0,1,0,0));
		*/
		return regionwise;
	}	

}
