<%@ page import="com.parent.model.User,java.sql.*,com.parent.util.DBConnection" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }

    int jobId = Integer.parseInt(request.getParameter("jobId"));
    String msg = request.getParameter("msg");

    // Fetch candidate profile
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    String fullName="", email="", phone="", address="", qualification="", skills="", projectSummary="";
    int profileStrength = 0;

    try {
        conn = DBConnection.getConnection();
        ps = conn.prepareStatement("SELECT * FROM candidate_profile WHERE user_id=?");
        ps.setInt(1, user.getId());
        rs = ps.executeQuery();
        if (rs.next()) {
            fullName = rs.getString("full_name") != null ? rs.getString("full_name") : "";
            email = rs.getString("email") != null ? rs.getString("email") : "";
            phone = rs.getString("phone") != null ? rs.getString("phone") : "";
            address = rs.getString("address") != null ? rs.getString("address") : "";
            qualification = rs.getString("qualification") != null ? rs.getString("qualification") : "";
            skills = rs.getString("skills") != null ? rs.getString("skills") : "";
            projectSummary = rs.getString("project_summary") != null ? rs.getString("project_summary") : "";
            profileStrength = rs.getInt("profile_strength");
        }
    } catch(Exception e) {
        e.printStackTrace();
        profileStrength = 0;
    } finally {
        if(rs != null) rs.close();
        if(ps != null) ps.close();
        if(conn != null) conn.close();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Apply for Job - Job Portal</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
    <div class="ml-auto">
        <span class="navbar-text mr-3">Welcome, <%= user.getName() %></span>
        <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
</nav>

<div class="container mt-5" style="max-width: 750px; margin: auto;">
<% if(msg != null) { %>
    <div class="alert alert-info"><%= msg %></div>
<% } %>

<%
    if(profileStrength < 70) {
%>
    <!-- Show Profile Update Form -->
    <div class="card shadow p-4 mb-4">
        <h3 class="mb-4 text-primary">Complete Your Profile to Apply</h3>
        <div class="alert alert-warning">
            Your profile completion is <%= profileStrength %>%. You must complete your profile (≥70%) before applying to this job.
        </div>

        <form action="<%= request.getContextPath() %>/updateProfile" method="post">
            <input type="hidden" name="redirectToApply" value="<%= jobId %>">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="full_name" class="form-control" value="<%= fullName %>">
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" class="form-control" value="<%= email %>">
            </div>
            <div class="form-group">
                <label>Phone</label>
                <input type="text" name="phone" class="form-control" value="<%= phone %>">
            </div>
            <div class="form-group">
                <label>Address</label>
                <textarea name="address" class="form-control"><%= address %></textarea>
            </div>
            <div class="form-group">
                <label>Qualification</label>
                <input type="text" name="qualification" class="form-control" value="<%= qualification %>">
            </div>
            <div class="form-group">
                <label>Skills</label>
                <textarea name="skills" class="form-control"><%= skills %></textarea>
            </div>
            <div class="form-group">
                <label>Project Summary</label>
                <textarea name="project_summary" class="form-control"><%= projectSummary %></textarea>
            </div>

            <div class="form-group">
                <label>Upload Resume</label>
                <input type="file" name="resume" class="form-control-file" accept=".pdf,.doc,.docx">
                <%-- If a resume already exists, show download link --%>
                <%
                    String resume = rs.getString("resume");
                    if (resume != null && !resume.trim().isEmpty()) {
                %>
                    <p class="mt-2">
                        Current Resume:
                        <a href="<%= request.getContextPath() + "/uploads/resumes/" + resume %>" target="_blank">
                            View/Download
                        </a>
                    </p>
                <%
                    }
                %>
            </div>





            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">Save Profile</button>
                <a href="<%= request.getContextPath() %>/jobs" class="btn btn-secondary">Back to Jobs</a>
            </div>
        </form>
    </div>
<%
    } else {
%>
    <!-- Show Apply Form -->
    <div class="card p-4 shadow">
        <h3 class="mb-4 text-center text-primary">Apply for Job</h3>
        <form action="<%= request.getContextPath() %>/apply" method="post">
            <input type="hidden" name="jobId" value="<%= jobId %>">
            <div class="form-group">
                <label for="coverLetter">Cover Letter (Optional)</label>
                <textarea name="coverLetter" id="coverLetter" class="form-control" rows="6"></textarea>
            </div>
            <button type="submit" class="btn btn-success btn-block">Submit Application</button>
        </form>
        <div class="text-center mt-3">
            <a href="<%= request.getContextPath() %>/jobs" class="btn btn-secondary">Back to Jobs</a>
        </div>
    </div>
<%
    }
%>
</div>
</body>
</html>
