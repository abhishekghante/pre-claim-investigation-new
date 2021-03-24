<%@page import = "java.util.List"%>
<%@page import = "com.preclaim.models.CaseStatusList"%>
<%
List<CaseStatusList> pending_list = (List<CaseStatusList>) session.getAttribute("pending_caseStatus");
session.removeAttribute("pending_caseStatus");
CaseStatusList caseStatusList = (CaseStatusList) session.getAttribute("caseStatusList");
session.removeAttribute("caseStatusList");
List<String> user_permission = (List<String>)session.getAttribute("user_permission");
boolean allow_statusChg = user_permission.contains("caseStatus/status");
boolean allow_delete = user_permission.contains("caseStatus/delete");
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
						<%=caseStatusList == null ? "Add " : "Update "%>
						Case Status
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
								<label class="col-md-4 control-label" for="caseStatus">Case Status
									 <span class="text-danger">*</span>
								</label>
								<div class="col-md-8">
									<input type="text" placeholder="Case Type Name"
										id="caseStatus" class="form-control" name="caseStatus"
										value = "<%=caseStatusList == null ? "" : caseStatusList.getCaseStatus()%>">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-offset-4 col-md-8">
									<%
									if(caseStatusList != null){
									%>
									<input type="hidden" value="<%=caseStatusList.getCaseStatusId()%>" id="caseStatusId"
										name="caseStatusId">
									<button class="btn btn-info" id="editCaseStatusSubmit"
										onClick="return updateCaseStatus();" type="button">Update</button>
									<a href="${pageContext.request.contextPath}/caseStatus/pending"
										class="btn btn-danger">Back</a>
									<%
									}else{
									%> 
									<button class="btn btn-info" id="addCaseStatusSubmit"
										onClick="return addCaseStatus();" type="button">Add Case Status</button>
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
						Case Status</span>
				</div>
				<div class="actions">
					<div class="btn-group">
						<a href="${pageContext.request.contextPath}/caseStatus/add"
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
										<th class="head2 no-sort">Status</th>
										<th class="head2 no-sort"></th>
									</tr>
								</tfoot>
								<tbody>
									<%
									if (pending_list != null) {

									  for (CaseStatusList list_CaseStatus : pending_list) {
									%>
									<tr>
										<td><%=list_CaseStatus.getSrNo()%></td>
										<td data-label = "Case Status"><%=list_CaseStatus.getCaseStatus()%></td>
										<td data-label = "Created Date"><%=list_CaseStatus.getCreatedDate()%></td>										
										<td data-label = "Status"><span class="label label-sm label-danger">Pending</span></td>											
										<td data-label = "Action">
											<a href="${pageContext.request.contextPath}/caseStatus/pending?caseStatus=<%=list_CaseStatus.getCaseStatus() %>&caseStatusId=<%=list_CaseStatus.getCaseStatusId() %>" 
												data-toggle="tooltip" title="Edit" class="btn btn-primary btn-xs">
												<i class="glyphicon glyphicon-edit"></i>
							   		  		</a>
									   		
									   		<a href="javascript:;" data-toggle="tooltip" title="Active" onClick="return updateStatus('<%=list_CaseStatus.getCaseStatusId()%>',1,<%=allow_statusChg %>);" 
									   		  	class="btn btn-success btn-xs"><i class="glyphicon glyphicon-ok-circle"></i>
								   		  	</a>
									   		
									   		<a href="#" data-toggle="tooltip" title="Delete" onClick="return deleteCaseStatus('<%=list_CaseStatus.getCaseStatusId()%>',<%=allow_delete %>);" 
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

function addCaseStatus() {
	<%if(!user_permission.contains("caseStatus/add")){%>
		toastr.error("Access Denied","Error");
		return false;
	<%}%>
	var caseStatus = $('#add_case_status #caseStatus').val();
	if (caseStatus == '') {
		toastr.error('Case status cannot be blank', 'Error');
		return false;
	}
	var formdata = {'caseStatus' : caseStatus};
	$.ajax({
		type : "POST",
		url : '${pageContext.request.contextPath}/caseStatus/addCaseStatus',
		data : formdata,
		beforeSend : function() {
			$("#addCaseStatusSubmit")
				.html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
			$("#addCaseStatusSubmit").prop('disabled', true);
		},
		success : function(data) {
			$("#addCaseStatusSubmit").html('Add Case Status');
			$("#addCaseStatusSubmit").prop('disabled', false);
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

function updateCaseStatus() {
	<%if(!user_permission.contains("caseStatus/add")){%>
		toastr.error("Access Denied","Error");
		return false;
	<%}%>
	var caseStatus = $('#add_case_status #caseStatus').val();
	var caseStatusId = $('#add_case_status #caseStatusId').val();
	if (caseStatus == '') {
		toastr.error('Case status cannot be blank', 'Error');
		return false;
	}	
	var formdata = {'caseStatus' : caseStatus,'caseStatusId' : caseStatusId};
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/caseStatus/updateCaseStatus',
			data : formdata,
			beforeSend : function() {
				$("#editCaseStatusSubmit")
						.html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
				$("#editCaseStatusSubmit").prop('disabled', true);
			},
			success : function(data) {
				$("#editCaseStatusSubmit").html('Update');
				$("#editCaseStatusSubmit").prop('disabled', false);
				
				if (data == "****") 
					location.href ="${pageContext.request.contextPath}/caseStatus/pending";
				else 
					toastr.error(data, 'Error');
			}
		});
}
</script>