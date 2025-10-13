package com.parent.dao;

import com.parent.model.Application;
import com.parent.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    // ⭐ Insert new application
    public boolean applyToJob(Application application) {
        boolean success = false;

        String sql = "INSERT INTO applications (job_id, user_id, resume, status) VALUES (?, ?, ?, 'applied')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, application.getJobId());
            stmt.setInt(2, application.getUserId());
            stmt.setString(3, application.getResume());

            int rowsInserted = stmt.executeUpdate();
            success = rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    // ⭐ Applications of a user (for "My Applications" page)
    public List<Application> getApplicationsByUserId(int userId) {
        List<Application> applications = new ArrayList<>();

        String sql = "SELECT a.*, j.title AS job_title, j.location AS job_location, j.salary AS job_salary " +
                "FROM applications a " +
                "JOIN jobs j ON a.job_id = j.id " +
                "WHERE a.user_id = ? " +
                "ORDER BY a.applied_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Application app = new Application();
                    app.setId(rs.getInt("id"));
                    app.setJobId(rs.getInt("job_id"));
                    app.setUserId(rs.getInt("user_id"));
                    app.setResume(rs.getString("resume"));
                    app.setStatus(rs.getString("status"));
                    app.setAppliedAt(rs.getTimestamp("applied_at"));

                    // Extra fields
                    app.setJobTitle(rs.getString("job_title"));
                    app.setJobLocation(rs.getString("job_location"));
                    app.setJobSalary(rs.getString("job_salary"));

                    applications.add(app);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }

    // ⭐ Applications by employer (for "View Applications" page for recruiter)
    public List<Application> getApplicationsByEmployerId(int employerId) {
        List<Application> applications = new ArrayList<>();

        String sql = "SELECT a.*, j.title AS job_title, u.name AS user_name, u.email AS user_email " +
                "FROM applications a " +
                "JOIN jobs j ON a.job_id = j.id " +
                "JOIN users u ON a.user_id = u.id " +
                "WHERE j.employer_id = ? " +
                "ORDER BY a.applied_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Application app = new Application();
                    app.setId(rs.getInt("id"));
                    app.setUserId(rs.getInt("user_id"));
                    app.setUserName(rs.getString("user_name"));
                    app.setUserEmail(rs.getString("user_email"));
                    app.setJobId(rs.getInt("job_id"));
                    app.setJobTitle(rs.getString("job_title"));
                    app.setResume(rs.getString("resume"));
                    app.setStatus(rs.getString("status"));
                    app.setAppliedAt(rs.getTimestamp("applied_at"));

                    applications.add(app);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }

    // ⭐ Update Application Status
    public boolean updateApplicationStatus(int applicationId, String newStatus) {
        boolean success = false;

        String sql = "UPDATE applications SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, applicationId);

            int rowsUpdated = stmt.executeUpdate();
            success = rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }


    public int countApplicationsByRecruiterId(int recruiterId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM applications a "
                + "JOIN jobs j ON a.job_id = j.id "
                + "WHERE j.recruiter_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countApplicationsByStatusAndRecruiterId(int recruiterId, String status) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM applications a "
                + "JOIN jobs j ON a.job_id = j.id "
                + "WHERE j.recruiter_id = ? AND a.status = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);
            stmt.setString(2, status);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    // ⭐ Latest applications for recruiter's jobs
    public List<Application> getLatestApplicationsByRecruiterId(int recruiterId, int limit) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT a.*, j.title AS job_title, u.name AS user_name, u.email AS user_email " +
                "FROM applications a " +
                "JOIN jobs j ON a.job_id = j.id " +
                "JOIN users u ON a.user_id = u.id " +
                "WHERE j.recruiter_id = ? " +
                "ORDER BY a.applied_at DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Application app = new Application();
                    app.setId(rs.getInt("id"));
                    app.setUserId(rs.getInt("user_id"));
                    app.setUserName(rs.getString("user_name"));
                    app.setUserEmail(rs.getString("user_email"));
                    app.setJobId(rs.getInt("job_id"));
                    app.setJobTitle(rs.getString("job_title"));
                    app.setResume(rs.getString("resume"));
                    app.setStatus(rs.getString("status"));
                    app.setAppliedAt(rs.getTimestamp("applied_at"));
                    applications.add(app);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }


    // In ApplicationDAO.java
    public List<Integer> getAppliedJobIdsByUser(int userId) {
        List<Integer> appliedJobIds = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT job_id FROM applications WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                appliedJobIds.add(rs.getInt("job_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appliedJobIds;
    }


}
