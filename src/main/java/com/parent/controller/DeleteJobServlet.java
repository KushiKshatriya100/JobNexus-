// src/main/java/com/parent/controller/DeleteJobServlet.java

package com.parent.controller;

import com.parent.dao.JobDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/delete_job")
public class DeleteJobServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String jobIdParam = request.getParameter("jobId");

        if (jobIdParam != null && !jobIdParam.isEmpty()) {
            try {
                int jobId = Integer.parseInt(jobIdParam);

                JobDAO jobDAO = new JobDAO();
                boolean deleted = jobDAO.deleteJob(jobId);

                // You can log success/failure if needed
                System.out.println("Job deleted: " + deleted);

            } catch (NumberFormatException e) {
                e.printStackTrace(); // handle invalid jobId
            }
        }

        // Always redirect after POST (PRG pattern)
        response.sendRedirect(request.getContextPath() + "/manage_jobs");
    }
}
