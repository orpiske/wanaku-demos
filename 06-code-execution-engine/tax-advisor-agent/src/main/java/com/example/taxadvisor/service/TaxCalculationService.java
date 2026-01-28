package com.example.taxadvisor.service;

import com.example.taxadvisor.dto.TaxCalculationRequest;
import com.example.taxadvisor.dto.TaxCalculationResponse;
import com.example.taxadvisor.entity.TaxCategory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;

@ApplicationScoped
public class TaxCalculationService {

    @Inject
    TaxRateService taxRateService;

    public TaxCalculationResponse calculate(TaxCalculationRequest request, TaxCategory category) {
        BigDecimal rate = taxRateService.getRate(request.country(), category);
        BigDecimal taxAmount = request.amount()
            .multiply(rate)
            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal netAmount = request.amount().subtract(taxAmount);

        return new TaxCalculationResponse(
            category,
            request.country(),
            request.amount(),
            rate,
            taxAmount,
            netAmount
        );
    }
}
