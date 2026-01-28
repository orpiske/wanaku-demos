package com.example.taxadvisor.service;

import com.example.taxadvisor.entity.TaxCategory;
import com.example.taxadvisor.entity.TaxRateConfig;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;

@ApplicationScoped
public class TaxRateService {

    public BigDecimal getRate(String countryCode, TaxCategory category) {
        TaxRateConfig config = TaxRateConfig.findByCountryAndCategory(countryCode.toUpperCase(), category);
        if (config == null) {
            config = TaxRateConfig.findDefaultByCategory(category);
        }
        return config != null ? config.rate : BigDecimal.valueOf(25.00);
    }

    public TaxRateConfig getRateConfig(String countryCode, TaxCategory category) {
        TaxRateConfig config = TaxRateConfig.findByCountryAndCategory(countryCode.toUpperCase(), category);
        if (config == null) {
            config = TaxRateConfig.findDefaultByCategory(category);
        }
        return config;
    }
}
