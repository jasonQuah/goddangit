package com.example.mobileassignment.models;

public class addJob {
    private String jobid;
    private String position;
    private String desc;
    private String salary;
    private String requirement;
    private String category;
    private String userId;

    public addJob(){
    }

    public addJob(String jobid, String position, String desc, String salary, String requirement, String category, String userId) {
        this.jobid = jobid;
        this.position = position;
        this.desc = desc;
        this.salary = salary;
        this.requirement = requirement;
        this.category = category;
        this.userId = userId;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
