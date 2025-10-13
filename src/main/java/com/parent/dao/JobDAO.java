// src/main/java/com/parent/dao/JobDAO.java

package com.parent.dao;

import com.parent.model.Job;
import com.parent.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    public boolean postJob(Job job) {
        boolean success = false;

        try (Connection conn = DBConnection.getConnection()) {

            String sql = "INSERT INTO jobs (title, description, location, salary, employer_id) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, job.getTitle());
            stmt.setString(2, job.getDescription());
            stmt.setString(3, job.getLocation());
            stmt.setString(4, job.getSalary());
            stmt.setInt(5, job.getEmployerId());

            int rowsInserted = stmt.executeUpdate();
            success = rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM jobs ORDER BY created_at DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Job job = new Job();
                job.setId(rs.getInt("id"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setLocation(rs.getString("location"));
                job.setSalary(rs.getString("salary"));
                job.setEmployerId(rs.getInt("employer_id"));
                job.setCreatedAt(rs.getTimestamp("created_at"));

                jobs.add(job);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }

    public List<Job> getJobsByRecruiter(int recruiterId) {
        List<Job> jobs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM jobs WHERE employer_id = ? ORDER BY created_at DESC")) {

            stmt.setInt(1, recruiterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Job job = new Job();
                job.setId(rs.getInt("id"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setLocation(rs.getString("location"));
                job.setSalary(rs.getString("salary"));
                job.setEmployerId(rs.getInt("employer_id"));
                job.setCreatedAt(rs.getTimestamp("created_at"));

                jobs.add(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public boolean deleteJob(int jobId) {
        boolean rowDeleted = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM jobs WHERE id = ?")) {

            stmt.setInt(1, jobId);
            rowDeleted = stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public int countJobsByRecruiterId(int recruiterId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM jobs WHERE employer_id = ?";

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

    // ⭐ Latest jobs posted by recruiter
    public List<Job> getLatestJobsByRecruiterId(int recruiterId, int limit) {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE employer_id = ? ORDER BY created_at DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Job job = new Job();
                    job.setId(rs.getInt("id"));
                    job.setTitle(rs.getString("title"));
                    job.setDescription(rs.getString("description"));
                    job.setLocation(rs.getString("location"));
                    job.setSalary(rs.getString("salary"));
                    job.setEmployerId(rs.getInt("employer_id"));
                    job.setCreatedAt(rs.getTimestamp("created_at"));
                    jobs.add(job);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }

}
