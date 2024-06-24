package com.example.demo.Service;

import com.example.demo.DTO.Partner;
import com.example.demo.DTO.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<Partner> findAllPartners() {
        return partnerRepository.findAll();
    }

    public Partner addPartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    public void deletePartnerById(Long id) {
        partnerRepository.deleteById(id);
    }

    public Partner findPartnerById(Long id) {
        return partnerRepository.findById(id).orElse(null);
    }
}
