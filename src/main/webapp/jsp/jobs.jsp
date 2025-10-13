<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.parent.model.Job" %>
<%@ page import="com.parent.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Available Jobs - Job Portal</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 70px;
        }
        .job-card {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            margin-bottom: 30px;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top shadow">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
    <div class="ml-auto">
        <%
            User currentUser = (session != null) ? (User) session.getAttribute("user") : null;
            if (currentUser != null) {
        %>
            <span class="navbar-text mr-3">
                Welcome, <%= currentUser.getName() %> (<%= currentUser.getRole() %>)
            </span>
            <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
        <% } else { %>
            <a class="btn btn-primary" href="<%= request.getContextPath() %>/jsp/login.jsp">Login</a>
        <% } %>
    </div>
</nav>

<div class="container mt-5">

    <h2 class="mb-4">Available Jobs</h2>

    <%
        List<Job> jobList = (List<Job>) request.getAttribute("jobList");
        boolean canApply = (currentUser != null && "job_seeker".equalsIgnoreCase(currentUser.getRole()));

        if (jobList != null && !jobList.isEmpty()) {
            for (Job job : jobList) {
    %>
    <div class="job-card shadow-sm">
        <h4><%= job.getTitle() %></h4>
        <p><strong>Location:</strong> <%= job.getLocation() %></p>
        <p><strong>Salary:</strong> <%= job.getSalary() %></p>
        <p><strong>Description:</strong> <%= job.getDescription() %></p>
        <p><strong>Posted At:</strong> <%= job.getCreatedAt() %></p>

        <div>
            <% if (canApply) { %>
                <form action="<%= request.getContextPath() %>/apply" method="post" class="d-inline">
                    <input type="hidden" name="jobId" value="<%= job.getId() %>">
                    <input type="hidden" name="resume" value="default_resume_text">
                    <button type="submit" class="btn btn-primary">Apply</button>
                </form>
            <% } else if (currentUser == null) { %>
                <a href="<%= request.getContextPath() %>/jsp/login.jsp" class="btn btn-warning">Login to Apply</a>
            <% } else { %>
                <button class="btn btn-secondary" disabled title="Only applicants can apply">Apply (Not Allowed)</button>
            <% } %>
        </div>
    </div>
    <%
            }
        } else {
    %>
    <p>No jobs available at the moment.</p>
    <%
        }
    %>

    <a href="<%= request.getContextPath() %>/jsp/index.jsp" class="btn btn-secondary mt-3">Back to Home</a>

</div>

<!-- Optional Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
