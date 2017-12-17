package com.merakianalytics.kernel;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.merakianalytics.kernel.exceptions.OriannaExceptionMapper;
import com.merakianalytics.kernel.exceptions.QueryValidationExceptionMapper;
import com.merakianalytics.kernel.filters.CORSFilter;
import com.merakianalytics.kernel.providers.JSONProvider;
import com.merakianalytics.kernel.providers.MessagePackProvider;
import com.merakianalytics.kernel.riotapi.ChampionAPI;
import com.merakianalytics.kernel.riotapi.ChampionMasteryAPI;
import com.merakianalytics.kernel.riotapi.LeagueAPI;
import com.merakianalytics.kernel.riotapi.MatchAPI;
import com.merakianalytics.kernel.riotapi.SpectatorAPI;
import com.merakianalytics.kernel.riotapi.StaticDataAPI;
import com.merakianalytics.kernel.riotapi.StatusAPI;
import com.merakianalytics.kernel.riotapi.SummonerAPI;
import com.merakianalytics.kernel.riotapi.ThirdPartyCodeAPI;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

/**
 * The jax-rs application definition. This is the "main" of the kernel application.
 */
@ApplicationPath("/")
public class WebServices extends Application {   
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> services = new HashSet<>();

        // Utilities
        services.add(OriannaExceptionMapper.class);
        services.add(QueryValidationExceptionMapper.class);
        services.add(CORSFilter.class);
        services.add(JSONProvider.class);
        services.add(MessagePackProvider.class);

        // Riot API
        services.add(ChampionMasteryAPI.class);
        services.add(ChampionAPI.class);
        services.add(LeagueAPI.class);
        services.add(StaticDataAPI.class);
        services.add(StatusAPI.class);
        services.add(MatchAPI.class);
        services.add(SpectatorAPI.class);
        services.add(SummonerAPI.class);
        services.add(ThirdPartyCodeAPI.class);
        
        // Swagger Documentation
        services.add(ApiListingResource.class);
        services.add(SwaggerSerializers.class);

        return services;
    }
}
