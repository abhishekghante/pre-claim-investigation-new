 package com.preclaim.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.preclaim.dao.CaseStatusDao;
import com.preclaim.dao.UserDAO;
import com.preclaim.models.CaseStatus;
import com.preclaim.models.CaseStatusList;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;

@Controller
@RequestMapping(value = "/caseStatus")
public class CaseStatusController {
	
    @Autowired
	private CaseStatusDao caseStatusDao;
    
    @Autowired
    private UserDAO userDao;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpSession session) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		session.removeAttribute("ScreenDetails");
    	ScreenDetails details = new ScreenDetails();
    	details.setScreen_name("../caseStatus/addCaseStatus.jsp");
    	details.setScreen_title("Add Case Status");
    	details.setMain_menu("Case Status");
    	details.setSub_menu1("Add Case Status");
    	details.setSub_menu2("Manage Case Status");
    	details.setSub_menu2_path("/caseStatus/pending");
    	if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
    	session.setAttribute("ScreenDetails", details);
    	return "common/templatecontent";
    }
   
    @RequestMapping(value = "/pending",method = RequestMethod.GET)
    public String pending(HttpSession session, HttpServletRequest request) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		session.removeAttribute("ScreenDetails");
    	ScreenDetails details=new ScreenDetails();
    	details.setScreen_name("../caseStatus/pendingCaseStatus.jsp");
    	details.setScreen_title("Pending Case Status");
    	details.setMain_menu("Case Status");
    	details.setSub_menu1("Pending Case Status");
    	if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
    	session.setAttribute("ScreenDetails", details);
    	List<CaseStatusList> pending_list= caseStatusDao.caseStatus_list(0);
    	session.setAttribute("pending_caseStatus", pending_list);
    	
    	if(request.getParameter("caseStatus")!= null && request.getParameter("caseStatusId") != null)
    	{
    		CaseStatusList caseStatusList = new CaseStatusList();
    		caseStatusList.setCaseStatusId(Integer.parseInt(request.getParameter("caseStatusId")));
    		caseStatusList.setCaseStatus(request.getParameter("caseStatus"));
    		session.setAttribute("caseStatusList", caseStatusList);
    	}
    	return "common/templatecontent";
    }
    
    @RequestMapping(value="/active",method = RequestMethod.GET)
    public String active(HttpSession session) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		session.removeAttribute("ScreenDetails");
    	ScreenDetails details=new ScreenDetails();
    	details.setScreen_name("../caseStatus/activeCaseStatus.jsp");;
    	details.setScreen_title("Active Case Status");
    	details.setMain_menu("Case Status");
    	details.setSub_menu1("Active Case Status");
    	if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
    	session.setAttribute("ScreenDetails", details);
    	List<CaseStatusList> active_list = caseStatusDao.caseStatus_list(1);
    	session.setAttribute("active_list", active_list);
    	return "common/templatecontent";
    }
    
    @RequestMapping(value = "/deleteCaseStatus", method = RequestMethod.POST)
	public @ResponseBody String deleteCaseStatus(HttpSession session, HttpServletRequest request)
	{
		int caseStatusId = Integer.parseInt(request.getParameter("caseStatusId"));
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		String message = caseStatusDao.deleteCaseStatus(caseStatusId);
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Case status deleted successfully");
			userDao.activity_log("CaseStatus", String.valueOf(caseStatusId), "DELETE", 
					user.getUsername());
		}
		return message;
	}
    
    @RequestMapping(value = "/addCaseStatus",method = RequestMethod.POST)
	public @ResponseBody String addGroup(HttpSession session, HttpServletRequest request) 
	{	
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		String caseStatus = request.getParameter("caseStatus");
		CaseStatus case_status = new CaseStatus();
		case_status.setCaseStatus(caseStatus);
		case_status.setCreatedBy(user.getUsername());
		String message = caseStatusDao.add_caseStatus(case_status);
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Case Status added successfully");
			userDao.activity_log("CaseStatus", caseStatus, "ADD", user.getUsername());
		}
		return message;
	}
	
	@RequestMapping(value = "/updateCaseStatus",method = RequestMethod.POST)
	public @ResponseBody String updateCaseStatus(HttpSession session, HttpServletRequest request) 
	{	
		int caseStatusId = Integer.parseInt(request.getParameter("caseStatusId"));		
		String CaseStatus = request.getParameter("caseStatus");
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		String message = caseStatusDao.updateCaseStatus(caseStatusId, CaseStatus, 
				user.getUsername());
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Case status updated successfully");
			userDao.activity_log("CaseStatus", CaseStatus, "UPDATE", user.getUsername());
		}
		return message;
	}
	
	@RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
	public @ResponseBody String updateStatus(HttpSession session, HttpServletRequest request) 
	{	
		int caseStatusId = Integer.parseInt(request.getParameter("caseStatusId"));	
		int status = Integer.parseInt(request.getParameter("status"));
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		String message = caseStatusDao.updateStatus(caseStatusId, status, user.getUsername());
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Case status changed successfully");
			userDao.activity_log("CaseStatus", String.valueOf(caseStatusId), 
					status == 1 ? "ACTIVE" : "DEACTIVE", user.getUsername());
		}
		return message;
	}
	
}
