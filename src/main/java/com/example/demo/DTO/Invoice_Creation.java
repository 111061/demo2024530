package com.example.demo.DTO;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoicecreation")
public class Invoice_Creation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parentCompany;
    private String orderNumber;
    private String engineer;
    private Double workTime;
    private String projectName;
    private String parentSales;
    private Double unitPrice;
    private String paymentTerms;
    private Double settlement;
    private Double settlementLowerLimit;
    private Double settlementUpperLimit;
    private Double overtimeUnitPrice;
    private Double deductionUnitPriceTotal;
    private Double settlementTimeUnit;
    private String dailyRateSetting;
    private LocalDate entryDate;
    private LocalDate expectedExitDate;


    @Transient
    private double settlementValue;
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSettlementValue() {
        return settlementValue;
    }

    public void setSettlementValue(double settlementValue) {
        this.settlementValue = settlementValue;
    }


    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public Double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Double workTime) {
        this.workTime = workTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getParentSales() {
        return parentSales;
    }

    public void setParentSales(String parentSales) {
        this.parentSales = parentSales;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public Double getSettlement() {
        return settlement;
    }

    public void setSettlement(Double settlement) {
        this.settlement = settlement;
    }

    public Double getSettlementLowerLimit() {
        return settlementLowerLimit;
    }

    public void setSettlementLowerLimit(Double settlementLowerLimit) {
        this.settlementLowerLimit = settlementLowerLimit;
    }

    public Double getSettlementUpperLimit() {
        return settlementUpperLimit;
    }

    public void setSettlementUpperLimit(Double settlementUpperLimit) {
        this.settlementUpperLimit = settlementUpperLimit;
    }

    public Double getOvertimeUnitPrice() {
        return overtimeUnitPrice;
    }

    public void setOvertimeUnitPrice(Double overtimeUnitPrice) {
        this.overtimeUnitPrice = overtimeUnitPrice;
    }

    public Double getDeductionUnitPriceTotal() {
        return deductionUnitPriceTotal;
    }

    public void setDeductionUnitPriceTotal(Double deductionUnitPriceTotal) {
        this.deductionUnitPriceTotal = deductionUnitPriceTotal;
    }

    public Double getSettlementTimeUnit() {
        return settlementTimeUnit;
    }

    public void setSettlementTimeUnit(Double settlementTimeUnit) {
        this.settlementTimeUnit = settlementTimeUnit;
    }

    public String getDailyRateSetting() {
        return dailyRateSetting;
    }

    public void setDailyRateSetting(String dailyRateSetting) {
        this.dailyRateSetting = dailyRateSetting;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getExpectedExitDate() {
        return expectedExitDate;
    }

    public void setExpectedExitDate(LocalDate expectedExitDate) {
        this.expectedExitDate = expectedExitDate;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "Invoice_Creation{" +
                "id=" + id +
                ", parentCompany='" + parentCompany + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", engineer='" + engineer + '\'' +
                ", workTime=" + workTime +
                ", projectName='" + projectName + '\'' +
                ", parentSales='" + parentSales + '\'' +
                ", unitPrice=" + unitPrice +
                ", paymentTerms='" + paymentTerms + '\'' +
                ", settlement=" + settlement +
                ", settlementLowerLimit=" + settlementLowerLimit +
                ", settlementUpperLimit=" + settlementUpperLimit +
                ", overtimeUnitPrice=" + overtimeUnitPrice +
                ", deductionUnitPriceTotal=" + deductionUnitPriceTotal +
                ", settlementTimeUnit=" + settlementTimeUnit +
                ", dailyRateSetting='" + dailyRateSetting + '\'' +
                ", entryDate=" + entryDate +
                ", expectedExitDate=" + expectedExitDate +
                '}';
    }
}
