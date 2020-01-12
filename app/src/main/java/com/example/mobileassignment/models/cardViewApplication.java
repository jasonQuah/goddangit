package com.example.mobileassignment.models;

public class cardViewApplication {
    private String userName;
    private String UserAddress;
    private String userAge;
    private String userId;

    public cardViewApplication(){
    }

    public cardViewApplication(String userName, String userAddress, String userAge, String userId) {
        this.userName = userName;
        this.UserAddress = userAddress;
        this.userAge = userAge;
        this.userId = userId;
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
}
