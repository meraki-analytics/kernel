package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.dto.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.dto.championmastery.ChampionMasteryScore;

@Path("/champion-mastery/v3")
public class ChampionMasteryAPI extends RiotAPIService {
    @Path("/champion-masteries/by-summoner/{summonerId}")
    @GET
    public ChampionMasteries bySummoner(@QueryParam("platform") Platform platform, @PathParam("summonerId") final long summonerId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(ChampionMasteries.class, query);
    }

    @Path("/champion-masteries/by-summoner/{summonerId}/by-champion/{championId}")
    @GET
    public ChampionMastery bySummonerByChampion(@QueryParam("platform") Platform platform, @PathParam("summonerId") final long summonerId,
        @PathParam("championId") final int championId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .put("championId", championId)
            .build();

        return context.getPipeline().get(ChampionMastery.class, query);
    }

    @Path("/scores/by-summoner/{summonerId}")
    @GET
    public ChampionMasteryScore score(@QueryParam("platform") Platform platform, @PathParam("summonerId") final long summonerId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(ChampionMasteryScore.class, query);
    }
}
