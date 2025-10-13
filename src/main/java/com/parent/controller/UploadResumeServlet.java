package com.parent.controller;

import com.parent.dao.CandidateProfileDAO;
import com.parent.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = {"/UploadResumeServlet", "/uploadResume"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class UploadResumeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        try {
            Part filePart = request.getPart("resume");
            if (filePart == null || filePart.getSize() == 0) {
                session.setAttribute("msg", "Please choose a file to upload.");
                response.sendRedirect(request.getContextPath() + "/jsp/edit_profile.jsp");
                return;
            }

            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String lowerName = fileName.toLowerCase();

            if (!(lowerName.endsWith(".pdf") || lowerName.endsWith(".doc") || lowerName.endsWith(".docx"))) {
                session.setAttribute("msg", "Invalid file type. Please upload a PDF or Word document.");
                response.sendRedirect(request.getContextPath() + "/jsp/edit_profile.jsp");
                return;
            }

            String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "resumes";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
            String uniqueFileName = "resume_" + user.getId() + "_" + System.currentTimeMillis() + extension;
            String filePath = uploadDir + File.separator + uniqueFileName;

            filePart.write(filePath);

            String relativePath = "uploads/resumes/" + uniqueFileName;
            CandidateProfileDAO dao = new CandidateProfileDAO();
            boolean updated = dao.updateResume(user.getId(), relativePath);

            if (updated) {
                session.setAttribute("msg", "Resume uploaded successfully!");
            } else {
                session.setAttribute("msg", "Failed to update resume in database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "Error while uploading resume: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/jsp/profile.jsp");
    }
}
