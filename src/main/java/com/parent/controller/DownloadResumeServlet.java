package com.parent.controller;

import com.parent.dao.UserDAO;
import com.parent.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/downloadResume")
public class DownloadResumeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        UserDAO dao = new UserDAO();
        String resumePath = dao.getResume(user.getId());

        if (resumePath == null) {
            response.setContentType("text/html");
            response.getWriter().println("<h3>No resume uploaded yet.</h3>");
            return;
        }

        // Absolute path (assuming uploads stored under webapp/uploads)
        String absolutePath = getServletContext().getRealPath("/") + resumePath;

        File file = new File(absolutePath);
        if (!file.exists()) {
            response.setContentType("text/html");
            response.getWriter().println("<h3>Resume file not found on server.</h3>");
            return;
        }

        // Set content type dynamically (default = octet-stream)
        response.setContentType(getServletContext().getMimeType(file.getName()));
        if (response.getContentType() == null) {
            response.setContentType("application/octet-stream");
        }

        // Set headers for download
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        response.setContentLengthLong(file.length());

        // Stream file
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
