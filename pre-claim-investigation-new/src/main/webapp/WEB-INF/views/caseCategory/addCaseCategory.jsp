<%@page import="java.util.List"%>
<%
List<String> user_permission=(List<String>)session.getAttribute("user_permission");
%>
<div class="row">
  <div class="col-md-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
          <i class="icon-plus"></i>
          <span class="caption-subject font-green-sharp sbold">Add Case Status</span>
        </div>
        <div class="actions">
          <div class="btn-group">
            <a href="${pageContext.request.contextPath}/caseStatus/pending" 
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
                  <input type="text" placeholder="Case Status" id="caseStatus" class="form-control" 
                  	name="caseStatus">
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- /.box-body -->
        <div class="box-footer">
          <div class="col-md-offset-2 col-md-10">
            <button class="btn btn-info" id="addCaseStatusSubmit" onClick="return addCaseStatus();" 
            	type="button">Add Case Status</button>
            <button class="btn btn-danger" type="reset">Clear</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript">
function addCaseStatus() {
	<%if(!user_permission.contains("caseStatus/add")){%>
		toastr.error("Access Denied","Error");
		return false;
	<%}%>

	var caseStatus = $( '#add_case_status #caseStatus' ).val(); 
	if(caseStatus == ''){
	  toastr.error('Case Status cannot be blank','Error');
	  return false;
	}
	var formdata ={'caseStatus':caseStatus};
	$.ajax({
	  type: "POST",
	  url: 'addCaseStatus',
	  data: formdata,
	  beforeSend: function() { 
	      $("#addCaseStatusSubmit").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
	      $("#addCaseStatusSubmit").prop('disabled', true);
	  },
	  success: function( data ) {
		  $("#addCaseStatusSubmit").html('Add Case Status');
	      $("#addCaseStatusSubmit").prop('disabled', false);
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