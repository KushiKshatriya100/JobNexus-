<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/header.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.parent.model.Application" %>

<html>
<head>
    <title>View Applications</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4">View Applications</h2>

    <table class="table table-bordered">
        <thead class="thead-dark">
            <tr>
                <th>Application ID</th>
                <th>Candidate Name</th>
                <th>Email</th>
                <th>Job Title</th>
                <th>Resume</th>
                <th>Status</th>
                <th>Applied At</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <%
            List<Application> applications = (List<Application>) request.getAttribute("applications");

            if (applications != null && !applications.isEmpty()) {
                for (Application app : applications) {
        %>
            <tr>
                <td><%= app.getId() %></td>
                <td><%= app.getUserName() %></td>
                <td><%= app.getUserEmail() %></td>
                <td><%= app.getJobTitle() %></td>
                <td>
                    <% if (app.getResume() != null && !app.getResume().trim().isEmpty()) { %>
                        <a href="<%= app.getResume() %>" target="_blank">View Resume</a>
                    <% } else { %>
                        N/A
                    <% } %>
                </td>
                <td><%= app.getStatus() %></td>
                <td><%= app.getAppliedAt() %></td>
                <td>
                    <form method="post" action="<%= request.getContextPath() %>/update_application_status">
                        <input type="hidden" name="applicationId" value="<%= app.getId() %>" />
                        <select name="status" class="form-control mb-2">
                            <option value="applied" <%= "applied".equalsIgnoreCase(app.getStatus()) ? "selected" : "" %>>Applied</option>
                            <option value="under review" <%= "under review".equalsIgnoreCase(app.getStatus()) ? "selected" : "" %>>Under Review</option>
                            <option value="rejected" <%= "rejected".equalsIgnoreCase(app.getStatus()) ? "selected" : "" %>>Rejected</option>
                            <option value="accepted" <%= "accepted".equalsIgnoreCase(app.getStatus()) ? "selected" : "" %>>Accepted</option>
                        </select>
                        <button type="submit" class="btn btn-primary btn-sm">Change Status</button>
                    </form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="8" class="text-center">No applications found.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="mt-3">
        <a href="<%= request.getContextPath() %>/jsp/index.jsp" class="btn btn-secondary">Back to Home</a>
    </div>

</div>

</body>
</html>
