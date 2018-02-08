package com.merakianalytics.kernel.riotapi;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.dto.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.dto.championmastery.ChampionMasteryScore;

import io.swagger.annotations.Api;

/**
 * The Champion Mastery API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#champion-mastery-v3
 */
@Path("/champion-mastery/v3")
@Api("Champion Mastery API")
public class ChampionMasteryAPI extends RiotAPIService {
    /**
     * /lol/champion-mastery/v3/champion-masteries/by-summoner/{summonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#champion-mastery-v3/GET_getAllChampionMasteries
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param summonerId
     *        the summoner's id
     * @return {@link com.merakianalytics.orianna.types.dto.championmastery.ChampionMasteries}
     */
    @Path("/champion-masteries/by-summoner/{summonerId}")
    @GET
    public ChampionMasteries getAllChampionMasteries(@QueryParam("platform") final String platformTag, @PathParam("summonerId") final long summonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(ChampionMasteries.class, query);
    }

    /**
     * /lol/champion-mastery/v3/champion-masteries/by-summoner/{summonerId}/by-champion/{championId}
     *
     * @see https://developer.riotgames.com/api-methods/#champion-mastery-v3/GET_getChampionMastery
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param summonerId
     *        the summoner's id
     * @param championId
     *        the champion's id
     * @return {@link com.merakianalytics.orianna.types.dto.championmastery.ChampionMastery}
     */
    @Path("/champion-masteries/by-summoner/{summonerId}/by-champion/{championId}")
    @GET
    public ChampionMastery getChampionMastery(@QueryParam("platform") final String platformTag, @PathParam("summonerId") final long summonerId,
        @PathParam("championId") final int championId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .put("championId", championId)
            .build();

        return context.getPipeline().get(ChampionMastery.class, query);
    }

    /**
     * /lol/champion-mastery/v3/scores/by-summoner/{summonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#champion-mastery-v3/GET_getChampionMasteryScore
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param summonerId
     *        the summoner's id
     * @return {@link com.merakianalytics.orianna.types.dto.championmastery.ChampionMasteryScore}
     */
    @Path("/scores/by-summoner/{summonerId}")
    @GET
    public ChampionMasteryScore getChampionMasteryScore(@QueryParam("platform") final String platformTag, @PathParam("summonerId") final long summonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(ChampionMasteryScore.class, query);
    }
}
