package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.spectator.CurrentGameInfo;
import com.merakianalytics.orianna.types.dto.spectator.FeaturedGames;

import io.swagger.annotations.Api;

/**
 * The Spectator API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#spectator-v3
 */
@Path("/spectator/v3")
@Api("Spectator API")
public class SpectatorAPI extends RiotAPIService {
    /**
     * /lol/spectator/v3/active-games/by-summoner/{summonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#spectator-v3/GET_getCurrentGameInfoBySummoner
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param summonerId
     *        the summoner's id
     * @return {@link com.merakianalytics.orianna.types.dto.spectator.CurrentGameInfo}
     */
    @Path("/active-games/by-summoner/{summonerId}")
    @GET
    public CurrentGameInfo getCurrentGameInfoBySummoner(@QueryParam("platform") final String platformTag, @PathParam("summonerId") final long summonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(CurrentGameInfo.class, query);
    }

    /**
     * /lol/spectator/v3/featured-games
     *
     * @see https://developer.riotgames.com/api-methods/#spectator-v3/GET_getFeaturedGames
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @return {@link com.merakianalytics.orianna.types.dto.spectator.FeaturedGames}
     */
    @Path("/featured-games")
    @GET
    public FeaturedGames getFeaturedGames(@QueryParam("platform") final String platformTag) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(FeaturedGames.class, query);
    }
}
