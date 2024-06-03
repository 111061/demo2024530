package com.example.demo.DTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Invoice_CreationRepository extends JpaRepository<Invoice_Creation, Long> {
}

