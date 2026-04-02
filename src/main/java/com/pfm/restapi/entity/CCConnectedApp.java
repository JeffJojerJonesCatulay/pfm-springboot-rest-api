package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ccconnectedapp")
public class CCConnectedApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private long id;
    @Column(name = "ccId")
    private int ccId;
    @Column(name = "connectedApp")
    private String connectedApp;
    @Column(name = "subscription")
    private String subscription;
    @Column(name = "autoDebit")
    private String autoDebit;
    @Column(name = "amount")
    private double amount;
    @Column(name = "date")
    private String date;
    @Column(name = "remarks")
    private String remarks;
    @Column (name = "dateAdded")
    private String dateAdded;
    @Column (name = "addedBy")
    private String addedBy;
    @Column (name = "updateDate")
    private String updateDate;
    @Column (name = "updateBy")
    private String updateBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCcId() {
        return ccId;
    }

    public void setCcId(int ccId) {
        this.ccId = ccId;
    }

    public String getConnectedApp() {
        return connectedApp;
    }

    public void setConnectedApp(String connectedApp) {
        this.connectedApp = connectedApp;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getAutoDebit() {
        return autoDebit;
    }

    public void setAutoDebit(String autoDebit) {
        this.autoDebit = autoDebit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @Override
    public String toString() {
        return "{"
                + "\"id\":" + id
                + ", \"ccId\":" + ccId
                + ", \"connectedApp\":\"" + connectedApp + "\""
                + ", \"subscription\":\"" + subscription + "\""
                + ", \"autoDebit\":\"" + autoDebit + "\""
                + ", \"amount\":\"" + amount + "\""
                + ", \"date\":\"" + date + "\""
                + ", \"remarks\":\"" + remarks + "\""
                + ", \"dateAdded\":\"" + dateAdded + "\""
                + ", \"addedBy\":\"" + addedBy + "\""
                + ", \"updateDate\":\"" + updateDate + "\""
                + ", \"updateBy\":\"" + updateBy + "\""
                + "}";
    }
}
