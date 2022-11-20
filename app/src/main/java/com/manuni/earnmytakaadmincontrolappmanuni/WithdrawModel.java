package com.manuni.earnmytakaadmincontrolappmanuni;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawModel {
    private String userId;
    private String payPalEmail;
    private String sentBy;
    private String myCoins;
    private String mobile;
    private String userEmail;
    private String status;



    public WithdrawModel(String userId, String payPalEmail, String myCoins, String mobile, String sentBy,String userEmail) {
        this.userId = userId;
        this.payPalEmail = payPalEmail;
        this.sentBy = sentBy;
        this.myCoins = myCoins;
        this.mobile = mobile;
        this.userEmail = userEmail;

    }
    public WithdrawModel() {
    }
    public String getuserEmail() {return userEmail;}
    public void setuserEmail(String userEmail) {this.userEmail = userEmail;}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMyCoins() {
        return myCoins;
    }

    public void setMyCoins(String myCoins) {
        this.myCoins = myCoins;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getpayPalEmail() {
        return payPalEmail;
    }

    public void setpayPalEmail(String payPalEmail) {
        this.payPalEmail = payPalEmail;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    @ServerTimestamp
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
