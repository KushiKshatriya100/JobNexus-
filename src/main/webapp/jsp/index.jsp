<!-- src/main/webapp/jsp/index.jsp -->

<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.parent.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome - Job Portal</title>

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        body {
            padding-top: 70px; /* Space for navbar */
        }
        .welcome-container {
            max-width: 700px;
            margin: 40px auto;
            text-align: center;
        }
        footer {
            margin-top: 50px;
            padding: 20px 0;
            background-color: #f8f9fa;
            text-align: center;
            color: #888;
            font-size: 14px;
        }
    </style>
</head>
<body class="bg-light">

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top shadow">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
    <div class="ml-auto">
        <%
            User user = (session != null) ? (User) session.getAttribute("user") : null;
            if (user != null) {
        %>
            <span class="navbar-text mr-3">
                Welcome, <%= user.getName() %> (<%= user.getRole() %>)
            </span>
            <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
        <% } else { %>
            <a class="btn btn-primary mr-2" href="<%= request.getContextPath() %>/jsp/login.jsp">Login</a>
            <a class="btn btn-success" href="<%= request.getContextPath() %>/jsp/register.jsp">Register</a>
        <% } %>
    </div>
</nav>

<div class="welcome-container">
    <h1 class="mb-4">Welcome to Job Portal</h1>

    <p class="lead">Your gateway to great career opportunities.</p>

    <div class="mt-4">
        <!-- Common button for all -->
        <a class="btn btn-info btn-lg m-2" href="<%= request.getContextPath() %>/jobs">View Jobs</a>

        <%
            if (user != null) {
                if ("recruiter".equalsIgnoreCase(user.getRole())) {
        %>
                    <!-- Recruiter buttons -->
                    <a class="btn btn-warning btn-lg m-2" href="<%= request.getContextPath() %>/jsp/post_job.jsp">Post a Job</a>
                    <a class="btn btn-secondary btn-lg m-2" href="<%= request.getContextPath() %>/manage_jobs">Manage My Jobs</a>
        <%
                } else if ("applicant".equalsIgnoreCase(user.getRole())) {
        %>
                    <!-- Applicant button -->
                    <a class="btn btn-secondary btn-lg m-2" href="<%= request.getContextPath() %>/my_applications">My Applications</a>
        <%
                }
        %>
                <!-- Common Profile button -->
                <a class="btn btn-dark btn-lg m-2" href="<%= request.getContextPath() %>/jsp/profile.jsp">My Profile</a>
        <%
            } else {
                // Guest user
        %>
                <a class="btn btn-primary btn-lg m-2" href="<%= request.getContextPath() %>/jsp/login.jsp">Login</a>
                <a class="btn btn-success btn-lg m-2" href="<%= request.getContextPath() %>/jsp/register.jsp">Register</a>
        <%
            }
        %>
    </div>
</div>

<!-- Footer -->
<footer>
    &copy; 2025 Job Portal Project. All rights reserved.
</footer>

<!-- Optional Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
