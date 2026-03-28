package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ccrecordexpensetracker")
public class CCRecordExpenseTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ccExpId")
    private long ccExpId;
    @Column (name = "ccRecId")
    private long ccRecId;
    @Column (name = "date")
    private String date;
    @Column (name = "expenseDescription")
    private String expenseDescription;
    @Column (name = "expenseValue")
    private long expenseValue;
    @Column (name = "dateAdded")
    private String dateAdded;
    @Column (name = "addedBy")
    private String addedBy;
    @Column (name = "updateDate")
    private String updateDate;
    @Column (name = "updatedBy")
    private String updatedBy;

    public long getCcExpId() {
        return ccExpId;
    }

    public void setCcExpId(long ccExpId) {
        this.ccExpId = ccExpId;
    }

    public long getCcRecId() {
        return ccRecId;
    }

    public void setCcRecId(long ccRecId) {
        this.ccRecId = ccRecId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public long getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(long expenseValue) {
        this.expenseValue = expenseValue;
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
                + "\"ccExpId\":" + ccExpId
                + ", \"ccRecId\":" + ccRecId
                + ", \"date\":\"" + date + "\""
                + ", \"expenseDescription\":\"" + expenseDescription + "\""
                + ", \"expenseValue\":" + expenseValue
                + ", \"dateAdded\":\"" + dateAdded + "\""
                + ", \"addedBy\":\"" + addedBy + "\""
                + ", \"updateDate\":\"" + updateDate + "\""
                + ", \"updatedBy\":\"" + updatedBy + "\""
                + "}";
    }
}
