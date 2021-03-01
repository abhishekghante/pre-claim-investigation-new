package com.preclaim.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.preclaim.config.Config;
import com.preclaim.config.CustomMethods;
import com.preclaim.dao.CaseDao;
import com.preclaim.dao.Case_movementDao;
import com.preclaim.dao.IntimationTypeDao;
import com.preclaim.dao.InvestigationTypeDao;
import com.preclaim.dao.LocationDao;
import com.preclaim.dao.MailConfigDao;
import com.preclaim.dao.UserDAO;
import com.preclaim.models.CaseDetails;
import com.preclaim.models.CaseMovement;
import com.preclaim.models.MailConfig;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;
import com.preclaim.models.UserRole;

@Controller
@RequestMapping(value = "/message")
public class CaseController {

	@Autowired
	CaseDao caseDao;
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	InvestigationTypeDao investigationDao;
	
	@Autowired
	IntimationTypeDao intimationTypeDao;
	
	@Autowired
	LocationDao locationDao;
	
	@Autowired
	Case_movementDao caseMovementDao;
	
	@Autowired
	MailConfigDao mailConfigDao;
	
    @RequestMapping(value = "/import_case", method = RequestMethod.GET)
    public String import_case(HttpSession session) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		session.removeAttribute("ScreenDetails");
    	ScreenDetails details=new ScreenDetails();
    	details.setScreen_name("../message/import_case.jsp");
    	details.setScreen_title("Import Case");
    	details.setMain_menu("Case Management");
    	details.setSub_menu1("Bulk case uploads");
    	details.setSub_menu2("App Users");
    	details.setSub_menu2_path("/app_user/app_user");
    	if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
    	session.setAttribute("ScreenDetails", details);
    	session.setAttribute("userRole", userDao.getAssigneeRole());
    	
