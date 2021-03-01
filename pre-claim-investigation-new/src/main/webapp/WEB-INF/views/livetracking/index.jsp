<%@page import = "java.util.List" %>
<%@page import = "com.preclaim.models.CaseDetails" %>
<%
List<CaseDetails> case_list = (List<CaseDetails>) session.getAttribute("live_cases");
session.removeAttribute("live_cases");
%>
<style type="text/css">
#imgAccount { display:none;}
</style>
<div class="row">
  <div class="col-md-12 col-sm-12">
    <div class="portlet box">
      <div class="portlet-title">
        <div class="caption">
            <i class="icon-user font-green-sharp"></i>
            <span class="caption-subject font-green-sharp sbold">Live tracking for Investigator</span>
        </div>
        <div class="actions"></div>
      </div>
    </div>
    <div class="box box-primary">
      <div class="box-body">
        <div class="row">
          <div class="col-md-12">
            <div id="map" style="width: 100%; height: 500px;"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
<script type="text/javascript">
  var locations = [];
  <%if(case_list != null){ int i = 1;
	for(CaseDetails cases:case_list) {%>
		locations.push(
				['<a href="${pageContext.request.contextPath}/message/active_message%>" title="View All Cases" class="btn red-haze btn-outline btn-xs">View Case</a>',
		<%=cases.getLongitude()%>,<%=cases.getLatitude()%>,<%=i%>]);
  <%i++;}}%>

  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 10,
    center: new google.maps.LatLng(19.112998, 72.8758992),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  });

  var infowindow = new google.maps.InfoWindow();

  var marker, i;

  for (i = 0; i < locations.length; i++) {  
    marker = new google.maps.Marker({
      position: new google.maps.LatLng(locations[i][1], locations[i][2]),
      map: map
    });

    google.maps.event.addListener(marker, 'click', (function(marker, i) {
      return function() {
        infowindow.setContent(locations[i][0]);
        infowindow.open(map, marker);
      }
    })(marker, i));
  }
</script>