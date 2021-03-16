<%@page import = "java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.preclaim.models.UserRole" %>
<%
ArrayList<String> role_permission = new ArrayList<String>();
role_permission = (ArrayList<String>) session.getAttribute("permission");
session.removeAttribute("permission"); 
String roleCode = (String)session.getAttribute("role_code");
session.removeAttribute("role_id");
String roleName=(String)session.getAttribute("user role");
session.removeAttribute("user role");
ArrayList<String> user_permission=(ArrayList<String>)session.getAttribute("user_permission");
List<UserRole> user_role=(List<UserRole>)session.getAttribute("role_list");
session.removeAttribute("role_list");

if(role_permission == null)
	role_permission = new ArrayList<String>();
%>
<div class="row add-permission-form">
    <div class="col-xs-12 col-sm-12">
    	<div class="portlet box">
	      <div class="portlet-title">
	        <div class="caption">
	            <i class="icon-users font-green-sharp"></i>
	            <span class="caption-subject font-green-sharp sbold">Edit Permission - <%=roleName%></span>
	            
	        </div>
	        <div class="actions">
	          <div class="btn-group">
	            <a href="${pageContext.request.contextPath}/user/role" data-toggle="tooltip" title="Back" 
	            	class="btn green-haze btn-outline btn-xs pull-right" data-toggle="tooltip" 
	            	style="margin-right: 5px;" data-original-title="Back">
	              <i class="fa fa-reply"></i>
	            </a>
	          </div>
	        </div>
	      </div>
	    </div>
        <div class="box box-primary">
            <div class="box-body">
           	  <form>
                <div class="row">
                    <div class="col-md-12">
                      <div id="message_role"></div>
                      <div class="box-body table-responsive no-padding">
                        <table id="permission_table" class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>Module</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                            <!-- 
                            	<tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("appUsers")) {%>checked <%} %> name="appUsers[]" id="appUsers" class="allPLCheck0"  value = "appUsers"> <label for="appUsers">App Users</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("appUsers/index")) {%>checked <%} %> name="appUsers[]" id="appUsers_index" class="indPLCheck0" value="appUsers/index"> <label for="appUsers_index">View</label>
			                    		<input type="checkbox" <%if(role_permission.contains("appUsers/delete")) {%>checked <%} %> name="appUsers[]" id="appUsers_delete" class="indPLCheck0" value="appUsers/delete"> <label for="appUsers_delete">Delete</label>
			                    		<input type="checkbox" <%if(role_permission.contains("appUsers/status")) {%>checked <%} %> name="appUsers[]" id="appUsers_status" class="indPLCheck0" value="appUsers/status"> <label for="appUsers_status">Approve Status</label>
			                    		<input type="checkbox" <%if(role_permission.contains("appUsers/import")) {%>checked <%} %> name="appUsers[]" id="appUsers_import" class="indPLCheck0" value="appUsers/import"> <label for="appUsers_import">Import</label>
			                    	</td>
			                    </tr>
			                     -->
			                    <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("intimationType")) {%>checked <%} %> name="intimationType[]" id="intimationType" class="allPLCheck1" value="intimationType"> <label for="intimationType">Intimation Type</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("intimationType/index")) {%>checked <%} %> name="intimationType[]" id="intimationType_index" class="indPLCheck1" value="intimationType/index"> <label for="intimationType_index">View</label>
			                    		<input type="checkbox" <%if(role_permission.contains("intimationType/add")) {%>checked <%} %> name="intimationType[]" id="intimationType_add" class="indPLCheck1" value="intimationType/add"> <label for="intimationType_add">Add</label>
			                    		<input type="checkbox" <%if(role_permission.contains("intimationType/delete")) {%>checked <%} %> name="intimationType[]" id="intimationType_delete" class="indPLCheck1" value="intimationType/delete"> <label for="intimationType_delete">Delete</label>
			                    		<input type="checkbox" <%if(role_permission.contains("intimationType/status")) {%>checked <%} %> name="intimationType[]" id="intimationType_status" class="indPLCheck1" value="intimationType/status"> <label for="intimationType_status">Approve Status</label>
			                    	</td>
			                    </tr>
			                    <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("location")) {%>checked <%} %> name="location[]" id="location" class="allPLCheckloc" value="location"> <label for="location">Location</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("location/index")) {%>checked <%} %> name="location[]" id="location_index" class="indPLCheckloc" value="location/index"> <label for="location_index">View</label>
			                    		<input type="checkbox" <%if(role_permission.contains("location/add")) {%>checked <%} %> name="location[]" id="location_add" class="indPLCheckloc" value="location/add"> <label for="location_add">Add</label>
			                    		<input type="checkbox" <%if(role_permission.contains("location/delete")) {%>checked <%} %> name="location[]" id="location_delete" class="indPLCheckloc" value="location/delete"> <label for="location_delete">Delete</label>
			                    		<input type="checkbox" <%if(role_permission.contains("location/status")) {%>checked <%} %> name="location[]" id="location_status" class="indPLCheckloc" value="location/status"> <label for="location_status">Approve Status</label>
			                    	</td>
			                    </tr>
			                    <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("investigationType")) {%>checked <%} %> name="investigationType[]" id="investigationType" class="allPLCheck4" value="investigationType"> <label for="investigationType">Investigation Type</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("investigationType/index")) {%>checked <%} %> name="investigationType[]" id="investigationType_index" class="indPLCheck4" value="investigationType/index"> <label for="investigationType_index">View</label>
			                    		<input type="checkbox" <%if(role_permission.contains("investigationType/add")) {%>checked <%} %> name="investigationType[]" id="investigationType_add" class="indPLCheck4" value="investigationType/add"> <label for="investigationType_add">Add</label>
			                    		<input type="checkbox" <%if(role_permission.contains("investigationType/delete")) {%>checked <%} %> name="investigationType[]" id="investigationType_delete" class="indPLCheck4" value="investigationType/delete"> <label for="investigationType_delete">Delete</label>
			                    		<input type="checkbox" <%if(role_permission.contains("investigationType/status")) {%>checked <%} %> name="investigationType[]" id="investigationType_status" class="indPLCheck4" value="investigationType/status"> <label for="investigationType_status">Approve Status</label>
			                    	</td>
			                    </tr>
			                    <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("dashboard")) {%>checked <%} %> name="dashboard[]" id="dashboard" class="allPLCheckDashboard" value="dashboard"> <label for="dashboard">Dashboard</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("dashboard/new")) {%>checked <%} %> name="dashboard[]" id="dashboard_new" class="indPLCheckDashboard" value="dashboard/new"> <label for="dashboard_new">New Cases</label>
			                    		<input type="checkbox" <%if(role_permission.contains("dashboard/assigned")) {%>checked <%} %> name="dashboard[]" id="dashboard_assigned" class="indPLCheckDashboard" value="dashboard/assigned"> <label for="dashboard_assigned">Assigned Cases</label>
			                    		<input type="checkbox" <%if(role_permission.contains("dashboard/piv")) {%>checked <%} %> name="dashboard[]" id="dashboard_piv" class="indPLCheckDashboard" value="dashboard/piv"> <label for="dashboard_piv">PIV/PIRV/LIVE Pending</label>                    		
	                    				<input type="checkbox" <%if(role_permission.contains("dashboard/cdp")) {%>checked <%} %> name="dashboard[]" id="dashboard_cdp" class="indPLCheckDashboard" value="dashboard/cdp"> <label for="dashboard_cdp">Claim Document Pickup Cases</label>         		
	                    				<input type="checkbox" <%if(role_permission.contains("dashboard/review")) {%>checked <%} %> name="dashboard[]" id="dashboard_review" class="indPLCheckDashboard" value="dashboard/review"> <label for="dashboard_review">Review Report</label>         		
			                    		<input type="checkbox" <%if(role_permission.contains("dashboard/active")) {%>checked <%} %> name="dashboard[]" id="dashboard_active" class="indPLCheckDashboard" value="dashboard/active"> <label for="dashboard_active">Active Case</label>         		
			                    		<input type="checkbox" <%if(role_permission.contains("dashboard/wip")) {%>checked <%} %> name="dashboard[]" id="dashboard_wip" class="indPLCheckDashboard" value="dashboard/wip"> <label for="dashboard_wip">WIP</label>         		
			                    		<input type="checkbox" <%if(role_permission.contains("dashboard/billing")) {%>checked <%} %> name="dashboard[]" id="dashboard_billing" class="indPLCheckDashboard" value="dashboard/billing"> <label for="dashboard_billing">Investigation Billing</label>
			                    	</td>
			                    </tr>
			                    <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("mailConfig")) {%>checked <%} %> name="mailConfig[]" id="mailConfig" class="allPLCheckMConfig" value="mailConfig"> <label for="mailConfig">Mail Config</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("mailConfig/index")) {%>checked <%} %> name="mailConfig[]" id="mailConfig_index" class="indPLCheckMConfig" value="mailConfig/index"> <label for="mailConfig_index">View</label>
			                    		<input type="checkbox" <%if(role_permission.contains("mailConfig/add")) {%>checked <%} %> name="mailConfig[]" id="mailConfig_add" class="indPLCheckMConfig" value="mailConfig/add"> <label for="mailConfig_add">Add</label>
			                    		<input type="checkbox" <%if(role_permission.contains("mailConfig/delete")) {%>checked <%} %> name="mailConfig[]" id="mailConfig_delete" class="indPLCheckMConfig" value="mailConfig/delete"> <label for="mailConfig_delete">Delete</label>
			                    		<input type="checkbox" <%if(role_permission.contains("mailConfig/status")) {%>checked <%} %> name="mailConfig[]" id="mailConfig_status" class="indPLCheckMConfig" value="mailConfig/status"> <label for="mailConfig_status">Approve Status</label>
			                    	</td>
			                    </tr>
			                    <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("messages")) {%>checked <%} %> name="messages[]" id="messages" class="allPLCheck7" value="messages"> <label for="messages">Broadcast</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("messages/index")) {%>checked <%} %> name="messages[]" id="messages_index" class="indPLCheck7" value="messages/index"> <label for="messages_index">View</label>
			                    		<input type="checkbox" <%if(role_permission.contains("messages/add")) {%>checked <%} %> name="messages[]" id="messages_add" class="indPLCheck7" value="messages/add"> <label for="messages_add">Add</label>
			                    		<input type="checkbox" <%if(role_permission.contains("messages/delete")) {%>checked <%} %> name="messages[]" id="messages_delete" class="indPLCheck7" value="messages/delete"> <label for="messages_delete">Delete</label>
			                    		<input type="checkbox" <%if(role_permission.contains("messages/assign")) {%>checked <%} %> name="messages[]" id="messages_assign" class="indPLCheck7" value="messages/assign"> <label for="messages_assign">Assign</label>
			                    		<input type="checkbox" <%if(role_permission.contains("messages/reopen")) {%>checked <%} %> name="messages[]" id="messages_reopen" class="indPLCheck7" value="messages/reopen"> <label for="messages_reopen">Reopen</label>
			                    		<input type="checkbox" <%if(role_permission.contains("messages/close")) {%>checked <%} %> name="messages[]" id="messages_close" class="indPLCheck7" value="messages/close"> <label for="messages_close">Case Closure</label>
			                    		<input type="checkbox" <%if(role_permission.contains("messages/caseSubStatus")) {%>checked <%} %> name="messages[]" id="messages_caseSubStatus" class="indPLCheck7" value="messages/caseSubStatus"> <label for="messages_caseSubStatus">Case Sub-status</label>
			                    	</td>
			                    </tr>
			                    <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("users")) {%>checked <%} %> name="users[]" id="users" class="allPLCheck8" value="users"> <label for="users">Users</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("users/index")) {%>checked <%} %> name="users[]" id="users_index" class="indPLCheck8" value="users/index"> <label for="users_index">View</label>
			                    		<input type="checkbox" <%if(role_permission.contains("users/add")) {%>checked <%} %> name="users[]" id="users_add" class="indPLCheck8" value="users/add"> <label for="users_add">Add</label>
			                    		<input type="checkbox" <%if(role_permission.contains("users/delete")) {%>checked <%} %> name="users[]" id="users_delete" class="indPLCheck8" value="users/delete"> <label for="users_delete">Delete</label>
			                    		<input type="checkbox" <%if(role_permission.contains("users/status")) {%>checked <%} %> name="users[]" id="users_status" class="indPLCheck8" value="users/status"> <label for="users_status">Approve Status</label>
			                    	</td>
			                    </tr>
			                    <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("role")) {%>checked <%} %> name="role[]" id="role" class="allPLCheck9" value="role"> <label for="role">Role</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("role/index")) {%>checked <%} %> name="role[]" id="role_index" class="indPLCheck9" value="role/index"> <label for="role_index">View</label>
			                    		<input type="checkbox" <%if(role_permission.contains("role/add")) {%>checked <%} %> name="role[]" id="role_add" class="indPLCheck9" value="role/add"> <label for="role_add">Add</label>
			                    		<input type="checkbox" <%if(role_permission.contains("role/delete")) {%>checked <%} %> name="role[]" id="role_delete" class="indPLCheck9" value="role/delete"> <label for="role_delete">Delete</label>
			                    		<input type="checkbox" <%if(role_permission.contains("role/permission")) {%>checked <%} %> name="role[]" id="role_permission" class="indPLCheck9" value="role/permission"> <label for="role_permission">Add Permission</label>
			                    	</td>
			                    </tr>
			                    <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("report")) {%>checked <%} %> name="report[]" id="report" class="allPLCheck10" value="report"> <label for="report">Report</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("report/investigator")) {%>checked <%} %> name="report[]" id="report_investigator" class="indPLCheck10" value="report/investigator"> <label for="report_investigator">Top 15 Investigator</label>
			                    		<input type="checkbox" <%if(role_permission.contains("report/regionwise")) {%>checked <%} %> name="report[]" id="report_regionwise" class="indPLCheck10" value="report/regionwise"> <label for="report_regionwise">Region-wise Report </label>
			                    		<input type="checkbox" <%if(role_permission.contains("report/vendorwise")) {%>checked <%} %> name="report[]" id="report_vendorwise" class="indPLCheck10" value="report/vendorwise"> <label for="report_vendorwise">Vendor-wise Report</label>
			                    		<input type="checkbox" <%if(role_permission.contains("report/intimationTypeScreen")) {%>checked <%} %> name="report[]" id="report_intimationTypeScreen" class="indPLCheck10" value="report/intimationTypeScreen"> <label for="report_intimationTypeScreen">Intimation Type Screen</label>
			                    		<input type="checkbox" <%if(role_permission.contains("report/uploadedDocument")) {%>checked <%} %> name="report[]" id="report_uploadedDocument" class="indPLCheck10" value="report/uploadedDocument"> <label for="report_uploadedDocument">Uploaded Document</label>
			                    	</td>
			                    </tr>
			                    
			                      <tr>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("billingManagement")) {%>checked <%} %> name="billingManagement[]" id="billingManagement" class="allPLCheck11" value="billingManagement"> <label for="billingManagement">Billing Management</label>
			                    	</td>
			                    	<td>
			                    		<input type="checkbox" <%if(role_permission.contains("billingManagement/enquiry")) {%>checked <%} %> name="billingManagement[]" id="billingManagement_enquiry" class="indPLCheck11" value="billingManagement/enquiry"> <label for="billingManagement_enquiry">Bill Enquiry</label>
			                    		<input type="checkbox" <%if(role_permission.contains("billingManagement/payment")) {%>checked <%} %> name="billingManagement[]" id="billingManagement_payment" class="indPLCheck11" value="billingManagement/payment"> <label for="billingManagement_payment">Bill Payment</label>	
		                    		</td>
			                    </tr>
			                    
			                     <tr>
			                    	<td>
			                    	    <input type="checkbox" <%if(role_permission.contains("approve")) {%>checked <%} %> name="approve" id="approve" class="allPLCheck12" value="approve"> <label for="approve">Approve</label>
			                    	</td>
			                    	<td>
			                    	     <%if(user_role!=null){
			                    	          for(UserRole role :user_role){	
			                    	     %>
		                           <input type="checkbox" <%if(role_permission.contains("approve/" + role.getRole_code())) {%>checked <%} %> name="approve[]" id="approve_<%=role.getRole_code() %>" class="indPLCheck12" value="approve/<%=role.getRole_code()%>"> <label for="approve_<%=role.getRole_code()%>"><%=role.getRole() %></label>	
			                        	<%}}%>
			                        </td>
			                         		
			                    </tr>
			                    
			                     <tr>
			                    	<td>
			                    	    <input type="checkbox" <%if(role_permission.contains("reopen")) {%>checked <%} %> name="reopen" id="reopen" class="allPLCheck13" value="reopen"> <label for="reopen">Reopen</label>
			                    	</td>
			                    	<td>
			                    	     <%if(user_role!=null){
			                    	          for(UserRole role :user_role){	
			                    	     %>
			                           <input type="checkbox" <%if(role_permission.contains("reopen/" + role.getRole_code())) {%>checked <%} %> name="reopen[]" id="reopen_<%=role.getRole_code() %>" class="indPLCheck13" value="reopen/<%=role.getRole_code()%>"> <label for="reopen_<%=role.getRole_code()%>"><%=role.getRole() %></label>	
			                        	<%}}%>
			                        </td>
			                         		
			                    </tr>
			                    
		                	</tbody>
                        </table>
                      </div>
                      <div style="padding-top: 10px;">
                      	<input type="hidden" name="role_code" id="role_code" value="<%= roleCode%>">
                      	<button type="button" id="addPermissionBtn" onClick="return addPermission();" class="btn btn-success btn-sm">Submit</button>
		    			<a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/user/role">Back</a>
		    		  </div>	
                    </div>
                </div>
              </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

	$( '.allPLCheck0' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck0' ).prop( "checked", true );
		} else {
			$( '.indPLCheck0' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck1' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck1' ).prop( "checked", true );
		} else {
			$( '.indPLCheck1' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck2' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck2' ).prop( "checked", true );
		} else {
			$( '.indPLCheck2' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck3' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck3' ).prop( "checked", true );
		} else {
			$( '.indPLCheck3' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck4' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck4' ).prop( "checked", true );
		} else {
			$( '.indPLCheck4' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck5' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck5' ).prop( "checked", true );
		} else {
			$( '.indPLCheck5' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheckMConfig' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheckMConfig' ).prop( "checked", true );
		} else {
			$( '.indPLCheckMConfig' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck6' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck6' ).prop( "checked", true );
		} else {
			$( '.indPLCheck6' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck7' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck7' ).prop( "checked", true );
		} else {
			$( '.indPLCheck7' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck8' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck8' ).prop( "checked", true );
		} else {
			$( '.indPLCheck8' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck9' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck9' ).prop( "checked", true );
		} else {
			$( '.indPLCheck9' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck10' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck10' ).prop( "checked", true );
		} else {
			$( '.indPLCheck10' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck11' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck11' ).prop( "checked", true );
		} else {
			$( '.indPLCheck11' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck12' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck12' ).prop( "checked", true );
		} else {
			$( '.indPLCheck12' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheck13' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheck13' ).prop( "checked", true );
		} else {
			$( '.indPLCheck13' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheckRM' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheckRM' ).prop( "checked", true );
		} else {
			$( '.indPLCheckRM' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheckAS' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheckAS' ).prop( "checked", true );
		} else {
			$( '.indPLCheckAS' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheckloc' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheckloc' ).prop( "checked", true );
		} else {
			$( '.indPLCheckloc' ).prop( 'checked', false );
		}
	});
	$( '.allPLCheckDashboard' ).on( 'click', function() {
		if( $( this ).is( ':checked' ) ) {
			$( '.indPLCheckDashboard' ).prop( "checked", true );
		} else {
			$( '.indPLCheckDashboard' ).prop( 'checked', false );
		}
	});
   function addPermission() {
	   <%if(!user_permission.contains("role/add")){%>
  		toastr.error("Access Denied", "Error");
  		return;
		<%}%>
		$('#permission_table').css("opacity",".5");
        var form_name            = $( '.add-permission-form' ).find( 'input, select, button, textarea' );
        var permission_form_data = form_name.serialize();
        console.log(permission_form_data);
        $.ajax({
            type        : 'POST',
            url         : '../addPermission',
            data        : permission_form_data,
            beforeSend: function () {
            	$("#addPermissionBtn").html('<img src="${pageContext.request.contextPath}/resources/img/input-spinner.gif"> Loading...');
            	$("#addPermissionBtn").prop('disabled', true);
            	$('#permission_table').css("opacity",".5");
            },
            success : function( msg ) {
            	$("#addPermissionBtn").html('Submit');
            	$("#addPermissionBtn").prop('disabled', false);
            	$('#permission_table').css("opacity","1");
                if( msg == "****" ) { 	
                    toastr.success(  'Permission added successfully','Success' );
                } 
                else {
                    toastr.error(  'Error while adding Permission','Error' );
                }
                $("#addPermissionBtn").html('Submit');
              	$("#addPermissionBtn").prop('disabled', false);
              	$('#permission_table').css("opacity","");
            }
        });
        return false;
    }
   </script>	
