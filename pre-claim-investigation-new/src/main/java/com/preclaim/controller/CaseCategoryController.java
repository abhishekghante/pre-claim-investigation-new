 package com.preclaim.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.preclaim.dao.CaseCategoryDao;
import com.preclaim.dao.UserDAO;
import com.preclaim.models.CaseCategory;
import com.preclaim.models.CaseCategoryList;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;

@Controller
@RequestMapping(value = "/caseCategory")
public class CaseCategoryController {
	
    @Autowired
	private CaseCategoryDao caseCategoryDao;
    
    @Autowired
    private UserDAO userDao;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpSession session) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		session.removeAttribute("ScreenDetails");
    	ScreenDetails details = new ScreenDetails();
    	details.setScreen_name("../caseCategory/addCaseCategory.jsp");
    	details.setScreen_title("Add Case Category");
    	details.setMain_menu("Case Category");
    	details.setSub_menu1("Add Case Category");
    	details.setSub_menu2("Manage Case Category");
    	details.setSub_menu2_path("/caseCategory/pending");
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
    	details.setScreen_name("../caseCategory/pendingCaseCategory.jsp");
    	details.setScreen_title("Pending Case Category");
    	details.setMain_menu("Case Category");
    	details.setSub_menu1("Pending Case Category");
    	if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
    	session.setAttribute("ScreenDetails", details);
    	List<CaseCategoryList> pending_list= caseCategoryDao.caseCategory_list(0);
    	session.setAttribute("pending_caseCategory", pending_list);
    	
    	if(request.getParameter("caseCategory")!= null && request.getParameter("caseCategoryId") != null
    			&& request.getParameter("caseStatus") != null)
    	{
    		CaseCategoryList caseCategoryList = new CaseCategoryList();
    		caseCategoryList.setCaseCategoryId(Integer.parseInt(request.getParameter("caseCategoryId")));
    		caseCategoryList.setCaseStatus(request.getParameter("caseStatus"));
    		caseCategoryList.setCaseCategory(request.getParameter("caseCategory"));
    		session.setAttribute("caseCategoryList", caseCategoryList);
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
    	details.setScreen_name("../caseCategory/activeCaseCategory.jsp");;
    	details.setScreen_title("Active Case Category");
    	details.setMain_menu("Case Category");
    	details.setSub_menu1("Active Case Category");
    	if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
    	session.setAttribute("ScreenDetails", details);
    	List<CaseCategoryList> active_list = caseCategoryDao.caseCategory_list(1);
    	session.setAttribute("active_list", active_list);
    	return "common/templatecontent";
    }
    
    @RequestMapping(value = "/deleteCaseCategory", method = RequestMethod.POST)
	public @ResponseBody String deleteCaseCategory(HttpSession session, HttpServletRequest request)
	{
		int caseCategoryId = Integer.parseInt(request.getParameter("caseCategoryId"));
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		String message = caseCategoryDao.deleteCaseCategory(caseCategoryId);
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Case status deleted successfully");
			userDao.activity_log("CaseCategory", String.valueOf(caseCategoryId), "DELETE", 
					user.getUsername());
		}
		return message;
	}
    
    @RequestMapping(value = "/addCaseCategory",method = RequestMethod.POST)
	public @ResponseBody String addGroup(HttpSession session, HttpServletRequest request) 
	{	
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		String caseCategory = request.getParameter("caseCategory");
		CaseCategory case_status = new CaseCategory();
		case_status.setCaseCategory(caseCategory);
		case_status.setCreatedBy(user.getUsername());
		String message = caseCategoryDao.add_caseCategory(case_status);
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Case Category added successfully");
			userDao.activity_log("CaseCategory", caseCategory, "ADD", user.getUsername());
		}
		return message;
	}
	
	@RequestMapping(value = "/updateCaseCategory",method = RequestMethod.POST)
	public @ResponseBody String updateGroup(HttpSession session, HttpServletRequest request) 
	{	
		int caseCategoryId = Integer.parseInt(request.getParameter("caseCategoryId"));		
		String CaseStatus = request.getParameter("caseStatus");
		String CaseCategory = request.getParameter("caseCategory");
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		String message = caseCategoryDao.updateCaseCategory(caseCategoryId, CaseStatus, CaseCategory, 
				user.getUsername());
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Case status updated successfully");
			userDao.activity_log("CaseCategory", CaseCategory, "UPDATE", user.getUsername());
		}
		return message;
	}
	
	@RequestMapping(value = "/updateCaseCategoryStatus",method = RequestMethod.POST)
	public @ResponseBody String updateCaseCategoryStatus(HttpSession session, HttpServletRequest request) 
	{	
		int caseCategoryId = Integer.parseInt(request.getParameter("caseCategoryId"));	
		int status = Integer.parseInt(request.getParameter("status"));
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		String message = caseCategoryDao.updateCaseCategoryStatus(caseCategoryId, status, user.getUsername());
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Case Category status changed successfully");
			userDao.activity_log("CaseCategory", String.valueOf(caseCategoryId), 
					status == 1 ? "ACTIVE" : "DEACTIVE", user.getUsername());
		}
		return message;
	}
	
}
