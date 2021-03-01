package com.preclaim.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.preclaim.dao.InvestigationTypeDao;
import com.preclaim.dao.UserDAO;
import com.preclaim.models.InvestigationType;
import com.preclaim.models.InvestigationTypeList;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;

@Controller
@RequestMapping(value = "/investigationType")
public class InvestigationTypeController {

	@Autowired
	private InvestigationTypeDao investigationTypedao;
	
	@Autowired
	private UserDAO userDao;

	@RequestMapping(value = "/addInvestigationType", method = RequestMethod.GET)
	public String addInvestigationType(HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		session.removeAttribute("ScreenDetails");
		ScreenDetails details = new ScreenDetails();
		details.setScreen_name("../investigationType/addInvestigationType.jsp");
		details.setScreen_title("Add Investigation Type");
		details.setMain_menu("Investigation Type");
		details.setSub_menu1("Add Investigation Type");
		details.setSub_menu2("Manage Investigation Type");
		details.setSub_menu2_path("/investigationType/pendingInvestigationType");
		if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
		session.setAttribute("ScreenDetails", details);
		return "common/templatecontent";
	}

	@RequestMapping(value = "/pendingInvestigationType", method = RequestMethod.GET)
	public String pendingInvestigationType(HttpSession session,HttpServletRequest request) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		session.removeAttribute("ScreenDetails");
		ScreenDetails details = new ScreenDetails();
		details.setScreen_name("../investigationType/pendingInvestigationType.jsp");
		details.setScreen_title("Investigation Type Lists");
		details.setMain_menu("Investigation Type");
		details.setSub_menu1("Pending Investigation Type");
		if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
		session.setAttribute("ScreenDetails", details);
		
		List<InvestigationTypeList> pendingInvestigationType = investigationTypedao.getInvestigationList(0);
		session.setAttribute("pendingInvestigationType", pendingInvestigationType);
		
		if(request.getParameter("investigationId")!=null)
		{
			int investigationId = Integer.parseInt(request.getParameter("investigationId"));
			String investigationType = request.getParameter("investigationType");
			InvestigationType editInvestigation = new InvestigationType(investigationId, investigationType);
			session.setAttribute("editInvestigation",editInvestigation);
		}
		return "common/templatecontent";
	}

	@RequestMapping(value = "/activeInvestigationType", method = RequestMethod.GET)
	public String activeInvestigationType(HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		session.removeAttribute("ScreenDetails");
		ScreenDetails details = new ScreenDetails();
		details.setScreen_name("../investigationType/activeInvestigationType.jsp");
		details.setScreen_title("Investigation Type Lists");
		details.setMain_menu("Investigation Type");
		details.setSub_menu1("Active Investigation Type");
		if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
		session.setAttribute("ScreenDetails", details);
		List<InvestigationTypeList> activeList = investigationTypedao.getInvestigationList(1);
		session.setAttribute("active_list", activeList);
		return "common/templatecontent";
	}

	@RequestMapping(value = "/addInvestigation", method = RequestMethod.POST)
	public @ResponseBody String addInvestigation(HttpSession session, HttpServletRequest request) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		InvestigationType investigationType = new InvestigationType();
		investigationType.setInvestigationType(request.getParameter("investigationType"));
		investigationType.setCreatedBy(user.getUsername());
		String message = investigationTypedao.addInvestigationType(investigationType);
		if(message.equals("****"))
		{
			userDao.activity_log("INVESTIGATION TYPE", investigationType.getInvestigationType(), "ADD", 
				user.getUsername());
		}
		return message;
	}
	
	@RequestMapping(value ="/updateInvestigation",method = RequestMethod.POST )
    public @ResponseBody String updateInvestigation(HttpSession session, HttpServletRequest request) {
	 int investigationId = Integer.parseInt(request.getParameter("investigationId"));
	 String investigationType = request.getParameter("investigationType");
	 UserDetails user = (UserDetails) session.getAttribute("User_Login");
	 String message = investigationTypedao.updateInvestigationType(investigationType, user.getUsername(), 
			 investigationId);
	 if(message.equals("****"))
	 {
		 session.setAttribute("success_message", "Investigation Type updated successfully");
		 userDao.activity_log("INVESTIGATION TYPE", investigationType, "UPDATE", user.getUsername());
	 }
	 return message;
	}
	
	@RequestMapping(value ="/deleteInvestigation",method = RequestMethod.POST )
    public @ResponseBody String deleteInvestigation(HttpSession session, HttpServletRequest request) {
	 int investigationId = Integer.parseInt(request.getParameter("investigationId"));	
     String message = investigationTypedao.deleteInvestigationType(investigationId);	
     UserDetails user = (UserDetails) session.getAttribute("User_Login");
     if(message.equals("****"))
     {
    	 session.setAttribute("success_message", "Investigation Type deleted successfully");
    	 userDao.activity_log("INVESTIGATION TYPE",String.valueOf(investigationId), "DELETE", 
    			 user.getUsername());
     }
     return message;
	}
	
	@RequestMapping(value = "/updateInvestigationStatus",method = RequestMethod.POST)
	public @ResponseBody String updateCategoryStatus(HttpSession session, HttpServletRequest request) {
		int status = Integer.parseInt(request.getParameter("status"));
		int investigationId = Integer.parseInt(request.getParameter("investigationId"));
		UserDetails user = (UserDetails) session.getAttribute("User_Login");		 
		String message = investigationTypedao.updateInvestigationTypeStatus(investigationId, 
				user.getUsername(), status);
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Investigation status changed successfully");
			userDao.activity_log("INVESTIGATION TYPE", String.valueOf(investigationId), status == 1 ? "ACTIVE" : "DEACTIVE", 
				user.getUsername());
		}
		return message;
	}
	  
   
}
