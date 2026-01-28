package com.example.taxadvisor.dto;

import com.example.taxadvisor.entity.TaxCategory;
import java.math.BigDecimal;

public record TaxRateConfigDTO(
    Long id,
    TaxCategory category,
    String countryCode,
    BigDecimal rate,
    String description
) {}
