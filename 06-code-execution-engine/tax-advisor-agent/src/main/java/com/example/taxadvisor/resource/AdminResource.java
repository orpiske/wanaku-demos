package com.example.taxadvisor.resource;

import com.example.taxadvisor.dto.TaxRateConfigDTO;
import com.example.taxadvisor.entity.TaxRateConfig;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/admin/tax-rates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    @GET
    public List<TaxRateConfigDTO> listAll() {
        return TaxRateConfig.<TaxRateConfig>listAll().stream()
            .map(this::toDTO)
            .toList();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        TaxRateConfig config = TaxRateConfig.findById(id);
        if (config == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(toDTO(config)).build();
    }

    @POST
    @Transactional
    public Response create(TaxRateConfigDTO dto) {
        TaxRateConfig config = new TaxRateConfig();
        config.category = dto.category();
        config.countryCode = dto.countryCode().toUpperCase();
        config.rate = dto.rate();
        config.description = dto.description();
        config.persist();
        return Response.status(Response.Status.CREATED).entity(toDTO(config)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, TaxRateConfigDTO dto) {
        TaxRateConfig config = TaxRateConfig.findById(id);
        if (config == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        config.category = dto.category();
        config.countryCode = dto.countryCode().toUpperCase();
        config.rate = dto.rate();
        config.description = dto.description();
        return Response.ok(toDTO(config)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = TaxRateConfig.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    private TaxRateConfigDTO toDTO(TaxRateConfig config) {
        return new TaxRateConfigDTO(
            config.id,
            config.category,
            config.countryCode,
            config.rate,
            config.description
        );
    }
}
