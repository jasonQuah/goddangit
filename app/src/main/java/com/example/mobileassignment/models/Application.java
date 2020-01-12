package com.example.mobileassignment.models;

public class Application {
    private String status;
    private String userId;
    private String jobId;

    public Application(){
    }

    public Application(String status, String userId, String jobId) {
        this.status = status;
        this.userId = userId;
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
