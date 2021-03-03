package com.preclaim.dao;

import java.util.List;

import com.preclaim.models.UserDetails;
import com.preclaim.models.UserList;
import com.preclaim.models.UserRole;

public interface UserDAO {
	
	String create_user(UserDetails user);
	List<UserList> user_list();
	String deleteAdminUser(int user_id);
	String create_role(UserRole role);
	List<UserRole> role_lists();
	List<UserRole> getUserRole_lists(String role_code, String status);
	String updateUserRole(UserRole role);	
	String delete_role(int roleId);
	String updateUserStatus(int user_id, int user_status, String username);
	UserDetails getUserDetails(int user_id);
	String updateUserDetails(UserDetails user_details);
	List<String> retrievePermission(String role_code);
	String addPermission(List<String> role_permission, String role_code);
	String accountValidate(String username);
	String updateProfile(UserDetails user_details);
	void activity_log(String moduleName,String moduleCode,String moduleAction,String username);
	String getUserRole(String roleCode);
	UserDetails getUserDetails(String username);
	List<UserDetails> getActiveUserList();
	List<UserRole> getAssigneeRole();
	List<UserDetails> getUserRoleList(String zone);
}
