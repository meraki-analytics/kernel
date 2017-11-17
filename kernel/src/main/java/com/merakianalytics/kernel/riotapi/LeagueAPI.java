package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Tier;
import com.merakianalytics.orianna.types.dto.league.SummonerPositions;
import com.merakianalytics.orianna.types.dto.league.LeagueList;
import com.merakianalytics.orianna.types.dto.league.SummonerLeagues;

@Path("/league/v3")
public class LeagueAPI extends RiotAPIService {
    @Path("/leagues/by-summoner/{summonerId}")
    @GET
    public SummonerLeagues bySummoner(@QueryParam("platform") Platform platform, @PathParam("summonerId") final long summonerId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(SummonerLeagues.class, query);
    }

    @Path("/challengerleagues/by-queue/{queue}")
    @GET
    public LeagueList challenger(@QueryParam("platform") Platform platform, @PathParam("queue") final Queue queue) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        if(!Queue.RANKED.contains(queue)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("queue", queue)
            .put("tier", Tier.CHALLENGER)
            .build();

        return context.getPipeline().get(LeagueList.class, query);
    }

    @Path("/leagues/{leagueId}")
    @GET
    public LeagueList league(@QueryParam("platform") Platform platform, @PathParam("leagueId") final String leagueId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("leagueId", leagueId)
            .build();

        return context.getPipeline().get(LeagueList.class, query);
    }

    @Path("/masterleagues/by-queue/{queue}")
    @GET
    public LeagueList master(@QueryParam("platform") Platform platform, @PathParam("queue") final Queue queue) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        if(!Queue.RANKED.contains(queue)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("queue", queue)
            .put("tier", Tier.MASTER)
            .build();

        return context.getPipeline().get(LeagueList.class, query);
    }

    @Path("/positions/by-summoner/{summonerId}")
    @GET
    public SummonerPositions positionsBySummoner(@QueryParam("platform") Platform platform, @PathParam("summonerId") final long summonerId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(SummonerPositions.class, query);
    }
}
