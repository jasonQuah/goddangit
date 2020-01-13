package com.example.mobileassignment.models;

public class cardViewApplication {
    private String userName;
    private String UserAddress;
    private String userAge;
    private String userId;
    private String jobId;
    private String status;

    public cardViewApplication(){
    }

    public cardViewApplication(String userName, String userAddress, String userAge, String userId, String jobId, String status) {
        this.userName = userName;
        this.UserAddress = userAddress;
        this.userAge = userAge;
        this.userId = userId;
        this.jobId = jobId;
        this.status = status;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }


    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
