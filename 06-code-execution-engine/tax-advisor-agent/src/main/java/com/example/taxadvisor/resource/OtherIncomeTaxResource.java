package com.example.taxadvisor.resource;

import com.example.taxadvisor.dto.TaxCalculationRequest;
import com.example.taxadvisor.dto.TaxCalculationResponse;
import com.example.taxadvisor.entity.TaxCategory;
import com.example.taxadvisor.service.TaxCalculationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/tax/other-income")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OtherIncomeTaxResource {

    @Inject
    TaxCalculationService taxCalculationService;

    @POST
    @Path("/calculate")
    public TaxCalculationResponse calculate(TaxCalculationRequest request) {
        return taxCalculationService.calculate(request, TaxCategory.OTHER_INCOME);
    }
}
