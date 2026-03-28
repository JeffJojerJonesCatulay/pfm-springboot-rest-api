package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wantlist")
public class WantList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "dateWanted")
    private String dateWanted;
    @Column(name = "item")
    private String item;
    @Column(name = "estimatedPrice")
    private int estimatedPrice;
    @Column(name = "afford")
    private String afford;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "status")
    private String status;
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

    public String getDateWanted() {
        return dateWanted;
    }

    public void setDateWanted(String dateWanted) {
        this.dateWanted = dateWanted;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(int estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getAfford() {
        return afford;
    }

    public void setAfford(String afford) {
        this.afford = afford;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @Override
    public String toString() {
        return "{"
                + "\"id\":" + id
                + ", \"dateWanted\":\"" + dateWanted + "\""
                + ", \"item\":\"" + item + "\""
                + ", \"estimatedPrice\":" + estimatedPrice
                + ", \"afford\":\"" + afford + "\""
                + ", \"remarks\":\"" + remarks + "\""
                + ", \"status\":\"" + status + "\""
                + ", \"dateAdded\":\"" + dateAdded + "\""
                + ", \"addedBy\":\"" + addedBy + "\""
                + ", \"updateDate\":\"" + updateDate + "\""
                + ", \"updateBy\":\"" + updateBy + "\""
                + "}";
    }
}
