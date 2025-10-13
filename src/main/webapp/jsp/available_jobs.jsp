<%@ page import="java.util.List" %>
<%@ page import="com.parent.model.Job" %>

<!DOCTYPE html>
<html>
<head>
    <title>Available Jobs - Job Portal</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .available-jobs {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            margin-top: 30px;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
</nav>

<div class="container">

    <div class="available-jobs">

        <h3 class="mb-4">Available Jobs</h3>

        <table class="table table-bordered">
            <thead class="thead-light">
                <tr>
                    <th>Job Title</th>
                    <th>Description</th>
                    <th>Location</th>
                    <th>Salary</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Job> jobs = (List<Job>) request.getAttribute("jobs");
                    if (jobs != null && !jobs.isEmpty()) {
                        for (Job job : jobs) {
                %>
                <tr>
                    <td><%= job.getTitle() %></td>
                    <td><%= job.getDescription() %></td>
                    <td><%= job.getLocation() %></td>
                    <td><%= job.getSalary() %></td>
                    <td>
                        <!-- Use POST form instead of GET link -->
                        <form action="<%= request.getContextPath() %>/apply" method="post" style="display:inline;">
                            <input type="hidden" name="jobId" value="<%= job.getId() %>">
                            <button type="submit" class="btn btn-primary btn-sm">Apply</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">No jobs available at the moment.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <a href="<%= request.getContextPath() %>/jsp/index.jsp" class="btn btn-secondary mt-3">Back to Home</a>

    </div>

</div>

<!-- Optional Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
