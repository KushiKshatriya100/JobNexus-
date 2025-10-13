package com.parent.controller;

import com.parent.dao.JobDAO;
import com.parent.model.Job;
import com.parent.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/post_job")
public class PostJobServlet extends HttpServlet {

    private JobDAO jobDAO;

    @Override
    public void init() {
        jobDAO = new JobDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String salary = request.getParameter("salary");

        // Session check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?error=notLoggedIn");
            return;
        }

        // Get logged-in user
        User recruiter = (User) session.getAttribute("user");

        // Only recruiter can post job
        if (!"recruiter".equals(recruiter.getRole())) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?error=unauthorized");
            return;
        }

        int employerId = recruiter.getId();

        Job job = new Job(title, description, location, salary, employerId);

        boolean success = jobDAO.postJob(job);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/recruiter_dashboard?jobPostSuccess=1");
        } else {
            response.sendRedirect(request.getContextPath() + "/jsp/post_job.jsp?error=1");
        }
    }
}
