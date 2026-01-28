package com.example.taxadvisor.dto;

import com.example.taxadvisor.entity.TaxCategory;
import java.math.BigDecimal;

public record TaxCalculationResponse(
    TaxCategory category,
    String country,
    BigDecimal amount,
    BigDecimal rate,
    BigDecimal taxAmount,
    BigDecimal netAmount
) {}
