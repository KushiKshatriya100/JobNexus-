<!-- src/main/webapp/jsp/post_job.jsp -->

<%@ page import="com.parent.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Post a Job - Job Portal</title>

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .post-job-form {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            margin-top: 30px;
        }
    </style>
</head>
<body>

<%
    User user = (session != null) ? (User) session.getAttribute("user") : null;

    if (user == null || !"recruiter".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
    <div class="ml-auto">
        <span class="navbar-text mr-3">
            Welcome, <%= user.getName() %> (Recruiter)
        </span>
        <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
</nav>

<div class="container">

    <div class="post-job-form">

        <h3 class="mb-4">Post a New Job</h3>

        <form action="<%= request.getContextPath() %>/post_job" method="post">

            <div class="form-group">
                <label for="title">Job Title:</label>
                <input type="text" id="title" name="title" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" rows="5" class="form-control" required></textarea>
            </div>

            <div class="form-group">
                <label for="location">Location:</label>
                <input type="text" id="location" name="location" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="salary">Salary:</label>
                <input type="text" id="salary" name="salary" class="form-control">
            </div>

            <button type="submit" class="btn btn-primary">Post Job</button>
            <a href="<%= request.getContextPath() %>/manage_jobs" class="btn btn-secondary ml-2">Back to Manage Jobs</a>

        </form>

    </div>

</div>

<!-- Optional Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
