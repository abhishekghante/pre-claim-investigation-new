<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@page import = "java.util.List" %>
<%@page import = "java.util.ArrayList" %>
<%@page import = "com.preclaim.models.BillManagementList"%>
<%@page import = "com.preclaim.models.IntimationType" %>
<%@page import = "com.preclaim.models.InvestigationType" %>
 <%
List<String>user_permission=(List<String>)session.getAttribute("user_permission");
boolean allow_delete = user_permission.contains("messages/delete");
boolean allow_add = user_permission.contains("messages/add");
List<BillManagementList> billManagementList = (List<BillManagementList>)session.getAttribute("billingPendingList");
session.removeAttribute("billingPendingList");
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
            <i class="icon-users font-green-sharp"></i>
            <span class="caption-subject font-green-sharp sbold">Bill Enquiry Lists</span>
        </div>
       <%--  <%if(allow_add){ %> --%>
        <div class="actions">
            <div class="btn-group">
              <a href="${pageContext.request.contextPath}/message/add_message" data-toggle="tooltip" title="Add" class="btn green-haze btn-outline btn-xs pull-right" data-toggle="tooltip" title="" style="margin-right: 5px;" data-original-title="Add New">
                <i class="fa fa-plus"></i>
              </a>
            </div>
        </div>
        <%-- <%} %> --%>
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
                          <th class="head1 no-sort">Type of Investigation</th>
                          <th class="head1 no-sort">Type of Intimation</th>
                          <th class="head1 no-sort">Supervisor ID</th>
                          <th class="head1 no-sort">Supervisor Name</th>
                          <th class="head1 no-sort">Charges</th>
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
                        </tr>
                      </tfoot>
                       <tbody>
                        <%if(billManagementList!=null){
                        	for(BillManagementList list_case :billManagementList){%>                       
                          
                          <tr>
                                <td><input type="checkbox" id="selectedCategory" value="<%=list_case.getCaseID()%>" /></td>
                  				<td><%=list_case.getSrNo()%></td>
                  				<td><%=list_case.getCaseID()%></td>
                  			   	<td><%=list_case.getPolicyNumber()%></td>
                  				<td><%=list_case.getInvestigationId()%></td>
                  				<td><%=list_case.getInitimationType()%></td>
                                <td><%=list_case.getSupervisorID()%></td>
                                <td><%=list_case.getSupervisorName()%></td>
                                <td><%=list_case.getCharges()%></td>
                                <td>
	                             <a href="${pageContext.request.contextPath}/message/edit?caseId=<%=list_case.getCaseID()%>" 
	                             	data-toggle="tooltip" title="Edit" class="btn btn-primary btn-xs">
	                             	<i class="glyphicon glyphicon-edit"></i>
	                         	 </a>
                         	   
	                             <a href="#" data-toggle="tooltip" title="Delete" 
	                             	onClick="return deleteMessage('<%=list_case.getCaseID() %>',
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
                     <div class="row">
                      <div class="col-md-offset-4 col-md-8">
                        <input type="button"  class="btn btn-info" id="createExcelData" onClick="createExcelData()" value="Add Intimation"/>
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
    if( i == 1 || i == 2 || i == 5 || i == 6 ){
      $(this).html( '<input type="text" class="form-control" placeholder="" />' );
    }
    else if(i == 3)
    {
      var cat_selectbox = '<select name="category" id="category" class="form-control">'
                              +'<option value="">All</option>';
		<%-- <%if(investigationList != null){
			for(InvestigationType investigation : investigationList)
			{
		%>
		cat_selectbox += "<option value = <%= investigation.getInvestigationType()%>><%= investigation.getInvestigationType()%></option>";	
        <%}}%> --%>
		cat_selectbox += '</select>';
        $(this).html( cat_selectbox );
    }
    else if(i == 4)
    {
      var cat_selectbox = '<select name="intimation" id="intimation" class="form-control">'
                              +'<option value="">All</option>';
	<%-- 	<%if(intimationTypeList != null){
			for(IntimationType intimation : intimationTypeList)
			{
		%>
		cat_selectbox += "<option value = <%= intimation.getIntimationType()%>><%= intimation.getIntimationType()%></option>";	
		<%}}%> --%>
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


<script>
function createExcelData() { 
	
        //Create an Array.
        var selected = new Array();
 
        //Reference the Table.
        var pending_case_list =$("#pending_case_list");
 
        //Reference all the CheckBoxes in Table.
        var chks = $("#pending_case_list").find("INPUT")
 
        
        // Loop and push the checked CheckBox value in Array.
        for (var i = 0; i < chks.length; i++) {
            if (chks[i].checked) {
                selected.push(chks[i].value);
            }
        }
       //Display the selected CheckBox values.
        console.log("chks"+selected);
       
        $.ajax({
	        type    : 'POST',
	        url     : 'getCheckboxValue',
	        dataType: 'json',
	        data    : {'selected':selected},
	        success : function( msg ) {
	            if( msg != "****" ) 
	                toastr.error(msg,'Error');
	            else
	            	toastr.error(msg,'Sucess');
	            
	        }
	    });




};


    
    
    
    
</script>
