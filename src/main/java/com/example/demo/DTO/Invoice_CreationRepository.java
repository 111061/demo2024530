package com.example.demo.DTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.DTO.Invoice_Creation;


import java.util.List;

@Repository
public interface Invoice_CreationRepository extends JpaRepository<Invoice_Creation, Long> {

    List<Invoice_Creation> findByparentCompany(String parentCompany);
    List<Invoice_Creation> findByorderNumber(String orderNumber);

    @Query("SELECT i FROM Invoice_Creation i WHERE " +
            "(:parentCompany IS NULL OR i.parentCompany = :parentCompany) AND " +
            "(:orderNumber IS NULL OR i.orderNumber = :orderNumber) AND " +
            "(:engineer IS NULL OR i.engineer = :engineer) AND " +

            "(CAST(i.id AS string) LIKE %:keyword% OR i.parentCompany LIKE %:keyword% OR i.orderNumber LIKE %:keyword% OR i.engineer LIKE %:keyword% OR i.projectName LIKE %:keyword% OR i.parentSales LIKE %:keyword% OR CAST(i.workTime AS string) LIKE %:keyword% OR i.paymentTerms LIKE %:keyword% OR CAST(i.settlement AS string) LIKE %:keyword% OR CAST(i.settlementLowerLimit AS string) LIKE %:keyword% OR CAST(i.settlementUpperLimit AS string) LIKE %:keyword% OR CAST(i.overtimeUnitPrice AS string) LIKE %:keyword% OR CAST(i.deductionUnitPriceTotal AS string) LIKE %:keyword% OR CAST(i.settlementTimeUnit AS string) LIKE %:keyword% OR i.dailyRateSetting LIKE %:keyword% OR CAST(i.entryDate AS string) LIKE %:keyword% OR CAST(i.expectedExitDate AS string) LIKE %:keyword%)")

    List<Invoice_Creation> searchInvoice_creations(@Param("parentCompany") String parentCompany,
                                                     @Param("orderNumber") String orderNumber,
                                                     @Param("engineer") String engineer,
                                                     @Param("keyword") String keyword);
}

