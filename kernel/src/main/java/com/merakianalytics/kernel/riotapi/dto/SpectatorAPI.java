package com.merakianalytics.kernel.riotapi.dto;

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
import com.merakianalytics.orianna.types.dto.spectator.CurrentGameInfo;
import com.merakianalytics.orianna.types.dto.spectator.FeaturedGames;

import io.swagger.annotations.Api;

/**
 * The Spectator API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#spectator-v4
 */
@Path("/spectator/v4")
@Api("Spectator API")
@GZIP
public class SpectatorAPI extends RiotAPIService {
    /**
     * /lol/spectator/v4/active-games/by-summoner/{encryptedSummonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#spectator-v4/GET_getCurrentGameInfoBySummoner
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedSummonerId
     *        the summoner's encrypted id
     * @return {@link com.merakianalytics.orianna.types.dto.spectator.CurrentGameInfo}
     */
    @Path("/active-games/by-summoner/{encryptedSummonerId}")
    @GET
    public CurrentGameInfo getCurrentGameInfoBySummoner(@QueryParam("platform") final String platformTag,
        @PathParam("encryptedSummonerId") final long encryptedSummonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", encryptedSummonerId)
            .build();

        return context.getPipeline().get(CurrentGameInfo.class, query);
    }

    /**
     * /lol/spectator/v4/featured-games
     *
     * @see https://developer.riotgames.com/api-methods/#spectator-v4/GET_getFeaturedGames
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
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(FeaturedGames.class, query);
    }
}
