package com.pfm.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salaryexpensetracker")
public class SalaryExpenseTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "salaryId")
    private int salaryId;
    @Column(name = "date")
    private String date;
    @Column(name = "expenseDescription")
    private String expenseDescription;
    @Column(name = "expenseValue")
    private double expenseValue;
    @Column(name = "expenseType")
    private String expenseType;
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

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
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

    public double getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(double expenseValue) {
        this.expenseValue = expenseValue;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
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
                + ", \"salaryId\":" + salaryId
                + ", \"date\":\"" + date + "\""
                + ", \"expenseDescription\":\"" + expenseDescription + "\""
                + ", \"expenseValue\":" + expenseValue
                + ", \"expenseType\":\"" + expenseType + "\""
                + ", \"dateAdded\":\"" + dateAdded + "\""
                + ", \"addedBy\":\"" + addedBy + "\""
                + ", \"updateDate\":\"" + updateDate + "\""
                + ", \"updateBy\":\"" + updateBy + "\""
                + "}";
    }
}
