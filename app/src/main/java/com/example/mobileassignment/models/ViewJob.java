package com.example.mobileassignment.models;

public class ViewJob {
    private String position;
    private String comName;
    private String desc;
    private String jobId;

    public ViewJob(){
    }

    public ViewJob(String position, String comName, String desc, String jobId) {
        this.position = position;
        this.comName = comName;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}