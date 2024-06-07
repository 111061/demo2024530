package com.example.demo.Service;

import com.example.demo.DTO.Contract_Management_Screen;
import com.example.demo.DTO.Invoice_Creation;
import com.example.demo.DTO.Invoice_CreationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class Invoice_CreationService {

    private final Invoice_CreationRepository invoiceCreationRepository;

    @Autowired
    public Invoice_CreationService(Invoice_CreationRepository invoiceCreationRepository) {
        this.invoiceCreationRepository = invoiceCreationRepository;
    }

    public double calculateSettlement(Long id) {
        Invoice_Creation invoiceCreation = invoiceCreationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice_Creation not found for this id :: " + id));

        double workTime = invoiceCreation.getWorkTime();
        double settlementLowerLimit = invoiceCreation.getSettlementLowerLimit();
        double settlementUpperLimit = invoiceCreation.getSettlementUpperLimit();
        double overtimeUnitPrice = invoiceCreation.getOvertimeUnitPrice();
        double deductionUnitPriceTotal = invoiceCreation.getDeductionUnitPriceTotal();

        // 计算公式
        double settlementAmount = 0.0;
        if (workTime < settlementLowerLimit) {
            settlementAmount = (settlementLowerLimit - workTime) * deductionUnitPriceTotal;
        } else if (workTime > settlementUpperLimit) {
            settlementAmount = (workTime - settlementUpperLimit) * overtimeUnitPrice;
        }

        return settlementAmount;
    }



    public List<Invoice_Creation> findAllInvoiceCreation() {
        return invoiceCreationRepository.findAll();
    }

    public List<Invoice_Creation> findByparentCompany(String parentCompany) {
        return invoiceCreationRepository.findByparentCompany(parentCompany);
    }

    public List<Invoice_Creation> findByorderNumber(String orderNumber) {
        return invoiceCreationRepository.findByorderNumber(orderNumber);
    }




    public Invoice_Creation addInvoiceCreation(Invoice_Creation invoiceCreation) {
        return invoiceCreationRepository.save(invoiceCreation);
    }

    public void deleteInvoiceCreateById(Long id) {
        invoiceCreationRepository.deleteById(id);
    }

    public List<Invoice_Creation> searchInvoice_creations(String parentCompany, String orderNumber, String engineer, String keyword) {
        if ("ALL".equals(parentCompany)) parentCompany = null;
        if ("ALL".equals(orderNumber)) orderNumber = null;
        if ("ALL".equals(engineer)) engineer = null;

        return invoiceCreationRepository.searchInvoice_creations(parentCompany, orderNumber, engineer, keyword);
    }

    // 其他查詢方法...


}
