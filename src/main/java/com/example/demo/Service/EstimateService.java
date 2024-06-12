package com.example.demo.Service;

import com.example.demo.DTO.Estimate;
import com.example.demo.DTO.EstimateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstimateService {

    private final EstimateRepository estimateRepository;

    @Autowired
    public EstimateService(EstimateRepository estimateRepository) {
        this.estimateRepository = estimateRepository;
    }

    public Estimate addEstimate(Estimate estimate) {
        return estimateRepository.save(estimate);
    }

    public List<Estimate> addMultipleEstimates(Estimate estimate) {
        List<Estimate> estimates = new ArrayList<>();

        LocalDate startDate = estimate.getTaskPeriodStart();
        LocalDate endDate = estimate.getTaskPeriodEnd();
        int quantity = estimate.getQuantity();

        long monthsBetween = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1));
        if (monthsBetween / quantity > 7) {
            throw new IllegalArgumentException("The period divided by the quantity exceeds the allowed limit of 7 periods.");
        }

        LocalDate currentStartDate = startDate;
        for (int i = 0; i <= quantity; i++) {
            LocalDate currentEndDate = currentStartDate.plusMonths(monthsBetween / quantity).minusDays(1);
            Estimate newEstimate = new Estimate();
            newEstimate.setEstimateNumber(estimate.getEstimateNumber());
            newEstimate.setOperatorName(estimate.getOperatorName());
            newEstimate.setTaskDescription(estimate.getTaskDescription());
            newEstimate.setTaskPeriodStart(currentStartDate);
            newEstimate.setTaskPeriodEnd(currentEndDate);
            newEstimate.setUnitPrice(estimate.getUnitPrice());
            newEstimate.setQuantity(estimate.getQuantity());  // // 保持原本的数量
            newEstimate.setSubtotal(estimate.getUnitPrice().multiply(BigDecimal.valueOf(1)));
            newEstimate.setResponsiblePerson(estimate.getResponsiblePerson());
            newEstimate.setApprover(estimate.getApprover());
            estimates.add(estimateRepository.save(newEstimate));
            currentStartDate = currentEndDate.plusDays(1);
        }

        return estimates;
    }

    public List<Estimate> findAllEstimates() {
        return estimateRepository.findAll();
    }

    public void deleteEstimatesByIds(List<Long> ids) {
        estimateRepository.deleteAllById(ids);
    }

    public Estimate updateEstimate(Long id, Estimate estimate) {
        Optional<Estimate> existingEstimate = estimateRepository.findById(id);
        if (existingEstimate.isPresent()) {
            Estimate updatedEstimate = existingEstimate.get();
            updatedEstimate.setEstimateNumber(estimate.getEstimateNumber());
            updatedEstimate.setOperatorName(estimate.getOperatorName());
            updatedEstimate.setTaskDescription(estimate.getTaskDescription());
            updatedEstimate.setTaskPeriodStart(estimate.getTaskPeriodStart());
            updatedEstimate.setTaskPeriodEnd(estimate.getTaskPeriodEnd());
            updatedEstimate.setUnitPrice(estimate.getUnitPrice());
            updatedEstimate.setQuantity(estimate.getQuantity());
            updatedEstimate.setSubtotal(estimate.getSubtotal());
            updatedEstimate.setResponsiblePerson(estimate.getResponsiblePerson());
            updatedEstimate.setApprover(estimate.getApprover());
            return estimateRepository.save(updatedEstimate);
        } else {
            return null;
        }
    }

    public List<Estimate> searchEstimates(String keyword) {
        return estimateRepository.searchByKeyword(keyword);
    }
}
