// src/main/java/com/parent/controller/MyApplicationsServlet.java

package com.parent.controller;

import com.parent.dao.ApplicationDAO;
import com.parent.model.Application;
import com.parent.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-applications")
public class MyApplicationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check if user is logged in and is a job_seeker
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            if ("job_seeker".equals(user.getRole())) {
                ApplicationDAO dao = new ApplicationDAO();
                List<Application> applications = dao.getApplicationsByUserId(user.getId());

                request.setAttribute("applications", applications);
                request.getRequestDispatcher("/jsp/my_applications.jsp").forward(request, response);
                return;
            }
        }

        // If not logged in OR not job_seeker, redirect to log in
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
    }
}
