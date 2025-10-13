// src/main/java/com/parent/controller/ViewApplicationsServlet.java

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

@WebServlet("/view_applications")
public class ViewApplicationsServlet extends HttpServlet {

    private ApplicationDAO applicationDAO;

    @Override
    public void init() throws ServletException {
        applicationDAO = new ApplicationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Only allow recruiter to access this page
        if (user == null || !"recruiter".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        // Fetch applications for this recruiter
        List<Application> applications = applicationDAO.getApplicationsByEmployerId(user.getId());

        // Pass to JSP
        request.setAttribute("applications", applications);
        request.getRequestDispatcher("/jsp/view_applications.jsp").forward(request, response);
    }
}
