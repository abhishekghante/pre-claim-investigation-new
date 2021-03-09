package com.preclaim.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.preclaim.config.Config;
import com.preclaim.config.CustomMethods;
import com.preclaim.dao.ReportDao;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.TopInvestigatorList;

@Controller
@RequestMapping(value = "/report")
public class ReportController {
	
	@Autowired
	ReportDao reportDao;
	
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
    
	@RequestMapping(value = "/downloadInvestigatorReport", method = RequestMethod.POST)
    public @ResponseBody String downloadInvestigatorReport(HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		//Print Body
		List<TopInvestigatorList> investigator = reportDao.getTopInvestigatorList(startDate, endDate); 
		
		if(investigator == null)
			return "No case investigated";
		
		if(investigator.size() <= 1)
			return "No case investigated";
		
		float threshold = investigator.get(investigator.size() - 1).getNotCleanRate();
		
		//Generate Excel
		try 
		{
			XSSFWorkbook investigator_wb = new XSSFWorkbook();
			XSSFSheet investigator_sheet = investigator_wb.createSheet("Top 15 Investigator");
			int rowNum = 1;
			Row newRow = investigator_sheet.createRow(rowNum);
			int colNum = 1;
			Cell cell = newRow.createCell(colNum);
			CellStyle style = investigator_wb.createCellStyle();
			
			//Print Header
			cell.setCellValue("Top 15 Investigators in terms of volume");
			style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
			cell.setCellStyle(style);
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("Clean");
			cell.setCellStyle(style);
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("Not Clean");
			cell.setCellStyle(style);
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("Grand Total");
			cell.setCellStyle(style);
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("Not Clean Rate");
			cell.setCellStyle(style);
			colNum++;
			
			//Print Body
			rowNum++;
			newRow = investigator_sheet.createRow(rowNum);
			for (TopInvestigatorList item : investigator) {
				colNum = 1;
				style = investigator_wb.createCellStyle();
				if(item.getNotCleanRate() <= threshold - 2)
					style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
				else if(item.getNotCleanRate() <= threshold)
					style.setFillBackgroundColor(IndexedColors.RED.getIndex());
				else if(item.getNotCleanRate() >= threshold) 
					style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
				else
					style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getInvestigator());
				cell.setCellStyle(style);
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getClean());
				cell.setCellStyle(style);
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getNotClean());
				cell.setCellStyle(style);
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getTotal());
				cell.setCellStyle(style);
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getNotCleanRate());
				cell.setCellStyle(style);
				colNum++;
				
				rowNum++;
				newRow = investigator_sheet.createRow(rowNum);
			}
			String filename = "Top Investigator_" + LocalDate.now() + ".xlsx";
			FileOutputStream outputStream = new FileOutputStream(Config.upload_directory + filename);
			investigator_wb.write(outputStream);
			investigator_wb.close();
			return filename;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
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
    	session.setAttribute("VendorList", reportDao.getVendor());
    	return "common/templatecontent";
    }
	
	@RequestMapping(value = "/downloadVendorwiseReport", method = RequestMethod.GET)
    public @ResponseBody String downloadVendorwiseReport(HttpServletRequest request) {
    	return "";
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
    	session.setAttribute("StateList", reportDao.getRegion());
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
