<%@page import = "java.util.List"%>
<%@page import = "com.preclaim.models.CaseCategoryList"%>
<%@page import="com.preclaim.models.CaseStatus"%>
<%
List<CaseCategoryList> pending_list = (List<CaseCategoryList>) session.getAttribute("pending_caseCategory");
session.removeAttribute("pending_caseCategory");
CaseCategoryList caseCategoryList = (CaseCategoryList) session.getAttribute("caseCategoryList");
session.removeAttribute("caseCategoryList");
List<CaseStatus> case_status = (List<CaseStatus>) session.getAttribute("case_status");
session.removeAttribute("case_status");
List<String> user_permission = (List<String>)session.getAttribute("user_permission");
boolean allow_statusChg = user_permission.contains("caseCategory/status");
boolean allow_delete = user_permission.contains("caseCategory/delete");
%>
<link href="${pageContext.request.contextPath}/resources/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resources/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<style>
@media(max-width:576px)
{
	table thead, table tfoot
	{
		display:none;
	}

	table, table tbody, table tr, table td
	{
		display : block;
		width   : 100%;
	}
	
	table td
	{
		width : 90%;
		text-align: right;
		position   : relative;
		padding-left: 50%;
	}
	table tr
	{
		margin-bottom : 15px;
	}
	table td::before
	{
		 content: attr(data-label);
		 position : absolute;
		 left : 10px;
		 width : 50%;
		 text-align: left;
	}

}


