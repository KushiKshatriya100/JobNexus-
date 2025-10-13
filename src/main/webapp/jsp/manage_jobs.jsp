<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.parent.model.Job" %>
<%@ page import="com.parent.model.User" %>

<%
    // Use implicit session object directly
    User user = (session != null) ? (User) session.getAttribute("user") : null;

    if (user == null || !"recruiter".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }

    List<Job> jobs = (List<Job>) request.getAttribute("jobs");
    String success = request.getParameter("success");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage My Jobs - Job Portal</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        body {
            padding-top: 70px;
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top shadow">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
    <div class="ml-auto">
        <span class="navbar-text mr-3">Welcome, <%= user.getName() %> (Recruiter)</span>
        <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
</nav>

<div class="container mt-4">

    <h3 class="mb-4">Manage My Posted Jobs</h3>

    <% if ("1".equals(success)) { %>
        <div class="alert alert-success">Job deleted successfully!</div>
    <% } %>

    <% if (jobs == null || jobs.isEmpty()) { %>
        <div class="alert alert-info">You have not posted any jobs yet.</div>
    <% } else { %>
        <table class="table table-striped table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Location</th>
                    <th>Salary</th>
                    <th>Posted On</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Job job : jobs) { %>
                    <tr>
                        <td><%= job.getId() %></td>
                        <td><%= job.getTitle() %></td>
                        <td><%= job.getLocation() %></td>
                        <td><%= job.getSalary() %></td>
                        <td><%= job.getCreatedAt() %></td>
                        <td>
                            <form action="<%= request.getContextPath() %>/delete_job" method="post" style="display: inline;">
                                <input type="hidden" name="jobId" value="<%= job.getId() %>">
                                <button type="submit" class="btn btn-danger btn-sm"
                                        onclick="return confirm('Are you sure you want to delete this job?');">Delete</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>

    <div class="mt-4">
        <a href="<%= request.getContextPath() %>/jsp/post_job.jsp" class="btn btn-primary">Post New Job</a>
        <a href="<%= request.getContextPath() %>/jsp/index.jsp" class="btn btn-secondary ml-2">Back to Home</a>
    </div>

</div>

<!-- Footer -->
<footer class="mt-5 py-3 bg-light text-center text-muted">
    &copy; 2025 Job Portal Project. All rights reserved.
</footer>

<!-- Optional Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
