package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "monthlygrowth")
public class MonthlyGrowth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "allocId")
    private int allocId;
    @Column(name = "month")
    private String month;
    @Column(name = "year")
    private int year;
    @Column(name = "contribution")
    private double contribution;
    @Column(name = "totalContribution")
    private double totalContribution;
    @Column(name = "currentValue")
    private double currentValue;
    @Column(name = "growthRate")
    private double growthRate;
    @Column(name = "previousContrib")
    private double previousContrib;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getContribution() {
        return contribution;
    }

    public void setContribution(double contribution) {
        this.contribution = contribution;
    }

    public double getTotalContribution() {
        return totalContribution;
    }

    public void setTotalContribution(double totalContribution) {
        this.totalContribution = totalContribution;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }

    public double getPreviousContrib() {
        return previousContrib;
    }

    public void setPreviousContrib(double previousContrib) {
        this.previousContrib = previousContrib;
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
                + ", \"month\":\"" + month + "\""
                + ", \"year\":" + year
                + ", \"contribution\":" + contribution
                + ", \"totalContribution\":" + totalContribution
                + ", \"currentValue\":" + currentValue
                + ", \"growthRate\":" + growthRate
                + ", \"previousContrib\":" + previousContrib
                + ", \"dateAdded\":\"" + dateAdded + "\""
                + ", \"addedBy\":\"" + addedBy + "\""
                + ", \"updateDate\":\"" + updateDate + "\""
                + ", \"updateBy\":\"" + updateBy + "\""
                + "}";
    }
}
