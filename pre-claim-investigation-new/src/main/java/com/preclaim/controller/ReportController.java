package com.preclaim.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.preclaim.config.Config;
import com.preclaim.config.CustomMethods;
import com.preclaim.models.ScreenDetails;

@Controller
@RequestMapping(value = "/report")
public class ReportController {

 
	@RequestMapping(value = "/top15investigator", method = RequestMethod.GET)
    public String top15investigator(HttpSession session) {
    	session.removeAttribute("ScreenDetails");
    	ScreenDetails details = new ScreenDetails();
    	details.setScreen_name("../report/top15investigator.jsp");
    	details.setScreen_title("Top 15 Investigator");
    	details.setMain_menu("Report");
    	details.setSub_menu1("Top 15 Investigator");
    	session.setAttribute("ScreenDetails", details);
    	return "common/templatecontent";
    }
    
	@RequestMapping(value = "/vendorWiseScreen", method = RequestMethod.GET)
    public String vendorWiseScreen(HttpSession session) {
    	session.removeAttribute("ScreenDetails");
    	ScreenDetails details = new ScreenDetails();
    	details.setScreen_name("../report/vendorWiseScreen.jsp");
    	details.setScreen_title("Vendor wise screen Lists");
    	details.setMain_menu("Report");
    	details.setSub_menu1("Vendor wise screen");
    	session.setAttribute("ScreenDetails", details);
    	return "common/templatecontent";
    }
	
	@RequestMapping(value = "/regionWiseScreen", method = RequestMethod.GET)
    public String regionWiseScreen(HttpSession session) {
    	session.removeAttribute("ScreenDetails");
    	ScreenDetails details = new ScreenDetails();
    	details.setScreen_name("../report/regionWiseScreen.jsp");
    	details.setScreen_title("Region wise screen Lists");
    	details.setMain_menu("Report");
    	details.setSub_menu1("Region wise screen");
    	session.setAttribute("ScreenDetails", details);
    	return "common/templatecontent";
    }
	
	@RequestMapping(value = "/uploadedDocument", method = RequestMethod.GET)
    public String uploadedDocument(HttpSession session) {
    	session.removeAttribute("ScreenDetails");
    	ScreenDetails details = new ScreenDetails();
    	details.setScreen_name("../report/uploadedDocument.jsp");
    	details.setScreen_title("Uploaded Document List");
    	details.setMain_menu("Report");
    	details.setSub_menu1("Uploaded Document");
    	session.setAttribute("ScreenDetails", details);
    	session.setAttribute("directory", Config.upload_directory);
    	return "common/templatecontent";
    }
	
	
	  @RequestMapping(value = "/downloadSysFile", method = RequestMethod.GET)
	  public void downloadSysFile(HttpServletRequest request, HttpServletResponse response) 
	  {
		  
		  try 
		  {
		  	ServletContext context = request.getSession().getServletContext();

	        String rootPath = Config.upload_directory + request.getParameter("filename");
	        File downloadFile = new File(rootPath);
	        FileInputStream inputStream = new FileInputStream(downloadFile);

	        // get MIME type of the file
	        String mimeType = context.getMimeType(rootPath);
	        if (mimeType == null) {
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	        System.out.println("MIME type: " + mimeType);

	        // set content attributes for the response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());

	        // set headers for the response
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);

	        // get output stream of the response
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
			  e.printStackTrace();
			  CustomMethods.logError(e);
		  }
	  }
	  	
}
