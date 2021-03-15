<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.preclaim.config.Config"%>
<%@page import="com.preclaim.models.UserRole"%>
<%@page import="com.preclaim.models.Location"%>
<%@page import="com.preclaim.models.InvestigationType"%>
<%@page import="com.preclaim.models.IntimationType"%>
<%@page import="com.preclaim.models.CaseDetails"%>
<%@page import="com.preclaim.models.CaseSubStatus"%>
<%
List<String>user_permission=(List<String>)session.getAttribute("user_permission");
CaseDetails case_detail = (CaseDetails) session.getAttribute("case_detail");
session.removeAttribute("case_detail");
List<CaseSubStatus> CaseSubStatus = (List<CaseSubStatus>) session.getAttribute("level");
System.out.println(CaseSubStatus);
session.removeAttribute("level");
List<InvestigationType> investigationList = (List<InvestigationType>) session.getAttribute("investigation_list");
session.removeAttribute("investigation_list");
List<IntimationType> intimationTypeList = (List<IntimationType>) session.getAttribute("intimation_list");
session.removeAttribute("intimation_list");
List<Location> location_list = (List<Location>) session.getAttribute("location_list");
session.removeAttribute("location_list");
List<UserRole> userRole =(List<UserRole>)session.getAttribute("userRole");
session.removeAttribute("userRole");
boolean allow_edit = user_permission.contains("messages/add");
boolean allow_assign = user_permission.contains("messages/assign");
boolean allow_reopen = user_permission.contains("messages/reopen");
boolean allow_closure = user_permission.contains("messages/close");
%>
<style type="text/css">
.placeImg { display:none !important;}
</style>
<link href="${pageContext.request.contextPath}/resources/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resources/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<div class="row">
  <div class="col-md-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
          <i class="icon-user font-green-sharp"></i>
          <span class="caption-subject font-green-sharp sbold">Add Case</span>
        </div>
        <div class="actions">
            <div class="btn-group">
              <a href="${pageContext.request.contextPath}/message/pending_message" class="btn green-haze btn-outline btn-xs pull-right" data-toggle="tooltip" data-toggle="tooltip" title="Back" style="margin-right: 5px;" data-original-title="Back">
                <i class="fa fa-reply"></i>
              </a>
            </div>
        </div>
      </div>
    </div>
    
    <div class="box box-primary">
      <!-- /.box-header -->
      <!-- form start -->
      <div id="message_alert"></div>
      <form novalidate id="edit_message_form" role="form" method="post" class="form-horizontal" enctype="multipart/form-data">
        <div class="box-body">
          <div class="row">
            <div class="col-sm-10 col-md-10 col-xs-12"> 
			  <div class="form-group">
                <label class="col-md-4 control-label" for="msgTitleEn">Case ID 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="text" value="<%=case_detail.getCaseId()%>" placeholder="Case ID" 
                  	name="caseId" id="caseId" class="form-control" disabled>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="msgTitleEn">Policy Number 
                	<span class="text-danger" >*</span>
               	</label>
                <div class="col-md-8">
                  <input type="text" value="<%=case_detail.getPolicyNumber()%>" 
                  	placeholder="Policy Number" name="policyNumber" id="policyNumber" 
                  	class="form-control" <%if(!allow_edit) {%>disabled<%} %>>
                </div>
              </div>
              
              <div class="form-group selectDiv">
                <label class="col-md-4 control-label" for="msgCategory">Select Investigation Category 
                	<span class="text-danger">*</span></label>
                <div class="col-md-8">
                  <select name="msgCategory" id="msgCategory" class="form-control" tabindex="-1"
                  	<%if(!allow_edit) {%>disabled<%} %>>
                    <option value="-1" disabled>Select</option>
                    <%if(investigationList != null){
                    	for(InvestigationType investigation: investigationList){%>
                    	<option value = "<%=investigation.getInvestigationId()%>"
                    		<%if(case_detail.getInvestigationId() == investigation.getInvestigationId()){ %> selected <%} %>>
                    		<%=investigation.getInvestigationType() %></option>
                    <%}} %>
                  </select>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="insuredName">Insured Name 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="text" value="<%=case_detail.getInsuredName()%>" placeholder="Insured Name" 
                  	name="insuredName" id="insuredName" class="form-control" 
                  	<%if(!allow_edit) {%>disabled<%} %>>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="insuredDOD"> Date of Death
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="date" value="<%=case_detail.getInsuredDOD()%>" placeholder="Date of Death" 
                  	name="insuredDOD" id="insuredDOD" <%if(!allow_edit) {%>disabled<%} %>
                  	class="form-control">
                </div>  
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="insuredDOB"> Insured Date of Birth 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="date" value="<%=case_detail.getInsuredDOB()%>" placeholder="Date of Death" 
                  	name="insuredDOB" id="insuredDOB" class="form-control"
                  	<%if(!allow_edit) {%>disabled<%} %>>
                </div>  
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="sumAssured">Sum Assured 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="number" value="<%=case_detail.getSumAssured()%>" placeholder="Sum Assured" 
                  	name="sumAssured" id="sumAssured" class="form-control" 
                  	<%if(!allow_edit) {%>disabled<%} %>>
                </div>
              </div>
              
              <div class="form-group selectDiv">
                <label class="col-md-4 control-label" for="msgIntimationType">Select Intimation Type 
                	<span class="text-danger">*</span></label>
                <div class="col-md-8">
                  <select name="msgIntimationType" id="msgIntimationType" class="form-control" 
                  	tabindex="-1" <%if(!allow_edit) {%>disabled<%} %>>
                    <option value="-1" disabled>Select</option>
                    <%if(intimationTypeList != null){
                    	for(IntimationType intimation: intimationTypeList){%>
                    	<option value = "<%=intimation.getIntimationType()%>"
                    		<%if(intimation.getIntimationType().equals(case_detail.getIntimationType())) {%>
                    		selected <%} %>>
                    	<%=intimation.getIntimationType() %></option>
                    <%}} %>
                  </select>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="claimantCity">Claimant City 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <select name="claimantCity" id="claimantCity" class="form-control" tabindex="-1"
                  	<%if(!allow_edit) {%>disabled<%} %>>
                  	 <option value="-1" selected disabled>Select</option>
                  	 <%if(location_list!=null){ 
                  	  for(Location location : location_list){%>  
                  	  <option value=<%=location.getLocationId()%> data-state = <%=location.getState() %>
                  	  	data-zone = <%=location.getZone() %>
                  	  	<%if(location.getLocationId() == case_detail.getLocationId()) {%>selected<%} %>
                  	  	><%=location.getCity()%></option>
                  	 <%}} %>
                  	</select>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="claimantState">Claimant State 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="text" value="<%=case_detail.getClaimantState()%>" placeholder="Claimant State" name="claimantState" 
                  	id="claimantState" class="form-control" <%if(!allow_edit) {%>disabled<%} %>>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="claimaintZone">Claimant Zone 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="text" value="<%=case_detail.getClaimantZone()%>" placeholder="Claimant Zone" name="claimantZone" id="claimantZone" 
                  	class="form-control" <%if(!allow_edit) {%>disabled<%} %>>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="nomineeName">Nominee Name
                	<span class="text-danger">*</span>
                </label>
                <div class="col-md-8">
                  <input type="text" value="<%=case_detail.getNominee_name()%>" placeholder="Nominee Name" name="nomineeName" id="nomineeName" 
                  	class="form-control" <%if(!allow_edit) {%>disabled<%} %>>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="nomineeMob">Nominee Contact Number</label>
                <div class="col-md-8">
                  <input type="number" value="<%=case_detail.getNomineeContactNumber()%>" 
                  	placeholder="Nominee Contact Number" name="nomineeMob" id="nomineeMob" 
                  	class="form-control" <%if(!allow_edit) {%>disabled<%} %>>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="nomineeAdd">Nominee Address</label>
                <div class="col-md-8">
                  <textarea name="nomineeAdd" id="nomineeAdd" class="form-control" rows="6"
                  	<%if(!allow_edit) {%>disabled<%} %>><%=case_detail.getNominee_address()%>
               	  </textarea>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="insuredAdd">Insured Address</label>
                <div class="col-md-8">
                  <textarea name="insuredAdd" id="insuredAdd" class="form-control" rows="6"
                  	<%if(!allow_edit) {%>disabled<%} %>><%=case_detail.getInsured_address()%>
                  </textarea>
                </div>
              </div>
              
              <%if(!case_detail.getLongitude().equals("")) {%>
              <div class="mt-2 form-group selectDiv">
                <label class="col-md-4 control-label" for="longitude">Longitude 
                	<span class="text-danger">*</span></label>
                <div class="col-md-3">
                  <input name="longitude" id="longitude" class="form-control"
                  	readonly disabled value = "<%= case_detail.getLongitude()%>">
                </div>
               
                <label class="col-md-2 control-label" for="latitude">Latitude 
                	<span class="text-danger">*</span></label>
                <div class="col-md-3">
                  <input name="latitude" id="latitude" class="form-control"
                  	readonly disabled value = "<%= case_detail.getLatitude()%>">           	
            	</div>
              </div>
              <%} %>
              
              <%if(!case_detail.getCase_description().equals("")) {%>  
              <div class="form-group">
                <label class="col-md-4 control-label" for="case_description">Case Description</label>
                <div class="col-md-8">
                  <textarea name="case_description" id="case_description" class="form-control" rows="6">
                  	<%=case_detail.getCase_description()%>
                  </textarea>
                </div>
              </div>
              <%} %>
              
              <%if(!case_detail.getCapturedDate().equals("")) {%>
              <div class="form-group">
                <label class="col-md-4 control-label" for="capturedDate"> Captured Date
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="date" value="<%=case_detail.getCapturedDate()%>" 
                  	name="capturedDate" id="capturedDate" readonly disabled
                  	class="form-control">
                </div>  
              </div>
              <%} %>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="msgTitleEn">Case Status</label>
                <div class="col-md-8">
                  <input type="text" placeholder="Status" name="status" id="status" class="form-control"
                  	value = "<%= case_detail.getCaseStatus() %>"  disabled>
                </div>
              </div>
              
              <div class="form-group">
                <label class="col-md-4 control-label" for="subStatus">Case Sub-Status</label>
                <div class="col-md-8">
                  <input type="text" placeholder="Case Sub-Status" name="subStatus" id="subStatus" class="form-control"
                  	value = "<%= case_detail.getCaseSubStatus() %>"  disabled>
                </div>
              </div>
              
              <div id="uploadImageDiv">
                <div class="form-group">
                  <label class="col-md-4 control-label">Case Docs</label>
                  <div class="col-md-8 col-nopadding-l">
                    <div class="col-md-3">
                      <a href="javascript:void(0);">
                        <div class="uploadFileDiv">
                          <span data-imgID="imgMsgEnLbl_1" data-ID="imgMsgEn_1" id="enLblDelBtn_1" class="delete_btn" data-linkID="link_msgImgEn_1" data-toggle="tooltip" data-toggle="tooltip" title="Remove">
                            <i class="fa fa-remove"></i>
                          </span>
                          <img src="${pageContext.request.contextPath}/resources/uploads/default_img.png" class="imgMsgEnLbl" id="imgMsgEnLbl_1" style="height:height:120px;width: 100%;" data-src="#" data-toggle="tooltip" data-toggle="tooltip" title="Click to upload Image 1" />
                        </div>
                        <input type="file" onchange="displayUploadImg(this, 'imgMsgEnLbl_1', 'enLblDelBtn_1', 'link_msgImgEn_1');" name="imgMsgEn_1" id="imgMsgEn_1" class="placeImg" accept="image/*" />
                       </a>
                    </div>
                    <div class="col-md-3">
                      <a href="javascript:void(0);">
                        <div class="uploadFileDiv">
                          <span data-imgID="imgMsgEnLbl_2" data-ID="imgMsgEn_2" id="enLblDelBtn_2" class="delete_btn" data-linkID="link_msgImgEn_2" data-toggle="tooltip" data-toggle="tooltip" title="Remove">
                            <i class="fa fa-remove"></i>
                          </span>
                          <img src="${pageContext.request.contextPath}/resources/uploads/default_img.png" class="imgMsgEnLbl" id="imgMsgEnLbl_2" style="height:height:120px;width: 100%;" data-src="#" data-toggle="tooltip" data-toggle="tooltip" title="Click to upload Image 2" />
                        </div>
                        <input type="file" onchange="displayUploadImg(this, 'imgMsgEnLbl_2', 'enLblDelBtn_2', 'link_msgImgEn_2');" name="imgMsgEn_2" id="imgMsgEn_2" class="placeImg" accept="image/*" />
                      </a>
                    </div>
                    
                    <div class="col-md-3">
                      <a href="javascript:void(0);">
                        <div class="uploadFileDiv">
                          <span data-imgID="imgMsgEnLbl_3" data-ID="imgMsgEn_3" id="enLblDelBtn_3" class="delete_btn" data-linkID="link_msgImgEn_3" data-toggle="tooltip" data-toggle="tooltip" title="Remove">
                            <i class="fa fa-remove"></i>
                          </span>
                          <img src="${pageContext.request.contextPath}/resources/uploads/default_img.png" class="imgMsgEnLbl" id="imgMsgEnLbl_3" style="height:height:120px;width: 100%;" data-src="#" data-toggle="tooltip" data-toggle="tooltip" title="Click to upload Image 3" />
                        </div>
                        <input type="file" onchange="displayUploadImg(this, 'imgMsgEnLbl_3', 'enLblDelBtn_3', 'link_msgImgEn_3');" name="imgMsgEn_3" id="imgMsgEn_3" class="placeImg" accept="image/*" />
                       </a>
                    </div>
                    
                    <div class="col-md-3">
                      <a href="javascript:void(0);">
                        <div class="uploadFileDiv">
                          <span data-imgID="imgMsgEnLbl_4" data-ID="imgMsgEn_4" id="enLblDelBtn_4" class="delete_btn" data-linkID="link_msgImgEn_4" data-toggle="tooltip" data-toggle="tooltip" title="Remove">
                            <i class="fa fa-remove"></i>
                          </span>
                          <img src="${pageContext.request.contextPath}/resources/uploads/default_img.png" class="imgMsgEnLbl" id="imgMsgEnLbl_4" style="height:height:120px;width: 100%;" data-src="#" data-toggle="tooltip" data-toggle="tooltip" title="Click to upload Image 4" />
                        </div>
                        <input type="file" onchange="displayUploadImg(this, 'imgMsgEnLbl_4', 'enLblDelBtn_4', 'link_msgImgEn_4');" name="imgMsgEn_4" id="imgMsgEn_4" class="placeImg" accept="image/*" />
                        </a>
                    </div>  
              	</div>
              </div>
              
              <%if(!case_detail.getImageFilePath().equals("")) {%>
	              <div class="form-group">
	       		  	<label class="col-md-4 control-label">Image</label>
	           		<div class="col-md-8">
	                	<img src = "<%= Config.upload_url + case_detail.getImageFilePath() %>">
	              	</div>
	              </div>                    
              <%} %>
	              
              <%if(!case_detail.getAudioFilePath().equals("")) {%>
	              <div class="form-group">
	       		  	<label class="col-md-4 control-label">Audio</label>
	           		<div class="col-md-8">
	                	<audio controls id="caseAudio">
							<source src="<%= Config.upload_url + case_detail.getAudioFilePath() %>">	
                		</audio>
	              	</div>
	              </div>                    
              <%} %>
              
              <%if(!case_detail.getVideoFilePath().equals("")) {%>
	              <div class="form-group">
	       		  	<label class="col-md-4 control-label">Video</label>
	           		<div class="col-md-8">
	                	<video controls id="caseVideo" width="320" height="240">
	                		<source src="<%= Config.upload_url + case_detail.getVideoFilePath() %>">
	                	</video>
	              	</div>
	              </div>                    
              <%} %>
	              <div class="mt-2 form-group selectDiv">
		                <label class="col-md-4 control-label" for="fromRole">From Role Name 
		                	<span class="text-danger">*</span></label>
		                <div class="col-md-3">
		                  <input name="fromRole" id="fromRole" class="form-control"
		                  	readonly disabled value = "<%= case_detail.getAssignerRole()%>">
		                    
		                </div>
	                
		                <label class="col-md-2 control-label" for="fromId">From User 
		                	<span class="text-danger">*</span></label>
		                <div class="col-md-3">
		                  <input name="fromId" id="fromId" class="form-control"
		                  	readonly disabled value = "<%= case_detail.getAssignerName()%>">           	
		            	</div>
	                
	              </div>
	              
	              <div class="form-group">
	                <label class="col-md-4 control-label" for="fromStatus">Assigner Status
	                	<span class="text-danger">*</span>
	                </label>
	                <div class="col-md-8">
	                  <input type="text" value="<%=case_detail.getApprovedStatus()%>" 
	                  	name="fromStatus" id="fromStatus" class="form-control" readonly disabled>
	                </div>
	              </div>	              
	                           
	              <div class="form-group">
	                <label class="col-md-4 control-label" for="fromRemarks">Remarks</label>
	                <div class="col-md-8">
	                  <textarea name="fromRemarks" id="fromRemarks" class="form-control" rows="6"
	                  	disabled readonly><%=case_detail.getAssignerRemarks() %></textarea>
	                </div>
              	 </div>
              	 
		         <div class="form-group selectDiv" id = "case-closure">
	                <label class="col-sm-4 control-label" for="toRole">Select Role Name 
	                	<span class="text-danger">*</span></label>
	                <div class="col-sm-3">
	                  <select name="toRole" id="toRole" class="form-control" tabindex="-1"
	                  	>
	                    <option value="-1" selected disabled>Select</option>
	                     <%if(userRole != null){
	                    	for(UserRole userRoleLists: userRole){%>
	                    	<option value = "<%=userRoleLists.getRole_code()%>">
	                    		<%=userRoleLists.getRole() %></option>
	                    <%}} %> 
	                  </select>
	                </div>
                
	                <label class="col-md-2 control-label" for="toId">Select User 
	                	<span class="text-danger">*</span></label>
	                <div class="col-md-3">
	                  <select name="toId" id="toId" class="form-control">
	                  	<option value = '-1' selected disabled>Select</option>
	                  </select>
	            	</div>
              	 </div>
              	 
	             <div class="form-group selectDiv">
	                <label class="col-md-4 control-label" for="toStatus">Case Status 
	                	<span class="text-danger">*</span></label>
	                <div class="col-md-2">
	                  <select name="toStatus" id="toStatus" class="form-control" 
	                  	tabindex="-1">
	                    <option value="-1" disabled>Select</option>
	                    <option value = "Approved">Approved</option>
	                    <%if(allow_reopen) {%>
	                    <option value = "Reopen">Reopen</option>
	                    <%} %>
	                    <%if(allow_closure) {%>
	                    <option value = "Closed">Closure</option>
	                    <%} %> 
	                  </select>
	                </div>
	                            
	                <div class="form-group selectDiv" id ="case-SubStatus">
	                 <label class="col-md-1 control-label" for="caseSubStatus">Case Sub-status 
		                	<span class="text-danger">*</span></label>
		                <div class="col-md-2">
		                  <select name="caseSubStatus" id="caseSubStatus" class="form-control">
		                  	<option value = '-1' selected disabled>Select</option>             	
		                  	<option value = "Clean">Clean</option>
		                  	<option value = "Not-Clean">Not-Clean</option>
		                  	<option value = "PIV Stoppped">PIV Stoppped</option>
		                  </select>
		            	</div>
	            	</div>
	            	
            	 	<div class="form-group selectDiv" id ="Not-CleanCategory">
	                 	<label class="col-md-4 control-label" for="NotCleanCategory">Not Clean Category 
		                	<span class="text-danger">*</span></label>
		                <div class="col-md-2">
		                  <select name="NotCleanCategory" id="NotCleanCategory" class="form-control">
		                  	<option value = '-1' selected disabled>Select</option>		                  	
		                  	<option value = 'Death Prior Application'> Death Prior Application</option>
		                  	<option value = 'Medical Non-Disclosure'> Medical Non-Disclosure</option>
		                  	<option value = 'Address not traceable'>Address not traceable</option>
		                  	<option value = 'Mis-statement of Age'>Mis-statement of Age</option>
		                  	<option value = 'Mis-statement of Income / Occupation'>Mis-statement of Income / Occupation</option>
		                  	<option value = 'Personal Habit non-disclosure'>Personal Habit non-disclosure</option>
		                  	<option value = 'Others'>Others</option> 
		                  </select>
		            	</div>
	            	</div>
	            		  
   	             </div>
   	             	              
              	<div class="form-group">
	                <label class="col-md-4 control-label" for="toRemarks">Remarks</label>
	                <div class="col-md-8">
	                  <textarea name="toRemarks" id="toRemarks" class="form-control" rows="6"></textarea>
	                </div>
              	</div>
              </div>
              
              <!--  Footer -->
              <%if((allow_assign && !allow_edit )|| allow_closure) {%>
              <div class="box-footer">
                <div class="row">
                  <div class="col-md-offset-4 col-md-8">
                    <button class="btn btn-info" id="assignmessagesubmit" type="button">
                    	Assign Case
                   	</button>
                    <button class="btn btn-danger" onClick="return clearForm();" type="button">Clear</button>
                  </div>
                </div>
              </div>
              <%}else if(allow_edit){ %>
              <div class="box-footer">
                <div class="row">
                  <div class="col-md-offset-4 col-md-8">
                    <button class="btn btn-info" id="editmessagesubmit" type="button">
                    	Update Case
                   	</button>
                    <button class="btn btn-danger" onClick="return clearForm();" type="button">Clear</button>
                  </div>
                </div>
              </div> 
              <%} %>
                        
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script>
$("document").ready(function(){
	$("#Not-CleanCategory").hide();
	$("#case-SubStatus").hide();
	
	
	$("#claimantCity").change(function(){
		$("#claimantState").val($("#claimantCity option:selected").data("state"));
		$("#claimantZone").val($("#claimantCity option:selected").data("zone"));
	});
	
	$("#claimantCity").trigger("change");
	
	$("#toStatus").change(function(){
		if($(this).val() == "Closed")
		{
			$("#case-closure").hide();
			$("#case-SubStatus").show();
		}
		else
		{
			$("#case-closure").show();
			$("#case-SubStatus").hide();
			$("#Not-CleanCategory").hide();
		}
		
	});

	$("#caseSubStatus").change(function(){
		if($(this).val() == "Not-Clean")
		{
			$("#Not-CleanCategory").show();
		}
		else
		{
			$("#Not-CleanCategory").hide();
		}
		
	});
	
});
</script>

