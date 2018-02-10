package com.merakianalytics.kernel.riotapi.data;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.kernel.riotapi.RiotAPIService;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.data.status.ShardStatus;

import io.swagger.annotations.Api;

/**
 * The Status API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#spectator-v3
 */
@Path("/status/v3")
@Api("Status API")
public class StatusAPI extends RiotAPIService {
    /**
     * /lol/status/v3/shard-data
     *
     * @see https://developer.riotgames.com/api-methods/#lol-status-v3/GET_getShardData
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @return {@link com.merakianalytics.orianna.types.data.status.ShardStatus}
     */
    @Path("/shard-data")
    @GET
    public ShardStatus getShardData(@QueryParam("platform") final String platformTag) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(ShardStatus.class, query);
    }
}
