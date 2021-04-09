package com.preclaim.dao;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.preclaim.models.CaseDetails;
import com.preclaim.models.MailConfig;
import com.preclaim.models.MailConfigList;
import com.preclaim.models.UserDetails;

public interface MailConfigDao {

	String add(MailConfigList mailConfig);
	String delete(int mailConfigId);
	String update(MailConfigList mailConfig);
    String updateStatus(int mailConfigId,int status, String username);
    List<MailConfigList> getMailConfigList(int status);
	MailConfigList getActiveConfigList();
	MailConfigList getMailConfigListById(int mailConfigId);
	MailConfig getActiveConfig();
	String sendMail(MailConfig mail);
    String sendMailWithAttachment(MailConfig mail);
    String textReplaceForAppointment(String path ,CaseDetails caselist,UserDetails toUser) throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException;
    String textReplaceForAuthorization(String path ,CaseDetails caselist,UserDetails toUser) throws IOException,
    InvalidFormatException,org.apache.poi.openxml4j.exceptions.InvalidFormatException;
}
