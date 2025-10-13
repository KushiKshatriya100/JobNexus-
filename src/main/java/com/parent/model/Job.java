// src/main/java/com/parent/model/Job.java

package com.parent.model;

import java.sql.Timestamp;

public class Job {

    private int id;
    private String title;
    private String description;
    private String location;
    private String salary;
    private int employerId;
    private Timestamp createdAt;

    // Constructors
    public Job() {}

    public Job(String title, String description, String location, String salary, int employerId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.employerId = employerId;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public int getEmployerId() { return employerId; }
    public void setEmployerId(int employerId) { this.employerId = employerId; }


    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
