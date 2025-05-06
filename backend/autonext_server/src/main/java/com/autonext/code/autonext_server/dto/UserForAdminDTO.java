package com.autonext.code.autonext_server.dto;

import com.autonext.code.autonext_server.models.enums.JobPosition;
import com.autonext.code.autonext_server.models.enums.Role;

public class UserForAdminDTO {
    private String name;
    private String surname;
    private String email;
    private int strikes;
    private Role role;
    private JobPosition jobPosition;
    private String workCenter;
    private int id;

    public UserForAdminDTO() {
    }

    public UserForAdminDTO(int id, String name, String surname, String email, int strikes, Role role,
            JobPosition jobPosition,
            String workCenter) {
        this.id = id;
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

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}