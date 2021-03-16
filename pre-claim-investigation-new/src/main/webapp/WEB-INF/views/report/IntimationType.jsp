<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%
List<String> user_permission=(List<String>)session.getAttribute("user_permission");
List<String> intimationTypeScreenLists = (List<String>)session.getAttribute("intimationTypeScreenLists");
session.removeAttribute("intimationTypeScreenLists");
%>
<div class="row">
  <div class="col-md-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
          <i class="fa fa-exclamation-triangle font-green-sharp"></i>
          <span class="caption-subject font-green-sharp sbold">Intimation Type List</span>
        </div>
        <div class="actions">
          <div class="btn-group">
            <a href="${pageContext.request.contextPath}/report/top15investigator" data-toggle="tooltip" title="Back" class="btn green-haze btn-outline btn-xs pull-right" data-toggle="tooltip" title="" style="margin-right: 5px;" data-original-title="Back">
              <i class="fa fa-reply"></i>
            </a>
          </div>
        </div>
      </div>
    </div>
    <div class="box box-primary">
      <!-- form start -->
      <div id="message_account"></div>
      <form novalidate="" id="add_intimation_type" role="form" method="post" class="form-horizontal">
        <div class="box-body">
          <div class="row">
           <div class="form-group">
           
            <label class="col-md-2 control-label" for="intimationType">Select Intimation type 
                	<span class="text-danger">*</span></label>
                <div class="col-md-2">
                  <select name="intimationType" id="intimationType" class="form-control" tabindex="-1" required>
                    <option value="-1" selected disabled>Select</option>
					<%for(String intimationList: intimationTypeScreenLists) {%>
						<option value = "<%=intimationList%>"><%=intimationList%></option>
					<%} %>
					<option value = "All">All</option>
                  </select>
                </div>
                <label class="col-md-1 control-label" for="startDate">Start date <span class="text-danger">*</span></label>
                <div class="col-md-2">
                  <input type="date" id="startDate" class="form-control" name="startDate">
                </div>
                 
                <label class="col-md-1 control-label" for="endDate">End date
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
            <button class="btn btn-info" id="downloadIntimationType" type="button"><i class="fa fa-download pr-1"></i> Download</button>
            <button class="btn btn-danger" type="reset">Clear</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript">
$("#downloadIntimationType").click(function(){
	
	var startDate  = $('#startDate').val(); 
	var endDate    = $('#endDate').val(); 
	var intimationType = $('#intimationType').val(); 
	
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
	if(startDate != "" && endDate != "")
	{
		var beginDate = new Date(startDate);
	    var lastDate = new Date(endDate);
	    if(beginDate >= lastDate)
    	{
	    	toastr.error("Start Date cannot be greater than End Date","Error");
			errorFlag = 1;
    	}
	}
	if(intimationType == '')
	{
		toastr.error("Intimation Type cannot be blank","Error");
		errorFlag = 1;
	}
	if(errorFlag == 1)
		return;
		
	var formdata = {'startDate':startDate, 'endDate':endDate, "intimationType":intimationType};
	$.ajax({
	  type: "POST",
	  url: 'downloadIntimationType',
	  data: formdata,
	  beforeSend: function() { 
	      $("#downloadIntimationType").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
	      $("#downloadIntimationType").prop('disabled', true);
	  },
	  success: function( data ) {
		  $("#downloadIntimationType").html('<i class="fa fa-download pr-1"></i> Download');
	      $("#downloadIntimationType").prop('disabled', false);
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
