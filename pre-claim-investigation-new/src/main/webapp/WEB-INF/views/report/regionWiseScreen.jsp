<%@page import="java.util.List"%>
<%
List<String> user_permission=(List<String>)session.getAttribute("user_permission");
List<String> state_list = (List<String>)session.getAttribute("StateList");
%>
<div class="row">
  <div class="col-md-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
          <i class="icon-users font-green-sharp"></i>
          <span class="caption-subject font-green-sharp sbold">Region wise screen Lists</span>
        </div>
        <div class="actions">
          <div class="btn-group">
            <a href="${pageContext.request.contextPath}/report/vendorWiseScreen" data-toggle="tooltip" title="Back" class="btn green-haze btn-outline btn-xs pull-right" data-toggle="tooltip" title="" style="margin-right: 5px;" data-original-title="Back">
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
           
            <label class="col-md-1 control-label" for="Vendor">Select Region 
                	<span class="text-danger">*</span></label>
                <div class="col-md-2">
                  <select name="RegionName" id="RegionName" class="form-control" tabindex="-1" required>
                    <option value="-1" selected disabled>Select</option>
					<%for(String state: state_list) {%>
						<option value = "<%=state%>"><%=state%></option>
					<%} %>
                  </select>
                </div>
                <label class="col-md-1 control-label" for="startDate">Start date <span class="text-danger">*</span></label>
                <div class="col-md-2">
                  <input type="date" required="" id="startDate" class="form-control" name="startDate">
                </div>
                 
                <label class="col-md-1 control-label" for="endDate">End date
                	<span class="text-danger">*</span></label>
                <div class="col-md-2">
                  <input type="date" required="" id="endDate" class="form-control" name="endDate">
                </div>
                
              </div>
          </div>
        </div><br>
        <!-- /.box-body -->
        <div class="box-footer">
          <div class="col-md-offset-4 col-md-10">
            <button class="btn btn-info" id="downloadRegionWiseReport" type="button"><i class="fa fa-download pr-1"></i> Download</button>
            <button class="btn btn-danger" type="reset">Clear</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
