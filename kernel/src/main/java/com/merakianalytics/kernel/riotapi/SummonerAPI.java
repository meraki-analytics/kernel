package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.summoner.Summoner;

@Path("/summoner/v3")
public class SummonerAPI extends RiotAPIService {
    @Path("/summoners/by-account/{accountId}")
    @GET
    public Summoner byAccount(@QueryParam("platform") Platform platform, @PathParam("accountId") final long accountId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("accountId", accountId)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }

    @Path("/summoners/{summonerId}")
    @GET
    public Summoner byId(@QueryParam("platform") Platform platform, @PathParam("summonerId") final long summonerId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("id", summonerId)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }

    @Path("/summoners/by-name/{summonerName}")
    @GET
    public Summoner byName(@QueryParam("platform") Platform platform, @PathParam("summonerName") final String summonerName) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("name", summonerName)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }
}
