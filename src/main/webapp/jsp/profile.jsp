<%@ page session="true" import="com.parent.model.User,java.sql.*,com.parent.util.DBConnection" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    String fullName="", email="", phone="", address="", qualification="", skills="", projectSummary="", resumePath="";
    int profileStrength = 0;

    try {
        conn = DBConnection.getConnection();
        ps = conn.prepareStatement("SELECT * FROM candidate_profile WHERE user_id=?");
        ps.setInt(1, user.getId());
        rs = ps.executeQuery();
        if (rs.next()) {
            fullName = rs.getString("full_name");
            email = rs.getString("email");
            phone = rs.getString("phone");
            address = rs.getString("address");
            qualification = rs.getString("qualification");
            skills = rs.getString("skills");
            projectSummary = rs.getString("project_summary");
            profileStrength = rs.getInt("profile_strength");
            resumePath = rs.getString("resume");
        }
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        try { if(rs!=null) rs.close(); } catch(Exception e) {}
        try { if(ps!=null) ps.close(); } catch(Exception e) {}
        try { if(conn!=null) conn.close(); } catch(Exception e) {}
    }

    String msg = (String) session.getAttribute("msg");
    session.removeAttribute("msg");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Your Profile - Job Portal</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/jsp/index.jsp">Job Portal</a>
    <div class="ml-auto">
        <span class="navbar-text mr-3">Welcome, <%= user.getName() %> (<%= user.getRole() %>)</span>
        <a class="btn btn-danger btn-sm" href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
</nav>

<div class="container mt-5">
    <div class="card shadow p-4 mx-auto" style="max-width: 750px;">
        <h3 class="mb-4 text-primary">Your Profile</h3>

        <% if (msg != null) { %>
            <div class="alert alert-info"><%= msg %></div>
        <% } %>

        <div class="mb-4">
            <label><strong>Profile Strength:</strong></label>
            <div class="progress">
                <div class="progress-bar <%= (profileStrength < 50) ? "bg-danger" : (profileStrength < 80 ? "bg-warning" : "bg-success") %>"
                     role="progressbar" style="width: <%= profileStrength %>%;"><%= profileStrength %>%</div>
            </div>
        </div>

        <p><strong>Full Name:</strong> <%= fullName %></p>
        <p><strong>Email:</strong> <%= email %></p>
        <p><strong>Phone:</strong> <%= phone %></p>
        <p><strong>Address:</strong> <%= address %></p>
        <p><strong>Qualification:</strong> <%= qualification %></p>
        <p><strong>Skills:</strong> <%= skills %></p>
        <p><strong>Project Summary:</strong> <%= projectSummary %></p>

        <% if (resumePath != null && !resumePath.isEmpty()) { %>
            <p><strong>Resume:</strong>
                <a href="<%= request.getContextPath() + "/" + resumePath %>" target="_blank">View / Download</a>
            </p>
        <% } else { %>
            <p><strong>Resume:</strong> Not Uploaded</p>
        <% } %>

        <!-- Buttons: Edit Profile and Back (Back is immediately to the right) -->
        <div class="d-flex">
            <a href="<%= request.getContextPath() %>/jsp/edit_profile.jsp" class="btn btn-primary">Edit Profile</a>
            <a href="<%= request.getContextPath() %>/jsp/index.jsp" class="btn btn-secondary ml-2">Back</a>
        </div>
    </div>
</div>
</body>
</html>
