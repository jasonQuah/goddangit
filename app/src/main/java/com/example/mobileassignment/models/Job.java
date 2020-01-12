package com.example.mobileassignment.models;

public class Job {
    private String position;
    private String comName;
    private String location;
    private String condition;
    private String jobId;

    public Job(){
    }

    public Job(String position, String comName, String location, String condition, String jobId) {
        this.position = position;
        this.comName = comName;
        this.location = location;
        this.condition = condition;
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

