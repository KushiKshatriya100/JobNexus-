package com.parent.controller;

import com.parent.dao.ApplicationDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/update_application_status")
public class UpdateApplicationStatusServlet extends HttpServlet {

    private ApplicationDAO applicationDAO;

    @Override
    public void init() throws ServletException {
        applicationDAO = new ApplicationDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String applicationIdParam = request.getParameter("applicationId");
        String newStatus = request.getParameter("status");

        if (applicationIdParam == null || newStatus == null || newStatus.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing application ID or status.");
            return;
        }

        try {
            int applicationId = Integer.parseInt(applicationIdParam);

            boolean updated = applicationDAO.updateApplicationStatus(applicationId, newStatus);

            System.out.println("[UpdateApplicationStatusServlet] applicationId=" + applicationId
                    + ", newStatus=" + newStatus
                    + ", updated=" + updated);

            // Redirect after update to ViewApplicationsServlet
            response.sendRedirect(request.getContextPath() + "/view_applications");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid application ID.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating status.");
        }
    }
}
