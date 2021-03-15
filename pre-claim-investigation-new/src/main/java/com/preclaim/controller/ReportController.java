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

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
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
import com.preclaim.models.RegionwiseList;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.TopInvestigatorList;
import com.preclaim.models.UserDetails;

@Controller
@RequestMapping(value = "/report")
public class ReportController {
	
	@Autowired
	ReportDao reportDao;
	
	@RequestMapping(value = "/top15investigator", method = RequestMethod.GET)
    public String top15investigator(HttpSession session) {
		
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
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
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
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
	
	@RequestMapping(value = "/regionWiseScreen", method = RequestMethod.GET)
    public String regionWiseScreen(HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
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
	
	@RequestMapping(value = "/downloadInvestigatorReport", method = RequestMethod.POST)
    public @ResponseBody String downloadInvestigatorReport(HttpServletRequest request,HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		//Print Body
		List<TopInvestigatorList> investigator = reportDao.getTopInvestigatorList(startDate, endDate); 
		
		if(investigator == null)
			return "No cases investigated";
		
		if(investigator.size() <= 1)
			return "No cases investigated";
		
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
			XSSFCellStyle style = investigator_wb.createCellStyle();
			Font font = investigator_wb.createFont();
			
			//Print Header
			cell.setCellValue("Top 15 Investigators in terms of volume");
			style.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 112, 192), new DefaultIndexedColorMap()));   //Blue
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font.setColor(IndexedColors.WHITE.getIndex());
			font.setBold(true);
			
			//for border colour
            style.setBorderLeft(BorderStyle.THIN);  
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderRight(BorderStyle.THIN);  
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderTop(BorderStyle.THIN);  
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderBottom(BorderStyle.THIN);  
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			
            
