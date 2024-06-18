package com.example.demo.DTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {

    @Query("SELECT e FROM Estimate e WHERE e.estimateNumber LIKE %:keyword% OR e.operatorName LIKE %:keyword% OR e.taskDescription LIKE %:keyword% OR e.responsiblePerson LIKE %:keyword% OR e.approver LIKE %:keyword%")
    List<Estimate> searchByKeyword(@Param("keyword") String keyword);
    @Query("SELECT e FROM Estimate e WHERE e.id IN :ids")
    List<Estimate> findAllByIds(@Param("ids") List<Long> ids);
}