</style>
<div class="row">
	<div class="col-md-12 col-sm-12">
		<div class="portlet box">
			<div class="portlet-title">
				<div class="caption">
					 <i class="icon-plus"></i>
					<span class="caption-subject font-green-sharp sbold">
						<%=caseCategoryList == null ? "Add " : "Update "%>
						Case Category
					</span>
				</div>
			</div>
		</div>
		<div class="portlet light bordered">
			<div class="portlet-body">
				<div id="message_account"></div>
				<form novalidate id="add_case_status" role="form" method="post"
					class="form-horizontal">
					<div class="row">
			            <div class="col-md-6">
			              <div class="form-group">
			                <label class="col-md-4 control-label" for="caseStatus">Case Status <span class="text-danger">*</span></label>
			                <div class="col-md-8">
			                  <select id="caseStatus" class="form-control">
			                  	<option value = "-1" <%if(caseCategoryList == null) { %>selected <%} %>
			                  		disabled>Select</option>
			                  	<%if(case_status != null) {
			                  		for(CaseStatus item: case_status)
			                  		{ 
			                  	%>
			                  			<option value = "<%=item.getCaseStatus()%>"
			                  				<%
			                  				if(caseCategoryList != null)
			                  				{
			                  					if(caseCategoryList.getCaseStatus().
			                  							equals(item.getCaseStatus())) 
			                  				{ %>selected <%}} %>>
			                  			<%=item.getCaseStatus() %></option>
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
								<label class="col-md-4 control-label" for="caseCategory">Case Category
									 <span class="text-danger">*</span>
								</label>
								<div class="col-md-8">
									<input type="text" placeholder="Case Category"
										id="caseCategory" class="form-control" name="caseCategory"
										value = "<%=caseCategoryList == null ? "" : caseCategoryList.getCaseCategory()%>">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-offset-4 col-md-8">
									<%
									if(caseCategoryList != null){
									%>
									<input type="hidden" value="<%=caseCategoryList.getCaseCategoryId()%>" id="caseCategoryId"
										name="caseCategoryId">
									<button class="btn btn-info" id="editCaseCategorySubmit"
										onClick="return updateCaseCategory();" type="button">Update</button>
									<a href="${pageContext.request.contextPath}/caseCategory/pending"
										class="btn btn-danger">Back</a>
									<%
									}else{
									%> 
									<button class="btn btn-info" id="addCaseCategorySubmit"
										onClick="return addCaseCategory();" type="button">Add Case Category</button>
									<button class="btn btn-danger" type="reset" value="">Clear</button>
									<%
									}
									%>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<!--<?php } ?>-->
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class="portlet box">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-clock"></i> <span
						class="caption-subject font-green-sharp sbold">Pending
						Case Category</span>
				</div>
				<div class="actions">
					<div class="btn-group">
						<a href="${pageContext.request.contextPath}/caseCategory/add"
							data-toggle="tooltip" title="Add" data-original-title="Add New"
							class="btn green-haze btn-outline btn-xs pull-right" data-toggle="tooltip"
							style="margin-right: 5px;"> <i class="fa fa-plus"></i>
						</a>
					</div>
				</div>
			</div>
		</div>

		<div class="box box-primary">
			<div class="box-body">
				<div class="row">
					<div class="col-md-12 table-container">
						<div class="box-body no-padding">
							<table id="pending_group_list"
								class="table table-striped table-bordered table-hover table-checkable dataTable data-tbl">
								<thead>
									<tr class="tbl_head_bg">
										<th class="head1 no-sort">#</th>
										<th class="head1 no-sort">Case Status</th>
										<th class="head1 no-sort">Case Category</th>
										<th class="head1 no-sort">Created Date</th>
										<th class="head1 no-sort">Status</th>
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
									</tr>
								</tfoot>
								<tbody>
									<%
									if (pending_list != null) {

									  for (CaseCategoryList list_CaseCategory : pending_list) {
									%>
									<tr>
										<td><%=list_CaseCategory.getSrNo()%></td>
										<td data-label = "Case Status"><%=list_CaseCategory.getCaseStatus()%></td>
										<td data-label = "Case Category"><%=list_CaseCategory.getCaseCategory()%></td>
										<td data-label = "Created Date"><%=list_CaseCategory.getCreatedDate()%></td>										
										<td data-label = "Status"><span class="label label-sm label-danger">Pending</span></td>											
										<td data-label = "Action">
											<a href="${pageContext.request.contextPath}/caseCategory/pending?caseStatus=<%=list_CaseCategory.getCaseStatus() %>&caseCategory=<%=list_CaseCategory.getCaseCategory() %>&caseCategoryId=<%=list_CaseCategory.getCaseCategoryId() %>" 
                    		         			data-toggle="tooltip" title="Edit" class="btn btn-primary btn-xs">
                    		         			<i class="glyphicon glyphicon-edit"></i>
                   		         			</a>
									   		
									   		<a href="javascript:;" data-toggle="tooltip" title="Active" onClick="return updateCaseCategoryStatus('<%=list_CaseCategory.getCaseCategoryId()%>',1,<%=allow_statusChg %>);" 
									   		  	class="btn btn-success btn-xs"><i class="glyphicon glyphicon-ok-circle"></i>
								   		  	</a>
									   		
									   		<a href="#" data-toggle="tooltip" title="Delete" onClick="return deleteCaseCategory('<%=list_CaseCategory.getCaseCategoryId()%>',<%=allow_delete %>);" 
									   		   	class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i>
								   		   	</a>  
										</td>

									</tr>

									<%
										}
									}
									%>


								</tbody>
							</table>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<!-- panel body -->
		</div>
	</div>
	<!-- content -->
</div>
<script type="text/javascript">
$(document).ready(function() 
{
	var i = 0;
	$('#pending_group_list tfoot th').each(function() {
		if (i == 1 || i == 3) {
			$(this).html('<input type="text" class="form-control" placeholder="" />');
		}
		i++;
	});
	
	// DataTable
	var table = $('#pending_group_list').DataTable();
	
	// Apply the search
	table.columns().every(function() {
		var that = this;
		$('input', this.footer()).on('keyup change',function() {
			if (that.search() !== this.value) {
				that.search(this.value)
						.draw();
			}
		});
		$('select', this.footer()).on('change',function() {
			if (that.search() !== this.value) {
				that.search(this.value).draw();
			}
		});
	});
});

function addCaseCategory() {
	<%if(!user_permission.contains("caseCategory/add")){%>
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
		type : "POST",
		url : '${pageContext.request.contextPath}/caseCategory/addCaseCategory',
		data : formdata,
		beforeSend : function() {
			$("#addCaseCategorySubmit")
				.html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
			$("#addCaseCategorySubmit").prop('disabled', true);
		},
		success : function(data) {
			$("#addCaseCategorySubmit").html('Add Case Category');
			$("#addCaseCategorySubmit").prop('disabled', false);
			if (data == "****") 
			{
				location.reload();
			}
			else 
			{
				toastr.error(data, 'Error');
			}
		}
	});
}

function updateCaseCategory() {
	<%if(!user_permission.contains("caseCategory/add")){%>
		toastr.error("Access Denied","Error");
		return false;
	<%}%>
	var caseStatus = $( '#add_case_status #caseStatus' ).val(); 
	var caseCategory = $( '#add_case_status #caseCategory' ).val();
	var caseCategoryId = $('#add_case_status #caseCategoryId').val();
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
	
	var formdata = {'caseStatus' : caseStatus,"caseCategory" : caseCategory,
					'caseCategoryId' : caseCategoryId};
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/caseCategory/updateCaseCategory',
			data : formdata,
			beforeSend : function() {
				$("#editCaseCategorySubmit")
						.html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
				$("#editCaseCategorySubmit").prop('disabled', true);
			},
			success : function(data) {
				$("#editCaseCategorySubmit").html('Update');
				$("#editCaseCategorySubmit").prop('disabled', false);
				
				if (data == "****") 
					location.href ="${pageContext.request.contextPath}/caseCategory/pending";
				else 
					toastr.error(data, 'Error');
			}
		});
}
</script>