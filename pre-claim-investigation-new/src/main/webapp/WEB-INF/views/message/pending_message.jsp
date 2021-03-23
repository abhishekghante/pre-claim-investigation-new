<%@page import="com.preclaim.models.CaseMovement"%>
<%@page import = "java.util.List" %>
<%@page import = "java.util.ArrayList" %>
<%@page import = "com.preclaim.models.CaseDetailList"%>
<%@page import = "com.preclaim.models.CaseDetails"%>
<%@page import = "com.preclaim.models.Location"%>
<%@page import = "com.preclaim.models.UserRole"%>
<%@page import = "com.preclaim.models.CaseSubStatus"%>
<%@page import = "com.preclaim.models.IntimationType" %>
<%@page import = "com.preclaim.models.InvestigationType" %>
<%
List<String>user_permission=(List<String>)session.getAttribute("user_permission");
List<CaseDetailList> pendingCaseDetailList = (List<CaseDetailList>)session.getAttribute("pendingCaseList");
session.removeAttribute("pendingCaseList");
List<InvestigationType> investigationList = (List<InvestigationType>) session.getAttribute("investigation_list");
session.removeAttribute("investigation_list");
List<IntimationType> intimationTypeList = (List<IntimationType>) session.getAttribute("intimation_list");
session.removeAttribute("intimation_list");


CaseDetails case_detail = (CaseDetails) session.getAttribute("case_detail");
session.removeAttribute("case_detail");
List<CaseSubStatus> CaseSubStatus = (List<CaseSubStatus>) session.getAttribute("level");
System.out.println(CaseSubStatus);
session.removeAttribute("level");
List<Location> location_list = (List<Location>) session.getAttribute("location_list");
session.removeAttribute("location_list");
List<UserRole> userRole =(List<UserRole>)session.getAttribute("userRole");
session.removeAttribute("userRole");
boolean allow_edit = user_permission.contains("messages/add");
boolean allow_assign = user_permission.contains("messages/assign");
boolean allow_reopen = user_permission.contains("messages/reopen");
boolean allow_closure = user_permission.contains("messages/close");
boolean allow_delete = user_permission.contains("messages/delete");
boolean allow_add = user_permission.contains("messages/add");


%>
<link href="${pageContext.request.contextPath}/resources/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resources/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<div class="row">
  <div class="col-xs-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
            <i class="icon-clock"></i>
            <span class="caption-subject font-green-sharp sbold">Pending Cases</span>
        </div>
        <%if(allow_add){ %>
        <div class="actions">
            <div class="btn-group">
              <a href="${pageContext.request.contextPath}/message/add_message" data-toggle="tooltip" title="Add" class="btn green-haze btn-outline btn-xs pull-right" data-toggle="tooltip" title="" style="margin-right: 5px;" data-original-title="Add New">
                <i class="fa fa-plus"></i>
              </a>
            </div>
        </div>
        <%} %>
      </div>
    </div>

    <div class="box box-primary">
      <div class="box-body">
          <div class="row">
            <div class="col-md-12 table-container">
                <div class="box-body no-padding">
                  <div class="table-responsive">
                    <table id="pending_case_list" class="table table-striped table-bordered table-hover table-checkable dataTable data-tbl">
                      <thead>
                        <tr class="tbl_head_bg">
                         <th class="head1 no-sort"><input type="checkbox" id="checkall" /></th>
                          <th class="head1 no-sort">Sr No.</th>
                          <th class="head1 no-sort">Case ID</th>
                          <th class="head1 no-sort">Policy No</th>
                          <th class="head1 no-sort">Name of Insured</th>
                          <th class="head1 no-sort">Type of Investigation</th>
                          <th class="head1 no-sort">Sum Assured</th>
                          <th class="head1 no-sort">Type of Intimation</th>
                          <th class="head1 no-sort">Case Sub-status</th>
                          <th class="head1 no-sort">Zone</th>
                          <th class="head1 no-sort">View history</th>
                          <th class="head1 no-sort">Action</th>
                        </tr>
                      </thead>
                      <tfoot>
                        <tr class="tbl_head_bg">
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                          <th class="head2 no-sort"></th>
                        </tr>
                      </tfoot>
                      <tbody>
                        <%if(pendingCaseDetailList!=null){
                        	for(CaseDetailList list_case :pendingCaseDetailList){%>                       
                          
                          <tr>
                                <td><input type="checkbox" class ="selectedCategory" value="<%=list_case.getCaseId()%>"></td>
                  				<td><%= list_case.getSrNo()%></td>
                  				<td><%=list_case.getCaseId()%></td>
                  			   	<td><%=list_case.getPolicyNumber()%></td>
                  				<td><%=list_case.getInsuredName()%></td>
                  				<td><%=list_case.getInvestigationCategory()%></td>
                  				<td><%=list_case.getSumAssured()%></td>
                                <td><%=list_case.getIntimationType()%></td>
                                 <td><%=list_case.getCaseSubStatus()%></td>
                                 <td><%=list_case.getZone()%></td>
                               <td ><a href="${pageContext.request.contextPath}/message/case_history?caseId=<%=list_case.getCaseId()%>">Case History</a></td>
                                <td>
	                             <a href="${pageContext.request.contextPath}/message/edit?caseId=<%=list_case.getCaseId()%>" 
	                             	data-toggle="tooltip" title="Edit" class="btn btn-primary btn-xs">
	                             	<i class="glyphicon glyphicon-edit"></i>
	                         	 </a>
                         	   
	                             <a href="#" data-toggle="tooltip" title="Delete" 
	                             	onClick="return deleteMessage('<%=list_case.getCaseId() %>',
	                             	<%=allow_delete%>);" class="btn btn-danger btn-xs"> 
	                             	<i class="glyphicon glyphicon-remove"></i>
	                           	 </a>
                         
                         		</td>
                                
                          
                          </tr>                      
                       
                       <% 		
                       	}
                        } 
                        %>
                      
                      
                      
                      </tbody>
                    </table>
                    <div class="form-group">
                      <div class="form-group selectDiv" id = "case-closure">
	                <label class="col-md-1 control-label" for="toRole">Select Role Name 
	                	<span class="text-danger">*</span></label>
	                <div class="col-md-2">
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
                
	                <label class="col-md-1 control-label" for="toId">Select User 
	                	<span class="text-danger">*</span></label>
	                <div class="col-md-2">
	                  <select name="toId" id="toId" class="form-control">
	                  	<option value = '-1' selected disabled>Select</option>
	                  </select>
	            	</div>
	            	<label class="col-md-1 control-label" for="toStatus">Case Status 
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
	                       
              	 </div>
   	             	              
              	<div class="form-group">
	                <label class="col-md-4 control-label" for="toRemarks">Remarks</label>
	                <div class="col-md-8">
	                  <textarea name="toRemarks" id="toRemarks" class="form-control" rows="6"></textarea>
	                </div>
              	</div> 
              </div>
                  </div>                 
                </div>
              <div class="clearfix"></div>
            </div>
          </div>
        <div class="clearfix"></div>
      </div><!-- panel body -->
    </div>
  </div><!-- content -->
