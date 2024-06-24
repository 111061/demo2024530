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
    private String job_details;
    private String work_place;
    private Double person_month;
    private Double unitPrice;
    private String paymentTerms;
    private Double settlement;
    private Double settlementLowerLimit;
    private Double settlementUpperLimit;
    private Double overtimeUnitPrice;
    private Double deductionUnitPrice;
    private Double settlementTimeUnit;
    private String dailyRateSetting;
    private String entryDate;
    private String expectedExitDate;

    // Default constructor for JPA
    public Order() {
    }

    // Constructor with parameters
    public Order(Long id, String subcontractor, String engineer, String contractingCompany, String projectName, String subcontractorSales,String job_details,String work_place,Double person_month, Double unitPrice, String paymentTerms, Double settlement, Double settlementLowerLimit, Double settlementUpperLimit, Double overtimeUnitPrice, Double deductionUnitPrice, Double settlementTimeUnit, String dailyRateSetting, String entryDate, String expectedExitDate) {
        this.id = id;
        this.subcontractor = subcontractor;
        this.engineer = engineer;
        this.contractingCompany = contractingCompany;
        this.projectName = projectName;
        this.subcontractorSales = subcontractorSales;
        this.job_details = job_details;
        this.work_place = work_place;
        this.person_month = person_month;
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
                ", job_details='" + job_details + '\'' +
                ", work_place='" + work_place + '\'' +
                ", person_month='" + person_month + '\'' +
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

    public String getJob_details() {return job_details;}

    public  void setJob_details(String job_details) {this.job_details = job_details;}

    public String getWork_place() {return work_place;}

    public void setWork_place(String work_place) {this.work_place = work_place;}

    public Double getPerson_month() {return person_month;}

    public void setPerson_month(Double person_month) {this.person_month = person_month;}

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

    public Double getDeductionUnitPrice() {
        return deductionUnitPrice;
    }

    public void setDeductionUnitPrice(Double deductionUnitPrice) {
        this.deductionUnitPrice = deductionUnitPrice;
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

