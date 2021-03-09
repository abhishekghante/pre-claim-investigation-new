<%@page import="java.time.LocalDate" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.preclaim.config.Config" %>
<%
HashMap<String, Integer> dashboard = (HashMap<String, Integer>) session.getAttribute("Dashboard Count");
String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
ArrayList<String> keys = new ArrayList<String>();
for(String item: dashboard.keySet())
	keys.add(item);
System.out.println(keys);
%>
<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <h3 class="page-title">Dashboard
            <small><%=Config.version %></small>
        </h3>
    </div>

    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"
    	<%if(dashboard.size() < 1) {%> style = "display:none" <%} %>>
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-comments"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup" data-value="">
                    	<% if(dashboard.size() >= 1) {%><%= dashboard.get(keys.get(0))%><%} %>
                   	</span>
                </div>
                <div class="desc"> <% if(dashboard.size() >= 1) {%><%= keys.get(0)%><%} %></div>
            </div>
            <a class="more" href="javascript:;"> 
                <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo2">View more</button>
                <div id="demo2" class="collapse">
                    <p><%=date %>&nbsp;&nbsp;&nbsp;&nbsp;
                    	<% if(dashboard.size() >= 1) {%><%= keys.get(0)%><%} %>
                    &nbsp;&nbsp;&nbsp;&nbsp;Active</p>
                </div>
            </a>
        </div>
    </div>

    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"
		<%if(dashboard.size() < 2) {%> style = "display:none" <%} %>>
        <div class="dashboard-stat red">
            <div class="visual">
                <i class="fa fa-comments"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup" data-value="">
                    	<% if(dashboard.size() >= 2) {%><%= dashboard.get(keys.get(1))%><%} %>
                    </span>
                </div>
                <div class="desc"> <% if(dashboard.size() >= 2) {%><%= keys.get(1)%><%} %> </div>
            </div>
            <a class="more" href="javascript:;"> 
                <a href="${pageContext.request.contextPath}/message/pending_message" 
                	class="btn btn-info">View more</a>
            </a>
        </div>
    </div>

    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"
    	<%if(dashboard.size() < 3) {%> style = "display:none" <%} %>>
        <div class="dashboard-stat green">
            <div class="visual">
                <i class="fa fa-users"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup" data-value="549">
                    	<% if(dashboard.size() >= 3) {%><%= dashboard.get(keys.get(2))%><%} %>
                    </span>
                </div>
                <div class="desc"> <% if(dashboard.size() >= 3) {%><%= keys.get(2)%><%} %> </div>
            </div>
            <a class="more" href="javascript:;"> 
                <a href="${pageContext.request.contextPath}/message/active_message" 
                	class="btn btn-info">View more</a>
            </a>
        </div>
    </div>

    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"
    	<%if(dashboard.size() < 4) {%> style = "display:none" <%} %>>
        <div class="dashboard-stat purple">
            <div class="visual">
                <i class="fa fa-globe"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup" data-value="89">
                    	<% if(dashboard.size() >= 4) {%><%= dashboard.get(keys.get(3))%><%} %>
                    </span>
                </div>
                <div class="desc"> <% if(dashboard.size() >= 4) {%><%= keys.get(3)%><%} %> </div>
            </div>
            <a class="more" href="javascript:;"> 
                <button onClick="return showAssignGraph();" type="button" class="btn btn-info">View more</button>
                <div id="demo5" class="collapse">
                    <p><%=date %>&nbsp;&nbsp;&nbsp;&nbsp;
                    <% if(dashboard.size() >= 4) {%><%= keys.get(3)%><%} %>
                    &nbsp;&nbsp;&nbsp;&nbsp;Active</p>
                </div>
            </a>
        </div>
    </div>

    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"
    	<%if(dashboard.size() < 5) {%> style = "display:none" <%} %>>
        <div class="dashboard-stat red">
            <div class="visual">
                <i class="fa fa-comments"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup" data-value="12,5">
                    	<% if(dashboard.size() >= 5) {%><%= dashboard.get(keys.get(4))%><%} %>
                   	</span>
                </div>
                <div class="desc"> <% if(dashboard.size() >= 5) {%><%= keys.get(4)%><%} %> </div>
            </div>
            <a class="more" href="javascript:;"> 
                <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo6">View more</button>
                <div id="demo6" class="collapse">
                    <p><%=date %>&nbsp;&nbsp;&nbsp;&nbsp;
                    <% if(dashboard.size() >= 5) {%><%= keys.get(4)%><%} %>
                    &nbsp;&nbsp;&nbsp;&nbsp;Active</p>
                </div>
            </a>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"
    	<%if(dashboard.size() < 6) {%> style = "display:none" <%} %>>
        <div class="dashboard-stat red">
            <div class="visual">
                <i class="fa fa-comments"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup" data-value="12,5">
                    	<% if(dashboard.size() >= 6) {%><%= dashboard.get(keys.get(5))%><%} %>
                    </span>
                </div>
                <div class="desc"> <% if(dashboard.size() >= 6) {%><%= keys.get(5)%><%} %> </div>
            </div>
            <a class="more" href="javascript:;"> 
                <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo7">View more</button>
                <div id="demo7" class="collapse">
                    <p><%=date %>&nbsp;&nbsp;&nbsp;&nbsp;
                    <% if(dashboard.size() >= 6) {%><%= keys.get(5)%><%} %>
                    &nbsp;&nbsp;&nbsp;&nbsp;Active</p>
                </div>
            </a>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"
    	<%if(dashboard.size() < 7) {%> style = "display:none" <%} %>>
        <div class="dashboard-stat purple">
            <div class="visual">
                <i class="fa fa-comments"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup" data-value="12,5">
                    	<% if(dashboard.size() >= 7) {%><%= dashboard.get(keys.get(6))%><%} %>
                    </span>
                </div>
                <div class="desc"> <% if(dashboard.size() >= 7) {%><%= keys.get(6)%><%} %> </div>
            </div>
            <a class="more" href="javascript:;"> 
                <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo8">View more</button>
                <div id="demo8" class="collapse">
                    <p><%=date %>&nbsp;&nbsp;&nbsp;&nbsp;
                    <% if(dashboard.size() >= 7) {%><%= keys.get(6)%><%} %>
                    &nbsp;&nbsp;&nbsp;&nbsp;Active</p>
                </div>
            </a>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"
    	<%if(dashboard.size() < 8) {%> style = "display:none" <%} %>>
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-comments"></i>
            </div>
            <div class="details">
                <div class="number">
                    <span data-counter="counterup" data-value="12,5">
                    	<% if(dashboard.size() >= 8) {%><%= dashboard.get(keys.get(7))%><%} %>
                    </span>
                </div>
                <div class="desc"> <% if(dashboard.size() >= 8) {%><%= keys.get(7)%><%} %> </div>
            </div>
            <a class="more" href="javascript:;"> 
                <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo9">View more</button>
                <div id="demo9" class="collapse">
                    <p><%=date %>&nbsp;&nbsp;&nbsp;&nbsp;
                    <% if(dashboard.size() >= 8) {%><%= keys.get(7)%><%} %>
                    &nbsp;&nbsp;&nbsp;&nbsp;Active</p>
                </div>
            </a>
        </div>
    </div>