<script>
$("#assignmessagesubmit").click(function()
{
	//Validation for Case Closure
	var caseId = $( '#edit_message_form #caseId' ).val();
	var toStatus = $( '#edit_message_form #toStatus' ).val();
    var toRemarks = $( '#edit_message_form #toRemarks').val().trim();
    var caseSubStatus = $( '#edit_message_form #caseSubStatus').val();
    var NotCleanCategory = $( '#edit_message_form #NotCleanCategory').val(); 
    
    var toId = "";
    var toRole = "";
    var validFlag = 1;
    
    if(toStatus == null)
   	{
   		toastr.error("Kindly select status", "Error");
   		validFlag = 0;
   	}
    
    
    if(toStatus != "Closed")
   	{
	    toId = $( '#edit_message_form #toId' ).val();
	    toRole = $( '#edit_message_form #toRole' ).val();
   	
	    if(toId == null)
	   	{
	   		toastr.error("Kindly select user", "Error");
	   		validFlag = 0;
	   	}
	    
	    if(toRole == null)
	   	{
	   		toastr.error("Kindly select User Role", "Error");
	   		validFlag = 0;
	   	}
   	}
    else if(toStatus == "Closed")
    {	
   		toId = $( '#edit_message_form #toId' ).val();
	    toRole = $( '#edit_message_form #toRole' ).val();
	    
	    if(caseSubStatus == null)
	   	{
	   		toastr.error("Kindly select Case Sub-status", "Error");
	   		validFlag = 0;
	   		   		
	   	}
	    else if(caseSubStatus == 'Not-Clean' && NotCleanCategory == null)
		{
	   		toastr.error("Kindly select Not-clean category", "Error");
	   		validFlag = 0;	   		
		}
   	}
    
    if(toStatus == "Rejected" && toRemarks == "")
   	{
   		toastr.error("Kindly enter Rejection reason");
   		validFlag = 0;
   	}
    
    
    if(validFlag == 0)
   	{
		return false;    	
   	}
    
    $("#assignmessagesubmit").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
    $("#assignmessagesubmit").prop('disabled', true);
    $('#assignmessagesubmit').css("opacity",".5");
    
    
    $.ajax({
	    type: "POST",
	    url: 'assignCase',
	    data:{
	    	"toId"            : toId, 
	    	"toRole"          : toRole, 
	    	"toStatus"        : toStatus, 
	    	"toRemarks"       : toRemarks, 
	    	"caseId"          : caseId,
	    	"caseSubStatus"   : caseSubStatus,
	    	"NotCleanCategory": NotCleanCategory},
	    success:function(message)
	    {
	    	$("#editmessagesubmit").html('Assign Case');
	        $("#editmessagesubmit").prop('disabled', false);
	        $('#editmessagesubmit').css("opacity","");
	        
	    	if(message == "****")
	    		{
		    		toastr.success("Case assigned successfully", "Success");
		    		location.href = "${pageContext.request.contextPath}/message/pending_message";
		    		return;
	    		}
	    	else
	    		{
	    			toastr.error(message,"Error");
    				return;
	    		}
	    }
    });
	
});


