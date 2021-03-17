package com.preclaim.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.preclaim.config.Config;
import com.preclaim.config.CustomMethods;
import com.preclaim.models.UserDetails;
import com.preclaim.models.UserList;
import com.preclaim.models.UserRole;

public class UserDAOImpl implements UserDAO{

	@Autowired
	DataSource datasource;
	
	JdbcTemplate template;
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<UserRole> role_lists() {
		String sql = "select * from user_role where status = 1";
		return template.query(sql, new RowMapper<UserRole>(){			
			public UserRole mapRow(ResultSet rs, int row) throws SQLException
			{
				UserRole role = new UserRole();
				role.setRoleId(rs.getLong("roleId"));
				role.setRole(rs.getString("role"));
				role.setRole_code(rs.getString("role_code"));
				role.setStatus(rs.getInt("status"));
				return role;
			}
		});
	}
	
	@Override
	public List<UserRole> getUserRole_lists(String role_code, String status) {
		String sql="";
		
		if(status.equals("Approved"))
		      sql = "select * from user_role where 'approve/' + role_code in (\n"
				      + "select module from permission where module like 'approve/%' and role_code = ?)";
		else if(status.equals("Reassigned"))
		      sql = "select * from user_role where 'reassign/'+ role_code in (\n"
				+ "select module from permission where module like 'reassign/%' and role_code = ?)";
		      
		return template.query(sql,new Object[] {role_code}, new RowMapper<UserRole>(){			
			public UserRole mapRow(ResultSet rs, int row) throws SQLException
			{
				UserRole role = new UserRole();
				role.setRoleId(rs.getLong("roleId"));
				role.setRole(rs.getString("role"));
				role.setRole_code(rs.getString("role_code"));
				role.setStatus(rs.getInt("status"));
				return role;
			}
		});
	}

