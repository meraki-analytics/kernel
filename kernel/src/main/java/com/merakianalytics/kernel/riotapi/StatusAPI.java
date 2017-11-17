package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.status.ShardStatus;

@Path("/status/v3")
public class StatusAPI extends RiotAPIService {
    @Path("/shard-data")
    @GET
    public ShardStatus status(@QueryParam("platform") Platform platform) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(ShardStatus.class, query);
    }
}
