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
import com.merakianalytics.orianna.types.data.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.data.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.data.championmastery.ChampionMasteryScore;

import io.swagger.annotations.Api;

/**
 * The Champion Mastery API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#champion-mastery-v4
 */
@Path("/champion-mastery/v4")
@Api("Champion Mastery API")
@GZIP
public class ChampionMasteryAPI extends RiotAPIService {
    /**
     * /lol/champion-mastery/v4/champion-masteries/by-summoner/{encryptedSummonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#champion-mastery-v4/GET_getAllChampionMasteries
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedSummonerId
     *        the summoner's encrypted id
     * @return {@link com.merakianalytics.orianna.types.data.championmastery.ChampionMasteries}
     */
    @Path("/champion-masteries/by-summoner/{encryptedSummonerId}")
    @GET
    public ChampionMasteries getAllChampionMasteries(@QueryParam("platform") final String platformTag, @PathParam("encryptedSummonerId") final long encryptedSummonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("encryptedSummonerId", encryptedSummonerId)
            .build();

        return context.getPipeline().get(ChampionMasteries.class, query);
    }

    /**
     * /lol/champion-mastery/v4/champion-masteries/by-summoner/{encryptedSummonerId}/by-champion/{championId}
     *
     * @see https://developer.riotgames.com/api-methods/#champion-mastery-v4/GET_getChampionMastery
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedSummonerId
     *        the summoner's encrypted id
     * @param championId
     *        the champion's id
     * @return {@link com.merakianalytics.orianna.types.data.championmastery.ChampionMastery}
     */
    @Path("/champion-masteries/by-summoner/{encryptedSummonerId}/by-champion/{championId}")
    @GET
    public ChampionMastery getChampionMastery(@QueryParam("platform") final String platformTag, @PathParam("encryptedSummonerId") final long encryptedSummonerId,
        @PathParam("championId") final int championId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("encryptedSummonerId", encryptedSummonerId)
            .put("championId", championId)
            .build();

        return context.getPipeline().get(ChampionMastery.class, query);
    }

    /**
     * /lol/champion-mastery/v4/scores/by-summoner/{encryptedSummonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#champion-mastery-v4/GET_getChampionMasteryScore
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedSummonerId
     *        the summoner's encrypted id
     * @return {@link com.merakianalytics.orianna.types.data.championmastery.ChampionMasteryScore}
     */
    @Path("/scores/by-summoner/{encryptedSummonerId}")
    @GET
    public ChampionMasteryScore getChampionMasteryScore(@QueryParam("platform") final String platformTag, @PathParam("encryptedSummonerId") final long encryptedSummonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("encryptedSummonerId", encryptedSummonerId)
            .build();

        return context.getPipeline().get(ChampionMasteryScore.class, query);
    }
}
