package com.trodev.visitingcardmaker;

public class ReadWriteUserDetails {

    public String mobile, email, password, gender;

    public ReadWriteUserDetails() {
    }

    public ReadWriteUserDetails(String mobile, String email, String password, String gender) {

        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

}
