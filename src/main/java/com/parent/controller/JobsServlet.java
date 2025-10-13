package com.parent.controller;

import com.parent.dao.ApplicationDAO;
import com.parent.dao.JobDAO;
import com.parent.model.Job;
import com.parent.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/jobs")
public class JobsServlet extends HttpServlet {

    private JobDAO jobDAO;
    private ApplicationDAO applicationDAO;

    @Override
    public void init() {
        jobDAO = new JobDAO();
        applicationDAO = new ApplicationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get all jobs
        List<Job> jobList = jobDAO.getAllJobs();
        request.setAttribute("jobList", jobList);

        // Get current user from session
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        // If user is job_seeker, get appliedJobIds also
        if (currentUser != null && "job_seeker".equalsIgnoreCase(currentUser.getRole())) {
            List<Integer> appliedJobIds = applicationDAO.getAppliedJobIdsByUser(currentUser.getId());
            request.setAttribute("appliedJobIds", appliedJobIds);
        }

        // Forward to JSP
        request.getRequestDispatcher("/jsp/jobs.jsp").forward(request, response);
    }
}
