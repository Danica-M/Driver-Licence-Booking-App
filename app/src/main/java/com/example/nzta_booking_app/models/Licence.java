package com.example.nzta_booking_app.models;

public class Licence {
    private String licenceHolderID;
    private String licence;
    private int passedTests;

    public Licence(String licenceHolderID, String licence, int passedTests) {
        this.licenceHolderID = licenceHolderID;
        this.licence = licence;
        this.passedTests = passedTests;
    }

    public String getLicenceHolderID() {
        return licenceHolderID;
    }

    public void setLicenceHolderID(String licenceHolderID) {
        this.licenceHolderID = licenceHolderID;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public int getPassedTests() {
        return passedTests;
    }

    public void setPassedTests(int passedTests) {
        this.passedTests = passedTests;
    }
}
