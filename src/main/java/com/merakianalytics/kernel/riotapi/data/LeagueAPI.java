package com.merakianalytics.kernel.riotapi.data;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import org.jboss.resteasy.annotations.GZIP;

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
 * @see https://developer.riotgames.com/api-methods/#league-v4
 */
@Path("/league/v4")
@Api("League API")
@GZIP
public class LeagueAPI extends RiotAPIService {
    /**
     * /lol/league/v4/challengerleagues/by-queue/{queue}
     *
     * @see https://developer.riotgames.com/api-methods/#league-v4/GET_getChallengerLeague
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
     * /lol/league/v4/grandmasterleagues/by-queue/{queue}
     *
     * @see https://developer.riotgames.com/api-methods/#league-v4/GET_getGrandmasterLeague
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param queue
     *        the {@link com.merakianalytics.orianna.types.common.Queue}
     * @return {@link com.merakianalytics.orianna.types.data.league.League}
     */
    @Path("/grandmasterleagues/by-queue/{queue}")
    @GET
    public League getGrandmasterLeague(@QueryParam("platform") final String platformTag, @PathParam("queue") final Queue queue) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("queue", queue)
            .put("tier", Tier.GRANDMASTER)
            .build();

        return context.getPipeline().get(League.class, query);
    }

    /**
     * /lol/league/v4/leagues/{leagueId}
     *
     * @see https://developer.riotgames.com/api-methods/#league-v4/GET_getLeagueById
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
     * /lol/league/v4/entries/by-summoner/{encryptedSummonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#league-v4/GET_getLeagueEntries
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedSummonerId
     *        the summoner's encrypted id
     * @return {@link com.merakianalytics.orianna.types.data.league.LeaguePositions}
     */
    @Path("/entries/by-summoner/{encryptedSummonerId}")
    @GET
    public LeaguePositions getLeagueEntries(@QueryParam("platform") final String platformTag,
        @PathParam("encryptedSummonerId") final String encryptedSummonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", encryptedSummonerId)
            .build();

        return context.getPipeline().get(LeaguePositions.class, query);
    }

    /**
     * /lol/league/v4/masterleagues/by-queue/{queue}
     *
     * @see https://developer.riotgames.com/api-methods/#league-v4/GET_getMasterLeague
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
