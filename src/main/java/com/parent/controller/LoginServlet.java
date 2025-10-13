package com.parent.controller;

import com.parent.dao.UserDAO;
import com.parent.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.validateUser(email, password);

        if (user != null) {
            // Create session and set user attribute
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60); // 30 min

            // Redirect based on role
            if ("recruiter".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/recruiter_dashboard");
            } else if ("job_seeker".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/jsp/profile.jsp");
            } else {
                // Default fallback
                response.sendRedirect(request.getContextPath() + "/jsp/profile.jsp");
            }
        } else {
            // Login failed
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
    }
}
