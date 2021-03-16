<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@page import = "java.util.List"%>
<%@page import = "com.preclaim.models.IntimationTypeList"%>
<%@page import="com.preclaim.config.Config"%>
<%@page import = "java.io.File" %>
<%
String dirName = (String) session.getAttribute("directory");
List<String> user_permission=(List<String>)session.getAttribute("user_permission");
boolean allow_statusChg = user_permission.contains("intimationType/status");
boolean allow_delete = user_permission.contains("intimationType/delete");
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
					<i class="fa fa-file-text font-green-sharp"></i> <span
						class="caption-subject font-green-sharp sbold"> Uploaded Document List</span>
				</div>
			</div>
		</div>

		<div class="box box-primary">
			<div class="box-body">
				<div class="row">
					<div class="col-md-12 table-container">
						<div class="box-body no-padding">
							<table id="fileList"
								class="table table-striped table-bordered table-hover table-checkable dataTable data-tbl">
								<thead>
									<tr class="tbl_head_bg">
										<th class="head1 no-sort">Sr no</th>
										<th class="head1 no-sort">Filename</th>
										<th class="head1 no-sort">Action</th>
									</tr>
								</thead>
								<tfoot>
									<tr class="tbl_head_bg">
										<th class="head2 no-sort"></th>
										<th class="head2 no-sort"></th>
										<th class="head2 no-sort"></th>
									</tr>
								</tfoot>
							 <tbody>

                                 <% File filelist = new File(dirName);

                                        if(filelist.exists())

                                        {

                                               int i = 1;

                                               for(File item:filelist.listFiles())

                                               {

                                                     if(item.isFile())

                                                     {

                                 %>

                                                            <tr>

                                                                   <td><%=i %></td>

                                                                   <td><%=item.getName() %></td>

                                                                   <td><a href = "#"><i class="fa fa-download pr-1"></i> Download</a></td>

                                                            </tr>

                                 <%

                                                            i++;

                                                     }                                             

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
		if (i == 1) {
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

</script>
<script>
$(function(){
	$("#fileList").DataTable();
	
})

</script>
<script>
$("#fileList tbody tr").on("click","a",function()
	{
		let filename = $(this).parent().parent().children().eq(1).text();
		console.log(filename);
		let fileDir = $("#fileDir").val();
		window.location.href = "${pageContext.request.contextPath}/report/downloadSysFile?filename=" + 
			encodeURIComponent(filename);	
	});
	

</script>