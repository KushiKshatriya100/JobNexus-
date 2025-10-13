package com.parent.controller;

import com.parent.dao.ApplicationDAO;
import com.parent.dao.CandidateProfileDAO;
import com.parent.model.Application;
import com.parent.model.CandidateProfile;
import com.parent.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/apply")
public class ApplyServlet extends HttpServlet {

    private ApplicationDAO applicationDAO;
    private CandidateProfileDAO profileDAO;

    @Override
    public void init() {
        applicationDAO = new ApplicationDAO();
        profileDAO = new CandidateProfileDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        try {
            int jobId = Integer.parseInt(request.getParameter("jobId"));

            // ✅ Fetch candidate profile and check strength
            CandidateProfile profile = profileDAO.getProfileByUserId(user.getId());
            if (profile == null || profile.getProfileStrength() < 80) {
                // redirect to profile page with alert
                response.sendRedirect(request.getContextPath() + "/jsp/profile.jsp?incomplete=1");
                return;
            }

            // proceed with application
            String resume = profile.getResume(); // use saved resume from DB

            Application application = new Application();
            application.setJobId(jobId);
            application.setUserId(user.getId());
            application.setResume(resume);

            boolean success = applicationDAO.applyToJob(application);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/jsp/my_applications.jsp?success=1");
            } else {
                response.sendRedirect(request.getContextPath() + "/jsp/jobs.jsp?error=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/jsp/jobs.jsp?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If someone tries GET, just redirect them safely
        response.sendRedirect(request.getContextPath() + "/jsp/jobs.jsp");
    }
}
