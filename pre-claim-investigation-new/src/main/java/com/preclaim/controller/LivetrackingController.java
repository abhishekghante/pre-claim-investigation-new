package com.preclaim.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.preclaim.dao.CaseDao;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;

@Controller
@RequestMapping(value = "/livetracking")
public class LivetrackingController {

	@Autowired
	CaseDao caseDao;
	
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpSession session) {
    	UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
    	session.removeAttribute("ScreenDetails");    	
		ScreenDetails details = new ScreenDetails();
    	details.setScreen_name("../livetracking/index.jsp");
    	details.setScreen_title("App Users Lists");
    	details.setMain_menu("Live Tracking");
    	session.setAttribute("ScreenDetails", details);
    	session.setAttribute("live_cases", caseDao.getLiveCaseList(user.getUsername()));
		return "common/templatecontent";
    }    
}
