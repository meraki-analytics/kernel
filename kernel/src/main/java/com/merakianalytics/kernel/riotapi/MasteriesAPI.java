package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.masteries.MasteryPages;

@Path("/platform/v3")
public class MasteriesAPI extends RiotAPIService {
    @Path("/masteries/by-summoner/{summonerId}")
    @GET
    public MasteryPages bySummoner(@QueryParam("platform") Platform platform, @PathParam("summonerId") final long summonerId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("summonerId", summonerId)
            .build();

        return context.getPipeline().get(MasteryPages.class, query);
    }
}