		    //style.setFont(font);
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
				if(item.getInvestigator().equals("Total"))
				{
					style.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 112, 192), new DefaultIndexedColorMap()));
					font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
				}
				else if(item.getNotCleanRate() >= threshold - 2 && item.getNotCleanRate() < threshold) {
					style.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 255, 0), new DefaultIndexedColorMap()));  //yellow					
				}
				else if(item.getNotCleanRate() <= threshold) {
					style.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 0, 0), new DefaultIndexedColorMap()));    //pink
				}
				else if(item.getNotCleanRate() >= threshold) {
					style.setFillForegroundColor(new XSSFColor(new java.awt.Color(146, 208, 80), new DefaultIndexedColorMap()));   //Green
				}
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				style.setBorderLeft(BorderStyle.THIN);  
	            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	            style.setBorderRight(BorderStyle.THIN);  
	            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	            style.setBorderTop(BorderStyle.THIN);  
	            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	            style.setBorderBottom(BorderStyle.THIN);  
	            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	            
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
			
			//Legends
			newRow = investigator_sheet.getRow(2) == null ? investigator_sheet.createRow(2) : investigator_sheet.getRow(2);
			cell = newRow.createCell(8);
			style.setFillForegroundColor(new XSSFColor(new java.awt.Color(146, 208, 80), new DefaultIndexedColorMap()));   //Green
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
			cell = newRow.createCell(9);
			cell.setCellValue("Above Average");
			
			newRow = investigator_sheet.getRow(3) == null ? investigator_sheet.createRow(3) : investigator_sheet.getRow(3);
			cell = newRow.createCell(8);
			style.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 255, 0), new DefaultIndexedColorMap()));  //yellow
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
			cell = newRow.createCell(9);  
			cell.setCellStyle(style);
			cell.setCellValue("Near Average");
			
			newRow = investigator_sheet.getRow(4) == null ? investigator_sheet.createRow(4) : investigator_sheet.getRow(4);
			cell = newRow.createCell(8);
			style.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 0, 0), new DefaultIndexedColorMap()));  //Red
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
			cell = newRow.createCell(9);
			cell.setCellStyle(style);
			cell.setCellValue("Below Average");
		
			//Cell Esthetic Settings
			investigator_sheet.autoSizeColumn(1);
			investigator_sheet.autoSizeColumn(2);
			investigator_sheet.autoSizeColumn(3);
			investigator_sheet.autoSizeColumn(4);
			investigator_sheet.autoSizeColumn(5);
			
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
	
	
	
	@RequestMapping(value = "/downloadVendorwiseReport", method = RequestMethod.POST)
    public @ResponseBody String downloadVendorwiseReport(HttpServletRequest request, HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		//Print Body
		List<TopInvestigatorList> investigator = reportDao.getTopInvestigatorList(startDate, endDate); 
		
		if(investigator == null)
			return "No cases investigated";
	
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
			XSSFCellStyle style = investigator_wb.createCellStyle();
			Font font = investigator_wb.createFont();
			
			//Print Header
		    cell.setCellValue("Top 15 Investigators in terms of volume");
			style.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 112, 192), new DefaultIndexedColorMap()));   //Blue
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		
			//for border colour
			style.setBorderLeft(BorderStyle.THIN);  
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderRight(BorderStyle.THIN);  
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderTop(BorderStyle.THIN);  
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderBottom(BorderStyle.THIN);  
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
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
				if(item.getNotCleanRate() <= threshold - 2) {
					style.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 255, 0), new DefaultIndexedColorMap()));  //yellow
					style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					
					//for border style
		            style.setBorderLeft(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            style.setBorderRight(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            style.setBorderTop(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            style.setBorderBottom(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				}
				else if(item.getNotCleanRate() <= threshold) {
					style.setFillForegroundColor(new XSSFColor(new java.awt.Color(248, 203, 173), new DefaultIndexedColorMap()));    //pink
					style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					//for border colour
		            style.setBorderLeft(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            style.setBorderRight(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            style.setBorderTop(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            style.setBorderBottom(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				}
				else if(item.getNotCleanRate() >= threshold) {
					style.setFillForegroundColor(new XSSFColor(new java.awt.Color(146, 208, 80), new DefaultIndexedColorMap()));   //Green
					style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					//for border colour
		            style.setBorderLeft(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            style.setBorderRight(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            style.setBorderTop(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            style.setBorderBottom(BorderStyle.THIN);  
		            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				}
				else
					cell.setCellValue("Top 15 Investigators in terms of volume");
					style.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 112, 192), new DefaultIndexedColorMap()));   //Blue
					style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
				
					//for border colour
					style.setBorderLeft(BorderStyle.THIN);  
					style.setRightBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderRight(BorderStyle.THIN);  
					style.setRightBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderTop(BorderStyle.THIN);  
					style.setRightBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderBottom(BorderStyle.THIN);  
					style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				
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
			
			//Legends
			//newRow = investigator_sheet.getRow(2);
		
			//Cell Asthetic Settings
			investigator_sheet.autoSizeColumn(1);
			investigator_sheet.autoSizeColumn(2);
			investigator_sheet.autoSizeColumn(3);
			investigator_sheet.autoSizeColumn(4);
			investigator_sheet.autoSizeColumn(5);
			
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
	
	@RequestMapping(value = "/downloadRegionwiseReport", method = RequestMethod.POST)
    public @ResponseBody String downloadRegionwiseReport(HttpServletRequest request,HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
		
		String region = request.getParameter("region");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		//Print Body
		List<RegionwiseList> regionwise = reportDao.getRegionwiseList(region, startDate, endDate); 
		
		if(regionwise == null)
			return "No cases investigated";
		
		//Generate Excel
		try 
		{
			XSSFWorkbook regionwise_wb = new XSSFWorkbook();
			XSSFSheet regionwise_sheet = regionwise_wb.createSheet(region);
			int rowNum = 1;
			Row newRow = regionwise_sheet.createRow(rowNum);
			int colNum = 1;
			Cell cell = newRow.createCell(colNum);
			
			//Print Header
			cell.setCellValue("Investigator");
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("Clean");
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("Not Clean");
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("PIV Stopped");
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("WIP");
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("Grand Total");
			colNum++;
			
			cell = newRow.createCell(colNum);
			cell.setCellValue("Not Clean Rate");
			colNum++;
			
			//Print Body
			rowNum++;
			newRow = regionwise_sheet.createRow(rowNum);
			for (RegionwiseList item : regionwise) {
				colNum = 1;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getInvestigator());
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getClean());
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getNotClean());
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getPiv());
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getWip());
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getTotal());
				colNum++;
				
				cell = newRow.createCell(colNum);
				cell.setCellValue(item.getNotCleanRate());
				colNum++;
				
				rowNum++;
				newRow = regionwise_sheet.createRow(rowNum);
			}
			
			//Legends
			//newRow = investigator_sheet.getRow(2);
		
			String filename = "Regionwise_" + LocalDate.now() + ".xlsx";
			FileOutputStream outputStream = new FileOutputStream(Config.upload_directory + filename);
			regionwise_wb.write(outputStream);
			regionwise_wb.close();
			return filename;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
    }
	
	@RequestMapping(value = "/uploadedDocument", method = RequestMethod.GET)
    public String uploadedDocument(HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("User_Login");
		if(user == null)
			return "common/login";
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
