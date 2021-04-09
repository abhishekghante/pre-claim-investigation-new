package com.preclaim.dao;

import java.io.FileOutputStream;
import java.io.IOException;
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
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.preclaim.config.Config;
import com.preclaim.config.CustomMethods;
import com.preclaim.models.CaseDetails;
import com.preclaim.models.MailConfig;
import com.preclaim.models.MailConfigList;
import com.preclaim.models.UserDetails;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailConfigDaoImpl implements MailConfigDao {

	@Autowired
	DataSource datasource;

	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String add(MailConfigList mailConfig) {
		try {
			String sql = "INSERT INTO mail_config(username, password, outgoingServer, outgoingPort, "
					+ "encryptionType, status, createdBy, created_on, updatedBy, updated_on)"
					+ "VALUES(?, ?, ?, ? ,? , 0, ?, getDate(), '', getDate())";
			template.update(sql, mailConfig.getUsername(), mailConfig.getPassword(), mailConfig.getOutgoingServer(),
					mailConfig.getOutgoingPort(), mailConfig.getEncryptionType(), mailConfig.getCreatedBy());
			return "****";
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
	}

	@Override
	public List<MailConfigList> getMailConfigList(int status) {
		try {
			String sql = "";
			if (status == 0)
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
			});
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public String delete(int mailConfigId) {
		try {
			String sql = "DELETE FROM mail_config where mailConfigId = ?";
			template.update(sql, mailConfigId);
			return "****";
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
	}

	@Override
	public String update(MailConfigList mailConfig) {
		try {
			String sql = "UPDATE mail_config SET username = ?, password = ?, outgoingServer = ?, "
					+ "outgoingPort = ?, encryptionType = ?, updatedBy = ?, updated_on = getDate() where "
					+ "mailConfigId = ?";
			template.update(sql, mailConfig.getUsername(), mailConfig.getPassword(), mailConfig.getOutgoingServer(),
					mailConfig.getOutgoingPort(), mailConfig.getEncryptionType(), mailConfig.getUpdatedBy(),
					mailConfig.getMailConfigId());
			return "****";
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return "Error updating configuration. Kindly contact system administrator";
		}
	}

	@Override
	public String updateStatus(int mailConfigId, int status, String username) {
		try {
			if (status == 1) {
				String sql = "SELECT count(*) FROM mail_config where status = 1";
				int activeCount = template.queryForObject(sql, Integer.class);
				if (activeCount > 0)
					return "Only one mail configuration can be active at a time";
			}
			String sql = "UPDATE mail_config SET status = ?, updatedBy = ?, updated_on = getDate() where"
					+ " mailConfigId = ?";
			template.update(sql, status, username, mailConfigId);
			return "****";
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
	}

	@Override
	public MailConfigList getActiveConfigList() {
		try {
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
			}).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public MailConfig getActiveConfig() {
		try {
			String sql = "SELECT * FROM mail_config where status = 1";
			return template.query(sql, (ResultSet rs, int rowNum) -> {

				MailConfig mailConfig = new MailConfig();
				mailConfig.setUsername(rs.getString("username"));
				mailConfig.setPassword(rs.getString("password"));
				mailConfig.setHost(rs.getString("outgoingServer"));
				mailConfig.setPort(rs.getInt("outgoingPort"));
				mailConfig.setEncryptionType(rs.getString("encryptionType"));
				return mailConfig;
			}).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public MailConfigList getMailConfigListById(int mailConfigId) {
		try {
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
			}).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public String sendMail(MailConfig mail) {
		Properties properties = System.getProperties();
		/*
		 * properties.put("mail.smtp.auth", "true"); properties.put("mail.smtp.host",
		 * mail.getHost()); properties.put("mail.smtp.port", mail.getPort());
		 * properties.put("mail.smtp.starttls.enable", "true");
		 * properties.put("mail.smtp.ssl.enable", "true");
		 */
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.host", mail.getHost());
			properties.put("mail.smtp.port", mail.getPort());
			properties.put("mail.smtp.starttls.enable", "false");
		
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
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getReceipent()));
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
		} catch (MessagingException mex) {
			mex.printStackTrace();
			CustomMethods.logError(mex);
			return mex.getMessage();
		}
	}

	@Override
	public String sendMailWithAttachment(MailConfig mail) {

		/*
		 * Properties props = new Properties(); props.put("mail.smtp.auth", "true");
		 * props.put("mail.smtp.ssl.enable","true"); props.put("mail.smtp.host",
		 * mail.getHost()); props.put("mail.smtp.port", mail.getPort());
		 */ Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", mail.getHost());
		properties.put("mail.smtp.port", mail.getPort());
		properties.put("mail.smtp.starttls.enable", "false");
		// Get the Session object.
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mail.getUsername(), mail.getPassword());
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(mail.getUsername()));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceipent()));

			// Set Subject: header field
			message.setSubject(mail.getSubject());

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText(mail.getMessageBody());

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			System.out.println("mail.getAppointmentPath()" + mail.getAppointmentPath());
			javax.activation.DataSource source = new FileDataSource(mail.getAppointmentPath());
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("Appointment Letter.docx");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			javax.activation.DataSource source1 = new FileDataSource(mail.getAuthorizationPath());
			messageBodyPart.setDataHandler(new DataHandler(source1));
			messageBodyPart.setFileName("Authorization Letter.docx");
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return "****";
	}

	public String textReplaceForAppointment(String path, CaseDetails caselist, UserDetails toUser)
			throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {

		try {

			XWPFDocument doc = new XWPFDocument(OPCPackage.open(path));

			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();

				if (runs != null) {

					for (XWPFRun r : runs) {

						String text = r.getText(0);

						if (text != null && text.contains("POLY000")) {
							text = text.replace("POLY000", caselist.getPolicyNumber());// your content
							r.setText(text, 0);
						}

						if (text != null && text.contains("DATEEE")) {
							text = text.replace("DATEEE", caselist.getCreatedDate());// your content
							System.out.println(caselist.getCreatedDate());
							r.setText(text, 0);
						}

						if (text != null && text.contains("USER000")) {
							text = text.replace("USER000", toUser.getFull_name());// your content
							r.setText(text, 0);
						}

						if (text != null && text.contains("ADD000")) {
							text = text.replace("ADD000", toUser.getAddress1());// your content
							r.setText(text, 0);
						}

						if (text != null && text.contains("INS000")) {
							text = text.replace("INS000", caselist.getInsuredName());// your content
							r.setText(text, 0);
						}

						if (text != null && text.contains("NOM000")) {
							text = text.replace("NOM000", caselist.getNominee_name());// your content
							r.setText(text, 0);
						}
					}
				}
			}
			doc.write(new FileOutputStream(Config.Path + caselist.getCaseId() + "_Appointment Letter.docx"));
		} catch (Exception e) {

			e.printStackTrace();
			e.getMessage();
		}
		return "****";

	}

	public String textReplaceForAuthorization(String path, CaseDetails caselist, UserDetails toUser)
			throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {

		try {
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(path));

			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();

				if (runs != null) {

					for (XWPFRun r : runs) {

						String text = r.getText(0);

						if (text != null && text.contains("DATE000")) {
							text = text.replace("DATE000", caselist.getCreatedDate());// your content
							r.setText(text, 0);
						}

						if (text != null && text.contains("VDATE000")) {
							text = text.replace("DATE000", caselist.getCreatedDate());// your content
							r.setText(text, 0);
						}

						if (text != null && text.contains("USER000")) {
							text = text.replace("USER000", toUser.getFull_name());// your content
							r.setText(text, 0);
						}

						if (text != null && text.contains("ADD000")) {
							text = text.replace("ADD000", toUser.getAddress1());// your content
							r.setText(text, 0);
						}

					}
				}
			}
			doc.write(new FileOutputStream(Config.Path + caselist.getCaseId() + "_Authorization Letter.docx"));
		} finally {

		}
		return "****";

	}

}
