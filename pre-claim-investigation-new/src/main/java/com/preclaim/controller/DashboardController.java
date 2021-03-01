package com.preclaim.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.preclaim.dao.DashboardDao;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;

@Controller
public class DashboardController {

	@Autowired
	DashboardDao dashboardDao;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		ScreenDetails details = new ScreenDetails();
    	details.setScreen_name("dashboard.jsp");
    	details.setScreen_title("Dashboard");
    	details.setMain_menu("Dashboard");
    	session.setAttribute("ScreenDetails", details);
    	
    	session.setAttribute("Dashboard Count", dashboardDao.getCaseCount(user.getUsername()));
    	return "common/templatecontent";
    }
}