</div>


<div id="testmodal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body" style="height: 430px;">
                <div class="col-md-12 col-sm-12">
                    <figure class="highcharts-figure">
                        <div id="container1"></div>
                    </figure>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-more.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
<script type="text/javascript">
function showAssignGraph(){
  $("#testmodal").modal('show');
}

Highcharts.chart('container1', {
    chart: {
        type: 'column'
    },
    title: {
        text: 'Assigned investigation'
    },
    subtitle: {
        text: 'Last Six Months'
    },
    xAxis: {
        categories: [
            'Dec',
            'Jan',
            'Feb',
            'Mar',
            'April',
            'Jun'
        ],
        crosshair: true
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Likes'
        }
    },
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    series: [{
        name: 'Report awaited',
        data: [135.6, 148.5, 216.4, 194.1, 95.6, 54.4]

    }, {
        name: 'Further requirement raised',
        data: [105.0, 104.3, 91.2, 83.5, 106.6, 92.3]

    }, {
        name: 'Sent for reinvestigation',
        data: [59.0, 59.6, 52.4, 65.2, 59.3, 51.2]

    }, {
        name: 'Review awaited',
        data: [57.4, 60.4, 47.6, 39.1, 46.8, 51.1]

    }, {
        name: 'Awaiting opinion',
        data: [105.0, 104.3, 91.2, 83.5, 106.6, 92.3]

    }, {
        name: 'Closed cases',
        data: [57.4, 60.4, 47.6, 39.1, 46.8, 51.1]

    }]
});
</script>