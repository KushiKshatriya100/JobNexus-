package com.parent.model;

import java.sql.Timestamp;

public class Application {

    private int id;
    private int jobId;
    private int userId;
    private String resume;
    private String status;
    private Timestamp appliedAt;

    // Extra fields to display job info
    private String jobTitle;
    private String jobLocation;
    private String jobSalary;

    // Extra fields for user info (needed for recruiter view)
    private String userName;
    private String userEmail;

    public Application() {}

    public Application(int jobId, int userId, String resume, String status) {
        this.jobId = jobId;
        this.userId = userId;
        this.resume = resume;
        this.status = status;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getResume() { return resume; }
    public void setResume(String resume) { this.resume = resume; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Timestamp appliedAt) { this.appliedAt = appliedAt; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getJobLocation() { return jobLocation; }
    public void setJobLocation(String jobLocation) { this.jobLocation = jobLocation; }

    public String getJobSalary() { return jobSalary; }
    public void setJobSalary(String jobSalary) { this.jobSalary = jobSalary; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
