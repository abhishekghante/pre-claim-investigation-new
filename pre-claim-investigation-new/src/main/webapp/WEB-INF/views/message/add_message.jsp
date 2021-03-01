<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.preclaim.models.Location"%>
<%@page import="com.preclaim.models.InvestigationType"%>
<%@page import="com.preclaim.models.IntimationType"%>
<%@page import="com.preclaim.models.UserDetails" %>
<%@page import="com.preclaim.models.UserRole"%>

<%
List<String>user_permission=(List<String>)session.getAttribute("user_permission");
List<InvestigationType> investigationList = (List<InvestigationType>) session.getAttribute("investigation_list");
session.removeAttribute("investigation_list");
List<IntimationType> intimationTypeList = (List<IntimationType>) session.getAttribute("intimation_list");
session.removeAttribute("intimation_list");
List<Location> location_list = (List<Location>) session.getAttribute("location_list");
session.removeAttribute("location_list");
List<UserRole> userRole =(List<UserRole>)session.getAttribute("userRole");
session.removeAttribute("userRole");
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
      <form novalidate id="add_message_form" role="form" method="post" class="form-horizontal" enctype="multipart/form-data">
        <div class="box-body">
          <div class="row">
            <div class="col-sm-10 col-md-10 col-xs-12"> 
			  <div class="form-group">
                <label class="col-md-4 control-label" for="msgTitleEn">Policy Number 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="text" placeholder="Policy Number" name="policyNumber" id="policyNumber" 
                  	class="form-control">
                </div>
              </div>
              <div class="form-group selectDiv">
                <label class="col-md-4 control-label" for="msgCategory">Select Investigation Category 
                	<span class="text-danger">*</span></label>
                <div class="col-md-8">
                  <select name="msgCategory" id="msgCategory" class="form-control" tabindex="-1">
                    <option value="-1" selected disabled>Select</option>
                    <%if(investigationList != null){
                    	for(InvestigationType investigation: investigationList){%>
                    	<option value = "<%=investigation.getInvestigationId()%>"><%=investigation.getInvestigationType() %></option>
                    <%}} %>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-4 control-label" for="insuredName">Insured Name 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="text" placeholder="Insured Name" name="insuredName" id="insuredName" 
                  	class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-4 control-label" for="insuredDOD"> Date of Death
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="date" placeholder="Date of Death" name="insuredDOD" id="insuredDOD" 
                  	class="form-control">
                </div>  
              </div>
              <div class="form-group">
                <label class="col-md-4 control-label" for="insuredDOB"> Insured Date of Birth 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="date" placeholder="Date of Death" name="insuredDOB" id="insuredDOB" 
                  	class="form-control">
                </div>  
              </div>
              <div class="form-group">
                <label class="col-md-4 control-label" for="sumAssured">Sum Assured 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  <input type="number" placeholder="Sum Assured" name="sumAssured" id="sumAssured" 
                  	class="form-control">
                </div>
              </div>
              <div class="form-group selectDiv">
                <label class="col-md-4 control-label" for="msgIntimationType">Select Intimation Type 
                	<span class="text-danger">*</span></label>
                <div class="col-md-8">
                  <select name="msgIntimationType" id="msgIntimationType" class="form-control" tabindex="-1">
                    <option value="-1" selected disabled>Select</option>
                    <%if(intimationTypeList != null){
                    	for(IntimationType intimation: intimationTypeList){%>
                    	<option value = "<%=intimation.getIntimationType()%>"><%=intimation.getIntimationType() %></option>
                    <%}} %>
                  </select>
                </div>
              </div>
              <div class="form-group selectDiv">
                <label class="col-md-4 control-label" for="claimantCity">Claimant City 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  	<select name="claimantCity" id="claimantCity" class="form-control" tabindex="-1">
                  	 <option value="-1" selected disabled>Select</option>
                  	 <%if(location_list!=null){ 
                  	  for(Location location : location_list){%>  
                  	  <option value=<%=location.getLocationId()%> data-state = <%=location.getState() %>
                  	  	data-zone = <%=location.getZone() %>><%=location.getCity()%></option>
                  	 <%}} %>
                  	</select>
                </div>
              </div>
              <div class="form-group selectDiv">
                <label class="col-md-4 control-label" for="claimantState">Claimant State 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  	<input type = "text" name="claimantState" id="claimantState" class="form-control" readonly disabled>
                </div>
              </div>
              <div class="form-group selectDiv">
                <label class="col-md-4 control-label" for="claimaintZone">Claimant Zone 
                	<span class="text-danger">*</span>
               	</label>
                <div class="col-md-8">
                  	<input type = "text" name="claimantZone" id="claimantZone" class="form-control" readonly disabled>
                </div>
              </div>           
              <div class="form-group">
                <label class="col-md-4 control-label" for="nomineeName">Nominee Name
                	<span class="text-danger">*</span>
                </label>
                <div class="col-md-8">
                  <input type="text" placeholder="Nominee Name" name="nomineeName" id="nomineeName" 
                  	class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-4 control-label" for="nomineeMob">Nominee Contact Number</label>
                <div class="col-md-8">
                  <input type="number" placeholder="Nominee Contact Number" name="nomineeMob" id="nomineeMob" 
                  	class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-4 control-label" for="nomineeAdd">Nominee Address</label>
                <div class="col-md-8">
                  <textarea name="nomineeAdd" id="nomineeAdd" class="form-control" rows="6"></textarea>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-4 control-label" for="insuredAdd">Insured Address</label>
                <div class="col-md-8">
                  <textarea name="insuredAdd" id="insuredAdd" class="form-control" rows="6"></textarea>
                </div>
              </div>
              <div class="form-group selectDiv">
                <label class="col-md-4 control-label" for="roleName">Select Role Name 
                	<span class="text-danger">*</span></label>
                <div class="col-md-3">
                  <select name="roleName" id="roleName" class="form-control" tabindex="-1">
                    <option value="-1" selected disabled>Select</option>
                     <%if(userRole != null){
                    	for(UserRole userRoleLists: userRole){%>
                    	<option value = "<%=userRoleLists.getRole_code()%>"><%=userRoleLists.getRole() %></option>
                    <%}} %> 
                  </select>
                </div>
                
                <label class="col-md-2 control-label" for="userRole">Select User 
                	<span class="text-danger">*</span></label>
                <div class="col-md-3">
                  <select name="assigneeId" id="assigneeId" class="form-control">
                  	<option value = '-1' selected disabled>Select</option>
                  </select>
                </div>
                
              </div>
              <!--  Footer -->
              <div class="box-footer">
                <div class="row">
                  <div class="col-md-offset-4 col-md-8">
                    <button class="btn btn-info" id="addmessagesubmit" type="submit">Broadcast</button>
                    <button class="btn btn-danger" onClick="return clearForm();" type="button">Clear</button>
                  </div>
                </div>
              </div>
                         
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript">

