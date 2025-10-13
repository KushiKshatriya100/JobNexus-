<!-- src/main/webapp/jsp/register.jsp -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Job Portal</title>

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4 mx-auto" style="max-width: 500px;">
        <h3 class="mb-4">Create Your Account</h3>

        <form action="../register" method="post">
            <div class="form-group">
                <label>Name:</label>
                <input type="text" name="name" class="form-control" required />
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" class="form-control" required />
            </div>

            <div class="form-group">
                <label>Password:</label>
                <input type="password" name="password" class="form-control" required />
            </div>

            <div class="form-group">
                <label>Role:</label>
                <select name="role" class="form-control" required>
                    <option value="">-- Select Role --</option>
                    <option value="job_seeker">Job Seeker</option>
                    <option value="recruiter">Recruiter</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Register</button>
        </form>

        <%
            String error = request.getParameter("error");
            if (error != null && error.equals("1")) {
        %>
            <p class="mt-3 text-danger">Registration failed. Please try again.</p>
        <%
            }
        %>

        <p class="mt-3">
            Already have an account?
            <a href="<%= request.getContextPath() %>/jsp/login.jsp">Login here</a>
        </p>
    </div>

</div>

<!-- Optional Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
