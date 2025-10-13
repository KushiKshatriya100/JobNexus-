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
            resumePath = rs.getString("resume");
        }
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        if(rs!=null) rs.close();
        if(ps!=null) ps.close();
        if(conn!=null) conn.close();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile - Job Portal</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4 mx-auto" style="max-width: 750px;">
        <h3 class="mb-4 text-primary">Edit Your Profile</h3>

        <!-- enctype added for file upload -->
        <form action="<%= request.getContextPath() %>/updateProfile" method="post" enctype="multipart/form-data">
            <input type="hidden" name="userId" value="<%= user.getId() %>">

            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="fullName" class="form-control" value="<%= fullName %>">
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
                <textarea name="projectSummary" class="form-control"><%= projectSummary %></textarea>
            </div>

            <!-- ✅ Resume Upload -->
            <div class="form-group">
                <label>Resume</label><br>
                <% if (resumePath != null && !resumePath.isEmpty()) { %>
                    <p>Current: <a href="<%= request.getContextPath() + "/" + resumePath %>" target="_blank">View / Download</a></p>
                <% } else { %>
                    <p>No Resume Uploaded</p>
                <% } %>
                <input type="file" name="resume" class="form-control-file">
            </div>

            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">Save Profile</button>
                <a href="<%= request.getContextPath() %>/jsp/profile.jsp" class="btn btn-secondary">Back</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
