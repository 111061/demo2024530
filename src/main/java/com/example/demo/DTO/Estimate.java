package com.example.demo.DTO;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "estimates")
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estimate_number", nullable = false)
    private String estimateNumber;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "task_period_start")
    private LocalDate taskPeriodStart;

    @Column(name = "task_period_end")
    private LocalDate taskPeriodEnd;

    @Column(name = "responsible_person")
    private String responsiblePerson;

    @Column(name = "approver")
    private String approver;

    // Constructors
    public Estimate() {
    }

    public Estimate(String estimateNumber, String operatorName, String taskDescription, BigDecimal unitPrice, Integer quantity, BigDecimal subtotal, LocalDate taskPeriodStart, LocalDate taskPeriodEnd, String responsiblePerson, String approver) {
        this.estimateNumber = estimateNumber;
        this.operatorName = operatorName;
        this.taskDescription = taskDescription;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.taskPeriodStart = taskPeriodStart;
        this.taskPeriodEnd = taskPeriodEnd;
        this.responsiblePerson = responsiblePerson;
        this.approver = approver;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstimateNumber() {
        return estimateNumber;
    }

    public void setEstimateNumber(String estimateNumber) {
        this.estimateNumber = estimateNumber;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public LocalDate getTaskPeriodStart() {
        return taskPeriodStart;
    }

    public void setTaskPeriodStart(LocalDate taskPeriodStart) {
        this.taskPeriodStart = taskPeriodStart;
    }

    public LocalDate getTaskPeriodEnd() {
        return taskPeriodEnd;
    }

    public void setTaskPeriodEnd(LocalDate taskPeriodEnd) {
        this.taskPeriodEnd = taskPeriodEnd;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }
}
