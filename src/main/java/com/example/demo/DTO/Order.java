package com.example.demo.DTO;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ordercreation")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subcontractor;
    private String engineer;
    private String contractingCompany;
    private String projectName;
    private String subcontractorSales;
    private BigDecimal unitPrice;
    private String paymentTerms;
    private BigDecimal settlement;
    private BigDecimal settlementLowerLimit;
    private BigDecimal settlementUpperLimit;
    private BigDecimal overtimeUnitPrice;
    private BigDecimal deductionUnitPrice;
    private BigDecimal settlementTimeUnit;
    private String dailyRateSetting;
    private String entryDate;
    private String expectedExitDate;

    // Default constructor for JPA
    public Order() {
    }

    // Constructor with parameters
    public Order(Long id, String subcontractor, String engineer, String contractingCompany, String projectName, String subcontractorSales, BigDecimal unitPrice, String paymentTerms, BigDecimal settlement, BigDecimal settlementLowerLimit, BigDecimal settlementUpperLimit, BigDecimal overtimeUnitPrice, BigDecimal deductionUnitPrice, BigDecimal settlementTimeUnit, String dailyRateSetting, String entryDate, String expectedExitDate) {
        this.id = id;
        this.subcontractor = subcontractor;
        this.engineer = engineer;
        this.contractingCompany = contractingCompany;
        this.projectName = projectName;
        this.subcontractorSales = subcontractorSales;
        this.unitPrice = unitPrice;
        this.paymentTerms = paymentTerms;
        this.settlement = settlement;
        this.settlementLowerLimit = settlementLowerLimit;
        this.settlementUpperLimit = settlementUpperLimit;
        this.overtimeUnitPrice = overtimeUnitPrice;
        this.deductionUnitPrice = deductionUnitPrice;
        this.settlementTimeUnit = settlementTimeUnit;
        this.dailyRateSetting = dailyRateSetting;
        this.entryDate = entryDate;
        this.expectedExitDate = expectedExitDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", subcontractor='" + subcontractor + '\'' +
                ", engineer='" + engineer + '\'' +
                ", contractingCompany='" + contractingCompany + '\'' +
                ", projectName=" + projectName +
                ", subcontractorSales='" + subcontractorSales + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", paymentTerms='" + paymentTerms + '\'' +
                ", settlement=" + settlement +
                ", settlementLowerLimit='" + settlementLowerLimit + '\'' +
                ", settlementUpperLimit='" + settlementUpperLimit + '\'' +
                ", overtimeUnitPrice='" + overtimeUnitPrice + '\'' +
                ", deductionUnitPrice=" + deductionUnitPrice +
                ", settlementTimeUnit='" + settlementTimeUnit + '\'' +
                ", dailyRateSetting='" + dailyRateSetting + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", expectedExitDate='" + expectedExitDate + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubcontractor() {
        return subcontractor;
    }

    public void setSubcontractor(String subcontractor) {
        this.subcontractor = subcontractor;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getContractingCompany() {
        return contractingCompany;
    }

    public void setContractingCompany(String contractingCompany) {
        this.contractingCompany = contractingCompany;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSubcontractorSales() {
        return subcontractorSales;
    }

    public void setSubcontractorSales(String subcontractorSales) {
        this.subcontractorSales = subcontractorSales;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public BigDecimal getSettlement() {
        return settlement;
    }

    public void setSettlement(BigDecimal settlement) {
        this.settlement = settlement;
    }

    public BigDecimal getSettlementLowerLimit() {
        return settlementLowerLimit;
    }

    public void setSettlementLowerLimit(BigDecimal settlementLowerLimit) {
        this.settlementLowerLimit = settlementLowerLimit;
    }

    public BigDecimal getSettlementUpperLimit() {
        return settlementUpperLimit;
    }

    public void setSettlementUpperLimit(BigDecimal settlementUpperLimit) {
        this.settlementUpperLimit = settlementUpperLimit;
    }

    public BigDecimal getOvertimeUnitPrice() {
        return overtimeUnitPrice;
    }

    public void setOvertimeUnitPrice(BigDecimal overtimeUnitPrice) {
        this.overtimeUnitPrice = overtimeUnitPrice;
    }

    public BigDecimal getDeductionUnitPrice() {
        return deductionUnitPrice;
    }

    public void setDeductionUnitPrice(BigDecimal deductionUnitPrice) {
        this.deductionUnitPrice = deductionUnitPrice;
    }

    public BigDecimal getSettlementTimeUnit() {
        return settlementTimeUnit;
    }

    public void setSettlementTimeUnit(BigDecimal settlementTimeUnit) {
        this.settlementTimeUnit = settlementTimeUnit;
    }

    public String getDailyRateSetting() {
        return dailyRateSetting;
    }

    public void setDailyRateSetting(String dailyRateSetting) {
        this.dailyRateSetting = dailyRateSetting;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getExpectedExitDate() {
        return expectedExitDate;
    }

    public void setExpectedExitDate(String expectedExitDate) {
        this.expectedExitDate = expectedExitDate;
    }
}

