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
import com.merakianalytics.orianna.types.data.summoner.Summoner;

import io.swagger.annotations.Api;

/**
 * The Summoner API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#summoner-v4
 */
@Path("/summoner/v4")
@Api("Summoner API")
@GZIP
public class SummonerAPI extends RiotAPIService {
    /**
     * /lol/summoner/v4/summoners/by-account/{encryptedAccountId}
     *
     * @see https://developer.riotgames.com/api-methods/#summoner-v4/GET_getByAccountId
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedAccountId
     *        the account's encrypted id
     * @return {@link com.merakianalytics.orianna.types.data.summoner.Summoner}
     */
    @Path("/summoners/by-account/{encryptedAccountId}")
    @GET
    public Summoner getByAccountId(@QueryParam("platform") final String platformTag, @PathParam("encryptedAccountId") final long encryptedAccountId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("accountId", encryptedAccountId)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }

    /**
     * /lol/summoner/v4/summoners/{encryptedPUUID}
     *
     * @see https://developer.riotgames.com/api-methods/#summoner-v4/GET_getByPUUID
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedPUUID
     *        the person's encrypted id
     * @return {@link com.merakianalytics.orianna.types.data.summoner.Summoner}
     */
    @Path("/summoners/{encryptedPUUID}")
    @GET
    public Summoner getByPUUID(@QueryParam("platform") final String platformTag, @PathParam("encryptedPUUID") final long encryptedPUUID) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("puuid", encryptedPUUID)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }

    /**
     * /lol/summoner/v4/summoners/{encryptedSummonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#summoner-v4/GET_getBySummonerId
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedSummonerId
     *        the summoner's encrypted id
     * @return {@link com.merakianalytics.orianna.types.data.summoner.Summoner}
     */
    @Path("/summoners/{encryptedSummonerId}")
    @GET
    public Summoner getBySummonerId(@QueryParam("platform") final String platformTag, @PathParam("encryptedSummonerId") final long encryptedSummonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", encryptedSummonerId)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }

    /**
     * /lol/summoner/v4/summoners/by-name/{summonerName}
     *
     * @see https://developer.riotgames.com/api-methods/#summoner-v4/GET_getBySummonerName
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param summonerName
     *        the summoner's name
     * @return {@link com.merakianalytics.orianna.types.data.summoner.Summoner}
     */
    @Path("/summoners/by-name/{summonerName}")
    @GET
    public Summoner getBySummonerName(@QueryParam("platform") final String platformTag, @PathParam("summonerName") final String summonerName) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("name", summonerName)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }
}