</script>

<script type="text/javascript">
  $("#editmessagesubmit").click(function(){
    
	var policyNumber   = $( '#edit_message_form #policyNumber' ).val();
    var msgCategory    = $( '#edit_message_form #msgCategory' ).val();
    var insuredName    = $( '#edit_message_form #insuredName' ).val();
    var insuredDOD     = $( '#edit_message_form #insuredDOD' ).val();
    var insuredDOB     = $( '#edit_message_form #insuredDOB' ).val();
    var sumAssured     = $( '#edit_message_form #sumAssured' ).val();
    var msgIntimationType  = $( '#edit_message_form #msgIntimationType' ).val();    
    var locationId     = $( '#edit_message_form #claimantCity' ).val();
    var claimantZone   = $( '#edit_message_form #claimantZone' ).val();
    var claimantState  = $( '#edit_message_form #claimantState' ).val();
    var subStatus      = $( '#edit_message_form #subStatus' ).val();
    var nomineeName    = $( '#edit_message_form #nomineeName' ).val();
    var nomineeMob     = $( '#edit_message_form #nomineeMob' ).val();
    var nomineeAdd     = $( '#edit_message_form #nomineeAdd' ).val();
    var insuredAdd     = $( '#edit_message_form #insuredAdd' ).val();
    var caseId         = $( '#edit_message_form #caseId' ).val();
    var toId           = $( '#edit_message_form #toId' ).val();
    var toRole         = $( '#edit_message_form #toRole' ).val();
    var toStatus       = $( '#edit_message_form #toStatus' ).val();
    var toRemarks      = $( '#edit_message_form #toRemarks' ).val();
    var caseSubstatus  = "";
    var NotCleanCategory = ""; 
    
    var currentDate        = new Date();
    var insuredDateOfBirth = new Date(insuredDOB);
    var insuredDateOfDeath = new Date(insuredDOD);
    
    $('#policyNumber').removeClass('has-error-2');
    $("#msgCategory").removeClass('has-error-2');
    $("#insuredName").removeClass('has-error-2');
    $("#insuredDOD").removeClass('has-error-2');
    $("#insuredDOB").removeClass('has-error-2');
    $("#sumAssured").removeClass('has-error-2');
    $("#msgIntimationType").removeClass('has-error-2');
    $("#claimantCity").removeClass('has-error-2');
    $("#claimantZone").removeClass('has-error-2');
    $("#claimantState").removeClass('has-error-2');
    $("#nomineeName").removeClass('has-error-2');
    $("#nomineeAdd").removeClass('has-error-2');
    $("#insuredAdd").removeClass('has-error-2');
    $("#toRole").removeClass('has-error-2');
    $("#toId").removeClass('has-error-2');
    $("#caseSubstatus").removeClass('has-error-2');
    
    var errorFlag = 0;
   
    if(toStatus != "Closed")
    {   
    	caseSubstatus = "";
    	NotCleanCategory = "";
	    if(toId == null)
	    {
	        toastr.error('Please select User','Error');
	        $("#toId").addClass('has-error-2');
	        $("#toId").focus();
	        errorFlag = 1;
	    }
	    if(toRole == null)
	    {
	        toastr.error('Role Name cannot be empty','Error');
	        $("#toRole").addClass('has-error-2');
	        $("#toRole").focus();
	        errorFlag = 1;
	    }
	    
    }
    else if(toStatus == "Closed")
   	{
    	toRole = "";
   		toId   = "";
   		caseSubstatus  = $( '#edit_message_form #caseSubstatus').val();
   	    NotCleanCategory = $( '#edit_message_form #NotCleanCategory').val(); 
   	    
   		if(caseSubStatus == null)
	   	{
	   		toastr.error("Kindly select Case Sub-status", "Error");
	   		validFlag = 0;
	   		   		
	   	}
	    else if(caseSubStatus == 'Not-Clean' && NotCleanCategory == null)
		{
	   		toastr.error("Kindly select Not-clean category", "Error");
	   		validFlag = 0;	   		
		}
   	}
    if(insuredAdd == '')
    {
        toastr.error('Please enter Insured Address','Error');
        $("#insuredAdd").addClass('has-error-2');
        $("#insuredAdd").focus();
        errorFlag = 1;
    }
    if(!(msgIntimationType == "PIV" || msgIntimationType == "PIRV" || msgIntimationType == "LIVE"))
   	{
	    if(nomineeAdd == '')
	    {
	        toastr.error('Please enter Nominee Address','Error');
	        $("#nomineeAdd").addClass('has-error-2');
	        $("#nomineeAdd").focus();
	        errorFlag = 1;
	    }
	    if(nomineeName == '')
	    {
	        toastr.error('Please enter Nominee Name','Error');
	        $("#nomineeName").addClass('has-error-2');
	        $("#nomineeName").focus();
	        errorFlag = 1;
	    }
	    if(nomineeMob)
	   	{
	    	if(nomineeMob.length != 10)
    		{
		    	$('#nomineeMob').addClass('has-error-2');
		        $('#nomineeMob').focus();
		        validflag = 0;
		        toastr.error("Nominee Mobile number should be of 10 digits","Error");
    		}
	   	}
	    if(insuredDOD == '')
	    {
	      	toastr.error('Insured Date of Death cannot be empty','Error');
	      	$("#insuredDOD").addClass('has-error-2');
	      	$("#insuredDOD").focus();
	      	errorFlag = 1;
	    }
   	}
    if(claimantState == '')
    {
      toastr.error('Claimant State cannot be empty','Error');
      $("#claimantState").addClass('has-error-2');
      $("#claimantState").focus();
      errorFlag = 1;
    }
    if(claimantZone == '')
    {
      toastr.error('Claimaint Zone Cannot be empty','Error');
      $("#claimantZone").addClass('has-error-2');
      $("#claimantZone").focus();
      errorFlag = 1;
    }
    if(claimantCity == null)
    {
		toastr.error('Claimant City cannot be empty','Error');
		$("#claimantCity").addClass('has-error-2');
		$("#claimantCity").focus();
		errorFlag = 1;
    }
    if(msgIntimationType == '')
    {
        toastr.error('Please select Intimation Type','Error');
        $("#msgIntimationType").addClass('has-error-2');
        $("#msgIntimationType").focus();
        errorFlag = 1;
    }
    if(sumAssured == '')
    {
        toastr.error('Sum Assured cannot be empty','Error');
        $("#sumAssured").addClass('has-error-2');
        $("#sumAssured").focus();
        errorFlag = 1;
    }
    if(insuredDOB == '')
    {
      	toastr.error('Insured Date of Birth cannot be empty','Error');
      	$("#insuredDOB").addClass('has-error-2');
      	$("#insuredDOB").focus();
      	errorFlag = 1;
    }
    if(insuredName == '')
    {
      	toastr.error('Please enter Insured Name','Error');
      	$("#insuredName").addClass('has-error-2');
      	$("#insuredName").focus();
      	errorFlag = 1;
    }
    if(insuredDateOfBirth >= currentDate)
   	{
    	toastr.error("Insured Date of Birth cannot be greater than equal to Today's Date",'Error');
      	$("#insuredDOB").addClass('has-error-2');
      	$("#insuredDOB").focus();
      	errorFlag = 1;
   	}
    if(insuredDOD != "")
   	{
	   if(insuredDateOfBirth >= insuredDateOfDeath)
	  	{
	   		toastr.error('Insured DOB cannot be greater than equal to Insured DOD','Error');
	     	$("#insuredDOD").addClass('has-error-2');
	     	$("#insuredDOD").focus();
	     	errorFlag = 1;
	  	}
	   if(insuredDateOfDeath > currentDate)
	  	{
	   		toastr.error("Insured DOD cannot be greater than Today's Date",'Error');
	     	$("#insuredDOD").addClass('has-error-2');
	     	$("#insuredDOD").focus();
	     	errorFlag = 1;
	  	}
   	}
    if(msgCategory == '')
    {
        toastr.error('Investigation Category cannot be empty','Error');
        $("#msgCategory").addClass('has-error-2');
        $("#msgCategory").focus();
        errorFlag = 1;
    }
    if(policyNumber == '')
    {
    	toastr.error('Policy Number cannot be empty','Error');
    	$('#policyNumber').addClass('has-error-2');
    	$('#policyNumber').focus();
    	errorFlag = 1;
    }
    
    if(errorFlag == 1)
    	return false;
        
    $("#editmessagesubmit").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
    $("#editmessagesubmit").prop('disabled', true);
    $('#editmessagesubmit').css("opacity",".5");

    
    var formdata = {
    		'policyNumber'     : policyNumber,
   	    	'msgCategory'      : msgCategory,
   	       	'insuredDOD'       : insuredDOD,
   	      	'insuredDOB'       : insuredDOB,
   	      	'insuredName'      : insuredName,
          	'sumAssured'       : sumAssured,
          	'msgIntimationType': msgIntimationType,
          	'locationId'       : locationId,
	       	'nomineeName'      : nomineeName,
	       	'nomineeMob'       : nomineeMob,
	       	'nomineeAdd'       : nomineeAdd,
	       	'insuredAdd'       : insuredAdd,
	       	'toRole'           : toRole,
	       	'toStatus'         : toStatus,
        	'toRemarks'        : toRemarks,
        	'caseId'           : caseId,
        	'caseSubStatus'    : caseSubstatus,
        	'NotCleanCategory' : NotCleanCategory,
        	"toId"             : toId 
        	};
    console.log(formdata);
    
    $.ajax({
	    type: "POST",
	    url: 'updateMessageDetails',
	    data: formdata,

	    success: function( data )
	    {
	        $("#editmessagesubmit").html('Update Case');
	        $("#editmessagesubmit").prop('disabled',false );
	        $('#editmessagesubmit').css("opacity","");
	  	  if(data == "****")
	  	  {
	         location.href = "${pageContext.request.contextPath}/message/pending_message";
	  	  }
	  	  else
	         toastr.error( data,'Error' );
	    } 
	    
	  });
  });

