package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.summoner.Summoner;

import io.swagger.annotations.Api;

/**
 * The Summoner API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#summoner-v3
 */
@Path("/summoner/v3")
@Api("Summoner API")
public class SummonerAPI extends RiotAPIService {
    /**
     * /lol/summoner/v3/summoners/by-account/{accountId}
     *
     * @see https://developer.riotgames.com/api-methods/#summoner-v3/GET_getByAccountId
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param accountId
     *        the account's id
     * @return {@link com.merakianalytics.orianna.types.dto.summoner.Summoner}
     */
    @Path("/summoners/by-account/{accountId}")
    @GET
    public Summoner getByAccountId(@QueryParam("platform") final String platformTag, @PathParam("accountId") final long accountId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("accountId", accountId)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }

    /**
     * /lol/summoner/v3/summoners/{summonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#summoner-v3/GET_getBySummonerId
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param summonerId
     *        the summoner's id
     * @return {@link com.merakianalytics.orianna.types.dto.summoner.Summoner}
     */
    @Path("/summoners/{summonerId}")
    @GET
    public Summoner getBySummonerId(@QueryParam("platform") final String platformTag, @PathParam("summonerId") final long summonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("id", summonerId)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }

    /**
     * /lol/summoner/v3/summoners/by-name/{summonerName}
     *
     * @see https://developer.riotgames.com/api-methods/#summoner-v3/GET_getBySummonerName
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param summonerName
     *        the summoner's name
     * @return {@link com.merakianalytics.orianna.types.dto.summoner.Summoner}
     */
    @Path("/summoners/by-name/{summonerName}")
    @GET
    public Summoner getBySummonerName(@QueryParam("platform") final String platformTag, @PathParam("summonerName") final String summonerName) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("name", summonerName)
            .build();

        return context.getPipeline().get(Summoner.class, query);
    }
}
