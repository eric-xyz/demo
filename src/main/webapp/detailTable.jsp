<html>
<head>
	<%@ page import="com.jsontotable.model.HealthReport" %>
	<%@ page import="com.jsontotable.model.Dependency" %>
	<%@ page import="java.util.List" %>
	<link rel="stylesheet" type="text/css" href="details.css">
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	
	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
  <% if(null == request.getAttribute("healthReport") || request.getAttribute("healthReport") == null){ %>
  		<p>No data received, Please post JSON data to /details<p>
  <% } else{  %>  
  <% HealthReport healthReport = (HealthReport)request.getAttribute("healthReport"); %>  
  <% List<Dependency> depList = healthReport.getDependencies(); %>  
  <h2><%=healthReport.getServiceName() %> Health Report</h2>
  <div class="row">
  		<div class="col-sm-4">Overall Status</div>
  		<div class="col-sm-8"></div>
  		<div class="col-sm-2" 
  	<% if(healthReport.getOverallHealth().equals("unhealthy")){ %>
  			<%="style =\"background-color: red\"" %>
  			<%}else{ %>
  			<%="style =\"background-color: green\"" %>
  			<%} %>
  		><%= healthReport.getOverallHealth() %></div>
  </div>
  <div class="row">
  		<div class="col-sm-4">Dependency</div>
  		<div class="col-sm-4"></div>
  		<div class="col-sm-4"></div>
  </div>
  <table class="table table-condensed">
    <thead class="thead-inverse">
      <tr>
        <th>Service Name</th>
        <th>Status</th>
        <th>Url</th>
        <th>Latency in ms</th>
        <th>Reason</th>
      </tr>
    </thead>
    <tbody>
    <%for(int i = 0; i < depList.size(); ++i){ %>
    	<% Dependency dependency = depList.get(i); %>
    	<% boolean healthy = (dependency.getStatus() == null || dependency.getStatus().equals("healthy"));%>
	      <tr class=<%= healthy? "\"healthyRow\"" : "\"unhealthyRow\"" %>>
	        <td>
	        <%= null == dependency.getServiceName()? "" : dependency.getServiceName()%></td>
	        <td>
	        <%= null == dependency.getStatus()? "" : dependency.getStatus()%></td>
	        <td>
	        <%= null == dependency.getUrl()? "" : dependency.getUrl()%></td>
	        <td>
	        <%= null == dependency.getLatency()? "" : dependency.getLatency()%></td>
	        <td>
	        <%= null == dependency.getDetails()? "" : dependency.getDetails()%></td>
	      </tr>
     <% } %>
    </tbody>
  </table>
   <%} %>
</div>
</body>
</html>
