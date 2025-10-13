package com.parent.controller;

import com.parent.dao.CandidateProfileDAO;
import com.parent.model.CandidateProfile;
import com.parent.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    private CandidateProfileDAO dao = new CandidateProfileDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getId(); // use session user id
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String qualification = req.getParameter("qualification");
        String skills = req.getParameter("skills");
        String projectSummary = req.getParameter("projectSummary");

        // Calculate profile strength
        int strength = 0;
        if (fullName != null && !fullName.isEmpty()) strength += 20;
        if (qualification != null && !qualification.isEmpty()) strength += 20;
        if (skills != null && !skills.isEmpty()) strength += 20;
        if (projectSummary != null && !projectSummary.isEmpty()) strength += 20;
        if (phone != null && !phone.isEmpty()) strength += 20;

        CandidateProfile profile = new CandidateProfile();
        profile.setUserId(userId);
        profile.setFullName(fullName);
        profile.setEmail(email);
        profile.setPhone(phone);
        profile.setAddress(address);
        profile.setQualification(qualification);
        profile.setSkills(skills);
        profile.setProjectSummary(projectSummary);
        profile.setProfileStrength(strength);

        try {
            dao.saveProfile(profile);
            session.setAttribute("msg", "Profile updated successfully! Strength: " + strength + "%");
            resp.sendRedirect(req.getContextPath() + "/jsp/profile.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