	@Override
	public String create_user(UserDetails user) {
		String sql = "SELECT count(*) from admin_user where username = '" + user.getUsername() + "'";
		int usernameExists = template.queryForObject(sql, Integer.class);
		if(usernameExists > 0)
			return "Username already exists";
		
		sql = "INSERT INTO admin_user(full_name, role_name, username, user_email, mobile_number, "
				+ "address1, address2, address3, fees, password, state, city, status, user_image, createdBy ,"
				+ "createdon, updatedDate, updatedBy)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, getdate(), getdate(), '')";
		System.out.println(user.getPassword());
		try 
		{
			template.update(sql, user.getFull_name(), user.getAccount_type(), user.getUsername(),
					user.getUser_email(),user.getContactNumber(), user.getAddress1(),user.getAddress2(),user.getAddress3(),user.getFees(),
					user.getPassword(), user.getState(), user.getCity(), user.getStatus(), user.getUserimage(), user.getCreatedBy());
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
	public String create_role(UserRole role) {
		try
		{
			String sql = "SELECT count(*) from user_role where role_code = '" + role.getRole_code() + "'";
			int count = template.queryForObject(sql, Integer.class);
			if(count > 0)
				return "Role code already exists";
			
			sql = "INSERT INTO user_role(role, role_code, status, created_on, updated_on) "
					+ "VALUES(?, ?, ?, getdate(), getdate())";
			template.update(sql, role.getRole(), role.getRole_code(), role.getStatus());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CustomMethods.logError(ex);
			return ex.getMessage();
		}
		return "****";
	}

	@Override
	public String delete_role(int roleId) {
		try
		{
			String sql =  "DELETE FROM permission WHERE role_code IN "
					+ "(SELECT role_code FROM user_role where roleId = ?)";
			template.update(sql, roleId);
			
			sql = "DELETE FROM user_role where roleId = ?";
			template.update(sql, roleId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CustomMethods.logError(ex);
			return ex.getMessage();
		}
		return "****";
	}

	@Override
	public List<UserList> user_list() {
		try
		{
			String sql = "select * from admin_user a, user_role b where a.role_name = b.role_code";			
			List<UserList> user_list = this.template.query(sql, 
					(ResultSet rs, int count) -> {
						UserList user = new UserList();
						user.setSrno(count + 1);
						user.setUser_id(rs.getInt("user_id"));
						user.setFull_name(rs.getString("full_name"));
						user.setAccount_type(rs.getString("role"));
						user.setAccount_code(rs.getString("roleId"));
						user.setUsername(rs.getString("username"));
						user.setUser_email(rs.getString("user_email"));
						user.decodePassword(rs.getString("password"));
						user.setUser_status(rs.getInt("status"));
						return user;
					});
			return user_list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}		
	}

	@Override
	public String deleteAdminUser(int user_id) {
		try
		{
			String sql = "DELETE FROM admin_user where user_id = ?";
			this.template.update(sql, user_id);
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
	public String updateUserStatus(int user_id, int user_status, String username) {
		try
		{
			String sql = "UPDATE admin_user SET status = ?, updatedBy = ? where user_id = ?";
			this.template.update(sql, user_status, username, user_id);
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
	public UserDetails getUserDetails(int user_id) {
		try
		{
			String sql = "SELECT * FROM admin_user where user_id = ?";
			List<UserDetails> user =  this.template.query(sql, new Object[] {user_id}, 
					(ResultSet rs, int rowCount) -> 
					{
						UserDetails details = new UserDetails();
						details.setFull_name(rs.getString("full_name"));
						details.decodePassword(rs.getString("password"));
						details.setStatus(rs.getInt("status"));
						details.setUser_email(rs.getString("user_email"));
						details.setUserimage(rs.getString("user_image"));
						details.setUserImageb64(Config.upload_directory + rs.getString("user_image"));
						details.setAccount_type(rs.getString("role_name"));
						details.setUsername(rs.getString("username"));
						details.setUserID(rs.getInt("user_id"));
						details.setState(rs.getString("state"));
						details.setAddress1(rs.getString("address1"));
						details.setAddress2(rs.getString("address2"));
						details.setAddress3(rs.getString("address3"));
						details.setFees(rs.getDouble("fees"));
						details.setCreatedBy(rs.getString("createdBy"));
						details.setUpdatedBy(rs.getString("updatedBy"));
						details.setCity(rs.getString("city"));
						details.setContactNumber(rs.getString("mobile_number"));
						return details;
					}
					);
			return user.get(0);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public UserDetails getUserDetails(String username) {
		try
		{
			String sql = "SELECT * FROM admin_user where username = ?";
			List<UserDetails> user =  this.template.query(sql, new Object[] {username}, 
					(ResultSet rs, int rowCount) -> 
					{
						UserDetails details = new UserDetails();
						details.setFull_name(rs.getString("full_name"));
						details.decodePassword(rs.getString("password"));
						details.setStatus(rs.getInt("status"));
						details.setUser_email(rs.getString("user_email"));
						details.setUserimage(rs.getString("user_image"));
						details.setUserImageb64(Config.upload_directory + rs.getString("user_image"));
						details.setAccount_type(rs.getString("role_name"));
						details.setUsername(rs.getString("username"));
						details.setUserID(rs.getInt("user_id"));
						details.setState(rs.getString("state"));
						details.setCity(rs.getString("city"));
						details.setContactNumber(rs.getString("mobile_number"));
						return details;
					}
					);
			return user.get(0);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}
	
	@Override
	public String updateUserDetails(UserDetails user_details) {
		try
		{
			String sql = "UPDATE admin_user SET full_name = ?, role_name = ?, user_email = ?, "
					+ "password = ?, status = ?, user_image = ?, city = ?, state = ?, fees =?, mobile_number = ?, "
					+ "address1 = ?, address2 = ?, address3 = ?, updatedDate = getdate(), updatedBy = ? "
					+ "where user_id = ?";
			template.update(sql, user_details.getFull_name(), user_details.getAccount_type(),
					user_details.getUser_email(), user_details.getPassword(), user_details.getStatus(),
					user_details.getUserimage(), user_details.getCity(),user_details.getState(),user_details.getFees(), 
					user_details.getContactNumber(), user_details.getAddress1(), user_details.getAddress2(), 
					user_details.getAddress3(), user_details.getUpdatedBy(), user_details.getUserID());
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
	public String updateUserRole(UserRole role) {
		try
		{
			String sql = "UPDATE user_role SET role = ?, role_code = ?, updated_on = getdate() "
					+ "where roleId = ?";
			template.update(sql, role.getRole(), role.getRole_code(), role.getRoleId());
					
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
	public List<String> retrievePermission(String role_code) {
		try
		{
			String sql = "SELECT * from permission where role_code = ?";
			return template.query(sql, new Object[] {role_code}, 
					(ResultSet rs, int rowCount) ->
					{						
						return rs.getString("module");
					}
					);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CustomMethods.logError(ex);
			return null;
		}
	}

	@Override
	public String addPermission(List<String> role_permission, String role_code) {		
		try
		{
			String sql = "DELETE FROM permission where role_code = ?";
			template.update(sql,role_code);
			sql = "INSERT INTO permission(module, role_code, status, created_on, updated_on)"
					+ "VALUES(?, ? ,1 ,getdate() ,getdate())";
			int batch_size = 3;
			for (int i = 0; i < role_permission.size(); i += batch_size) {

				final List<String> batchList = role_permission.subList(i,
						i + batch_size > role_permission.size() ? role_permission.size() : i + batch_size);

				template.batchUpdate(sql, new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement pStmt, int rowCount) throws SQLException {
						pStmt.setString(1, batchList.get(rowCount));
						pStmt.setString(2, role_code);
					}

					@Override
					public int getBatchSize() {
						return batchList.size();
					}

				});
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CustomMethods.logError(ex);
			return ex.getMessage();
		}
		
		return "****";
	}

	@Override
	public String accountValidate(String username) {
		try
		{
			String sql = "SELECT count(*) from admin_user where username = '" + username + "'";
			int usernameExists = template.queryForObject(sql, Integer.class);
			if(usernameExists > 0)
				return "Username already exists";
			return "****";	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
	}

	@Override
	public String updateProfile(UserDetails user_details) {
		try
		{
			String sql = "UPDATE admin_user SET full_name = ?, username = ?, user_email = ?,"
					+ " password = ?, user_image = ?, updatedDate = getDate(), updatedBy = ? "
					+ "where user_id = ?";
			template.update(sql, user_details.getFull_name(), user_details.getUsername(), 
					user_details.getUser_email(), user_details.getPassword(),user_details.getUserimage(), 
					user_details.getUsername(), user_details.getUserID());				
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
	public void activity_log(String moduleName, String moduleCode, String moduleAction, String username) {
		try 
		{
		  	String sql="INSERT INTO activity_log(moduleName, moduleCode, moduleAction, user_name, logDate) "
		  			+ "values(?, ?, ?, ?, getdate())";
	          this.template.update(sql, moduleName, moduleCode, moduleAction, username);	
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			CustomMethods.logError(e);
		}
		
	}

	@Override
	public String getUserRole(String roleCode) {
        String sql="select role from user_role where role_code = ?";
	    return this.template.query(sql, new Object[] {roleCode}, 
	    		(ResultSet rs, int rowNum) -> {return rs.getString("role");}).get(0);
	
	}
	
	public List<UserDetails> getActiveUserList(){
		String query="SELECT * FROM admin_user WHERE status = 1";
		return this.template.query(query, (ResultSet rs, int rowNum ) -> {
			UserDetails user=new UserDetails();
			user.setUserID(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			return user;
		});
			
	}

	@Override
	public List<UserRole> getAssigneeRole() {
		String sql = "select * from user_role where role_code NOT IN ('SUPADM') and status = 1";
		return template.query(sql, new RowMapper<UserRole>(){			
			public UserRole mapRow(ResultSet rs, int row) throws SQLException
			{
				UserRole role = new UserRole();
				role.setRoleId(rs.getInt(1));
				role.setRole(rs.getString(2));
				role.setRole_code(rs.getString(3));
				role.setStatus(rs.getInt(4));
				return role;
			}
		});
	}

	@Override
	public List<UserDetails> getUserRoleList(String zone) {
			try
			{
				String sql = "select * from admin_user where role_name='REGMAN' and city IN(select city from location_lists where zone =?)";			
				List<UserDetails> user_list = this.template.query(sql,new Object[] {zone}, 
						(ResultSet rs, int count) -> {
							UserDetails user = new UserDetails();
							user.setFull_name(rs.getString("full_name"));
							user.setUser_email(rs.getString("user_email"));
							return user;
						});
				return user_list;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				CustomMethods.logError(e);
				return null;
			}		
		}
}
