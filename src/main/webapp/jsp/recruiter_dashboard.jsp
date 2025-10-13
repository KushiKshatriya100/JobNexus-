<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.parent.model.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"recruiter".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
%>

<html>
<head>
    <title>Recruiter Dashboard</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/recruiter_dashboard">Job Portal - Dashboard</a>
    <div class="ml-auto">
        <span class="navbar-text mr-3">Welcome, <%= user.getName() %> (Recruiter)</span>
        <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
</nav>

<div class="container mt-5">
    <h2 class="mb-4">Recruiter Dashboard</h2>

    <!-- Success message -->
    <% if (request.getParameter("jobPostSuccess") != null) { %>
        <div class="alert alert-success">Job posted successfully!</div>
    <% } %>
    <% if (request.getParameter("logout") != null) { %>
        <div class="alert alert-success">You have been logged out successfully.</div>
    <% } %>

    <div class="row">
        <!-- Total Jobs -->
        <div class="col-md-3 mb-4">
            <div class="card text-white bg-primary">
                <div class="card-body">
                    <h5 class="card-title">Total Jobs Posted</h5>
                    <h3><%= request.getAttribute("totalJobs") %></h3>
                </div>
            </div>
        </div>

        <!-- Total Applications -->
        <div class="col-md-3 mb-4">
            <div class="card text-white bg-success">
                <div class="card-body">
                    <h5 class="card-title">Total Applications</h5>
                    <h3><%= request.getAttribute("totalApplications") %></h3>
                </div>
            </div>
        </div>

        <!-- Interview -->
        <div class="col-md-3 mb-4">
            <div class="card text-white bg-info">
                <div class="card-body">
                    <h5 class="card-title">Interview</h5>
                    <h3><%= request.getAttribute("countUnderReview") %></h3>
                </div>
            </div>
        </div>

        <!-- Applied -->
        <div class="col-md-3 mb-4">
            <div class="card text-white bg-warning">
                <div class="card-body">
                    <h5 class="card-title">Applied</h5>
                    <h3><%= request.getAttribute("countApplied") %></h3>
                </div>
            </div>
        </div>

        <!-- Rejected -->
        <div class="col-md-3 mb-4">
            <div class="card text-white bg-danger">
                <div class="card-body">
                    <h5 class="card-title">Rejected</h5>
                    <h3><%= request.getAttribute("countRejected") %></h3>
                </div>
            </div>
        </div>

        <!-- Selected -->
        <div class="col-md-3 mb-4">
            <div class="card text-white bg-success">
                <div class="card-body">
                    <h5 class="card-title">Selected</h5>
                    <h3><%= request.getAttribute("countAccepted") %></h3>
                </div>
            </div>
        </div>
    </div>

    <!-- Action Buttons -->
    <div class="mt-4">
        <a href="<%= request.getContextPath() %>/manage_jobs" class="btn btn-outline-primary mr-2">Manage Jobs</a>
        <a href="<%= request.getContextPath() %>/view_applications" class="btn btn-outline-secondary">View Applications</a>
    </div>
</div>

<!-- Footer -->
<footer class="mt-5 py-3 bg-light text-center text-muted">
    &copy; 2025 Job Portal Project. All rights reserved.
</footer>

</body>
</html>
