<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.parent.model.User" %>

<%
    User user = (User) session.getAttribute("user");
%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/">
            Job Portal
        </a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">

            <ul class="navbar-nav ml-auto">

                <% if (user == null) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/jsp/login.jsp">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/jsp/register.jsp">Register</a>
                    </li>

                <% } else if ("recruiter".equals(user.getRole())) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/recruiter_dashboard">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/manage_jobs">Manage Jobs</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/view_applications">Applications</a>
                    </li>
                    <li class="nav-item">
                        <span class="nav-link">Hi, <%= user.getName() %> (Recruiter)</span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
                    </li>

                <% } else if ("candidate".equals(user.getRole())) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/view_jobs">Browse Jobs</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/my_applications">My Applications</a>
                    </li>
                    <li class="nav-item">
                        <span class="nav-link">Hi, <%= user.getName() %> (Candidate)</span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
                    </li>
                <% } %>

            </ul>

        </div>
    </div>
</nav>
