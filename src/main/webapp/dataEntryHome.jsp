<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Data Entry Home</title>
    <style>
        .red-text {
            color: red;
        }
        .form-box {
            margin-bottom: 10px;
        }
        .form-box label {
            display: inline-block;
            width: 100px;
            text-align: right;
            margin-right: 10px;
        }
        .form-box textarea {
            width: 200px;
            height: 20px;
        }
        form {
            margin-bottom: 20px;
        }
        input[type="text"] {
            padding: 8px;
            margin-right: 10px;
            margin-bottom: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        input[type="submit"] {
            padding: 8px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Welcome to the Spring 2024 Project 4 Enterprise System</h1>
<p>Data Entry Application</p>
<p>You are connected to the Project 4 Enterprise System database as a <span class="red-text">data-entry-level</span> user.</p>
<p>Enter the data values in a form below to add a new record to the corresponding database table.</p>

<hr>
<h3>Suppliers Record Insert</h3>
<form action="SuppliersInsertServlet" method="post">
    <input type="text" id="supplier-snum" name="snum" placeholder="snum">
    <input type="text" id="supplier-sname" name="sname" placeholder="sname">
    <input type="text" id="supplier-status" name="status" placeholder="status">
    <input type="text" id="supplier-city" name="city" placeholder="city">
    <br><br>
    <input type="submit" id="enter-supplier" value="Enter Supplier Record Into Database">
    <input type="submit" id="clear-supplier-results" value="Clear Data and Results">
</form>
<h3>Parts Record Insert</h3>
<form action="PartsInsertServlet" method="post">
    <input type="text" id="part-pnum" name="pnum" placeholder="pnum">
    <input type="text" id="part-pname" name="pname" placeholder="pname">
    <input type="text" id="part-color" name="color" placeholder="color">
    <input type="text" id="part-weight" name="weight" placeholder="weight">
    <input type="text" id="part-city" name="city" placeholder="city">
    <br><br>
    <input type="submit" id="enter-part" value="Enter Part Record Into Database">
    <input type="submit" id="clear-part-results" value="Clear Data and Results">
</form>

<h3>Job Record Insert</h3>
<form action="JobInsertServlet" method="post">
    <input type="text" id="job-jnum" name="jnum" placeholder="jnum">
    <input type="text" id="job-jname" name="jname" placeholder="jname">
    <input type="text" id="job-numworkers" name="numworkers" placeholder="numworkers">
    <input type="text" id="job-city" name="city" placeholder="city">
    <br><br>
    <input type="submit" id="enter-job" value="Enter Job Record Into Database">
    <input type="submit" id="clear-job-results" value="Clear Data and Results">
</form>

<h3>Shipments Record Insert</h3>
<form action="ShipmentInsertServlet" method="post">
    <input type="text" id="shipment-snum" name="shipment-snum" placeholder="snum">
    <input type="text" id="shipment-pnum" name="shipment-pnum" placeholder="pnum">
    <input type="text" id="shipment-jnum" name="shipment-jnum" placeholder="jnum">
    <input type="text" id="shipment-quantity" name="shipment-quantity" placeholder="quantity">
    <br><br>
    <input type="submit" id="enter-shipment" value="Enter Shipment Record Into Database">
    <input type="submit" id="clear-shipment-results" value="Clear Data and Results">
</form>

<hr>
<h2>Execution Results:</h2>
<%
    String executionMessage = (String) request.getAttribute("executionMessage");
    if (executionMessage != null && !executionMessage.isEmpty()) {
%>
<p><%= executionMessage %></p>
<%
    }
%>
</body>
</html>
