<!-- src/main/webapp/jsp/application_success.jsp -->

<%@ page import="com.parent.model.User" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession session = request.getSession(false);
    User user = (session != null) ? (User) session.getAttribute("user") : null;
%>

<!DOCTYPE html>
<html>
<head>
    <title>Application Success - Job Portal</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .status-container {
            max-width: 600px;
            margin: 80px auto;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            text-align: center;
        }
    </style>
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
    <div class="ml-auto">
        <% if (user != null) { %>
            <span class="navbar-text mr-3">Welcome, <%= user.getName() %></span>
            <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
        <% } else { %>
            <a class="btn btn-primary mr-2" href="<%= request.getContextPath() %>/jsp/login.jsp">Login</a>
        <% } %>
    </div>
</nav>

<div class="status-container">
    <h3 class="mb-4">Application Submitted</h3>
    <p class="lead text-success"><%= request.getAttribute("message") %></p>
    <a class="btn btn-info mt-3" href="<%= request.getContextPath() %>/jobs">Back to Job List</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
