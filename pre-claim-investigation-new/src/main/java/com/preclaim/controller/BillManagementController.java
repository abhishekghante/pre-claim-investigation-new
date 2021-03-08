package com.preclaim.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.preclaim.dao.BillingManagementDao;
import com.preclaim.models.BillManagementList;
import com.preclaim.models.ScreenDetails;
import com.preclaim.models.UserDetails;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
	  
	  
	  
	  @RequestMapping(value = "/getCheckboxValue",method = RequestMethod.POST)
		public @ResponseBody void getCheckboxValue(HttpSession session, HttpServletRequest request) throws IOException {
			UserDetails user = (UserDetails) session.getAttribute("User_Login");
             System.out.println("hii");
             List<Integer> list=new ArrayList<>();
             String tempStr[] = request.getParameterValues("selected[]");
             List<BillManagementList> billingList = new ArrayList<BillManagementList>();
             for(String values: tempStr) {
            	 if(!values.equals("on")) {
            	 billingList.addAll( billingDao.billingEnquiryPendingList(Integer.parseInt(values)));  
            	 }
             }
             System.out.println("billingList"+billingList);
             
             //Create blank workbook
             XSSFWorkbook workbook = new XSSFWorkbook();
             
             //Create a blank sheet
             XSSFSheet spreadsheet = workbook.createSheet( " Employee Info ");

             //Create row object
             XSSFRow row;

             //This data needs to be written (Object[])
             Map<Integer, Object[]> empinfo = new TreeMap < Integer, Object[] >();
             
		
		  empinfo.put( 0, new Object[] {"SR NO", "CASE ID", "POLICY NUMBER", "INVESTIGATION ID", "INTIMATION TYPE", "SUPERVISOR ID", "SUPERVISOR NAME", "CHARGES"});
		  int i=1;
		  for(BillManagementList listAllValues :billingList) 
		    {
			 
		        empinfo.put( i, new Object[] {i,listAllValues.getCaseID(), listAllValues.getPolicyNumber(), listAllValues.getInvestigationId(), listAllValues.getInitimationType()
		        		                        ,listAllValues.getSupervisorID(),listAllValues.getSupervisorName(),listAllValues.getCharges()});
		        i++;
		    }
             
             //Iterate over data and write to sheet
             Set<Integer> keyid = empinfo.keySet();
             int rowid = 0;
              
		
		  for (Integer key : keyid) 
		  { 
			  row = spreadsheet.createRow(rowid++); Object []
		      objectArr = empinfo.get(key); int cellid = 0;
		  
		    for (Object obj : objectArr)
		    { 
			  Cell cell = row.createCell(cellid++);
		      cell.setCellValue(obj.toString()); 
	        } 
		  } //Write the workbook in file system
		  FileOutputStream out = new FileOutputStream( new
		  File("C:/Pre-Claim Investigation/uploads/"+user.getFull_name()+".xlsx"));
		  
		  workbook.write(out); out.close();
		  System.out.println("Writesheet.xlsx written successfully");
		 		 
           }
                    
		} 
	  

