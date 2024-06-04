package com.example.demo.DTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface Contract_Management_ScreenRepository extends JpaRepository<Contract_Management_Screen, Long> {
    List<Contract_Management_Screen> findByContractingCompany(String contractingCompany);
    List<Contract_Management_Screen> findByContractType(String contractType);

    @Query("SELECT c FROM Contract_Management_Screen c WHERE " +
            "(:contractingCompany IS NULL OR c.contractingCompany = :contractingCompany) AND " +
            "(:ourPosition IS NULL OR c.ourPosition = :ourPosition) AND " +
            "(:contractType IS NULL OR c.contractType = :contractType) AND " +
            "(:keyword IS NULL OR " +
            "CAST(c.contractCode AS string) LIKE %:keyword% OR " +
            "c.contractingCompany LIKE %:keyword% OR " +
            "c.contractedCompany LIKE %:keyword% OR " +
            "c.contractType LIKE %:keyword% OR " +
            "c.ourPosition LIKE %:keyword% OR " +
            "c.ourSalesRepresentative LIKE %:keyword% OR " +
            "c.theirSalesRepresentative LIKE %:keyword% OR " +
            "CAST(c.contractDate AS string) LIKE %:keyword% OR " +
            "c.quotation LIKE %:keyword%)")
    List<Contract_Management_Screen> searchContracts(@Param("contractingCompany") String contractingCompany,
                                                     @Param("ourPosition") String ourPosition,
                                                     @Param("contractType") String contractType,
                                                     @Param("keyword") String keyword);
}


