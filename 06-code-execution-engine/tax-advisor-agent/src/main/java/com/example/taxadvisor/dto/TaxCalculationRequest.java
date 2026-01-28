package com.example.taxadvisor.dto;

import java.math.BigDecimal;

public record TaxCalculationRequest(
    String country,
    BigDecimal amount
) {}
