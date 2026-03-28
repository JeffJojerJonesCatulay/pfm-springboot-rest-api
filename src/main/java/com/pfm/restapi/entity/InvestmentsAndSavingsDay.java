package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "investmentsandsavingsday")
public class InvestmentsAndSavingsDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "allocId")
    private int allocId;
    @Column(name = "date")
    private String date;
    @Column(name = "valueAdded")
    private int valueAdded;
    @Column(name = "marketValue")
    private int marketValue;
    @Column(name = "dateAdded")
    private String dateAdded;
    @Column(name = "addedBy")
    private String addedBy;
    @Column(name = "updateDate")
    private String updateDate;
    @Column(name = "updateBy")
    private String updateBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAllocId() {
        return allocId;
    }

    public void setAllocId(int allocId) {
        this.allocId = allocId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValueAdded() {
        return valueAdded;
    }

    public void setValueAdded(int valueAdded) {
        this.valueAdded = valueAdded;
    }

    public int getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(int marketValue) {
        this.marketValue = marketValue;
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
                + ", \"allocId\":" + allocId
                + ", \"date\":\"" + date + "\""
                + ", \"valueAdded\":" + valueAdded
                + ", \"marketValue\":" + marketValue
                + ", \"dateAdded\":\"" + dateAdded + "\""
                + ", \"addedBy\":\"" + addedBy + "\""
                + ", \"updateDate\":\"" + updateDate + "\""
                + ", \"updateBy\":\"" + updateBy + "\""
                + "}";
    }
}
