// src/main/java/com/parent/controller/ManageJobsServlet.java

package com.parent.controller;

import com.parent.dao.JobDAO;
import com.parent.model.Job;
import com.parent.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/manage_jobs")
public class ManageJobsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"recruiter".equalsIgnoreCase(user.getRole())) {
            // If not logged in or not a recruiter
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        JobDAO jobDAO = new JobDAO();
        List<Job> jobs = jobDAO.getJobsByRecruiter(user.getId());

        request.setAttribute("jobs", jobs);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/manage_jobs.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String jobIdStr = request.getParameter("jobId");

        if (jobIdStr != null && !jobIdStr.isEmpty()) {
            try {
                int jobId = Integer.parseInt(jobIdStr);
                JobDAO jobDAO = new JobDAO();
                jobDAO.deleteJob(jobId);
            } catch (NumberFormatException e) {
                e.printStackTrace();  // handle invalid number
            }
        }

        // Redirect to refresh the Manage Jobs page
        response.sendRedirect(request.getContextPath() + "/manage_jobs");
    }
}
