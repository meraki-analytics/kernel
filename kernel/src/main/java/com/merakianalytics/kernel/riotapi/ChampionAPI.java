package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.champion.Champion;
import com.merakianalytics.orianna.types.dto.champion.ChampionList;

@Path("/platform/v3")
public class ChampionAPI extends RiotAPIService {
    @Path("/champions")
    @GET
    public ChampionList all(@QueryParam("platform") Platform platform, @QueryParam("freeToPlay") @DefaultValue("false") final boolean freeToPlay) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("freeToPlay", freeToPlay)
            .build();

        return context.getPipeline().get(ChampionList.class, query);
    }

    @Path("/champions/{id}")
    @GET
    public Champion byId(@QueryParam("platform") Platform platform, @PathParam("id") final int id) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("id", id)
            .build();

        return context.getPipeline().get(Champion.class, query);
    }
}
