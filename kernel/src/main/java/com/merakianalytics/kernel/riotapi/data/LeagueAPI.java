package com.merakianalytics.kernel.riotapi.data;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.kernel.riotapi.RiotAPIService;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Tier;
import com.merakianalytics.orianna.types.data.league.League;
import com.merakianalytics.orianna.types.data.league.LeaguePositions;

import io.swagger.annotations.Api;

/**
 * The League API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#league-v3
 */
@Path("/league/v3")
@Api("League API")
public class LeagueAPI extends RiotAPIService {
    /**
     * /lol/league/v3/positions/by-summoner/{summonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#league-v3/GET_getAllLeaguePositionsForSummoner
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param summonerId
     *        the summoner's id
     * @return {@link com.merakianalytics.orianna.types.data.league.LeaguePositions}
     */
    @Path("/positions/by-summoner/{summonerId}")
    @GET
    public LeaguePositions getAllLeaguePositionsForSummoner(@QueryParam("platform") final String platformTag,
        @PathParam("summonerId") final long summonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(LeaguePositions.class, query);
    }

    /**
     * /lol/league/v3/challengerleagues/by-queue/{queue}
     *
     * @see https://developer.riotgames.com/api-methods/#league-v3/GET_getChallengerLeague
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param queue
     *        the {@link com.merakianalytics.orianna.types.common.Queue}
     * @return {@link com.merakianalytics.orianna.types.data.league.League}
     */
    @Path("/challengerleagues/by-queue/{queue}")
    @GET
    public League getChallengerLeague(@QueryParam("platform") final String platformTag, @PathParam("queue") final Queue queue) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("queue", queue)
            .put("tier", Tier.CHALLENGER)
            .build();

        return context.getPipeline().get(League.class, query);
    }

    /**
     * /lol/league/v3/leagues/{leagueId}
     *
     * @see https://developer.riotgames.com/api-methods/#league-v3/GET_getLeagueById
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param leagueId
     *        the league's id
     * @return {@link com.merakianalytics.orianna.types.data.league.League}
     */
    @Path("/leagues/{leagueId}")
    @GET
    public League getLeagueById(@QueryParam("platform") final String platformTag, @PathParam("leagueId") final String leagueId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("leagueId", leagueId)
            .build();

        return context.getPipeline().get(League.class, query);
    }

    /**
     * /lol/league/v3/masterleagues/by-queue/{queue}
     *
     * @see https://developer.riotgames.com/api-methods/#league-v3/GET_getMasterLeague
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param queue
     *        the {@link com.merakianalytics.orianna.types.common.Queue}
     * @return {@link com.merakianalytics.orianna.types.data.league.League}
     */
    @Path("/masterleagues/by-queue/{queue}")
    @GET
    public League getMasterLeague(@QueryParam("platform") final String platformTag, @PathParam("queue") final Queue queue) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("queue", queue)
            .put("tier", Tier.MASTER)
            .build();

        return context.getPipeline().get(League.class, query);
    }
}