$("document").ready(function(){
	
	$("#claimantCity").change(function(){
		$("#claimantState").val($("#claimantCity option:selected").data("state"));
		$("#claimantZone").val($("#claimantCity option:selected").data("zone"));
	});
		
});


function displayUploadImg(input, PlaceholderID, deleteID, linkID) {
  if (input.files && input.files[0]) {
    var upfile = input.files[0];
    var imagefile = upfile.type;
    var match= ["image/jpeg","image/png","image/jpg"];
    if(!((imagefile==match[0]) || (imagefile==match[1]) || (imagefile==match[2]))){
        alert('Please select a valid image file (JPEG/JPG/PNG).');
        $("#"+input.id).val('');
        return false;
    }
    var file_size = upfile.size/1024/1024;
    if(file_size < 5){
      var reader = new FileReader();
      reader.onload = function (e) {
        $('#'+PlaceholderID)
            .attr('src', e.target.result)
            .width('100%')
            .height(120);
        };
      reader.readAsDataURL(upfile);
      $('#'+deleteID).show();
      $('#'+linkID).show();
    }else{
      alert('File too large. File must be less than 5 MB.');
      $("#"+input.id).val('');
      return false;
    }
  }
}
  $("#add_message_form").on('submit', function(e){
    e.preventDefault();
    var policyNumber   = $( '#add_message_form #policyNumber').val();
    var msgCategory    = $( '#add_message_form #msgCategory').val();
    var insuredName    = $( '#add_message_form #insuredName').val();
    var insuredDOD     = $( '#add_message_form #insuredDOD').val();
    var insuredDOB     = $( '#add_message_form #insuredDOB').val();
    var sumAssured     = $( '#add_message_form #sumAssured').val();
    var msgIntimationType  = $( '#add_message_form #msgIntimationType').val();    
    var claimantCity   = $( '#add_message_form #claimantCity option:selected').val();
    var claimantZone   = $( '#add_message_form #claimantZone').val();
    var claimantState  = $( '#add_message_form #claimantState').val();
    var nomineeName    = $( '#add_message_form #nomineeName').val();
    var nomineeMob     = $( '#add_message_form #nomineeMob').val();
    var nomineeAdd     = $( '#add_message_form #nomineeAdd').val();
    var insuredAdd     = $( '#add_message_form #insuredAdd').val();
    var roleName       = $( '#add_message_form #roleName').val();
    var assigneeId       = $( '#add_message_form #assigneeId').val();
    
    var currentDate = new Date();
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
    $("#roleName").removeClass('has-error-2');
    $("#assigneeId").removeClass('has-error-2');
    
    var errorFlag = 0;
    
    if(assigneeId == null)
    {
        toastr.error('Please select User','Error');
        $("#assigneeId").addClass('has-error-2');
        $("#assigneeId").focus();
        errorFlag = 1;
    }
    if(roleName == null)
    {
        toastr.error('Role Name cannot be empty','Error');
        $("#roleName").addClass('has-error-2');
        $("#roleName").focus();
        errorFlag = 1;
    }
    if(insuredAdd == '')
    {
        toastr.error('Please enter Insured Address','Error');
        $("#insuredAdd").addClass('has-error-2');
        $("#insuredAdd").focus();
        errorFlag = 1;
    }
    if(nomineeMob)
   	{
    	if(nomineeMob.length != 10)
		{
	    	$('#nomineeMob').addClass('has-error-2');
	        $('#nomineeMob').focus();
	        errorFlag = 1;
	        toastr.error("Nominee Mobile number should be of 10 digits","Error");
		}
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
	    if(nomineeMob == '')
	   	{
    		$('#nomineeMob').addClass('has-error-2');
	        $('#nomineeMob').focus();
	        errorFlag = 1;
	        toastr.error("Kindly enter Nominee Mobile number","Error");		
	   	}
	    if(nomineeName == '')
	    {
	        toastr.error('Please enter Nominee Name','Error');
	        $("#nomineeName").addClass('has-error-2');
	        $("#nomineeName").focus();
	        errorFlag = 1;
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
    if(insuredName == '')
    {
      	toastr.error('Please enter Insured Name','Error');
      	$("#insuredName").addClass('has-error-2');
      	$("#insuredName").focus();
      	errorFlag = 1;
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
        
    $.ajax({
	    type: "POST",
	    url: 'addMessage',
	    data: {'policyNumber':policyNumber,'msgCategory':msgCategory,'insuredName':insuredName,'insuredDOD':insuredDOB,'insuredDOB':insuredDOD,
	    	       'sumAssured':sumAssured,'msgIntimationType':msgIntimationType,'claimantCity':claimantCity,'claimantState':claimantState,
	    	       'nomineeName':nomineeName,'nomineeMob':nomineeMob,'nomineeAdd':nomineeAdd,'insuredAdd':insuredAdd, 'roleName':roleName, 'assigneeId':assigneeId},
	    beforeSend: function() {
	    	$("#addmessagesubmit").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
	        $("#addmessagesubmit").prop('disabled', true);
	        $('#add_message_form').css("opacity",".5");
	    },
	    success: function( data )
	    {
	        $("#addmessagesubmit").html('Broadcast');
	        $("#addmessagesubmit").prop('disabled',false );
	  	  if(data == "****")
	  	  {
	         toastr.success( 'Case added successfully.','Success' );
	         $("form#add_message_form").trigger("reset");            
	  	  }
	  	  else
	         toastr.error( data,'Error' );
	      $('#add_message_form').css("opacity","");
	    }
	  });
  });

function clearForm(){
  $( '#small_modal' ).modal();
  $( '#sm_modal_title' ).html( 'Are you Sure?' );
  $( '#sm_modal_body' ).html( 'Do you really want to clear this form data?' );
  $( '#sm_modal_footer' ).html( '<button type="button" class="btn dark btn-outline" data-dismiss="modal">Cancel</button><button type="button" id="continuemodal_cl" class="btn green">Yes</button>' );
  $( '#continuemodal_cl' ).click( function() {
  $("form#add_message_form").trigger("reset");
  $('#small_modal').modal('hide');
  });
}
</script>

<script>
$("#roleName").change(function(){
	console.log($("#roleName option:selected").val());
	var roleCode = $(this).val();
	$("#assigneeId option").each(function(){
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
	    	$("#assigneeId").append(options);
	    }
});

});
</script>