    	return "common/templatecontent";
    }
    
    @RequestMapping(value = "/add_message", method = RequestMethod.GET)
    public String add_message(HttpSession session, HttpServletRequest request) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
    	session.removeAttribute("ScreenDetails");
    	
    	ScreenDetails details=new ScreenDetails();
    	details.setScreen_name("../message/add_message.jsp");
    	details.setScreen_title("Add Cases");
    	details.setMain_menu("Case Management");
    	details.setSub_menu1("Create Case");
    	details.setSub_menu2("Manage Cases");
    	details.setSub_menu2_path("../message/pending_message.jsp");
    	session.setAttribute("ScreenDetails", details);    	
    
    	session.setAttribute("userRole", userDao.getAssigneeRole());
    	session.setAttribute("investigation_list", investigationDao.getActiveInvestigationList());
    	session.setAttribute("intimation_list", intimationTypeDao.getActiveIntimationType());
    	session.setAttribute("location_list", locationDao.getActiveLocationList());
    	
    	return "common/templatecontent";
    }
    
    @RequestMapping(value = "/pending_message", method = RequestMethod.GET)
    public String pending_message(HttpSession session) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		session.removeAttribute("ScreenDetails");
    	ScreenDetails details=new ScreenDetails();
    	details.setScreen_name("../message/pending_message.jsp");
    	details.setScreen_title("Pending Cases Lists");
    	details.setMain_menu("Case Management");
    	details.setSub_menu1("Pending Cases");
    	if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
    	session.setAttribute("ScreenDetails", details);
    	session.setAttribute("pendingCaseList", caseDao.getPendingCaseList(user.getUsername()));
    	session.setAttribute("investigation_list", investigationDao.getActiveInvestigationList());
    	session.setAttribute("intimation_list", intimationTypeDao.getActiveIntimationType());
    	return "common/templatecontent"; 
    }
  
    @RequestMapping(value = "/active_message", method = RequestMethod.GET)
    public String active_message(HttpSession session) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		session.removeAttribute("ScreenDetails");
    	
		ScreenDetails details=new ScreenDetails();
    	details.setScreen_name("../message/active_message.jsp");
    	details.setScreen_title("Active Cases Lists");
    	details.setMain_menu("Case Management");
    	details.setSub_menu1("Case Lists");
    	session.setAttribute("ScreenDetails", details);
    	session.setAttribute("assignCaseList", caseDao.getAssignedCaseList(user.getUsername()));
    	session.setAttribute("investigation_list", investigationDao.getActiveInvestigationList());
    	session.setAttribute("intimation_list", intimationTypeDao.getActiveIntimationType());
    	
    	return "common/templatecontent";
    }
    
    @RequestMapping(value = "/importData", method = RequestMethod.POST)
	public @ResponseBody String importData(@RequestParam("userfile") ArrayList<MultipartFile> userfile, HttpSession session, HttpServletRequest request)
	{
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
    	String message = "";
		//File Uploading Routine
		if(userfile != null)
		{
			try 
			{
				byte[] temp = userfile.get(0).getBytes();
	    		String filename = userfile.get(0).getOriginalFilename();
				String toId = request.getParameter("userId");
	    		filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-SS")) + "_" 
	    				+ filename;
	    		Path path = Paths.get(Config.upload_directory + filename);
	    		Files.write(path, temp);
	    		
	    		
				message = caseDao.addBulkUpload(filename, user.getUsername(), toId);
				if(message.equals("****"))
				{
					userDao.activity_log("RCUTEAM", "Excel", "BULKUPLOAD", user.getUsername());
					session.setAttribute("success_message", "File Uploaded successfully");
					try
			    	{
				    	MailConfig mail = mailConfigDao.getActiveConfig();
				    	if(mail != null)
				    	{
				    		//From ID
					    	mail.setSubject("Case Assigned - Claims");
					    	String message_body = "Dear <User>, \n Case has been assigned successfully\n\n";
					    	message_body = message_body.replaceAll("<User>", user.getFull_name());
					    	message_body+= "Thanks & Regards,\n Claims";
					        mail.setMessageBody(message_body);
					        mail.setReceipent(user.getUser_email());
					    	mailConfigDao.sendMail(mail);
					    	
					    	//To ID
					    	UserDetails toUser = userDao.getUserDetails(toId);
					    	mail.setSubject("New Case Assigned - Claims");
					    	message_body = "Dear <User>, \n Your are required to take action on new cases\n\n";
					    	message_body = message_body.replace("<User>", toUser.getFull_name());
					    	message_body+= "Thanks & Regards,\n Claims";
					        mail.setMessageBody(message_body);
					        mail.setReceipent(toUser.getUser_email());
					    	mailConfigDao.sendMail(mail);
				    	}
			    	}
			    	catch(Exception e)
			    	{
			    		CustomMethods.logError(e);
			    	}
				}
				else
					return message;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return e.getMessage();
			}    	
		}
	  
		return message;
	}
    
    @RequestMapping(value = "/addMessage",method = RequestMethod.POST)
   	public @ResponseBody String addMessage(HttpSession session, HttpServletRequest request) 
   	{			
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
       	
		CaseDetails caseDetail = new CaseDetails();
       	caseDetail.setPolicyNumber(request.getParameter("policyNumber"));
       	caseDetail.setInvestigationId(Integer.parseInt(request.getParameter("msgCategory")));
       	caseDetail.setInsuredName( request.getParameter("insuredName"));
       	caseDetail.setInsuredDOD(request.getParameter("insuredDOD"));
       	caseDetail.setInsuredDOB(request.getParameter("insuredDOB"));
       	caseDetail.setSumAssured(Integer.parseInt(request.getParameter("sumAssured")));
       	caseDetail.setIntimationType( request.getParameter("msgIntimationType"));
       	caseDetail.setLocationId(Integer.parseInt(request.getParameter("claimantCity")));
       	caseDetail.setNominee_name(request.getParameter("nomineeName"));
       	caseDetail.setNomineeContactNumber(request.getParameter("nomineeMob"));
       	caseDetail.setNominee_address(request.getParameter("nomineeAdd"));
       	caseDetail.setInsured_address(request.getParameter("insuredAdd"));
		caseDetail.setCreatedBy(user.getUsername()); 
       	long caseId = caseDao.addcase(caseDetail);
       	
       	if(caseId == 0)
       		return "Error adding case";
       	
       	System.out.println("caseDetail============="+caseDetail);
       	CaseMovement caseMovement = new CaseMovement();
       	caseMovement.setCaseId(caseId);
       	caseMovement.setFromId(caseDetail.getCreatedBy());
       	caseMovement.setToId(request.getParameter("assigneeId"));
       	String message = caseMovementDao.CreatecaseMovement(caseMovement);
       	if(message.equals("****"))
       	{
       		userDao.activity_log("CASE HISTORY", caseDetail.getPolicyNumber(), "ADD CASE", 
       				user.getUsername());
       		try
	    	{
		    	MailConfig mail = mailConfigDao.getActiveConfig();
		    	if(mail != null)
		    	{
		    		//From ID
			    	mail.setSubject("Case Assigned - Claims");
			    	String message_body = "Dear <User>, \n Case has been assigned successfully\n\n";
			    	message_body = message_body.replaceAll("<User>", user.getFull_name());
			    	message_body+= "Thanks & Regards,\n Claims";
			        mail.setMessageBody(message_body);
			        mail.setReceipent(user.getUser_email());
			    	mailConfigDao.sendMail(mail);
			    	
			    	//To ID
			    	UserDetails toUser = userDao.getUserDetails(caseMovement.getToId());
			    	mail.setSubject("New Case Assigned - Claims");
			    	message_body = "Dear <User>, \n Your are required to take action on new cases\n\n";
			    	message_body = message_body.replace("<User>", toUser.getFull_name());
			    	message_body+= "Thanks & Regards,\n Claims";
			        mail.setMessageBody(message_body);
			        mail.setReceipent(toUser.getUser_email());
			    	mailConfigDao.sendMail(mail);
		    	}
	    	}
	    	catch(Exception e)
	    	{
	    		CustomMethods.logError(e);
	    	}
       	}
       	return message;
   	}
    
    @RequestMapping(value = "/deleteMessage",method = RequestMethod.POST)
    public @ResponseBody String deleteMessage(HttpServletRequest request,HttpSession session) 
    {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		int caseId = Integer.parseInt(request.getParameter("msgId"));
	    String message = caseDao.deleteCase(caseId);
	    if(message.equals("****"))
	    {
	    	session.setAttribute("success_message", "Case deleted successfully");
	    	userDao.activity_log("CASE HISTORY", String.valueOf(caseId), "DELETE CASE", 
       				user.getUsername());
	    }
	    return message;
    }
    
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
   	public String edit(HttpSession session, HttpServletRequest request) 
   	{			
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		session.removeAttribute("ScreenDetails"); 	
		ScreenDetails details=new ScreenDetails();
    	details.setScreen_name("../message/edit_message.jsp");
    	details.setScreen_title("Edit Case");
    	details.setMain_menu("Case Management");
    	details.setSub_menu1("Create Case");
    	details.setSub_menu2("Manage Cases");
    	details.setSub_menu2_path("../message/pending_message.jsp");
    	session.setAttribute("ScreenDetails", details);    	
    	session.setAttribute("userRole", userDao.getUserRole_lists(user.getAccount_type(), "Approved"));
    	session.setAttribute("location_list", locationDao.getActiveLocationList());
    	session.setAttribute("investigation_list", investigationDao.getActiveInvestigationList());
    	session.setAttribute("intimation_list", intimationTypeDao.getActiveIntimationType());
    	session.setAttribute("case_detail",caseDao.getCaseDetail(Integer.parseInt(request.getParameter("caseId"))));
		return "common/templatecontent";
   	}
    
    @RequestMapping(value = "/updateMessageDetails",method = RequestMethod.POST)
    public @ResponseBody String updateMessageDetails(HttpSession session, HttpServletRequest request) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
       	
		CaseDetails caseDetail = new CaseDetails();
       	caseDetail.setPolicyNumber(request.getParameter("policyNumber"));
       	caseDetail.setInvestigationId(Integer.parseInt(request.getParameter("msgCategory")));
       	caseDetail.setInsuredName(request.getParameter("insuredName"));
       	caseDetail.setInsuredDOD(request.getParameter("insuredDOD"));
       	caseDetail.setInsuredDOB(request.getParameter("insuredDOB"));
       	caseDetail.setSumAssured(Double.parseDouble(request.getParameter("sumAssured")));
       	caseDetail.setIntimationType( request.getParameter("msgIntimationType"));
    	caseDetail.setLocationId(Integer.parseInt(request.getParameter("locationId")));
       	caseDetail.setNominee_name(request.getParameter("nomineeName"));
       	caseDetail.setNomineeContactNumber(request.getParameter("nomineeMob"));
       	caseDetail.setNominee_address(request.getParameter("nomineeAdd"));
       	caseDetail.setInsured_address(request.getParameter("insuredAdd"));
		caseDetail.setUpdatedBy(user.getUsername());
		caseDetail.setCaseId(Long.parseLong(request.getParameter("caseId")));
       	System.out.println(caseDetail.toString());
		String update = caseDao.updateCaseDetails(caseDetail);
       	if(!update.equals("****"))
       		return update;
       	long caseId = caseDetail.getCaseId();
		String toId = request.getParameter("toId");
		String fromId = user.getUsername();
		String toStatus = request.getParameter("toStatus");
		String toRemarks = request.getParameter("toRemarks");
    	CaseMovement case_movement = new CaseMovement(caseId, fromId, toId, toStatus, toRemarks);
    	String message = caseMovementDao.updateCaseMovement(case_movement);
    	if(message.equals("****"))
    	{
    		session.setAttribute("success_message", "Case Details updated successfully");
	    	userDao.activity_log("CASE HISTORY", caseDetail.getPolicyNumber(), "EDIT CASE", user.getUsername());
    	}
    	return message;
    }
    
    @RequestMapping(value = "/assignCase",method = RequestMethod.POST)
    public @ResponseBody String assignToRM(HttpServletRequest request,HttpSession session) 
    {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
    	long caseId = Integer.parseInt(request.getParameter("caseId"));
		String toId = request.getParameter("toId");
		String fromId = user.getUsername();
		String toStatus = request.getParameter("toStatus");
		String toRemarks = request.getParameter("toRemarks");
    	CaseMovement case_movement = new CaseMovement(caseId, fromId, toId, toStatus, toRemarks);
    	String message = caseMovementDao.updateCaseMovement(case_movement);
    	if(message.equals("****"))
    	{
    		session.setAttribute("success_message", "Case assigned successfully");
	    	userDao.activity_log("CASE HISTORY","", "ASSIGN CASE", user.getUsername());
	    	try
	    	{
		    	MailConfig mail = mailConfigDao.getActiveConfig();
		    	if(mail != null)
		    	{
		    		//From ID
			    	mail.setSubject("Case Assigned - Claims");
			    	String message_body = "Dear <User>, \n Case has been assigned successfully\n\n";
			    	message_body = message_body.replaceAll("<User>", user.getFull_name());
			    	message_body+= "Thanks & Regards,\n Claims";
			        mail.setMessageBody(message_body);
			        mail.setReceipent(user.getUser_email());
			    	mailConfigDao.sendMail(mail);
			    	
			    	//To ID
			    	UserDetails toUser = userDao.getUserDetails(toId);
			    	mail.setSubject("New Case Assigned - Claims");
			    	message_body = "Dear <User>, \n Your are required to take action on new cases\n\n";
			    	message_body = message_body.replace("<User>", toUser.getFull_name());
			    	message_body+= "Thanks & Regards,\n Claims";
			        mail.setMessageBody(message_body);
			        mail.setReceipent(toUser.getUser_email());
			    	mailConfigDao.sendMail(mail);
		    	}
	    	}
	    	catch(Exception e)
	    	{
	    		CustomMethods.logError(e);
	    	}
    	}
    	return message;
		
    }
    
    @RequestMapping(value = "/downloadErrorReport",method = RequestMethod.GET)
    public void downloadErrorReport(HttpServletRequest request, HttpServletResponse response) 
    {
    	ServletContext context = request.getSession().getServletContext();
    	String filename = Config.upload_directory + "error_log.xlsx";
    	File downloadFile = new File(filename);
    	if(!(downloadFile.isFile() && downloadFile.exists()))
    		return;
        try
        {
	    	FileInputStream inputStream = new FileInputStream(downloadFile);
	
	        response.setContentType(context.getMimeType(filename));
	        response.setContentLength((int) downloadFile.length());
	        response.setHeader("Content-Disposition", 
	        		String.format("attachment; filename=\"%s\"", downloadFile.getName()));
	        OutputStream outStream = response.getOutputStream();
	
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	
	        // write bytes read from the input stream into the output stream
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	
	        inputStream.close();
	        outStream.close();
        }
        catch(Exception e)
        {
        	CustomMethods.logError(e);
        	return;
        }
    }
    
    @RequestMapping(value = "/getUserByRole",method = RequestMethod.POST)
    public @ResponseBody List<UserDetails> getUserByRole(HttpServletRequest request,HttpSession session) 
    {
    	String role_code = request.getParameter("role_code");
    	userDao.getUserRole(role_code);
    	return caseDao.getUserListByRole(role_code);
		
    }
    
    
    
    @RequestMapping(value = "/case_history",method = RequestMethod.GET)
    public String timeline(HttpServletRequest request,HttpSession session) 
    {			
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		long caseId = Long.parseLong(request.getParameter("caseId"));
		
		session.removeAttribute("ScreenDetails"); 	
		ScreenDetails details=new ScreenDetails();
    	details.setScreen_name("../message/timeline.jsp");
    	details.setScreen_title("Case History");
    	details.setMain_menu("Case Management");
    	details.setSub_menu1("");
    	details.setSub_menu2("");
    	details.setSub_menu2_path("../message/pending_message.jsp");
    	session.setAttribute("ScreenDetails", details);
    	session.setAttribute("case_history", caseMovementDao.getCaseMovementHistory(caseId));
    
    	return "common/templatecontent";
   	}
    
    @RequestMapping(value = "/getUserRoleBystatus",method = RequestMethod.POST)
    public @ResponseBody List<UserRole> getUserRoleBystatus(HttpServletRequest request,HttpSession session) 
    {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
    	String status = request.getParameter("status");
    	return userDao.getUserRole_lists(user.getAccount_type(), status);
		
    }
    
    
}
