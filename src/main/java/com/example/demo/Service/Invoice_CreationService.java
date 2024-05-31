package com.example.demo.Service;
import com.example.demo.DTO.*;
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
    public List<Invoice_Creation> findAllInvoiceCreation() {
        return invoiceCreationRepository.findAll();
    }
    public Invoice_Creation addInvoiceCreation(Invoice_Creation invoiceCreation) {
        // 處理新增員工的邏輯
        return invoiceCreationRepository.save(invoiceCreation);
    }
    public void deleteInvoiceCreateById(Long id){
        invoiceCreationRepository.deleteById(id);
    }
    // 其他查詢方法...






}


