package com.parent.controller;

import com.parent.dao.ApplicationDAO;
import com.parent.dao.JobDAO;
import com.parent.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/recruiter_dashboard")
public class RecruiterDashboardServlet extends HttpServlet {

    private JobDAO jobDAO;
    private ApplicationDAO applicationDAO;

    @Override
    public void init() throws ServletException {
        jobDAO = new JobDAO();
        applicationDAO = new ApplicationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !"recruiter".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        int recruiterId = user.getId();

        // Fetch metrics
        int totalJobs = jobDAO.countJobsByRecruiterId(recruiterId);
        int totalApplications = applicationDAO.countApplicationsByRecruiterId(recruiterId);

        int countApplied = applicationDAO.countApplicationsByStatusAndRecruiterId(recruiterId, "applied");
        int countUnderReview = applicationDAO.countApplicationsByStatusAndRecruiterId(recruiterId, "interview"); // correct ENUM
        int countRejected = applicationDAO.countApplicationsByStatusAndRecruiterId(recruiterId, "rejected");
        int countAccepted = applicationDAO.countApplicationsByStatusAndRecruiterId(recruiterId, "selected"); // correct ENUM

        // Fetch latest jobs + latest applications
        var latestJobs = jobDAO.getLatestJobsByRecruiterId(recruiterId, 5);
        var latestApplications = applicationDAO.getLatestApplicationsByRecruiterId(recruiterId, 5);

        // Set attributes for JSP
        request.setAttribute("totalJobs", totalJobs);
        request.setAttribute("totalApplications", totalApplications);
        request.setAttribute("countApplied", countApplied);
        request.setAttribute("countUnderReview", countUnderReview);
        request.setAttribute("countRejected", countRejected);
        request.setAttribute("countAccepted", countAccepted);

        request.setAttribute("latestJobs", latestJobs);
        request.setAttribute("latestApplications", latestApplications);

        request.getRequestDispatcher("/jsp/recruiter_dashboard.jsp").forward(request, response);
    }

}
