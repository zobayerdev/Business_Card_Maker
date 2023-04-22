package com.trodev.visitingcardmaker;

public class ReadWriteUserDetails {

    private String mobile, email, password, gender;

    public ReadWriteUserDetails(String mobile, String email, String password, String gender) {

        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
