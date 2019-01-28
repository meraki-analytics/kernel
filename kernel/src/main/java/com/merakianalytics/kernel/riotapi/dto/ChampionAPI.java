package com.merakianalytics.kernel.riotapi.dto;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import org.jboss.resteasy.annotations.GZIP;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.kernel.riotapi.RiotAPIService;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.champion.ChampionInfo;

import io.swagger.annotations.Api;

/**
 * The Champion API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#champion-v3
 */
@Path("/platform/v3")
@Api("Champion API")
@GZIP
public class ChampionAPI extends RiotAPIService {
    /**
     * /lol/platform/v3/champion-rotations
     *
     * @see https://developer.riotgames.com/api-methods/#champion-v3/GET_getChampionInfo
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @return {@link com.merakianalytics.orianna.types.dto.champion.ChampionRotation}
     */
    @Path("/champion-rotations")
    @GET
    public ChampionInfo getChampionInfo(@QueryParam("platform") final String platformTag) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(ChampionInfo.class, query);
    }
}
