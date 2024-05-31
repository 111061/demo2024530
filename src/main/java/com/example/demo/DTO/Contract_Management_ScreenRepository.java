package com.example.demo.DTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Contract_Management_ScreenRepository extends JpaRepository<Contract_Management_Screen, Long> {
    List<Contract_Management_Screen> findByContractingCompany(String contractingCompany);
    List<Contract_Management_Screen> findByContractType(String contractType);
}
