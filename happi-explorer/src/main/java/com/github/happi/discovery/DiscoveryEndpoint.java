package com.github.happi.discovery;

import java.util.Objects;
import java.util.Set;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import com.github.happi.security.HappiSecurityManager;

/**
 * A controller for registered entities.
 */
@RequestScoped
@Path("discovery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiscoveryEndpoint {

    /**
     * Security manager for this module.
     */
    @Inject
    private HappiSecurityManager securityManager;

    /**
     * Discovery service.
     */
    @Inject
    private DiscoveryService service;

    /**
     * Default constructor.
     * This class is injectable, don't call this constructor explicitly.
     */
    public DiscoveryEndpoint() {
        // NO-OP
    }

    /**
     * Show all registered entities.
     *
     * @return A set of registered entities
     */
    @GET
    public Set<Entity> discover() {
        checkIfEndpointEnable();
        return this.service.getEntities();
    }

    /**
     * Check if this endpoint is enabled.
     *
     * @throws ForbiddenException if this endpoint is disabled
     */
    private void checkIfEndpointEnable() {
        var permission = this.securityManager
                .getConfiguration(HappiSecurityManager.Configuration.DISCOVERY_ENDPOINT);

        if (Objects.equals(Boolean.parseBoolean(permission), Boolean.FALSE)) {
            throw new ForbiddenException();
        }
    }
}
