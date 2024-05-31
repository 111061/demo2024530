package com.example.demo.Service;

import com.example.demo.DTO.Contract_Management_Screen;
import com.example.demo.DTO.Contract_Management_ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Contract_Management_ScreenService {

    private final Contract_Management_ScreenRepository contractRepository;

    @Autowired
    public Contract_Management_ScreenService(Contract_Management_ScreenRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract_Management_Screen> findAllContracts() {
        return contractRepository.findAll();
    }

    public Contract_Management_Screen findContractById(Long id) {
        return contractRepository.findById(id).orElse(null);
    }

    public List<Contract_Management_Screen> findByContractingCompany(String contractingCompany) {
        return contractRepository.findByContractingCompany(contractingCompany);
    }

    public List<Contract_Management_Screen> findByContractType(String contractType) {
        return contractRepository.findByContractType(contractType);
    }

    public Contract_Management_Screen addContract(Contract_Management_Screen contract) {
        // 處理新增契約的邏輯
        return contractRepository.save(contract);
    }

    public void deleteContractById(Long id) {
        contractRepository.deleteById(id);
    }

    // 其他查詢方法...
}
