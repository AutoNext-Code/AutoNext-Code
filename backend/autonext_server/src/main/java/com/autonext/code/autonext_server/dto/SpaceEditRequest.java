package com.autonext.code.autonext_server.dto;

import com.autonext.code.autonext_server.models.enums.JobPosition;
import com.autonext.code.autonext_server.models.enums.PlugType;

public class SpaceEditRequest {
    
    private int id;
    private PlugType plugType;
    private JobPosition jobPosition;

    
    public SpaceEditRequest() { }

    public SpaceEditRequest(int id, PlugType plugType, JobPosition jobPosition) {
        this.id = id;
        this.plugType = plugType;
        this.jobPosition = jobPosition;
    }
 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public PlugType getPlugType() {
        return plugType;
    }
    public void setPlugType(PlugType plugType) {
        this.plugType = plugType;
    }
    public JobPosition getJobPosition() {
        return jobPosition;
    }
    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }

    @Override
    public String toString() {
        return "SpaceEditRequest [id=" + id + ", plugType=" + plugType + ", jobPosition=" + jobPosition + "]";
    }

    

}
