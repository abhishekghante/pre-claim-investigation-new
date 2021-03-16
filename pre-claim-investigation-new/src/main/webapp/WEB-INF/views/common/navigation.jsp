<%@page import = "java.util.List" %>
<%@page import = "java.util.ArrayList" %>
<%@page import = "com.preclaim.controller.LoginController"%>
<%@page import = "com.preclaim.models.ScreenDetails" %>
<%
ScreenDetails details = (ScreenDetails) session.getAttribute("ScreenDetails");
ArrayList<String> user_permission = (ArrayList<String>)session.getAttribute("user_permission");
%>
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
    <!-- BEGIN SIDEBAR -->
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
        <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
        <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
        <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200" style="">
            <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
            <li class="sidebar-toggler-wrapper hide">
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler"> </div>
                <!-- END SIDEBAR TOGGLER BUTTON -->
            </li>
            <li class="nav-item start <%if(details.getMain_menu().equals("Dashboard")){%>active open<%}%>">
                <a href="${pageContext.request.contextPath}/dashboard" class="nav-link">
                    <i class="icon-home"></i>
                    <span class="title">Dashboard</span>
                </a>
            </li>
            <!-- 
            <%if(user_permission.contains("appUsers")) {%>
            <li class="nav-item start <%if(details.getMain_menu().equals("App Users Management")){%>active open<%}%>">
                <a href="${pageContext.request.contextPath}/app_user/app_user" class="nav-link">
                    <i class="icon-user"></i>
                    <span class="title">App Users Management</span>
                </a>
            </li>
            <%} %>
             --> 
            <li class="nav-item <%if(details.getMain_menu().equals("Live Tracking")){%>active open<%}%>">
                <a href="${pageContext.request.contextPath}/livetracking/index" class="nav-link">
                    <i class="icon-pointer"></i>
                    <span class="title">Live Tracking</span>
                </a>
            </li>
            <%if(user_permission.contains("messages")){ %>
            <li class="nav-item <%if(details.getMain_menu().equals("Case Management")){%>active open<%}%>">
              <a href="javascript:;" class="nav-link nav-toggle">
                  <i class="icon-envelope-letter"></i>
                  <span class="title">Case Management </span>
                  <span class="arrow "></span>
              </a>
              <ul class="sub-menu">
                <%if(user_permission.contains("messages/add")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Bulk case uploads")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/message/import_case" 
                  	class="nav-link nav-toggle">
                    <i class="icon-plus"></i> Bulk case uploads
                  </a>
                </li>
                <%} %>
                <%if(user_permission.contains("messages/add")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Create Case")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/message/add_message" 
                  	class="nav-link nav-toggle">
                    <i class="icon-plus"></i> Create Case
                  </a>
                </li>
                  <%} %>
                  
                <li class="nav-item <%if(details.getSub_menu1().equals("Pending Cases")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/message/pending_message" 
                  	class="nav-link nav-toggle">
                    <i class="icon-clock"></i> Pending Cases
                  </a>
                </li>
                <li class="nav-item <%if(details.getSub_menu1().equals("Case Lists")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/message/active_message" 
                  	class="nav-link nav-toggle">
                    <i class="icon-check"></i> Case Lists
                  </a>
                </li>
              </ul>
            </li>
            <%} %>
            <li class="heading">
                <h3 class="uppercase">Settings</h3>
            </li>
                <%if(user_permission.contains("users")){ %>
            <li class="nav-item <%if(details.getMain_menu().equals("Users")){%>active open<%}%>">
              <a href="${pageContext.request.contextPath}/users" class="nav-link nav-toggle">
                  <i class="icon-user"></i>
                  <span class="title">Users</span>
                  <span class="arrow "></span>
              </a>
              <ul class="sub-menu">
                
                 <%if(user_permission.contains("users/add")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Add User")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/user/add_user" class="nav-link nav-toggle">
                    <i class="icon-user-follow"></i> Add User
                  </a>
                </li>
                 <%} %>
                 
                <li class="nav-item <%if(details.getSub_menu1().equals("User Lists")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/user/user_list" class="nav-link nav-toggle">
                    <i class="icon-user-follow"></i> User Lists
                  </a>
                </li>
                
                <li class="nav-item <%if(details.getSub_menu1().equals("User Role")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/user/role" class="nav-link nav-toggle">
                    <i class="icon-user-following"></i> User Role
                  </a>
                </li>
              </ul>
            </li>
             <%} %>
             <%if(user_permission.contains("intimationType")){ %>
            <li class="nav-item <%if(details.getMain_menu().equals("Intimation Type")){%>active open<%}%>">
              <a href="javascript:;" class="nav-link nav-toggle">
                  <i class="fa fa-exclamation-triangle" style = "color:white"></i>
                  <span class="title">Intimation Type</span>
                  <span class="arrow "></span>
              </a>
              <ul class="sub-menu">
                <%if(user_permission.contains("intimationType/add")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Add Intimation Type")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/intimationType/add" class="nav-link nav-toggle">
                    <i class="icon-plus"></i> Add Intimation Type
                  </a>
                </li>
                 <%} %>
                 <%if(user_permission.contains("intimationType/index")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Pending Intimation")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/intimationType/pending" class="nav-link nav-toggle">
                    <i class="icon-clock"></i> Pending Intimation
                  </a>
                </li>
                <%} %>
				<%if(user_permission.contains("intimationType/index")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Active Intimation")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/intimationType/active" class="nav-link nav-toggle">
                    <i class="icon-check"></i> Active Intimation
                  </a>
                </li>
                <%} %>
              </ul>
            </li>
            <%} %>
            <%if(user_permission.contains("location")){ %>
            <li class="nav-item <%if(details.getMain_menu().equals("Location")){%>active open<%}%>">
              <a href="javascript:;" class="nav-link nav-toggle">
                  <i class="icon-pointer" style = "color:white"></i>
                  <span class="title">Location</span>
                  <span class="arrow "></span>
              </a>
              <ul class="sub-menu">
                <%if(user_permission.contains("location/add")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Add Location")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/location/add" class="nav-link nav-toggle">
                    <i class="icon-plus"></i> Add Location
                  </a>
                </li>
                 <%} %>
                 <%if(user_permission.contains("location/index")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Pending Location")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/location/pending" class="nav-link nav-toggle">
                    <i class="icon-clock"></i> Pending Location
                  </a>
                </li>
                <%} %>
				<%if(user_permission.contains("location/index")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Active Location")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/location/active" class="nav-link nav-toggle">
                    <i class="icon-check"></i> Active Location
                  </a>
                </li>
                <%} %>
              </ul>
            </li>
            <%} %>
            <%if(user_permission.contains("mailConfig")){ %>
            <li class="nav-item <%if(details.getMain_menu().equals("Mail Config")){%>active open<%}%>">
              <a href="javascript:;" class="nav-link nav-toggle">
                  <i class="fa fa-inbox" style = "color:white"></i>
                  <span class="title">Mail Config</span>
                  <span class="arrow "></span>
              </a>
              <ul class="sub-menu">
                <%if(user_permission.contains("mailConfig/add")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Add Mail Config")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/mailConfig/add" class="nav-link nav-toggle">
                    <i class="icon-plus"></i> Add Mail Config
                  </a>
                </li>
                 <%} %>
                 <%if(user_permission.contains("mailConfig/index")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Pending Mail Config")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/mailConfig/pending" class="nav-link nav-toggle">
                    <i class="icon-clock"></i> Pending Mail Config
                  </a>
                </li>
                <%} %>
				<%if(user_permission.contains("mailConfig/index")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Active Mail Config")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/mailConfig/active" class="nav-link nav-toggle">
                    <i class="icon-check"></i> Active Mail Config
                  </a>
                </li>
                <%} %>
              </ul>
            </li>
            <%} %>
            <%if(user_permission.contains("investigationType")){ %>
            <li class="nav-item <%if(details.getMain_menu().equals("Investigation Type")){%>active open<%}%>">
              <a href="javascript:;" class="nav-link nav-toggle">
                  <i class="icon-grid"></i>
                  <span class="title">Investigation Types</span>
                  <span class="arrow "></span>
              </a>
              <ul class="sub-menu">
                <%if(user_permission.contains("investigationType/add")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Add Investigation Type")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/investigationType/addInvestigationType" class="nav-link nav-toggle">
                    <i class="icon-plus"></i> Add Investigation Type
                  </a>
                </li>
                <%} %>
				<%if(user_permission.contains("investigationType/index")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Pending Investigation Type")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/investigationType/pendingInvestigationType" class="nav-link nav-toggle">
                    <i class="icon-clock"></i> Pending Investigation Type
                  </a>
                </li>
                <%} %>
                <%if(user_permission.contains("investigationType/index")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Active Investigation Type")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/investigationType/activeInvestigationType" class="nav-link nav-toggle">
                    <i class="icon-check"></i> Active Investigation Type
                  </a>
                </li>
                <%} %>
              </ul>
            </li>
            <%} %>
            <li class="heading">
                <h3 class="uppercase">----</h3>
            </li>
            <%if(user_permission.contains("report")){ %> 
            <li class="nav-item <%if(details.getMain_menu().equals("Report")){%>active open<%}%>">
              <a href="javascript:;" class="nav-link nav-toggle">
                  <i class="icon-folder-alt"></i>
                  <span class="title">Report</span>
                  <span class="arrow"></span>
              </a>
              <ul class="sub-menu">
              	<%if(user_permission.contains("report/investigator")){ %>
	                <li class="nav-item <%if(details.getSub_menu1().equals("Top 15 Investigator")){%>active<%}%>">
	                  <a href="${pageContext.request.contextPath}/report/top15investigator" class="nav-link nav-toggle">
	                    <i class="icon-bar-chart"></i> Top 15 Investigator  
	                  </a>
	                </li>
                <%}%>
                <%if(user_permission.contains("report/vendorwise")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Vendor wise screen")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/report/vendorWiseScreen" class="nav-link nav-toggle">
                    <i class="fa fa-user"></i>  Vendor wise screen
                  </a>
                </li>
                <%}%>
                <%if(user_permission.contains("report/regionwise")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Region wise screen")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/report/regionWiseScreen" class="nav-link nav-toggle">
                    <i class="fa fa-globe"></i> Region wise screen
                  </a>
                </li>
                <%}%>
                  <%if(user_permission.contains("report/intimationTypeScreen")){ %>
                  <li class="nav-item <%if(details.getSub_menu1().equals("Intimation Type screen")){%>active<%}%>">
                    <a href="${pageContext.request.contextPath}/report/intimationTypeScreen" class="nav-link nav-toggle">
                     <i class="fa fa-exclamation-triangle"></i> Intimation Type screen
                    </a>
                </li>
                 <%}%>
                <%if(user_permission.contains("report/uploadedDocument")){ %>                
                  <li class="nav-item <%if(details.getSub_menu1().equals("Uploaded Document")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/report/uploadedDocument" class="nav-link nav-toggle">
                    <i class="fa fa-file-text"></i> Uploaded Document
                  </a>
                </li>
                <%}%>
                
              </ul>
            </li>
           <%} %>
           <%if(user_permission.contains("billingManagement")){ %> 
            <li class="nav-item <%if(details.getMain_menu().equals("Billing Management")){%>active open<%}%>">
              <a href="javascript:;" class="nav-link nav-toggle">
                  <i class="icon-grid"></i>
                  <span class="title">Billing management</span>
                  <span class="arrow "></span>
              </a>
              <ul class="sub-menu">
              <%if(user_permission.contains("billingManagement/enquiry")){ %>
                <li class="nav-item <%if(details.getSub_menu1().equals("Bill Enquiry")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/billManagement/bill_enquiry" class="nav-link nav-toggle">
                    <i class="icon-plus"></i> Bill Enquiry
                  </a>
                </li>
                 <%} %>
                 <%if(user_permission.contains("billingManagement/payment")){ %>
                 <li class="nav-item <%if(details.getSub_menu1().equals("Bill Payment")){%>active<%}%>">
                  <a href="${pageContext.request.contextPath}/billManagement/bill_payment" class="nav-link nav-toggle">
                    <i class="icon-clock"></i> Bill Payment
                  </a>
                </li>
                 <%} %>
              </ul>
            </li>
           <%} %>
            
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>
<!-- END SIDEBAR -->