package com.example.demo.DTO;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.sql.Date;



@Entity
@Table(name = "invoice_creation")
public class Invoice_Creation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inId;
    private String parent_company;
    private String order_number;
    private String engineer;
    private LocalTime work_time;
    private String project_name;
    private String parent_sales;
    private BigDecimal unitPrice;
    private String payment_terms;
    private BigDecimal settlement;
    private BigDecimal settlement_lower_limit;
    private BigDecimal settlement_upper_limit;
    private BigDecimal overtime_unit_price;
    private BigDecimal deduction_unit_price_total;
    private BigDecimal settlement_time_unit;
    private String daily_rate_setting;
    private  Date entryDate;
    private Date expected_exit_date;



    // Getters and setters
    public Long getInId() {
        return inId;
    }

    public void setInId(Long inId) {
        this.inId = inId;
    }

    public String getParent_company(){ return parent_company;}

    public void setParent_company(String parent_company){this.parent_company = parent_company;}

    public String getOrder_number(){ return order_number;}

    public void setOrder_number(String order_number){this.order_number = order_number;}

    public String getEngineer(){ return engineer;}

    public void setEngineer(String engineer){this.engineer = engineer;}

    public LocalTime getWork_time(){ return work_time;}

    public void setWork_time(LocalTime work_time){this.work_time = work_time;}

    public String getProject_name(){ return project_name;}

    public void setProject_name(String project_name){this.project_name = project_name;}

    public String getProject_sales(){ return parent_sales;}

    public void setProject_sales(String parent_sales){this.parent_sales = parent_sales;}

    public BigDecimal getUnitPrice(){ return unitPrice;}

    public void setUnitPrice(BigDecimal unitPrice){this.unitPrice = unitPrice;}

    public String getPayment_terms(){ return payment_terms;}

    public void setPayment_terms(String payment_terms){this.payment_terms = payment_terms;}

    public BigDecimal getSettlement(){ return settlement;}

    public void setSettlement(BigDecimal settlement){this.settlement = settlement;}

    public BigDecimal getSettlement_lower_limit(){ return settlement_lower_limit;}

    public void setSettlement_lower_limit(BigDecimal settlement_lower_limit){this.settlement_lower_limit = settlement_lower_limit;}

    public BigDecimal getSettlement_upper_limit(){ return settlement_upper_limit;}

    public void setSettlement_upper_limit(BigDecimal settlement_upper_limit){this.settlement_upper_limit = settlement_upper_limit;}

    public BigDecimal getOvertime_unit_price(){ return overtime_unit_price;}

    public void setOvertime_unit_price(BigDecimal overtime_unit_price){this.overtime_unit_price = overtime_unit_price;}

    public BigDecimal getDeduction_unit_price_total(){ return deduction_unit_price_total;}

    public void setDeduction_unit_price_total(BigDecimal deduction_unit_price_total){this.deduction_unit_price_total = deduction_unit_price_total;}

    public BigDecimal getSettlement_time_unit(){ return settlement_time_unit;}

    public void setSettlement_time_unit(BigDecimal settlement_time_unit){this.settlement_time_unit = settlement_time_unit;}

    public String getDaily_rate_setting(){ return daily_rate_setting;}

    public void setDaily_rate_setting(String daily_rate_setting){this.daily_rate_setting = daily_rate_setting;}

    public  Date getEntryDate(){return entryDate;}

    public void setEntryDate(Date entryDate){this.entryDate = entryDate;}

    public  Date getExpected_exit_date(){return expected_exit_date;}

    public void setExpected_exit_date(Date expected_exit_date){this.expected_exit_date = expected_exit_date;}

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "Invoice_creation{" +
                "inId=" + inId +
                ", parent_company='" + parent_company + '\'' +
                ", order_number='" + order_number + '\'' +
                ", engineer=" + engineer +
                ", work_time='" + work_time + '\'' +
                ", project_name='" + project_name + '\'' +
                ", parent_sales='" + parent_sales + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", payment_terms='" + payment_terms + '\'' +
                ", settlement='" + settlement + '\'' +
                ", settlement_lower_limit='" + settlement_lower_limit + '\'' +
                ", settlement_upper_limit='" + settlement_upper_limit + '\'' +
                ", overtime_unit_price='" + overtime_unit_price + '\'' +
                ", deduction_unit_price_total='" + deduction_unit_price_total + '\'' +
                ", settlement_time_unit='" + settlement_time_unit + '\'' +
                ", daily_rate_setting='" + daily_rate_setting + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", expected_exit_date='" + expected_exit_date + '\'' +
                '}';
    }

}
