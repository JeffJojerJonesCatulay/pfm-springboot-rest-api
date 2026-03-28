package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ccrecordtracker")
public class CCRecordTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ccRecId")
    private long ccRecId;
    @Column(name = "ccId")
    private int ccId;
    @Column(name = "dateFrom")
    private String dateFrom;
    @Column(name = "dateTo")
    private String dateTo;
    @Column(name = "dueDate")
    private String dueDate;
    @Column(name = "status")
    private String status;
    @Column (name = "dateAdded")
    private String dateAdded;
    @Column (name = "addedBy")
    private String addedBy;
    @Column (name = "updateDate")
    private String updateDate;
    @Column (name = "updatedBy")
    private String updatedBy;

    public long getCcRecId() {
        return ccRecId;
    }

    public void setCcRecId(long ccRecId) {
        this.ccRecId = ccRecId;
    }

    public int getCcId() {
        return ccId;
    }

    public void setCcId(int ccId) {
        this.ccId = ccId;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "{"
                + "\"ccRecId\":" + ccRecId
                + ", \"ccId\":" + ccId
                + ", \"dateFrom\":\"" + dateFrom + "\""
                + ", \"dateTo\":\"" + dateTo + "\""
                + ", \"dueDate\":\"" + dueDate + "\""
                + ", \"status\":\"" + status + "\""
                + ", \"dateAdded\":\"" + dateAdded + "\""
                + ", \"addedBy\":\"" + addedBy + "\""
                + ", \"updateDate\":\"" + updateDate + "\""
                + ", \"updatedBy\":\"" + updatedBy + "\""
                + "}";
    }
}
