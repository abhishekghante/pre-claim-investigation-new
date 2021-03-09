package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.CustomMethods;
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
		List<TopInvestigatorList> investigator = new ArrayList<TopInvestigatorList>();
		investigator.add(new TopInvestigatorList("Fox i Vision", 1303, 363));
		investigator.add(new TopInvestigatorList("Fourth Force Survelliance Indo Pvt. Ltd",1453,118));
		investigator.add(new TopInvestigatorList("SMA E Expert",648,152));
		investigator.add(new TopInvestigatorList("Lancers Network Limited",745,90));
		investigator.add(new TopInvestigatorList("Vision investigation services",559,96));
		investigator.add(new TopInvestigatorList("Deo Investigators",532,63));
		investigator.add(new TopInvestigatorList("Global Risk Management Services",482,90));
		investigator.add(new TopInvestigatorList("Iservze",330,16));
		investigator.add(new TopInvestigatorList("Integrated Information Services",604,75));
		investigator.add(new TopInvestigatorList("CAG SERVICES",562,12));
		investigator.add(new TopInvestigatorList("Alpine Risk mitigation services ",402,188));
		investigator.add(new TopInvestigatorList("Oracle Investigation",390,99));
		investigator.add(new TopInvestigatorList("SUNRISE INVESTIGATOR",346,8));
		investigator.add(new TopInvestigatorList("CP Solutions",302,25));
		investigator.add(new TopInvestigatorList("Findcentric cornerstone",356,5));
		investigator.add(new TopInvestigatorList("Total",12568,1777));
		
		return investigator;
	}
	
		

}
