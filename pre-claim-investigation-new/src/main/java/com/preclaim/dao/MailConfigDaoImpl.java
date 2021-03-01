package com.preclaim.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.preclaim.config.CustomMethods;
import com.preclaim.models.MailConfig;
import com.preclaim.models.MailConfigList;

public class MailConfigDaoImpl implements MailConfigDao {

	@Autowired
	DataSource datasource;
	
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String add(MailConfigList mailConfig) {
		try
		{
			String sql = "INSERT INTO mail_config(username, password, outgoingServer, outgoingPort, "
					+ "encryptionType, status, createdBy, created_on, updatedBy, updated_on)"
					+ "VALUES(?, ?, ?, ? ,? , 0, ?, getDate(), '', getDate())";
			template.update(sql, mailConfig.getUsername(), mailConfig.getPassword(), 
					mailConfig.getOutgoingServer(), mailConfig.getOutgoingPort(), 
					mailConfig.getEncryptionType(), mailConfig.getCreatedBy());
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
	public List<MailConfigList> getMailConfigList(int status) {
		try
		{
			String sql = "";
			if(status == 0)
				sql = "SELECT * FROM mail_config where status = 0";	
			else
				sql = "SELECT * FROM mail_config where status = 1 or status = 2";
			return template.query(sql, (ResultSet rs, int rowNum) -> {
				
				MailConfigList mailConfig = new MailConfigList();
				mailConfig.setMailConfigId(rs.getInt("mailConfigId"));
				mailConfig.setUsername(rs.getString("username"));
				mailConfig.setPassword(rs.getString("password"));
				mailConfig.setOutgoingServer(rs.getString("outgoingServer"));
				mailConfig.setOutgoingPort(rs.getInt("outgoingPort"));
				mailConfig.setEncryptionType(rs.getString("encryptionType"));
				mailConfig.setStatus(rs.getInt("status"));
				return mailConfig;
			} );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public String delete(int mailConfigId) {
		try
		{
			String sql = "DELETE FROM mail_config where mailConfigId = ?";
			template.update(sql,mailConfigId);
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
	public String update(MailConfigList mailConfig) {
		try
		{
			String sql = "UPDATE mail_config SET username = ?, password = ?, outgoingServer = ?, "
					+ "outgoingPort = ?, encryptionType = ?, updatedBy = ?, updated_on = getDate() where "
					+ "mailConfigId = ?";
			template.update(sql, mailConfig.getUsername(), mailConfig.getPassword(), 
					mailConfig.getOutgoingServer(), mailConfig.getOutgoingPort(), 
					mailConfig.getEncryptionType(), mailConfig.getUpdatedBy(), 
					mailConfig.getMailConfigId());
			return "****";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return "Error updating configuration. Kindly contact system administrator";
		}
	}

	@Override
	public String updateStatus(int mailConfigId, int status, String username) {
		try
		{
			if(status == 1)
			{
				String sql = "SELECT count(*) FROM mail_config where status = 1";
				int activeCount = template.queryForObject(sql, Integer.class);
				if(activeCount > 0)
					return "Only one mail configuration can be active at a time";
			}
			String sql = "UPDATE mail_config SET status = ?, updatedBy = ?, updated_on = getDate() where"
					+ " mailConfigId = ?";
			template.update(sql, status, username, mailConfigId);
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
	public MailConfigList getActiveConfigList() {
		try
		{
			String sql = "SELECT * FROM mail_config where status = 1";
			return template.query(sql, (ResultSet rs, int rowNum) -> {
				
				MailConfigList mailConfig = new MailConfigList();
				mailConfig.setMailConfigId(rs.getInt("mailConfigId"));
				mailConfig.setUsername(rs.getString("username"));
				mailConfig.setPassword(rs.getString("password"));
				mailConfig.setOutgoingServer(rs.getString("outgoingServer"));
				mailConfig.setOutgoingPort(rs.getInt("outgoingPort"));
				mailConfig.setEncryptionType(rs.getString("encryptionType"));
				mailConfig.setStatus(rs.getInt("status"));
				return mailConfig;
			} ).get(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}
	
	@Override
	public MailConfig getActiveConfig() {
		try
		{
			String sql = "SELECT * FROM mail_config where status = 1";
			return template.query(sql, (ResultSet rs, int rowNum) -> {
				
				MailConfig mailConfig = new MailConfig();
				mailConfig.setUsername(rs.getString("username"));
				mailConfig.setPassword(rs.getString("password"));
				mailConfig.setHost(rs.getString("outgoingServer"));
				mailConfig.setPort(rs.getInt("outgoingPort"));
				mailConfig.setEncryptionType(rs.getString("encryptionType"));
				return mailConfig;
			} ).get(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}
	
	@Override
	public MailConfigList getMailConfigListById(int mailConfigId) {
		try
		{
			String sql = "SELECT * FROM mail_config where mailConfigId = " + mailConfigId;
			return template.query(sql, (ResultSet rs, int rowNum) -> {
				
				MailConfigList mailConfig = new MailConfigList();
				mailConfig.setMailConfigId(rs.getInt("mailConfigId"));
				mailConfig.setUsername(rs.getString("username"));
				mailConfig.setPassword(rs.getString("password"));
				mailConfig.setOutgoingServer(rs.getString("outgoingServer"));
				mailConfig.setOutgoingPort(rs.getInt("outgoingPort"));
				mailConfig.setEncryptionType(rs.getString("encryptionType"));
				mailConfig.setStatus(rs.getInt("status"));
				return mailConfig;
			} ).get(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}
	
	@Override
	public String sendMail(MailConfig mail)
	{
		Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", mail.getHost());
        properties.put("mail.smtp.port", mail.getPort());
        properties.put("mail.smtp.starttls.enable", "false");  
        /*if(mail.getEncryptionType().equals("SSL"))
        {
        	properties.put("mail.smtp.socketFactory.port", mail.getPort());
        	properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        }
        else if(mail.getEncryptionType().equals("TLS"))
        	properties.put("mail.smtp.starttls.enable", "true");
        */
     // Get the default Session object.
        Session session = Session.getInstance(properties, new Authenticator() {
        	@Override
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(mail.getUsername(), mail.getPassword());
        	}
        });

        try {
           MimeMessage message = new MimeMessage(session);
           message.setFrom(new InternetAddress(mail.getUsername()));
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(mail.getReceipent()));
           message.setSubject(mail.getSubject());

           BodyPart messageBodyPart = new MimeBodyPart();
           messageBodyPart.setText(mail.getMessageBody());
           
           Multipart multipart = new MimeMultipart();
           multipart.addBodyPart(messageBodyPart);
           // Send the complete message parts
           message.setContent(multipart);

           // Send message
           Transport.send(message);
           return "****";
        } 
        catch (MessagingException mex) {
        	mex.printStackTrace();
        	CustomMethods.logError(mex);
			return mex.getMessage();
        }       
	}
}
