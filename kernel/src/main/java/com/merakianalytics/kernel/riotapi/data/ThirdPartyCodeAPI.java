package com.merakianalytics.kernel.riotapi.data;

import java.net.HttpURLConnection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import org.jboss.resteasy.annotations.GZIP;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.kernel.riotapi.RiotAPIService;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.data.thirdpartycode.VerificationString;

import io.swagger.annotations.Api;

/**
 * The Third Party Code API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#third-party-code-v4
 */
@Path("/platform/v4")
@Api("Third Party Code API")
@GZIP
public class ThirdPartyCodeAPI extends RiotAPIService {
    /**
     * /lol/platform/v4/third-party-code/by-summoner/{encryptedSummonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#third-party-code-v4/GET_getThirdPartyCodeBySummonerId
     *
     * @param platform
     *        platform the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedSummonerId
     *        the summoner's encrypted id
     * @return {@link com.merakianalytics.orianna.types.data.thirdpartycode.VerificationString}
     */
    @Path("/third-party-code/by-summoner/{encryptedSummonerId}")
    @GET
    public VerificationString verificationString(@QueryParam("platform") final String platformTag, @PathParam("encryptedSummonerId") final long encryptedSummonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("encryptedSummonerId", encryptedSummonerId);

        return context.getPipeline().get(VerificationString.class, builder.build());
    }
}
