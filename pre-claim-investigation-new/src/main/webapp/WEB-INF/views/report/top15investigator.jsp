<%@page import="java.util.List"%>
<%
List<String> user_permission=(List<String>)session.getAttribute("user_permission");
%>
<div class="row">
  <div class="col-md-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
          <i class="icon-users font-green-sharp"></i>
          <span class="caption-subject font-green-sharp sbold">Top 15 Investigator</span>
        </div>
       <%--  <div class="actions">
          <div class="btn-group">
            <a href="${pageContext.request.contextPath}/intimationType/pending" data-toggle="tooltip" title="Back" class="btn green-haze btn-outline btn-xs pull-right" data-toggle="tooltip" title="" style="margin-right: 5px;" data-original-title="Back">
              <i class="fa fa-reply"></i>
            </a>
          </div>
        </div> --%>
      </div>
    </div>
    <div class="box box-primary">
      <!-- form start -->
      <div id="message_account"></div>
      <form novalidate id="topInvestigator" role="form" method="post" class="form-horizontal">
        <div class="box-body">
          <div class="row">
           <div class="form-group">
           
                <label class="col-md-2 control-label" for="startDate">Start date <span class="text-danger">*</span></label>
                <div class="col-md-2">
                  <input type="date" id="startDate" class="form-control" name="startDate">
                </div>
                 
                <label class="col-md-2 control-label" for="endDate">End date
                	<span class="text-danger">*</span></label>
                <div class="col-md-2">
                  <input type="date" id="endDate" class="form-control" name="endDate">
                </div>
                
              </div>
          </div>
        </div><br>
        <!-- /.box-body -->
        <div class="box-footer">
          <div class="col-md-offset-4 col-md-10">
            <button class="btn btn-info" id="downloadInvestigatorReport" type="button"><i class="fa fa-download pr-1"></i> Download</button>
            <button class="btn btn-danger" type="reset" id = "clear">Clear</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript">
$("#downloadInvestigatorReport").click(function(){
	/*
	<%if(!user_permission.contains("report/topinvestigator")){%>
		toastr.error("Access Denied","Error");
		return false;
	<%}%>
	*/
	var startDate = $('#startDate').val(); 
	var endDate = $('#endDate').val(); 
	
	var errorFlag = 0;
	if(startDate == '')
	{
		toastr.error("Start Date cannot be blank","Error");
		errorFlag = 1;
	}
	
	if(endDate == '')
	{
		toastr.error("End Date cannot be blank","Error");
		errorFlag = 1;
	}
	if(errorFlag == 1)
		return;
		
	var formdata = {'startDate':startDate, 'endDate':endDate};
	$.ajax({
	  type: "POST",
	  url: 'downloadInvestigatorReport',
	  data: formdata,
	  beforeSend: function() { 
	      $("#downloadInvestigatorReport").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
	      $("#downloadInvestigatorReport").prop('disabled', true);
	  },
	  success: function( data ) {
		  $("#downloadInvestigatorReport").html('<i class="fa fa-download pr-1"></i> Download');
	      $("#downloadInvestigatorReport").prop('disabled', false);
	    if(data.includes(".xlsx"))
	    {
	      toastr.success("Report downloaded successfully","Success");
	      window.location.href = "${pageContext.request.contextPath}/report/downloadSysFile?filename=" + 
			encodeURIComponent(data);
	      $("#clear").trigger("click");
	    }
	    else
	    {
	      toastr.error( data,'Error' );
	    }
	  }
	});
     
});
</script>