</div>
<script type="text/javascript">
$(document).ready(function() {
  var i = 0;
  //DataTable  
  var table = $('#pending_case_list').DataTable();

   $('#pending_case_list tfoot th').each( function () {
    if( i == 1 || i == 2 || i == 3 || i == 4 ){
      $(this).html( '<input type="text" class="form-control" placeholder="" />' );
    }
    else if(i == 5)
    {
      var cat_selectbox = '<select name="category" id="category" class="form-control">'
                              +'<option value="">All</option>';
		<%if(investigationList != null){
			for(InvestigationType investigation : investigationList)
			{
		%>
		cat_selectbox += "<option value = <%= investigation.getInvestigationType()%>><%= investigation.getInvestigationType()%></option>";	
        <%}}%>
		cat_selectbox += '</select>';
        $(this).html( cat_selectbox );
    }
    else if(i == 7)
    {
      var cat_selectbox = '<select name="intimation" id="intimation" class="form-control">'
                              +'<option value="">All</option>';
		<%if(intimationTypeList != null){
			for(IntimationType intimation : intimationTypeList)
			{
		%>
		cat_selectbox += "<option value = <%= intimation.getIntimationType()%>><%= intimation.getIntimationType()%></option>";	
		<%}}%>
      cat_selectbox += '</select>';
      $(this).html( cat_selectbox );
    }
    i++;
  });

  // Apply the search
  table.columns().every( function () {
    var that = this;
    $( 'input', this.footer() ).on( 'keyup change', function () {
      if ( that.search() !== this.value ) {
        that
          .search( this.value )
          .draw();
      }
    });
    $( 'select', this.footer() ).on( 'change', function () {
      if ( that.search() !== this.value ) {
        that
          .search( this.value )
          .draw();
      }
    });
  });
});

</script>
<script type="text/javascript">
    $("#pending_case_list #checkall").click(function () {
        if ($("#pending_case_list #checkall").is(':checked')) {
            $("#pending_case_list input[type=checkbox]").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("#pending_case_list input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
</script>
<!-- <script>
function createExcelData() { 
	
    //Create an Array.
    var selected = [];

    //Reference all the CheckBoxes in Table.
    var chks = $("#pending_case_list tbody").find("INPUT")

    // Loop and push the checked CheckBox value in Array.
    for (var i = 0; i < chks.length; i++) {
        if (chks[i].checked) {
            selected.push(chks[i].value);
        }
    }
   
   if(selected.length == 0)
	   {
  	   	toastr.error("Kindly select atleast one case","Error");
  	   	return;
	   }
}


</script> -->
