package com.venkat.ktv;

public class LandDetailsModel {

    private String name;
    private String Phnum;
    private String district;
    private String taluka;
    private String village;
    private String division;
    private String status;
    private String farmer;
    //private String landarea;


    public LandDetailsModel() {
    }

    public LandDetailsModel(String name, String phnum, String district, String taluka, String village, String division, String status, String farmer) {
        this.name = name;
        Phnum = phnum;
        this.district = district;
        this.taluka = taluka;
        this.village = village;
        this.division = division;
        this.status = status;
        this.farmer = farmer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhnum() {
        return Phnum;
    }

    public void setPhnum(String phnum) {
        Phnum = phnum;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFarmer() {
        return farmer;
    }

    public void setFarmer(String farmer) {
        this.farmer = farmer;
    }
}
