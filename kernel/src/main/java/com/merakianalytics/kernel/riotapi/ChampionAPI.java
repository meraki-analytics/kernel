package com.merakianalytics.kernel.riotapi;

import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.champion.Champion;
import com.merakianalytics.orianna.types.dto.champion.ChampionList;

/**
 * The Champion Status API proxy for the Riot API
 * 
 * @see https://developer.riotgames.com/api-methods/#champion-v3
 */
@Path("/platform/v3")
public class ChampionAPI extends RiotAPIService {
    /**
     * /lol/platform/v3/champions
     *
     * @see https://developer.riotgames.com/api-methods/#champion-v3/GET_getChampions
     *
     * @param platform
     *        the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param freeToPlay
     *        whether to only get free to play champions (default: false)
     * @return {@link com.merakianalytics.orianna.types.dto.champion.ChampionList}
     */
    @Path("/champions")
    @GET
    public ChampionList getChampions(@QueryParam("platform") Platform platform, @QueryParam("freeToPlay") @DefaultValue("false") final boolean freeToPlay) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("freeToPlay", freeToPlay)
            .build();

        return context.getPipeline().get(ChampionList.class, query);
    }

    /**
     * /lol/platform/v3/champions/{id}
     *
     * @see https://developer.riotgames.com/api-methods/#champion-v3/GET_getChampionsById
     *
     * @param platform
     *        the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param id
     *        the champion's id
     * @return {@link com.merakianalytics.orianna.types.dto.champion.Champion}
     */
    @Path("/champions/{id}")
    @GET
    public Champion getChampionsById(@QueryParam("platform") Platform platform, @PathParam("id") final int id) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("id", id)
            .build();

        return context.getPipeline().get(Champion.class, query);
    }
}
