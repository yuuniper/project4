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

<p>You are connected to the Project 4 Enterprise System database as a <span class="red-text">root-level user</span> user.</p>
<p>Please enter any SQL query or update command in the box below.</p>

<form action="RootUserServlet" method="post">
    <textarea id="sqlQuery" name="sqlQuery" rows="8" cols="50"><%= request.getParameter("sqlQuery") %></textarea><br><br>
    <button type="submit" name="executeCommand">Execute Command</button>
    <button type="button" onclick="resetForm()">Reset Form</button>
    <button type="button" onclick="clearResults()">Clear Results</button>
</form>

<hr>
<p>All execution results will appear below this line.</p>
<h3>Execution results:</h3>
<div id="executionResults" class="result">
    <%= request.getAttribute("tableHTML") %>
</div>

<script>
    function resetForm() {
        document.getElementById("sqlQuery").value = "";
    }

    function clearResults() {
        document.getElementById("executionResults").innerHTML = "";
    }
</script>
</body>
</html>
