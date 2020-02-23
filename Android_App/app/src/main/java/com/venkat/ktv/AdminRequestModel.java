package com.venkat.ktv;

public class AdminRequestModel {

    private String uid;
    private String status;

    public AdminRequestModel() {
    }

    public AdminRequestModel(String uid, String status) {
        this.uid = uid;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AdminRequestModel(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
