<%@ page import="java.sql.*" %>
<html>
<body>
<h2>Librarian Dashboard</h2>
<p>Welcome, <%= session.getAttribute("email") %> (Role: <%= session.getAttribute("role") %>)</p>
<p><a href="logout">Logout</a></p>
</body>
</html>