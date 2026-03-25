package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "allocationmapping")
public class AllocationMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "allocId")
    private long allocId;
    @Column (name = "allocation")
    private String allocation;
    @Column (name = "type")
    private String type;
    @Column (name = "description")
    private String description;
    @Column (name = "status")
    private String status;
    @Column (name = "dateAdded")
    private String dateAdded;
    @Column (name = "addedBy")
    private String addedBy;
    @Column (name = "updateDate")
    private String updateDate;
    @Column (name = "updateBy")
    private String updateBy;

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public long getAllocId() {
        return allocId;
    }

    public void setAllocId(long allocId) {
        this.allocId = allocId;
    }

    @Override
    public String toString() {
        return "AllocationMapping{" +
                "allocId=" + allocId +
                ", allocation='" + allocation + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                ", addedBy='" + addedBy + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}
