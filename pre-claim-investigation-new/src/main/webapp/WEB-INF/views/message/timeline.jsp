<%@page import = "java.util.ArrayList" %>
<%@page import = "com.preclaim.models.CaseHistory"%>
<%
ArrayList<CaseHistory> case_history = (ArrayList<CaseHistory>) session.getAttribute("case_history");
session.removeAttribute("case_history");
%>
<link href="${pageContext.request.contextPath}/resources/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resources/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<style>
<style>

/* The actual timeline (the vertical ruler) */
.timeline {
  position: relative;
  border: 1px solid Red;
}

/* The actual timeline (the vertical ruler) */
.timeline::after {
  content: '';
  position: absolute;
  width: 6px;
  background-color: white;
  top: 0%;
  bottom:0%;
  left: 20%;
  margin-left: -3px;
  border: 1px solid Green;
}


/* Container around content */
.container {
  padding: 10px 40px;
  position: relative;
  background-color: inherit;
  width: 70%;
  order: 1px solid red;
}

/* The circles on the timeline */
.container::after {
  content: '';
  position: absolute;
  width: 30px;
  height: 30px;
  right: -17px;
  background-color: white;
  border: 4px solid #FF9F55;
  top: 15px;
  border-radius: 50%;
  z-index: 1;
}

/* Place the container to the left */
.left {
  left: 0;
}

/* Place the container to the right */
.right {
  left: 5%;
}

/* Add arrows to the left container (pointing right) */
.left::before {
  content: " ";
  height: 0;
  position: absolute;
  top: 22px;
  width: 0;
  z-index: 1;
  right: 30px;
  border: 1px solid Black;
  border-width: 10px 0 10px 10px;
  border-color: transparent transparent transparent Black;
}

/* Add arrows to the right container (pointing left) */
.right::before {
  content: " ";
  height: 0;
  position: absolute;
  top: 22px;
  width: 0;
  z-index: 1;
  left: 30px;
  border: 1px solid Black;
  border-width: 10px 10px 10px 0;
  border-color: transparent Black transparent transparent;
}

/* Fix the circle for containers on the right side */
.right::after {
  left: -16px;
}

/* The actual content */
.content {
  padding: 20px 30px;
  background-color: white;
  position: relative;
  border-radius: 6px;
  border: 1px solid Black;
  color:green;
}

/* Media queries - Responsive timeline on screens less than 600px wide */
@media screen and (max-width: 600px) {
  /* Place the timelime to the left */
  .timeline::after {
  left: 31px;
  }
  
  /* Full-width containers */
  .container {
  width: 100%;
  padding-left: 70px;
  padding-right: 25px;
  }
  
  /* Make sure that all arrows are pointing leftwards */
  .container::before {
  left: 60px;
  border-width: 10px 10px 10px 0;
  border-color: transparent white transparent transparent;
  
  }

  /* Make sure all circles are at the same spot */
  .left::after, .right::after {
  left: 15px;
  }
  
  /* Make all right containers behave like the left ones */
  .right {
  left: 0%;
  }
}
</style>

<div class="row">
  <div class="col-xs-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
            <i class="icon-users font-green-sharp"></i>
            <span class="caption-subject font-green-sharp sbold">Case History</span>
        </div>
        <div class="actions">
            <div class="btn-group">
              <a href="${pageContext.request.contextPath}/message/pending_message" class="btn green-haze btn-outline btn-xs pull-right" data-toggle="tooltip" data-toggle="tooltip" title="Back" style="margin-right: 5px;" data-original-title="Back">
                <i class="fa fa-reply"></i>
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
		          <div class="timeline">
		          <%if (case_history == null){ %>
		          
		           <div class="container right">
						<div class="content">
							<div class = "row">
								<div class = "col-md-6">Name :- </div>
								<div class = "col-md-6">Received Date :-  </div>
							</div>
							<div class = "row">
								<div class = "col-md-12">Role :-  </div>
							</div>
							<div class = "row">
								<div class = "col-md-12">Status :-  </div>
							</div>
							<div class = "row">
								<div class = "col-md-12">Remarks :-  </div>
							</div>
						</div>
					</div>
		          
		          	<%} %>
		          
		          <%if (case_history!=null){ %>
		          <%for(CaseHistory list: case_history) {%>
		             <div class="container right">
						<div class="content">
							<div class = "row">
								<div class = "col-md-6">Name :- <%=list.getFromUserName() %></div>
								<div class = "col-md-6">Received Date :- <%=list.getCreatedDate() %></div>
							</div>
							<div class = "row">
								<div class = "col-md-12">Role :- <%=list.getRole() %></div>
							</div>
							<div class = "row">
								<div class = "col-md-12">Status :- <%=list.getCaseStatus() %></div>
							</div>
							<div class = "row">
								<div class = "col-md-12">Remarks :- <%=list.getRemarks() %></div>
							</div>
						</div>
					</div>
					<%} %>
					<%} %>
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
