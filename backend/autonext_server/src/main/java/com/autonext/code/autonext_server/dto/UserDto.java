
package com.autonext.code.autonext_server.dto;

public class UserDto {

    private String name;
    private String surname;
    private String email;
    private String jobPosition;
    private String delegation;
    // private int strikes;

    public UserDto(String name, String surname, String email, String jobPosition, String delegation) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.jobPosition = jobPosition;
        this.delegation = delegation;
    }
    
    // public UserDto(String name, String email, String jobPosition, String delegation, int strikes) {
    //     this.name = name;
    //     this.email = email;
    //     this.jobPosition = jobPosition;
    //     this.delegation = delegation;
    //     this.strikes = strikes;
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    // public int getStrikes() {
    //     return strikes;
    // }

    // public void setStrikes(int strikes) {
    //     this.strikes = strikes;
    // }

    

}