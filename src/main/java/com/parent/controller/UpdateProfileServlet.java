package com.parent.controller;

import com.parent.dao.CandidateProfileDAO;
import com.parent.model.CandidateProfile;
import com.parent.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/updateProfile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 10 * 1024 * 1024,     // 10 MB
        maxRequestSize = 50 * 1024 * 1024)  // 50 MB
public class UpdateProfileServlet extends HttpServlet {

    private static final int TOTAL_FIELDS = 7; // fullName,email,phone,address,qualification,skills,projectSummary

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        try {
            // read fields (names match edit_profile.jsp)
            String fullName = trimParam(request.getParameter("fullName"));
            String email = trimParam(request.getParameter("email"));
            String phone = trimParam(request.getParameter("phone"));
            String address = trimParam(request.getParameter("address"));
            String qualification = trimParam(request.getParameter("qualification"));
            String skills = trimParam(request.getParameter("skills"));
            String projectSummary = trimParam(request.getParameter("projectSummary"));

            // profileStrength: if user sent explicit value use it, otherwise compute
            String psParam = trimParam(request.getParameter("profileStrength"));
            int profileStrength;
            if (!psParam.isEmpty()) {
                try {
                    profileStrength = Integer.parseInt(psParam);
                } catch (NumberFormatException nfe) {
                    profileStrength = computeProfileStrength(fullName, email, phone, address, qualification, skills, projectSummary);
                }
            } else {
                profileStrength = computeProfileStrength(fullName, email, phone, address, qualification, skills, projectSummary);
            }

            // Build CandidateProfile object (do not set resume here unless a file is actually uploaded)
            CandidateProfile profile = new CandidateProfile();
            profile.setUserId(user.getId());
            profile.setFullName(fullName);
            profile.setEmail(email);
            profile.setPhone(phone);
            profile.setAddress(address);
            profile.setQualification(qualification);
            profile.setSkills(skills);
            profile.setProjectSummary(projectSummary);
            profile.setProfileStrength(profileStrength);

            // If this request is multipart and contains a resume file, process it and set profile.resume
            String contentType = request.getContentType();
            if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
                Part filePart = request.getPart("resume");
                if (filePart != null && filePart.getSize() > 0) {
                    String submitted = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String lower = submitted.toLowerCase();
                    if (!(lower.endsWith(".pdf") || lower.endsWith(".doc") || lower.endsWith(".docx"))) {
                        session.setAttribute("msg", "Invalid resume format. Please upload PDF or Word document.");
                        response.sendRedirect(request.getContextPath() + "/jsp/edit_profile.jsp");
                        return;
                    }

                    // Save to webapp/uploads/resumes
                    String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "resumes";
                    File uploadFolder = new File(uploadDir);
                    if (!uploadFolder.exists()) uploadFolder.mkdirs();

                    String extension = submitted.contains(".") ? submitted.substring(submitted.lastIndexOf(".")) : "";
                    String uniqueName = "resume_" + user.getId() + "_" + System.currentTimeMillis() + extension;
                    String absolutePath = uploadDir + File.separator + uniqueName;

                    // write file
                    filePart.write(absolutePath);

                    String relativePath = "uploads/resumes/" + uniqueName;
                    profile.setResume(relativePath);
                }
            }

            // Save/update using DAO (preserves existing resume if profile.getResume() is null)
            CandidateProfileDAO dao = new CandidateProfileDAO();
            dao.saveOrUpdateProfile(profile);

            session.setAttribute("msg", "Profile updated successfully!");
            response.sendRedirect(request.getContextPath() + "/jsp/profile.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "Error updating profile: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/jsp/edit_profile.jsp");
        }
    }

    private static String trimParam(String p) {
        return (p == null) ? "" : p.trim();
    }

    private static int computeProfileStrength(String fullName, String email, String phone, String address, String qualification, String skills, String projectSummary) {
        int filled = 0;
        if (!fullName.isEmpty()) filled++;
        if (!email.isEmpty()) filled++;
        if (!phone.isEmpty()) filled++;
        if (!address.isEmpty()) filled++;
        if (!qualification.isEmpty()) filled++;
        if (!skills.isEmpty()) filled++;
        if (!projectSummary.isEmpty()) filled++;

        return (int) Math.round((filled * 100.0) / TOTAL_FIELDS);
    }
}
