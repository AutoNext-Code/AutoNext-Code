package com.autonext.code.autonext_server.dto;

import com.autonext.code.autonext_server.models.enums.Role;

public class UserForAdminDTO {
    private String name;
    private String surname;
    private String email;
    private int strikes;
    private Role role;
    private String jobPosition;
    private String workCenter;
    
    public UserForAdminDTO() {}

    public UserForAdminDTO(String name, String surname, String email, int strikes, Role role, String jobPosition,
            String workCenter) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.strikes = strikes;
        this.role = role;
        this.jobPosition = jobPosition;
        this.workCenter = workCenter;
    }

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

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }
   
}