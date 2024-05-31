package com.example.demo.DTO;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "contracts")
public class Contract_Management_Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contractCode;

    private String contractingCompany;
    private String contractedCompany;
    private String contractType;
    private String ourPosition;
    private String ourSalesRepresentative;
    private String theirSalesRepresentative;
    private LocalDate contractDate;
    private String quotation;

    // Getters and setters
    public Integer getContractCode() {
        return contractCode;
    }

    public void setContractCode(Integer contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractingCompany() {
        return contractingCompany;
    }

    public void setContractingCompany(String contractingCompany) {
        this.contractingCompany = contractingCompany;
    }

    public String getContractedCompany() {
        return contractedCompany;
    }

    public void setContractedCompany(String contractedCompany) {
        this.contractedCompany = contractedCompany;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getOurPosition() {
        return ourPosition;
    }

    public void setOurPosition(String ourPosition) {
        this.ourPosition = ourPosition;
    }

    public String getOurSalesRepresentative() {
        return ourSalesRepresentative;
    }

    public void setOurSalesRepresentative(String ourSalesRepresentative) {
        this.ourSalesRepresentative = ourSalesRepresentative;
    }

    public String getTheirSalesRepresentative() {
        return theirSalesRepresentative;
    }

    public void setTheirSalesRepresentative(String theirSalesRepresentative) {
        this.theirSalesRepresentative = theirSalesRepresentative;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "ContractManagementScreen{" +
                "contractCode=" + contractCode +
                ", contractingCompany='" + contractingCompany + '\'' +
                ", contractedCompany='" + contractedCompany + '\'' +
                ", contractType='" + contractType + '\'' +
                ", ourPosition='" + ourPosition + '\'' +
                ", ourSalesRepresentative='" + ourSalesRepresentative + '\'' +
                ", theirSalesRepresentative='" + theirSalesRepresentative + '\'' +
                ", contractDate=" + contractDate +
                ", quotation='" + quotation + '\'' +
                '}';
    }
}
