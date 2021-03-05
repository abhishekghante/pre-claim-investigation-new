package com.preclaim.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.preclaim.dao.BillingManagementDao;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;

@Controller
@RequestMapping(value = "/billManagement")
public class BillManagementController {
	
	
	@Autowired
	 BillingManagementDao billingDao;

	
	  @RequestMapping(value = "/bill_enquiry", method = RequestMethod.GET)
	    public String pending_message(HttpSession session) {
	    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
			if(user == null)
				return "common/login";
			session.removeAttribute("ScreenDetails");
	    	ScreenDetails details=new ScreenDetails();
	    	details.setScreen_name("../billManagement/bill_enquiry.jsp");
	    	details.setScreen_title("Bill Enquiry Lists");
	    	details.setMain_menu("Billing Management");
	    	details.setSub_menu1("Bill Enquiry");
	    	if(session.getAttribute("success_message") != null)
	    	{
	    		details.setSuccess_message1((String)session.getAttribute("success_message"));
	    		session.removeAttribute("success_message");
	    	}
	    	session.setAttribute("ScreenDetails", details);
	    	session.setAttribute("billingPendingList",billingDao.billingEnquiryPendingList());
	    	return "common/templatecontent"; 
	    }
	
	  @RequestMapping(value = "/bill_payment",method = RequestMethod.GET)
		public String bill_payment(HttpSession session, HttpServletRequest request) {
			UserDetails user = (UserDetails) session.getAttribute("User_Login");
			if(user == null)
				return "common/login";
			session.removeAttribute("ScreenDetails");
			ScreenDetails details=new ScreenDetails();
			details.setScreen_name("../billManagement/bill_payment.jsp");
			details.setScreen_title("Bill Payment Lists");
			details.setMain_menu("Billing Management");
			details.setSub_menu1("Bill Payment");
			if(session.getAttribute("success_message") != null)
	    	{
	    		details.setSuccess_message1((String)session.getAttribute("success_message"));
	    		session.removeAttribute("success_message");
	    	}
			session.setAttribute("ScreenDetails", details);
			return "common/templatecontent";
		}
	  
}
