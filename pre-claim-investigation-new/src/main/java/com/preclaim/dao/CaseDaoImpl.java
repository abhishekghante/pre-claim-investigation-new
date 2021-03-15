package com.preclaim.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.preclaim.config.Config;
import com.preclaim.config.CustomMethods;
import com.preclaim.models.CaseDetailList;
import com.preclaim.models.CaseDetails;
import com.preclaim.models.CaseMovement;
import com.preclaim.models.CaseSubStatus;
import com.preclaim.models.InvestigationType;
import com.preclaim.models.Location;
import com.preclaim.models.MailConfig;
import com.preclaim.models.UserDetails;

public class CaseDaoImpl implements CaseDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate template;

	@Autowired
	CaseDao caseDao;

	@Autowired
	InvestigationTypeDao investigationDao;

	@Autowired
	IntimationTypeDao intimationTypeDao;

	@Autowired
	LocationDao locationDao;

	@Autowired
	UserDAO userDao;

	@Autowired
	Case_movementDao case_movementDao;
	
	@Autowired
	MailConfigDao mailConfigDao;
	

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String addBulkUpload(String filename, UserDetails fromUser, String user_role) {

		String extension = StringUtils.getFilenameExtension(filename).toLowerCase();
		String error = "";
		if (extension.equals("xlsx"))
			error = readCaseXlsx(filename, fromUser, user_role);
		else
			error = "Invalid File extension";
		return error;
	}

	@Override
	public long addcase(CaseDetails casedetail) {
		try 
		{
			String current_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			String query = "INSERT INTO case_lists (policyNumber, investigationId, insuredName, "
					+ "insuredDOD, insuredDOB, sumAssured, intimationType, locationId, caseStatus, "
					+ "caseSubstatus, notCleanCategory, paymentApproved, nominee_name, "
					+ "nominee_ContactNumber, nominee_address, insured_address, case_description, "
					+ "longitude, latitude, pdf1FilePath , pdf2FilePath, pdf3FilePath, audioFilePath, "
					+ "videoFilePath, signatureFilePath , image, capturedDate, createdDate, createdBy, "
					+ "updatedDate, updatedBy)"
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					//pdf1FilePath onwards
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '')";
			this.template.update(query, casedetail.getPolicyNumber(), casedetail.getInvestigationId(),
					casedetail.getInsuredName(), casedetail.getInsuredDOD(), casedetail.getInsuredDOB(),
					casedetail.getSumAssured(), casedetail.getIntimationType(), casedetail.getLocationId(),
					casedetail.getCaseStatus(), casedetail.getCaseSubStatus(), casedetail.getNotCleanCategory(),
					casedetail.getPaymentApproved(), casedetail.getNominee_name(), casedetail.getNomineeContactNumber(), 
					casedetail.getNominee_address(), casedetail.getInsured_address(), casedetail.getCase_description(), 
					casedetail.getLongitude(), casedetail.getLatitude(), casedetail.getPdf1FilePath(),
					casedetail.getPdf2FilePath(), casedetail.getPdf3FilePath(), casedetail.getAudioFilePath(),
					casedetail.getVideoFilePath(), casedetail.getSignatureFilePath(),"",casedetail.getCapturedDate(),
					current_date, casedetail.getCreatedBy(), current_date);

			query = "SELECT caseId FROM case_lists where policyNumber = ? and createdBy = ? and "
					+ "createdDate = ? and updatedBy = ''";
			return template.query(query,
					new Object[] { casedetail.getPolicyNumber(), casedetail.getCreatedBy(), current_date },
					(ResultSet rs, int rowcount) -> {
						return rs.getLong("caseId");
					}).get(0);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return 0;
		}
	}

	@Override
	public List<CaseDetailList> getPendingCaseList(String user_role, String zone, String username) {
		try {
			String sql = "";
			if (user_role.equalsIgnoreCase("RCU")) 
			{
				sql = "SELECT * FROM case_lists a, case_movement b where a.caseId = b.caseId and a.caseStatus <> 'Closed' and (b.fromId = ? and b.user_role ='REGMAN' and b.zone = ? and b.toId ='')";
				List<CaseDetailList> casedetailList = template.query(sql, new Object[] { username,zone },
						(ResultSet rs, int rowCount) -> {
							CaseDetailList casedetail = new CaseDetailList();
							casedetail.setSrNo(rowCount + 1);
							casedetail.setCaseId(rs.getLong("caseId"));
							casedetail.setPolicyNumber(rs.getString("policyNumber"));
							casedetail.setInsuredName(rs.getString("insuredName"));
							casedetail.setInvestigationCategoryId(rs.getInt("investigationId"));
							casedetail.setSumAssured(rs.getDouble("sumAssured"));
							casedetail.setCaseStatus(rs.getString("caseStatus"));
							casedetail.setIntimationType(rs.getString("intimationType"));
							casedetail.setNotCleanCategory(rs.getString("notCleanCategory"));
							casedetail.setCaseSubStatus(rs.getString("caseSubStatus"));
							return casedetail;
						});
				HashMap<Integer, String> investigationList = investigationDao.getActiveInvestigationMapping();
				for (CaseDetailList caseDetail : casedetailList)
					caseDetail.setInvestigationCategory(
							investigationList.get(Integer.valueOf(caseDetail.getInvestigationCategoryId())));
				return casedetailList;

			} 
			else if(user_role.equalsIgnoreCase("REGMAN")) 
			{
				sql = "SELECT * FROM case_lists a, case_movement b where a.caseId = b.caseId and a.caseStatus <> 'Closed' and "
						+ "(toId = ? or (b.user_role = ? and b.zone = ? and b.toId =''))";
				List<CaseDetailList> casedetailList = template.query(sql, new Object[] {username, user_role,zone },
						(ResultSet rs, int rowCount) -> {
							CaseDetailList casedetail = new CaseDetailList();
							casedetail.setSrNo(rowCount + 1);
							casedetail.setCaseId(rs.getLong("caseId"));
							casedetail.setPolicyNumber(rs.getString("policyNumber"));
							casedetail.setInsuredName(rs.getString("insuredName"));
							casedetail.setInvestigationCategoryId(rs.getInt("investigationId"));
							casedetail.setSumAssured(rs.getDouble("sumAssured"));
							casedetail.setCaseStatus(rs.getString("caseStatus"));
							casedetail.setIntimationType(rs.getString("intimationType"));
							casedetail.setNotCleanCategory(rs.getString("notCleanCategory"));
							casedetail.setCaseSubStatus(rs.getString("caseSubStatus"));
							return casedetail;
						});
				HashMap<Integer, String> investigationList = investigationDao.getActiveInvestigationMapping();
				for (CaseDetailList caseDetail : casedetailList)
					caseDetail.setInvestigationCategory(
							investigationList.get(Integer.valueOf(caseDetail.getInvestigationCategoryId())));
				return casedetailList;
			}
			else 
			{	
				sql = " SELECT * FROM case_lists a, case_movement b where a.caseId = b.caseId and a.caseStatus <> 'Closed' and b.toId =?";
				List<CaseDetailList> casedetailList = template.query(sql, new Object[] {username},
						(ResultSet rs, int rowCount) -> {
							CaseDetailList casedetail = new CaseDetailList();
							casedetail.setSrNo(rowCount + 1);
							casedetail.setCaseId(rs.getLong("caseId"));
							casedetail.setPolicyNumber(rs.getString("policyNumber"));
							casedetail.setInsuredName(rs.getString("insuredName"));
							casedetail.setInvestigationCategoryId(rs.getInt("investigationId"));
							casedetail.setSumAssured(rs.getDouble("sumAssured"));
							casedetail.setCaseStatus(rs.getString("caseStatus"));
							casedetail.setIntimationType(rs.getString("intimationType"));
							casedetail.setNotCleanCategory(rs.getString("notCleanCategory"));
							casedetail.setCaseSubStatus(rs.getString("caseSubStatus"));
							return casedetail;
						});
				HashMap<Integer, String> investigationList = investigationDao.getActiveInvestigationMapping();
				for (CaseDetailList caseDetail : casedetailList)
					caseDetail.setInvestigationCategory(
							investigationList.get(Integer.valueOf(caseDetail.getInvestigationCategoryId())));
				return casedetailList;	
			}

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			CustomMethods.logError(ex);
			return null;
		}
	}

	@Override
	public List<CaseDetailList> getAssignedCaseList(String username) {
		try {
			String sql = "SELECT a.caseId, a.policyNumber, a.insuredName, a.investigationId, "
					+ "a.sumAssured, a.caseStatus, a.intimationType "
					+ "FROM case_lists a, audit_case_movement b where a.caseId = b.caseId and" + " b.fromId = ? ";
			List<CaseDetailList> casedetailList = template.query(sql, new Object[] { username },
					(ResultSet rs, int rowCount) -> {
						CaseDetailList casedetail = new CaseDetailList();
						casedetail.setSrNo(rowCount + 1);
						casedetail.setCaseId(rs.getLong("caseId"));
						casedetail.setPolicyNumber(rs.getString("policyNumber"));
						casedetail.setInsuredName(rs.getString("insuredName"));
						casedetail.setInvestigationCategoryId(rs.getInt("investigationId"));
						casedetail.setSumAssured(rs.getDouble("sumAssured"));
						casedetail.setCaseStatus(rs.getString("caseStatus"));
						casedetail.setIntimationType(rs.getString("intimationType"));
						return casedetail;
					});

			HashMap<Integer, String> investigationList = investigationDao.getActiveInvestigationMapping();
			for (CaseDetailList caseDetail : casedetailList)
				caseDetail.setInvestigationCategory(
						investigationList.get(Integer.valueOf(caseDetail.getInvestigationCategoryId())));
			return casedetailList;
		} catch (Exception ex) {
			ex.printStackTrace();
			CustomMethods.logError(ex);
			return null;
		}
	}

	@Override
	public CaseDetails getCaseDetail(long caseID) {
		try {
			String sql = "SELECT * FROM case_lists a where a.caseId = ?";
			List<CaseDetails> caseDetail = this.template.query(sql, new Object[] { caseID },
					(ResultSet rs, int rowCount) -> {
						CaseDetails detail = new CaseDetails();
						detail.setCaseId(rs.getLong("caseId"));
						detail.setPolicyNumber(rs.getString("policyNumber"));
						detail.setInvestigationId(rs.getInt("investigationId"));
						detail.setInsuredName(rs.getString("insuredName"));
						detail.setInsuredDOD(rs.getString("insuredDOD"));
						detail.setInsuredDOB(rs.getString("insuredDOB"));
						detail.setSumAssured(rs.getInt("sumAssured"));
						detail.setIntimationType(rs.getString("intimationType"));
						detail.setLocationId(rs.getInt("locationId"));
						detail.setCaseStatus(rs.getString("caseStatus"));
						detail.setCaseSubStatus(rs.getString("caseSubStatus"));
						detail.setNominee_name(rs.getString("nominee_name"));
						detail.setNomineeContactNumber(rs.getString("nominee_ContactNumber"));
						detail.setNominee_address(rs.getString("nominee_address"));
						detail.setInsured_address(rs.getString("insured_address"));
						detail.setCase_description(rs.getString("case_description"));
						detail.setLongitude(rs.getString("longitude"));
						detail.setLatitude(rs.getString("latitude"));
						detail.setPdf1FilePath(rs.getString("pdf1FilePath"));
						detail.setPdf2FilePath(rs.getString("pdf2FilePath"));
						detail.setPdf3FilePath(rs.getString("pdf3FilePath"));
						detail.setAudioFilePath(rs.getString("audioFilePath"));
						detail.setVideoFilePath(rs.getString("videoFilePath"));
						detail.setSignatureFilePath(rs.getString("signatureFilePath"));
						detail.setImageFilePath(rs.getString("image"));
						detail.setCapturedDate(rs.getString("capturedDate"));
						return detail;
					});

			// Assigner Details
			CaseMovement case_movement = case_movementDao.getCaseById(caseID);
			UserDetails user = userDao.getUserDetails(case_movement.getFromId());
			caseDetail.get(0).setAssignerName(user.getFull_name());
			caseDetail.get(0).setApprovedStatus(case_movement.getCaseStatus());
			caseDetail.get(0).setAssignerRemarks(case_movement.getRemarks());

			// Get Role Name
			caseDetail.get(0).setAssignerRole(userDao.getUserRole(user.getAccount_type()));

			return caseDetail.get(0);

		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public String updateCaseDetails(CaseDetails casedetail) {
		try {
			String sql = "UPDATE case_lists SET policyNumber = ?, investigationId = ?, insuredName = ?, "
					+ "insuredDOD = ?, insuredDOB = ?, sumAssured = ?, intimationType = ?, locationId = ?, "
					+ "nominee_Name = ?, nominee_ContactNumber = ?, nominee_address = ?, "
					+ "insured_address = ?, caseStatus = ?, caseSubstatus = ?, notCleanCategory = ?, "
					+ "updatedDate = getdate(), updatedBy = ? where caseId = ?";
			template.update(sql, casedetail.getPolicyNumber(), casedetail.getInvestigationId(),
					casedetail.getInsuredName(), casedetail.getInsuredDOD(), casedetail.getInsuredDOB(),
					casedetail.getSumAssured(), casedetail.getIntimationType(), casedetail.getLocationId(),
					casedetail.getNominee_name(), casedetail.getNomineeContactNumber(), casedetail.getNominee_address(),
					casedetail.getInsured_address(), casedetail.getCaseStatus(), casedetail.getCaseSubStatus(),
					casedetail.getNotCleanCategory(), casedetail.getUpdatedBy(), casedetail.getCaseId());

		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
		return "****";
	}
	
	@Override
	public String updateCaseTypeAndSubType(CaseDetails casedetail) {
		try {
			String sql = "UPDATE case_lists SET notCleanCategory = ? ,caseStatus = ?, caseSubStatus =? where caseId = ?";
			template.update(sql,casedetail.getNotCleanCategory(), casedetail.getCaseStatus(),
					casedetail.getCaseSubStatus(),casedetail.getCaseId());
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
		return "****";
	}

	@Transactional
	public String readCaseXlsx(String filename, UserDetails fromUser, String user_role) {
		try {
			Set<String> value = new TreeSet<String>();
			File error_file = new File(Config.upload_directory + "error_log.xlsx");
			if (error_file.exists())
				error_file.delete();
			File file = new File(Config.upload_directory + filename);
			// File not found error won't occur
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator(); // iterating over excel file
			itr.hasNext();
			String error_message = sanityCheck(itr.next());
			if (!error_message.equals("****")) {
				wb.close();
				return error_message;
			}
			CaseSubStatus status = caseDao.getCaseStatus(fromUser.getAccount_type(), 1);
			List<InvestigationType> investigation_list = investigationDao.getActiveInvestigationList();
			List<String> intimation_list = intimationTypeDao.getActiveIntimationTypeStringList();
			List<Location> location_list = locationDao.getActiveLocationList();
			Map<CaseDetails, String> error_case = new HashMap<CaseDetails, String>();
			while (itr.hasNext()) {
				error_message = "";
				String intimationType = "";
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator(); // iterating over each column
				CaseDetails caseDetails = new CaseDetails();
				Cell cell;
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					caseDetails.setPolicyNumber(readCellStringValue(cell).toUpperCase());
					if (!(caseDetails.getPolicyNumber().startsWith("C")
							|| caseDetails.getPolicyNumber().startsWith("U")))
						error_message += "Invalid Policy Number, ";
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					String investigationCategory = readCellStringValue(cell);
					caseDetails.setInvestigationCategory(investigationCategory);
					for (InvestigationType investigation : investigation_list) {
						if (investigation.getInvestigationType().equals(investigationCategory)) {
							caseDetails.setInvestigationId(investigation.getInvestigationId());
							break;
						}
					}
					if (caseDetails.getInvestigationId() == 0)
						error_message += "Invalid Investigation Type, ";

				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					caseDetails.setIntimationType(readCellStringValue(cell));
					if (!intimation_list.contains(caseDetails.getIntimationType()))
						error_message += "Invalid Intimation Type";
					intimationType = caseDetails.getIntimationType().toUpperCase();
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					caseDetails.setInsuredName(readCellStringValue(cell));
					if (caseDetails.getInsuredName().equals("")) {

						if (!(intimationType.equals("PIV") || intimationType.equals("PIRV")
								|| intimationType.equals("LIVE")))
							error_message += "Insured Name is mandatory, ";
					}
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					try {
						caseDetails.setInsuredDOD(readCellDateValue(cell));
					} catch (Exception ex) {
						if (!(intimationType.equals("PIV") || intimationType.equals("PIRV")
								|| intimationType.equals("LIVE")))
							error_message += "Insured DOD is mandatory, ";
					}
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					try {
						caseDetails.setInsuredDOB(readCellDateValue(cell));
					} catch (Exception e) {
						if (!(intimationType.equals("PIV") || intimationType.equals("PIRV")
								|| intimationType.equals("LIVE")))
							error_message += "Insured DOB is mandatory";
					}
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					try {
						caseDetails.setSumAssured(readCellIntValue(cell));
					} catch (Exception e) {
						error_message += "Invalid Sum Assured, ";
						caseDetails.setSumAssured(0);
					}
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					caseDetails.setClaimantCity(readCellStringValue(cell));
					for (Location list : location_list) {
						if (caseDetails.getClaimantCity().equalsIgnoreCase(list.getCity())) {
							caseDetails.setLocationId(list.getLocationId());
							caseDetails.setClaimantState(list.getState());
							caseDetails.setClaimantZone(list.getZone());
							break;
						}
					}
					if (caseDetails.getClaimantState().equals(""))
						error_message = "City not present in database, ";
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					caseDetails.setNominee_name(readCellStringValue(cell));
					if (caseDetails.getNominee_name().equals("")) {

						if (!(intimationType.equals("PIV") || intimationType.equals("PIRV")
								|| intimationType.equals("LIVE")))
							error_message += "Nominee Name is mandatory, ";
					}
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					try {
						caseDetails.setNomineeContactNumber(String.valueOf(readCellIntValue(cell)));
					} catch (Exception e) {
					}
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					caseDetails.setNominee_address(readCellStringValue(cell));
					if (caseDetails.getNominee_address().equals("")) {

						if (!(intimationType.equals("PIV") || intimationType.equals("PIRV")
								|| intimationType.equals("LIVE")))
							error_message += "Nominee Address is mandatory, ";
					}
				}
				if (cellIterator.hasNext()) {
					cell = cellIterator.next();
					caseDetails.setInsured_address(readCellStringValue(cell));
					if (caseDetails.getInsured_address().equals("")) {

						if (!intimationType.equals("CDP"))
							error_message += "Insured Address is mandatory, ";
					}
				}
				if (error_message.equals("")) 
				{
					caseDetails.setCaseStatus(status.getCase_status());
					caseDetails.setCaseSubStatus(status.getCaseSubStatus());
					value.add(caseDetails.getClaimantZone());
					long caseId = addcase(caseDetails);
					CaseMovement caseMovement = new CaseMovement();
					caseMovement.setCaseId(caseId);
					caseMovement.setFromId(fromUser.getUsername());
					caseMovement.setUser_role(user_role);
					caseMovement.setZone(caseDetails.getClaimantZone());
					case_movementDao.CreatecaseMovement(caseMovement);
					userDao.activity_log("CASE HISTORY", caseDetails.getPolicyNumber(), "ADD CASE", 
							fromUser.getUsername());
				} 
				else 
				{
					error_message = error_message.trim();
					error_message = error_message.substring(0, error_message.length());
					error_case.put(caseDetails, error_message);
				}
			}
			wb.close();
			for(String val :value) 
			{
				System.out.println(val);	
				getExcelMail(val);
			}
			
			// Error File
			if (error_case != null)
				writeErrorCase(error_case);
			return "****";
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}
	}

	  public void getExcelMail(String zone) 
	  {
			try 
			{
				MailConfig mail = mailConfigDao.getActiveConfig();
				if (mail != null) {
					List<UserDetails> toUser = userDao.getUserRoleList(zone);
					for (UserDetails toMailId : toUser) {
						mail.setSubject("New Cases Assigned From Bulk-upload - Claims");
						String message_body = "Dear <User>, \n Your are required to take action on new cases\n\n";
						message_body = message_body.replace("<User>", toMailId.getFull_name());
						message_body += "Thanks & Regards,\n Claims";
						mail.setMessageBody(message_body);
						mail.setReceipent(toMailId.getUser_email());
						mailConfigDao.sendMail(mail);
						/* System.out.println("mail sent"); */
					}
				}
			} 
			catch (Exception e) 
			{
				CustomMethods.logError(e);
			}
	  }
	
	public String readCellStringValue(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC: // field that represents number cell type
			return String.valueOf(cell.getNumericCellValue()).trim();
		default:
			return "";
		}
	}

	public String readCellDateValue(Cell cell) {
		Date date = Date.valueOf(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		return date.toString();
	}

	public int readCellIntValue(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return Integer.parseInt(cell.getStringCellValue());
		case NUMERIC: // field that represents number cell type
			return (int) cell.getNumericCellValue();
		default:
			return 0;
		}
	}

	public String sanityCheck(Row row) {
		Iterator<Cell> cellIterator = row.cellIterator(); // iterating over each column
		Cell cell;
		ArrayList<String> headerList = CustomMethods.importCaseHeader();
		while (cellIterator.hasNext()) {
			cell = cellIterator.next();
			if (!headerList.contains(readCellStringValue(cell).trim()))
				return "Invalid File Format";
		}
		return "****";
	}

	@Override
	public String deleteCase(int caseId) {
		try

		{
			String sql = "DELETE FROM case_lists where caseId = ?";
			this.template.update(sql, caseId);

			sql = "DELETE FROM case_movement where caseId = ?";
			this.template.update(sql, caseId);

			sql = "DELETE FROM audit_case_movement where caseId = ?";
			this.template.update(sql, caseId);
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return e.getMessage();
		}

		return "****";
	}

	private void writeErrorCase(Map<CaseDetails, String> error_case) {
		// File error_file = new File(Config.upload_directory + "error_log.xlsx");
		try {
			XSSFWorkbook error_wb = new XSSFWorkbook();
			XSSFSheet error_sheet = error_wb.createSheet("Error Log");
			int rowNum = 0;
			Row newRow = error_sheet.createRow(rowNum);
			ArrayList<String> headerList = CustomMethods.importCaseHeader();
			int colNum = 0;
			for (String header : headerList) {
				Cell cell = newRow.createCell(colNum);
				cell.setCellValue(header);
				colNum++;
			}
			Cell cell = newRow.createCell(colNum);
			colNum++;
			cell.setCellValue("Remarks");
			rowNum++;
			newRow = error_sheet.createRow(rowNum);
			for (Map.Entry<CaseDetails, String> entry : error_case.entrySet()) {
				colNum = 0;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getPolicyNumber());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getInvestigationId());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getIntimationType());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getInsuredName());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getInsuredDOD());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getInsuredDOB());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getSumAssured());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getClaimantCity());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getNominee_name());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getNomineeContactNumber());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getNominee_address());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getKey().getInsured_address());
				colNum++;
				cell = newRow.createCell(colNum);
				cell.setCellValue(entry.getValue());
				rowNum++;
				newRow = error_sheet.createRow(rowNum);
			}
			FileOutputStream outputStream = new FileOutputStream(Config.upload_directory + "error_log.xlsx");
			error_wb.write(outputStream);
			error_wb.close();
		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
		}

		return;
	}

	@Override
	public List<UserDetails> getUserListByRole(String role_code) {
		String sql = "SELECT * FROM admin_user where status = 1 and role_name = ?";
		return template.query(sql, new Object[] { role_code }, (ResultSet rs, int rowCount) -> {
			UserDetails details = new UserDetails();
			details.setFull_name(rs.getString("full_name"));
			details.setUsername(rs.getString("username"));
			details.setUserID(rs.getInt("user_id"));
			return details;
		});
	}

	@Override
	public List<CaseDetails> getLiveCaseList(String username) {
		try {
			String sql = "SELECT * FROM case_lists a where longitude <> '' and latitude <> '' and caseId in "
					+ "(select caseId from audit_case_movement where toId = ?)";
			List<CaseDetails> caseDetail = this.template.query(sql, new Object[] { username },
					(ResultSet rs, int rowCount) -> {
						CaseDetails detail = new CaseDetails();
						detail.setCaseId(rs.getLong("caseId"));
						detail.setPolicyNumber(rs.getString("policyNumber"));
						detail.setLongitude(rs.getString("longitude"));
						detail.setLatitude(rs.getString("latitude"));
						return detail;
					});

			return caseDetail;

		} catch (Exception e) {
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}

	@Override
	public CaseSubStatus getCaseStatus(String user_role, int level) {
		try
		{
			String sql = "SELECT * FROM case_substatus where user_role = ? and level = ?";
			return this.template.query(sql, new Object[] {user_role, level},
					(ResultSet rs, int rowCount) -> {
						CaseSubStatus detail = new CaseSubStatus();
						detail.setId(rs.getLong("id"));
						detail.setUser_role(rs.getString("user_role"));
						detail.setCase_status(rs.getString("Case_status"));
						detail.setCaseSubStatus(rs.getString("caseSubStatus"));
						detail.setLevel(rs.getInt("level"));
						return detail;
					}).get(0);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CustomMethods.logError(e);
			return null;
		}
	}
	

}
