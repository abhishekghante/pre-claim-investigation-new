package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.CustomMethods;
import com.preclaim.models.Location;

public class LocationDaoImpl implements LocationDao {

	@Autowired
	DataSource datasource;
	
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String addLocation(Location location) 
	{
		try 
		{		
			String locationCheck = "SELECT count(*) FROM location_lists WHERE city='" 
					+ location.getCity() + "'";
			int locationCount = this.template.queryForObject(locationCheck, Integer.class);
			if (locationCount == 0) 			 
			{
				String query = "INSERT INTO location_lists(city, state, zone, createdBy, createdDate, "
						+ "updatedDate, updatedBy, status) values(?, ?, ?, ?, getdate(), "
						+ "getdate(), '', 0)";
				template.update(query, location.getCity(), location.getState(), location.getZone(), 
						location.getCreatedBy());
			} 
			else 
				return "City already exists";		    
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
	public List<Location> locationList(int status) 
	{
		String query="";
		if(status==0) 
	      query = "SELECT * FROM location_lists WHERE status = " + status;
	    else 
	    	query = "SELECT * FROM location_lists WHERE status = 1 or status = 2";
		return template.query(query, (ResultSet rs, int rowNum) -> {
			Location locationList = new Location();
			locationList.setLocationId(rs.getInt("locationId"));
			locationList.setCity(rs.getString("city"));
			locationList.setState(rs.getString("state"));
			locationList.setZone(rs.getString("zone"));
			locationList.setStatus(rs.getInt("status"));
			return locationList;
		});
	}

	@Override
	public String deleteLocation(int locationId) {
		try 
		{
			String query = "DELETE FROM location_lists WHERE locationId = ?";
			template.update(query, locationId);
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
	public String updateLocation(int locationId, String city, String state, String zone, String updatedBy) {
		try 
		{
			String sql = "UPDATE location_lists SET city = ? , state = ?, zone =  ?, "
					+ "updatedDate = getdate(), updatedBy = ? WHERE locationId = ?";
			template.update(sql, city, state, zone, updatedBy, locationId);
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
	public String updateLocationStatus(int locationId, int status, String username) {
		try 
		{
		  String query = "UPDATE location_lists SET status = ?, updatedBy = ?, updatedDate = getdate() "
		  		+ "WHERE locationId = ?";
          this.template.update(query, status, username, locationId);	  
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
	public Location getLocationById(int locationId) {
		try
		{
			String sql = "SELECT * FROM location_lists where locationId = " + locationId;
			return template.query(sql,   
					(ResultSet rs, int rowNum) -> 
					{
						Location location = new Location();
						location.setLocationId(rs.getInt("locationId"));
						location.setCity(rs.getString("city"));
						location.setState(rs.getString("state"));
						location.setZone(rs.getString("zone"));
						return location;
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
	public List<Location> getActiveLocationList() {
		String query = "SELECT * FROM location_lists WHERE status = 1";
		return template.query(query, (ResultSet rs, int rowNum) -> {
			Location location = new Location();
			location.setLocationId(rs.getInt("locationId"));
			location.setCity(rs.getString("city"));
			location.setState(rs.getString("state"));
			location.setZone(rs.getString("zone"));
			return location;
		});
	}
}
