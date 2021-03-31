package com.preclaim.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.preclaim.config.Config;
import com.preclaim.config.CustomMethods;
import com.preclaim.dao.UserDAO;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	@Autowired
	UserDAO dao;
	
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpSession session) {
    	UserDetails user_details = (UserDetails) session.getAttribute("User_Login");
    	if(user_details == null)
    		return "common/login";
    	session.removeAttribute("ScreenDetails");    	
		ScreenDetails details = new ScreenDetails();
    	details.setScreen_name("../profile/edit_profile.jsp");
    	details.setScreen_title("Edit Profile");
    	session.setAttribute("ScreenDetails", details);
		return "common/templatecontent";
    }
    
    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public @ResponseBody String updateProfile(HttpSession session, HttpServletRequest request,
			@RequestParam(name = "account_image", required = false) MultipartFile account_img )
	{
		UserDetails user_details = (UserDetails) session.getAttribute("User_Login");
		
		if(user_details == null)
			return "Kindly login again";
		
		if(account_img != null)
		{
			try
			{
				String filename = user_details.getUsername() + "_profilepic." + 
						StringUtils.getFilenameExtension(account_img.getOriginalFilename());
				Files.write(Paths.get(Config.upload_directory + filename), account_img.getBytes());
				dao.updateUserDoc(filename, user_details.getUsername());
				user_details.setUserimage(filename);
				user_details.setUserImageb64(Config.upload_directory + filename);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				CustomMethods.logError(e);
				return e.getMessage();
			}
		}
		user_details.setFull_name(request.getParameter("full_name"));
		user_details.setUser_email(request.getParameter("user_email"));
		user_details.setUsername(request.getParameter("username"));
		user_details.setPassword(request.getParameter("password"));
		user_details.setUserID(Integer.parseInt(request.getParameter("user_id")));
		user_details.setUpdatedBy(user_details.getUsername());
		session.removeAttribute("User_Login");
		session.setAttribute("User_Login",user_details);
		String message = dao.updateProfile(user_details);
		dao.activity_log("PROFILE", user_details.getUsername(), "UPDATE", user_details.getUsername());
		return message; 
	}
    
}
