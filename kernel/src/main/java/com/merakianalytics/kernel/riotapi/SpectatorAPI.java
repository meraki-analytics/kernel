package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.spectator.CurrentGameInfo;
import com.merakianalytics.orianna.types.dto.spectator.FeaturedGames;

@Path("/spectator/v3")
public class SpectatorAPI extends RiotAPIService {
    @Path("/active-games/by-summoner/{summonerId}")
    public CurrentGameInfo current(@QueryParam("platform") Platform platform, @PathParam("summonerId") final long summonerId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(CurrentGameInfo.class, query);
    }

    @Path("/featured-games")
    public FeaturedGames featured(@QueryParam("platform") Platform platform) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(FeaturedGames.class, query);
    }
}
