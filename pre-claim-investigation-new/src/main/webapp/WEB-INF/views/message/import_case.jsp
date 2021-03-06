<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@page import = "java.util.List" %>
<%@page import = "com.preclaim.config.Config" %>
<%@page import = "com.preclaim.models.ScreenDetails" %>
<%@page import = "com.preclaim.models.UserRole"%>
<%@page import="com.preclaim.models.UserDetails" %>
<%@page import = "java.io.File" %>
<%
String dirName = (String) session.getAttribute("directory");
System.out.println("error"+dirName);
List<String>user_permission=(List<String>)session.getAttribute("user_permission");
List<UserRole> userRole =(List<UserRole>)session.getAttribute("userRole");
session.removeAttribute("userRole");
ScreenDetails details = (ScreenDetails) session.getAttribute("ScreenDetails");
UserDetails user = (UserDetails) session.getAttribute("User_Login");
%>
<style type="text/css">
#imgAccount { display:none;}
</style>
<div class="row">
  <div class="col-md-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
            <i class="icon-plus"></i>
            <span class="caption-subject font-green-sharp sbold">Import Cases</span>
        </div>
        <div class="actions">
            <div class="btn-group">
              <a href="${pageContext.request.contextPath}/app_user/app_user" data-toggle="tooltip" 
              	title="Back" class="btn green-haze btn-outline btn-xs pull-right" 
              	style="margin-right: 5px;" data-original-title="Back">
                <i class="fa fa-reply"></i>
              </a>
            </div>
        </div>
      </div>
    </div>
    <div class="box box-primary">
      <!-- /.box-header -->
      <!-- form start -->

      <div class="box-body">
        <div class="row">
          <div class="col-md-12">
          <form id="import_user_form" class="form-horizontal" method = "POST" enctype="multipart/form-data">
              <div class="form-group">
                <label class="col-md-2 padding-left-5 col-xs-4 control-label">Import Data</label>
                <div class="col-md-6 padding-left-0 col-xs-6">
                  <input type="file" name="userfile" id="userfile" class="form-control" required>
                  <note>Kindly upload .xlsx file only</note>
                </div>
                
                <div class="col-md-6 text-center">
                  <div>
                  	<a style="display: inline-block;" href="../resources/uploads/Import Case.xlsx">Click to download sample "Excel" file</a>
                  </div>
                </div>
              </div>
              <div class="form-group selectDiv">
                <label class="col-md-2 control-label" for="roleName">Select Role Name 
                	<span class="text-danger">*</span></label>
                <div class="col-md-2">
                  <select name="roleName" id="roleName" class="form-control" tabindex="-1" required>
                    <option value="-1" selected>Select</option>
                     <%if(userRole != null){
                    	for(UserRole userRoleLists: userRole){%>
                    	<option value = "<%=userRoleLists.getRole_code()%>"><%=userRoleLists.getRole() %></option>
                    <%}} %> 
                  </select>
                </div>
                
                <%if(user.getAccount_type().equals("CLAMAN")){ %>
                <div id = "assigneeDetails">
	                <label class="col-md-1 control-label" for="assigneeId">Select User 
	                	<span class="text-danger">*</span></label>
	                <div class="col-md-2">
	                  <select name="assigneeId" id="assigneeId" class="form-control">
	                  	<option value = '-1' selected disabled>Select</option>
	                  </select>
	                </div>
                </div>                
                <%} %>
              </div>
              <div class="col-md-4 control-label" id="fileList">
                  <button type="button" class="btn btn-info btn-sm" name="importfile" id ="importfile" onclick="importData()">
                  	Import
                  </button>
<%--                   <%if(!(details.getSuccess_message1().equals(""))){%>
                  		<a href = "<%=Config.upload_url + "error_log.xlsx" %>" target = "_blank" 
                  			id = "error_log"><i class =" fa fa-exclamation"></i> Error Log</a>
                  	<%}%> --%>
                  	
                  <%if(!(details.getSuccess_message1().equals(""))){%>
							
                                 <% File filelist = new File(dirName);
                                 
                                        if(filelist.exists())

                                        {
                                               for(File item:filelist.listFiles())
                                               {
                                                     if(item.isFile() && item.getName().equals("error_log.xlsx"))
                                                     {

                                 %>
                                     <input type="hidden" id ="fileName" value=<%=item.getName() %>>
                                                               

                                     <a href = "#"><i class="fa fa-download pr-1"></i> Download error log</a>
                          

                                 <%
                                                     }                                             
                                               }
                                        }
                                 %>
                  	<%}%>
                  	            
                  	
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
$(document).ready(function(){
	<% details.setSuccess_message1("");%>
});


function importData()
{
	var roleName = $("#import_user_form #roleName").val();
	var importfile = $('input[type="file"]').val();
	var assigneeId =$('#assigneeId').val();
	
	var userfile = $('#userfile').val().toLowerCase();
	var userfileFileExtension = userfile.substring(userfile.lastIndexOf('.') + 1);
	
	var errorFlag = 0;

	
	if(assigneeId == null && <%=user.getAccount_type().equals("CLAMAN")%>)
	{	
		toastr.error("Kindly select User","Error");	
		errorFlag = 1;
	}
	
	if(roleName == null)
	{	
		toastr.error("Kindly select Assignee Role","Error");	
		errorFlag = 1;
	}
	
	if(userfile == "" )
	{
		toastr.error("Please select Excel file","Error");
		errorFlag = 1;
	}
	if(userfile != "" && userfileFileExtension!="xlsx")
	{	
		toastr.error("Please upload excel file in xlsx format.","Error");
		errorFlag = 1;
	}
	
	if(errorFlag == 1)
		return false;
	
	var formData = new FormData();
	var files = $('[type="file"]');

    if (files.length > 0) {
        var count = 0;
        $(files).each(function (i, value) {
            count++;
            formData.append('userfile', value.files[0]);
        });
        formData.append("roleName", roleName);
        if(assigneeId!=null){
        	formData.append("assigneeId", assigneeId);}
    }
    $("#importfile").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
    $("#importfile").prop('disabled', true);
    $('#import_user_form').css("opacity",".5");
	$.ajax({
	    type: "POST",
	    url: 'importData',
	    data: formData,
	    cache: false,
        contentType: false,
        processData: false,
	    success: function(data)
	    {
	    	$("#importfile").html('Import');
	        $("#importfile").prop('disabled', false);
	        $('#import_user_form').css("opacity","");
	  		if(data == "****")
  			{
	  		  location.reload();
  			}
	  		else
  			{
  				toastr.error(data, "Error");
  				return false;
  			}
	    }
});	

}

</script>


<!-- <script>
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
</script> -->
<script>
$("#roleName").change(function(){
	
	console.log($("#roleName option:selected").val());
	var roleCode = $(this).val();
	$("#assigneeId option").each(function(){
		if($(this).val() != '-1')
			$(this).remove();
	});
	
	if($("#msgIntimationType").val == "CDP")
		return;
	$("#assigneeId").prop("disabled",true);	
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
	    	$("#assigneeId").prop("disabled",false);
	    }
});

});
</script>
<script>
$("#fileList").on("click","a",function()
	{
		/* let filename = $(this).parent().parent().children().eq(1).text(); */
		let filename = $('#fileName').val();
		console.log(filename);
		let fileDir = $("#fileDir").val();
		window.location.href = "${pageContext.request.contextPath}/report/downloadSysFile?filename=" + 
			encodeURIComponent(filename);	
	});
	

</script>