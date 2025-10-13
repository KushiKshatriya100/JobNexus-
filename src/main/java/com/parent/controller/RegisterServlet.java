// src/main/java/com/parent/controller/RegisterServlet.java

package com.parent.controller;

import com.parent.dao.UserDAO;
import com.parent.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ⭐ Read form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");  // From dropdown

        // ⭐ Create User object with role
        User user = new User(name, email, password, role);

        // ⭐ Save user to DB
        boolean success = userDAO.registerUser(user);

        // ⭐ Redirect based on result
        if (success) {
            // Registration successful → Go to login page
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        } else {
            // Registration failed → Stay on register page with error param
            response.sendRedirect(request.getContextPath() + "/jsp/register.jsp?error=1");
        }
    }
}
