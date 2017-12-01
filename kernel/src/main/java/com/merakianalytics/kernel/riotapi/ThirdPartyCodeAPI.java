package com.merakianalytics.kernel.riotapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.thirdpartycode.VerificationString;

@Path("/platform/v3")
public class ThirdPartyCodeAPI extends RiotAPIService {
    @Path("/third-party-code/by-summoner/{summonerId}")
    @GET
    public VerificationString verificationString(@QueryParam("platform") Platform platform, @PathParam("summonerId") final long summonerId) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("summonerId", summonerId);

        return context.getPipeline().get(VerificationString.class, builder.build());
    }
}