function clearForm(){
  $( '#small_modal' ).modal();
  $( '#sm_modal_title' ).html( 'Are you Sure?' );
  $( '#sm_modal_body' ).html( 'Do you really want to clear this form data?' );
  $( '#sm_modal_footer' ).html( '<button type="button" class="btn dark btn-outline" data-dismiss="modal">Cancel</button><button type="button" id="continuemodal_cl" class="btn green">Yes</button>' );
  $( '#continuemodal_cl' ).click( function() {
	  $("#toRole").val($("#toRole option:first").val());
	  $("#toId").val($("#toId option:first").val());
	  $("#toRemarks").val("");
	  $('#small_modal').modal('hide');
  });
}
</script>
<script>
$("#toRole").change(function(){
	console.log($("#toRole option:selected").val());
	var roleCode = $(this).val();
	$("#toId option").each(function(){
		if($(this).val() != '-1')
			$(this).remove();
	});
	$.ajax({
	    type: "POST",
	    url: 'getUserByRole',
	    data: {"role_code": roleCode},
	    success: function(userList)
	    {
	    	console.log(userList);
	  		var options = "";
	    	for(i = 0; i < userList.length ; i++)
	  			{
	  				options += "<option value ='" + userList[i].username + "'>" + userList[i].full_name + "</option>";  
	  			}
	  		console.log(options);
	    	$("#toId").append(options);
	    }
});

});
</script>

<script>
$("#toStatus").change(function(){
	console.log($("#toStatus option:selected").val());
	var status = $(this).val();
	
	if(status == "Closed")
		return;
	$.ajax({
	    type: "POST",
	    url: 'getUserRoleBystatus',
	    data: {"status": status},
	    success: function(roleList)
	    {
	    	console.log(roleList);
	  		var options = "";
	    	for(i = 0; i < roleList.length ; i++)
	  			{
	  				options += "<option value ='" + userList[i].username + "'>" + userList[i].full_name + "</option>";  
	  			}
	    	$("#toId").append(options);
	    }
});

});
</script>
