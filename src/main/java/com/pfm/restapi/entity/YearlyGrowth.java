package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "yearlygrowth")
public class YearlyGrowth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "allocId")
    private long allocId;
    @Column(name = "year")
    private long year;
    @Column(name = "totalContribution")
    private long totalContribution;
    @Column(name = "averageContribution")
    private long averageContribution;
    @Column(name = "averageCurrentValue")
    private long averageCurrentValue;
    @Column(name = "averageGrowthRate")
    private long averageGrowthRate;
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

    public long getAllocId() {
        return allocId;
    }

    public void setAllocId(long allocId) {
        this.allocId = allocId;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public long getTotalContribution() {
        return totalContribution;
    }

    public void setTotalContribution(long totalContribution) {
        this.totalContribution = totalContribution;
    }

    public long getAverageContribution() {
        return averageContribution;
    }

    public void setAverageContribution(long averageContribution) {
        this.averageContribution = averageContribution;
    }

    public long getAverageCurrentValue() {
        return averageCurrentValue;
    }

    public void setAverageCurrentValue(long averageCurrentValue) {
        this.averageCurrentValue = averageCurrentValue;
    }

    public long getAverageGrowthRate() {
        return averageGrowthRate;
    }

    public void setAverageGrowthRate(long averageGrowthRate) {
        this.averageGrowthRate = averageGrowthRate;
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
                + ", \"year\":" + year
                + ", \"totalContribution\":" + totalContribution
                + ", \"averageContribution\":" + averageContribution
                + ", \"averageCurrentValue\":" + averageCurrentValue
                + ", \"averageGrowthRate\":" + averageGrowthRate
                + ", \"dateAdded\":\"" + dateAdded + "\""
                + ", \"addedBy\":\"" + addedBy + "\""
                + ", \"updateDate\":\"" + updateDate + "\""
                + ", \"updateBy\":\"" + updateBy + "\""
                + "}";
    }
}
