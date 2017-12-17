package com.merakianalytics.kernel.riotapi;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.GZIP;

import com.merakianalytics.kernel.KernelContext;
import com.merakianalytics.kernel.providers.MessagePackProvider;

/**
 * A proxy service for the Riot API
 */
@GZIP
@Produces({MediaType.APPLICATION_JSON, MessagePackProvider.APPLICATION_MSGPACK})
public abstract class RiotAPIService {
    @Inject
    protected KernelContext context;
}
