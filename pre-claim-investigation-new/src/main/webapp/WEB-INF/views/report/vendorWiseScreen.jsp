<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%
List<String> user_permission=(List<String>)session.getAttribute("user_permission");
HashMap<String, String> vendorlist = (HashMap<String, String>) session.getAttribute("VendorList"); 
%>
<div class="row">
  <div class="col-md-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
          <i class="icon-users font-green-sharp"></i>
          <span class="caption-subject font-green-sharp sbold">Vendor wise screen Lists</span>
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
           
            <label class="col-md-1 control-label" for="Vendor">Select Vendor 
                	<span class="text-danger">*</span></label>
                <div class="col-md-2">
                  <select name="VendorName" id="VendorName" class="form-control" tabindex="-1" required>
                    <option value="-1" selected disabled>Select</option>
					<%for(String vendor: vendorlist.keySet()) {%>
						<option value = "<%=vendor%>"><%=vendorlist.get(vendor)%></option>
					<%} %>
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
            <button class="btn btn-info" id="downloadVendorwiseReport" type="button"><i class="fa fa-download pr-1"></i> Download</button>
            <button class="btn btn-danger" type="reset">Clear</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript">
$("#downloadVendorwiseReport").click(function(){
	
	var startDate  = $('#startDate').val(); 
	var endDate    = $('#endDate').val(); 
	var VendorName = $('#VendorName').val(); 
	
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
	if(VendorName == '')
	{
		toastr.error("Vendor Name cannot be blank","Error");
		errorFlag = 1;
	}
	if(errorFlag == 1)
		return;
		
	var formdata = {'startDate':startDate, 'endDate':endDate, "VendorName":VendorName};
	$.ajax({
	  type: "POST",
	  url: 'downloadVendorwiseReport',
	  data: formdata,
	  beforeSend: function() { 
	      $("#downloadVendorwiseReport").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
	      $("#downloadVendorwiseReport").prop('disabled', true);
	  },
	  success: function( data ) {
		  $("#downloadVendorwiseReport").html('<i class="fa fa-download pr-1"></i> Download');
	      $("#downloadVendorwiseReport").prop('disabled', false);
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
