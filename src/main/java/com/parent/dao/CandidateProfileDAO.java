package com.parent.dao;

import com.parent.model.CandidateProfile;
import com.parent.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidateProfileDAO {

    // Save new profile
    public void saveProfile(CandidateProfile profile) throws SQLException {
        String sql = "INSERT INTO candidate_profile (user_id, full_name, email, phone, address, qualification, skills, project_summary, profile_strength, resume) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFullName());
            ps.setString(3, profile.getEmail());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getAddress());
            ps.setString(6, profile.getQualification());
            ps.setString(7, profile.getSkills());
            ps.setString(8, profile.getProjectSummary());
            ps.setInt(9, profile.getProfileStrength());
            ps.setString(10, profile.getResume());

            ps.executeUpdate();
        }
    }

    // Update only resume path
    public boolean updateResume(int userId, String resumePath) {
        String sql = "UPDATE candidate_profile SET resume=? WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, resumePath);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0; // true if at least one row updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update full profile (if user already exists), otherwise insert new.
    public boolean saveOrUpdateProfile(CandidateProfile profile) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {

            if (profile.getResume() != null && !profile.getResume().trim().isEmpty()) {
                // Update including resume
                String sql = "UPDATE candidate_profile SET full_name=?, email=?, phone=?, address=?, qualification=?, skills=?, project_summary=?, profile_strength=?, resume=? WHERE user_id=?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, profile.getFullName());
                    ps.setString(2, profile.getEmail());
                    ps.setString(3, profile.getPhone());
                    ps.setString(4, profile.getAddress());
                    ps.setString(5, profile.getQualification());
                    ps.setString(6, profile.getSkills());
                    ps.setString(7, profile.getProjectSummary());
                    ps.setInt(8, profile.getProfileStrength());
                    ps.setString(9, profile.getResume());
                    ps.setInt(10, profile.getUserId());

                    int rows = ps.executeUpdate();
                    if (rows == 0) {
                        saveProfile(profile);
                    }
                    return true;
                }
            } else {
                // Update without touching resume
                String sql = "UPDATE candidate_profile SET full_name=?, email=?, phone=?, address=?, qualification=?, skills=?, project_summary=?, profile_strength=? WHERE user_id=?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, profile.getFullName());
                    ps.setString(2, profile.getEmail());
                    ps.setString(3, profile.getPhone());
                    ps.setString(4, profile.getAddress());
                    ps.setString(5, profile.getQualification());
                    ps.setString(6, profile.getSkills());
                    ps.setString(7, profile.getProjectSummary());
                    ps.setInt(8, profile.getProfileStrength());
                    ps.setInt(9, profile.getUserId());

                    int rows = ps.executeUpdate();
                    if (rows == 0) {
                        saveProfile(profile);
                    }
                    return true;
                }
            }
        }
    }

    // ✅ NEW: Fetch candidate profile by userId
    public CandidateProfile getProfileByUserId(int userId) {
        String sql = "SELECT * FROM candidate_profile WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CandidateProfile profile = new CandidateProfile();
                    profile.setId(rs.getInt("id"));
                    profile.setUserId(rs.getInt("user_id"));
                    profile.setFullName(rs.getString("full_name"));
                    profile.setEmail(rs.getString("email"));
                    profile.setPhone(rs.getString("phone"));
                    profile.setAddress(rs.getString("address"));
                    profile.setQualification(rs.getString("qualification"));
                    profile.setSkills(rs.getString("skills"));
                    profile.setProjectSummary(rs.getString("project_summary"));
                    profile.setProfileStrength(rs.getInt("profile_strength"));
                    profile.setResume(rs.getString("resume"));
                    return profile;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no profile found
    }
}
