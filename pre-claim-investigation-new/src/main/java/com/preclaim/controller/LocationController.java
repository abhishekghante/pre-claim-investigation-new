package com.preclaim.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.preclaim.dao.LocationDao;
import com.preclaim.dao.UserDAO;
import com.preclaim.models.Location;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;

@Controller
@RequestMapping(value = "/location")
public class LocationController{

	@Autowired
	private LocationDao locationDao;
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String add_location(HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		session.removeAttribute("ScreenDetails");
		ScreenDetails details=new ScreenDetails();
		details.setScreen_name("../location/addLocation.jsp");
		details.setScreen_title("Add Location");
		details.setMain_menu("Location");
		details.setSub_menu1("Add Location");
    	details.setSub_menu2("Manage Locations");
    	details.setSub_menu2_path("/location/pending");
    	if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
		session.setAttribute("ScreenDetails", details);
		return "common/templatecontent";
	}
	
	@RequestMapping(value = "/pending",method = RequestMethod.GET)
	public String pending_location(HttpSession session, HttpServletRequest request) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		session.removeAttribute("ScreenDetails");
		ScreenDetails details=new ScreenDetails();
		details.setScreen_name("../location/pendingLocation.jsp");
		details.setScreen_title("Pending Location");
		details.setMain_menu("Location");
		details.setSub_menu1("Pending Location");
		if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
		session.setAttribute("ScreenDetails", details);
		session.setAttribute("pending_location", locationDao.locationList(0));
		
		if(request.getParameter("locationId")!= null)
			session.setAttribute("location", 
					locationDao.getLocationById(Integer.parseInt(request.getParameter("locationId"))));
		return "common/templatecontent";
	}
	
	@RequestMapping(value = "/active",method = RequestMethod.GET)
	public String active_location(HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		session.removeAttribute("ScreenDetails");
		ScreenDetails details=new ScreenDetails();
		details.setScreen_name("../location/activeLocation.jsp");
		details.setScreen_title("Active Location");
		details.setMain_menu("Location");
		details.setSub_menu1("Active Location");
		if(session.getAttribute("success_message") != null)
    	{
    		details.setSuccess_message1((String)session.getAttribute("success_message"));
    		session.removeAttribute("success_message");
    	}
		session.setAttribute("ScreenDetails", details);
		session.setAttribute("active_location", locationDao.locationList(1));
		return "common/templatecontent";
	}
	
	@RequestMapping(value = "/deleteLocation",method = RequestMethod.POST)
	public @ResponseBody String deleteRegion(HttpSession session, HttpServletRequest request) {
	
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		int locationId = Integer.parseInt(request.getParameter("locationId"));
		String message = locationDao.deleteLocation(locationId);
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Location deleted successfully");
	    	userDao.activity_log("LOCATION", String.valueOf(locationId), "DELETE", user.getUsername()); 
		}
		return message;
	}
	
	@RequestMapping(value = "/addLocation",method = RequestMethod.POST)
	public @ResponseBody String addRegion(HttpSession session, HttpServletRequest request) 
	{	
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		Location location = new Location();
		location.setCity(request.getParameter("city"));
		location.setState(request.getParameter("state"));
		location.setZone(request.getParameter("zone"));
		location.setCreatedBy(user.getUsername());
		String message = locationDao.addLocation(location);
		if(message.equals("****"))
		{
			session.setAttribute("success_message", "Location added successfully");
			userDao.activity_log("LOCATION" ,location.getCity(), "ADD", user.getUsername());
			userDao.activity_log("LOCATION" ,location.getState(), "ADD", user.getUsername());
			userDao.activity_log("LOCATION" ,location.getZone(), "ADD", user.getUsername());
		}
		return message;
	}
	
	@RequestMapping(value = "/updateLocation",method = RequestMethod.POST)
	public @ResponseBody String updateRegion(HttpSession session, HttpServletRequest request) 
	{	
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		int locationId=Integer.parseInt(request.getParameter("locationId"));		
	    String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zone = request.getParameter("zone");
        String updatedBy=user.getUsername();
        System.out.println(updatedBy);
        String message = locationDao.updateLocation(locationId, city, state, zone, updatedBy);
        if(message.equals("****"))
        {
        	session.setAttribute("success_message", "Location updated successfully");
	    	userDao.activity_log("LOCATION", String.valueOf(locationId), "UPDATE", user.getUsername());
        }
        return message;
	}
	
	@RequestMapping(value = "/updateLocationStatus",method = RequestMethod.POST)
	public @ResponseBody String updateRegionStatus(HttpSession session, HttpServletRequest request)
	{
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		int locationId=Integer.parseInt(request.getParameter("locationId"));
		int locationStatus=Integer.parseInt(request.getParameter("status"));
	    String message= locationDao.updateLocationStatus(locationId, locationStatus, user.getUsername()); 
	    if(message.equals("****"))
	    {
	    	session.setAttribute("success_message", "Location status changed successfully");
	    	userDao.activity_log("LOCATION", String.valueOf(locationId), 
	    			locationStatus == 1 ? "ACTIVE" : "DEACTIVE", user.getUsername());
	    }
	    return message;
    }
}


