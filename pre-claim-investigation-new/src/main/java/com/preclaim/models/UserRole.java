package com.preclaim.models;

public class UserRole {

	private long roleId;
	private String role;
	private String role_code;
	private int status;
	public UserRole()
	{
		this.roleId = 0;
		this.role = "";
		this.role_code = "";
		this.status = 0;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "UserRole [roleId=" + roleId + ", role=" + role + ", role_code=" + role_code + ", status=" + status
				+ "]";
	}
	

	
}
