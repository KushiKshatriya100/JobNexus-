<!-- src/main/webapp/jsp/my_applications.jsp -->

<%@ page import="java.util.List" %>
<%@ page import="com.parent.model.User" %>
<%@ page import="com.parent.model.Application" %>

<%
    User user = (session != null) ? (User) session.getAttribute("user") : null;

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }

    String success = request.getParameter("success");
    List<Application> applications = (List<Application>) request.getAttribute("applications");
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Applications - Job Portal</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body { background-color: #f8f9fa; padding-top: 70px; }
        .application-card {
            border: 1px solid #ddd;
            padding: 25px;
            margin-bottom: 25px;
            border-radius: 12px;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
        }
        .badge-status {
            font-size: 14px;
            padding: 5px 10px;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top shadow-sm">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
    <div class="ml-auto">
        <span class="navbar-text mr-3">Welcome, <%= user.getName() %></span>
        <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
</nav>

<div class="container mt-5">
    <h3 class="mb-4">My Applications</h3>

    <% if ("1".equals(success)) { %>
        <div class="alert alert-success">Your application has been submitted!</div>
    <% } %>

    <% if (applications != null && !applications.isEmpty()) { %>
        <% for (Application app : applications) {
            String status = app.getStatus();
            String statusClass = "badge-secondary";
            if ("Accepted".equalsIgnoreCase(status)) {
                statusClass = "badge-success";
            } else if ("Rejected".equalsIgnoreCase(status)) {
                statusClass = "badge-danger";
            } else if ("Pending".equalsIgnoreCase(status)) {
                statusClass = "badge-warning";
            }
        %>
            <div class="application-card">
                <h4>
                    <a href="#" style="text-decoration: none; color: #007bff;">
                        <%= app.getJobTitle() %>
                    </a>
                    <span class="badge badge-status <%= statusClass %>"><%= status %></span>
                </h4>
                <p><strong>Location:</strong> <%= app.getJobLocation() %></p>
                <p><strong>Salary:</strong> <%= app.getJobSalary() %></p>
                <p><strong>Applied At:</strong> <%= app.getAppliedAt() %></p>
                <p><strong>Resume:</strong></p>
                <textarea class="form-control mb-2" rows="4" readonly><%= app.getResume() %></textarea>
            </div>
        <% } %>
    <% } else { %>
        <div class="alert alert-info">You have not applied to any jobs yet.</div>
    <% } %>

    <div class="mt-4">
        <a class="btn btn-primary" href="<%= request.getContextPath() %>/jobs">Back to Job List</a>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
