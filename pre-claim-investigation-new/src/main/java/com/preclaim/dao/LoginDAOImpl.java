package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.Config;
import com.preclaim.config.CustomMethods;
import com.preclaim.models.Login;
import com.preclaim.models.UserDetails;

public class LoginDAOImpl implements LoginDAO {
	
	@Autowired  
	DataSource datasource;
	  
	private JdbcTemplate template;
	  
	public void setTemplate(JdbcTemplate template) 
	{  
      this.template = template;    
	}
	
	public UserDetails validateUser(Login login)	  
	{	
		String sql = "SELECT * FROM admin_user WHERE username = ? and password = ?";
		List<UserDetails> user_list = template.query(sql, 
				new Object[] {login.getUsername(),login.getPassword()},
				(ResultSet rs, int arg1) ->{
					UserDetails login_user = new UserDetails();
					login_user.setUserID(rs.getInt("user_id"));
					login_user.setUsername(rs.getString("username"));
					login_user.decodePassword(rs.getString("password"));
					login_user.setFull_name(rs.getString("full_name"));
					login_user.setStatus(rs.getInt("status"));
					login_user.setUser_email(rs.getString("user_email"));
					login_user.setAccount_type(rs.getString("role_name"));
					login_user.setUserimage(rs.getString("user_image"));
					login_user.setState(rs.getString("state"));
					login_user.setCity(rs.getString("city"));
					login_user.setUserImageb64(Config.upload_directory + rs.getString("user_image"));
					return login_user;
				});
		return user_list.size() > 0 ? user_list.get(0) : null ;
	  }

	@Override
	public UserDetails checkUser(String username) {
		try
		{
			String sql = "SELECT * FROM admin_user WHERE username = ?";
			List<UserDetails> user_list = template.query(sql, 
					new Object[] {username},
					(ResultSet rs, int arg1) ->{
						UserDetails login_user = new UserDetails();
						login_user.setUserID(rs.getInt("user_id"));
						login_user.setUsername(rs.getString("username"));
						login_user.decodePassword(rs.getString("password"));
						login_user.setFull_name(rs.getString("full_name"));
						login_user.setStatus(rs.getInt("status"));
						login_user.setUser_email(rs.getString("user_email"));
						login_user.setUserimage(rs.getString("user_image"));
						login_user.setUserImageb64(Config.upload_directory + rs.getString("user_image"));
						return login_user;
					});
			return user_list.size() > 0 ? user_list.get(0) : null ;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CustomMethods.logError(ex);
			return null;
		}
	}

	@Override
	public String updatePassword(String username, String password) {
		try
		{
			String sql = "UPDATE admin_user SET password = ?, updatedBy = ?, updatedDate = getdate() "
					+ "WHERE username = ?";
			template.update(sql, password, username, username);
		}
		catch(Exception ex)
		{
			CustomMethods.logError(ex);
			return ex.getMessage();
		}
		return "****";
	}
	
}

