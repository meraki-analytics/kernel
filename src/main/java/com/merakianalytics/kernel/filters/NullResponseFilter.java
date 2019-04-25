package com.merakianalytics.kernel.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;

/**
 * Replaces null returns with HTTP 404 errors instead of empty 204s.
 */
public class NullResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
        if(responseContext.getStatus() == 204 && responseContext.getEntity() == null) {
            responseContext.setStatusInfo(Response.Status.NOT_FOUND);
        }
    }
}
