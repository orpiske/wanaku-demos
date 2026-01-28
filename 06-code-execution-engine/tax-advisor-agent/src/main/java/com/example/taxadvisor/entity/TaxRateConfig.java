package com.example.taxadvisor.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "tax_rate_config")
public class TaxRateConfig extends PanacheEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TaxCategory category;

    @Column(name = "country_code", nullable = false)
    public String countryCode;

    @Column(nullable = false, precision = 5, scale = 2)
    public BigDecimal rate;

    @Column(length = 500)
    public String description;

    public static TaxRateConfig findByCountryAndCategory(String countryCode, TaxCategory category) {
        return find("countryCode = ?1 and category = ?2", countryCode, category).firstResult();
    }

    public static TaxRateConfig findDefaultByCategory(TaxCategory category) {
        return find("countryCode = ?1 and category = ?2", "DEFAULT", category).firstResult();
    }
}
