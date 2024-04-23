<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Root Home</title>
    <style>
        .red-text {
            color: red;
        }
        .result {
            border: 1px solid black;
            padding: 10px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<h1>Welcome to the Spring 2024 Project 4 Enterprise System</h1>
<p>A Servlet/JSP-based Multi-tiered Enterprise Application Using a Tomcat Container</p>

<p>You are connected to the Project 4 Enterprise System database as a <span class="red-text">accountant-level</span> user.</p>
<p>Please select the operation you would like to perform from the list below:</p>
<a href="AccountantServlet?sqlQuery=CALL Get_The_Maximum_Status_Of_All_Suppliers()">Get the maximum status value of All suppliers</a><br>
<a href="AccountantServlet?sqlQuery=CALL Get_The_Sum_Of_All_Parts_Weights()">Get the Total weight of all parts</a><br>
<a href="AccountantServlet?sqlQuery=CALL Get_The_Total_Number_Of_Shipments()">Get the total number of shipments</a><br>
<a href="AccountantServlet?sqlQuery=CALL Get_The_Name_Of_The_Job_With_The_Most_Workers()">Get the Name and Number of Workers of the Job with the most Workers</a><br>
<a href="AccountantServlet?sqlQuery=CALL List_The_Name_And_Status_Of_All_Suppliers()">List the name and status of Every Supplier</a><br>

<p>All execution results will appear below this line.</p>
<h3>Execution results:</h3>

<%
    ResultSet results = (ResultSet) request.getAttribute("results");
    if (results != null) {
%>
<table border="1">
    <%
        try {
            out.println("<tr>");
            for (int i = 1; i <= results.getMetaData().getColumnCount(); i++) {
                out.println("<th>" + results.getMetaData().getColumnName(i) + "</th>");
            }
            out.println("</tr>");

            while (results.next()) {
    %>
    <tr>
        <%
            for (int i = 1; i <= results.getMetaData().getColumnCount(); i++) {
        %>
        <td><%= results.getString(i) %></td>
        <%
            }
        %>
    </tr>
    <%
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    %>
</table>
<%
} else {
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<p><%= errorMessage %></p>
<%
        }
    }
%>

</body>
</html>
