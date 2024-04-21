<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Enterprise System Authentication</title>
</head>
<body>
<h1>Welcome to the Spring 2024 Project 4 Enterprise System</h1>
<p>A Servlet/JSP-based Multi-tiered Enterprise Application Using a Tomcat Container</p>
<p>User Authentication Page</p>

<form action="authenticationServlet" method="get">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">Click to Authenticate</button>
</form>
</body>
</html>
