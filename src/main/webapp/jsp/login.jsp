<!-- src/main/webapp/jsp/login.jsp -->

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Job Portal</title>

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        .login-container {
            max-width: 400px;
            margin: 60px auto;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
        }
    </style>
</head>
<body class="bg-light">

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
</nav>

<div class="container">
    <div class="login-container">
        <h3 class="text-center mb-4">Login</h3>

        <form action="<%= request.getContextPath() %>/login" method="post">
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" class="form-control" required>
            </div>

            <div class="form-group">
                <label>Password:</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Login</button>
        </form>

        <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger mt-3" role="alert">
                Invalid email or password!
            </div>
        <% } %>

        <div class="text-center mt-3">
            <a href="<%= request.getContextPath() %>/jsp/register.jsp">New user? Register here</a>
        </div>
    </div>
</div>

<!-- Optional Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
