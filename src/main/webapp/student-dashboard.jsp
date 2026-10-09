<%
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("STUDENT")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<body>
<h2>Student Dashboard</h2>
<p>Welcome, <%= session.getAttribute("email") %> (Role: <%= session.getAttribute("role") %>)</p>
<p><a href="logout">Logout</a></p>
</body>
</html>