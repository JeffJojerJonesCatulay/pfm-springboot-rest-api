package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ccdetails")
public class CCDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ccId")
    private long ccId;
    @Column (name = "ccName")
    private String ccName;
    @Column (name = "ccLastDigit")
    private String ccLastDigit;
    @Column (name = "ccAcronym")
    private String ccAcronym;
    @Column (name = "dateAdded")
    private String dateAdded;
    @Column (name = "addedBy")
    private String addedBy;
    @Column (name = "updateDate")
    private String updateDate;
    @Column (name = "updateBy")
    private String updateBy;

    public long getCcId() {
        return ccId;
    }

    public void setCcId(long ccId) {
        this.ccId = ccId;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcLastDigit() {
        return ccLastDigit;
    }

    public void setCcLastDigit(String ccLastDigit) {
        this.ccLastDigit = ccLastDigit;
    }

    public String getCcAcronym() {
        return ccAcronym;
    }

    public void setCcAcronym(String ccAcronym) {
        this.ccAcronym = ccAcronym;
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
        return "{" +
                "\"ccId\":" + ccId + "," +
                "\"ccName\":\"" + ccName + "\"," +
                "\"ccLastDigit\":\"" + ccLastDigit + "\"," +
                "\"ccAcronym\":\"" + ccAcronym + "\"," +
                "\"dateAdded\":\"" + dateAdded + "\"," +
                "\"addedBy\":\"" + addedBy + "\"," +
                "\"updateDate\":\"" + updateDate + "\"," +
                "\"updateBy\":\"" + updateBy + "\"" +
                "}";
    }
}
