package com.merakianalytics.kernel.riotapi;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.data.match.MatchList;
import com.merakianalytics.orianna.types.dto.match.Match;
import com.merakianalytics.orianna.types.dto.match.MatchTimeline;
import com.merakianalytics.orianna.types.dto.match.TournamentMatches;

@Path("/match/v3")
public class MatchAPI extends RiotAPIService {
    @Path("/matches/{matchId}/by-tournament-code/{tournamentCode}")
    @GET
    public Match byTournamentCode(@QueryParam("platform") Platform platform, @PathParam("matchId") final String matchId,
        @PathParam("tournamentCode") final String tournamentCode) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("matchId", matchId)
            .put("tournamentCode", tournamentCode)
            .build();

        return context.getPipeline().get(Match.class, query);
    }

    @Path("/matches/{matchId}")
    @GET
    public Match match(@QueryParam("platform") Platform platform, @PathParam("matchId") final String matchId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("matchId", matchId)
            .build();

        return context.getPipeline().get(Match.class, query);
    }

    @Path("/matchlists/by-account/{accountId}")
    @GET
    public MatchList matchlistByAccount(@QueryParam("platform") Platform platform, @PathParam("accountId") final long accountId,
        @QueryParam("queue") final Set<Integer> queue, @QueryParam("endTime") @DefaultValue("-1") final long endTime,
        @QueryParam("beginIndex") @DefaultValue("-1") final int beginIndex, @QueryParam("beginTime") @DefaultValue("-1") final long beginTime,
        @QueryParam("season") final Set<Integer> season, @QueryParam("champion") final Set<Integer> champion,
        @QueryParam("endIndex") @DefaultValue("-1") final int endIndex) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("accountId", accountId);

        if(queue != null) {
            builder.put("queue", queue);
        }

        if(endTime != -1L) {
            builder.put("endTime", endTime);
        }

        if(beginIndex != -1) {
            builder.put("beginIndex", beginIndex);
        }

        if(beginTime != -1L) {
            builder.put("beginTime", beginTime);
        }

        if(season != null) {
            builder.put("season", season);
        }

        if(champion != null) {
            builder.put("champion", champion);
        }

        if(endIndex != -1) {
            builder.put("endIndex", endIndex);
        }

        return context.getPipeline().get(MatchList.class, builder.build());
    }

    @Path("/matchlists/by-account/{accountId}/recent")
    @GET
    public MatchList recent(@QueryParam("platform") Platform platform, @PathParam("accountId") final long accountId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("accountId", accountId)
            .put("recent", true)
            .build();

        return context.getPipeline().get(MatchList.class, query);
    }

    @Path("/timelines/by-match/{matchId}")
    @GET
    public MatchTimeline timeline(@QueryParam("platform") Platform platform, @PathParam("matchId") final long matchId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("matchId", matchId)
            .build();

        return context.getPipeline().get(MatchTimeline.class, query);
    }

    @Path("/matches/by-tournament-code/{tournamentCode}/ids")
    @GET
    public TournamentMatches tournament(@QueryParam("platform") Platform platform, @PathParam("tournamentCode") final String tournamentCode) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("tournamentCode", tournamentCode)
            .build();

        return context.getPipeline().get(TournamentMatches.class, query);
    }
}
