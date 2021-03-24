<%@page import="java.util.List"%>
<%@page import="com.preclaim.models.CaseStatus"%>
<%
List<String> user_permission=(List<String>)session.getAttribute("user_permission");
List<CaseStatus> case_status = (List<CaseStatus>) session.getAttribute("case_status");
session.removeAttribute("case_status");
%>
<div class="row">
  <div class="col-md-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
          <i class="icon-plus"></i>
          <span class="caption-subject font-green-sharp sbold">Add Case Category</span>
        </div>
        <div class="actions">
          <div class="btn-group">
            <a href="${pageContext.request.contextPath}/caseCategory/pending" 
            	data-toggle="tooltip" title="Back" class="btn green-haze btn-outline btn-xs pull-right" 
            	style="margin-right: 5px;" data-original-title="Back">
              <i class="fa fa-reply"></i>
            </a>
          </div>
        </div>
      </div>
    </div>
    <div class="box box-primary">
      <!-- form start -->
      <div id="message_account"></div>
      <form novalidate id="add_case_status" role="form" method="post" class="form-horizontal">
        <div class="box-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="col-md-4 control-label" for="caseStatus">Case Status <span class="text-danger">*</span></label>
                <div class="col-md-8">
                  <select id="caseStatus" class="form-control">
                  	<option value = "-1" selected disabled>Select</option>
                  	<%if(case_status != null) {
                  		for(CaseStatus item: case_status)
                  		{ 
                  	%>
                  			<option value = "item.getCaseStatus()"><%=item.getCaseStatus() %></option>
                  	<%	}
                  		}%>
                  </select>
                </div>
              </div>
            </div>
          </div>
          
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="col-md-4 control-label" for="caseCategory">Case Category <span class="text-danger">*</span></label>
                <div class="col-md-8">
                  <input type="text" placeholder="Case Category" id="caseCategory" class="form-control" 
                  	name="caseCategory">
                </div>
              </div>
            </div>
        </div>
       </div>
        <!-- /.box-body -->
        <div class="box-footer">
          <div class="col-md-offset-2 col-md-10">
            <button class="btn btn-info" id="addCaseCategorySubmit" onClick="return addCaseCategory();" 
            	type="button">Add Case Category</button>
            <button class="btn btn-danger" type="reset">Clear</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript">
function addCaseCategory() {
	<%if(!user_permission.contains("caseStatus/add")){%>
		toastr.error("Access Denied","Error");
		return false;
	<%}%>

	var caseStatus = $( '#add_case_status #caseStatus' ).val(); 
	var caseCategory = $( '#add_case_status #caseCategory' ).val();
	var errorFlag = 0;
	if(caseStatus == null)
	{
	  toastr.error('Case Status cannot be blank','Error');
	  errorFlag = 1;
	}
	if(caseCategory == "")
	{
		toastr.error('Case Category cannot be blank','Error');
	  	errorFlag = 1;
	}
	if(errorFlag == 1)
		return false;
	var formdata ={'caseStatus':caseStatus, "caseCategory" : caseCategory};
	$.ajax({
	  type: "POST",
	  url: 'addCaseCategory',
	  data: formdata,
	  beforeSend: function() { 
	      $("#addCaseCategorySubmit").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
	      $("#addCaseCategorySubmit").prop('disabled', true);
	  },
	  success: function( data ) {
		  $("#addCaseCategorySubmit").html('Add Case Status');
	      $("#addCaseCategorySubmit").prop('disabled', false);
	    if(data == "****")
	    {
	      location.reload();
	    }
	    else
	    {
	      toastr.error( data,'Error' );
	    }
	  }
	});
     
}
</script>