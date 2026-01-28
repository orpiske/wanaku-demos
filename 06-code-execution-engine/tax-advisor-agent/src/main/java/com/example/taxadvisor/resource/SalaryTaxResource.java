package com.example.taxadvisor.resource;

import com.example.taxadvisor.dto.TaxCalculationRequest;
import com.example.taxadvisor.dto.TaxCalculationResponse;
import com.example.taxadvisor.entity.TaxCategory;
import com.example.taxadvisor.service.TaxCalculationService;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/tax/salary")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalaryTaxResource {
    private static final Logger LOG = Logger.getLogger(SalaryTaxResource.class);

    @Inject
    TaxCalculationService taxCalculationService;

    @POST
    @Path("/calculate")
    public TaxCalculationResponse calculate(TaxCalculationRequest request) {
        LOG.infof("Calculating tax calculation: %s", request.toString());
        return taxCalculationService.calculate(request, TaxCategory.SALARY);
    }
}
