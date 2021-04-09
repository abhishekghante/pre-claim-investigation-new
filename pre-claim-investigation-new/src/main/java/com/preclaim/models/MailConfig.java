package com.preclaim.models;

public class MailConfig {

	private String host;
	private int port;
	private String username;
	private String password;
	private String encryptionType;
	private String receipent;
	private String subject;
	private String messageBody;
	private String AppointmentPath;
	private String AuthorizationPath;
	
	public MailConfig()
	{
		host = "";
		port = 0;
		username = "";
		password = "";
		encryptionType = "";
		receipent = "";
		subject = "";
		messageBody = "";
		AppointmentPath="";
		AuthorizationPath="";
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEncryptionType() {
		return encryptionType;
	}
	public void setEncryptionType(String encryptionType) {
		this.encryptionType = encryptionType;
	}
	public String getReceipent() {
		return receipent;
	}
	public void setReceipent(String receipent) {
		this.receipent = receipent;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	
	public String getAppointmentPath() {
		return AppointmentPath;
	}
	public void setAppointmentPath(String appointmentPath) {
		AppointmentPath = appointmentPath;
	}
	public String getAuthorizationPath() {
		return AuthorizationPath;
	}
	public void setAuthorizationPath(String authorizationPath) {
		AuthorizationPath = authorizationPath;
	}
	@Override
	public String toString() {
		return "MailConfig [host=" + host + ", port=" + port + ", username=" + username + ", password=" + password
				+ ", encryptionType=" + encryptionType + ", receipent=" + receipent + ", subject=" + subject
				+ ", messageBody=" + messageBody + ", AppointmentPath=" + AppointmentPath + ", AuthorizationPath="
				+ AuthorizationPath + "]";
	}
	
}
