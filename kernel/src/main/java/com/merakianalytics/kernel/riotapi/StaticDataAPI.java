package com.merakianalytics.kernel.riotapi;

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.staticdata.Champion;
import com.merakianalytics.orianna.types.dto.staticdata.ChampionList;
import com.merakianalytics.orianna.types.dto.staticdata.Item;
import com.merakianalytics.orianna.types.dto.staticdata.ItemList;
import com.merakianalytics.orianna.types.dto.staticdata.LanguageStrings;
import com.merakianalytics.orianna.types.dto.staticdata.Languages;
import com.merakianalytics.orianna.types.dto.staticdata.MapData;
import com.merakianalytics.orianna.types.dto.staticdata.Mastery;
import com.merakianalytics.orianna.types.dto.staticdata.MasteryList;
import com.merakianalytics.orianna.types.dto.staticdata.ProfileIconData;
import com.merakianalytics.orianna.types.dto.staticdata.Realm;
import com.merakianalytics.orianna.types.dto.staticdata.Rune;
import com.merakianalytics.orianna.types.dto.staticdata.RuneList;
import com.merakianalytics.orianna.types.dto.staticdata.SummonerSpell;
import com.merakianalytics.orianna.types.dto.staticdata.SummonerSpellList;
import com.merakianalytics.orianna.types.dto.staticdata.Versions;

import io.swagger.annotations.Api;

/**
 * The Static Data API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3
 */
@Path("/static-data/v3")
@Api("Static Data API")
public class StaticDataAPI extends RiotAPIService {
    /**
     * /lol/static-data/v3/champions/{id}
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getChampionById
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param id
     *        the champion's id
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.Champion}
     */
    @Path("/champions/{id}")
    @GET
    public Champion getChampionList(@QueryParam("platform") final String platformTag, @PathParam("id") final int id, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("id", id);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(Champion.class, builder.build());
    }

    /**
     * /lol/static-data/v3/champions
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getChampionList
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @param dataById
     *        whether the returned mapping is by id or key
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.ChampionList}
     */
    @Path("/champions")
    @GET
    public ChampionList getChampionList(@QueryParam("platform") final String platformTag, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags,
        @QueryParam("dataById") @DefaultValue("false") final boolean dataById) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("dataById", dataById);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(ChampionList.class, builder.build());
    }

    /**
     * /lol/static-data/v3/items/{id}
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getItemById
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param id
     *        the item id
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.Item}
     */
    @Path("/items/{id}")
    @GET
    public Item getItemById(@QueryParam("platform") final String platformTag, @PathParam("id") final int id, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("id", id);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(Item.class, builder.build());
    }

    /**
     * /lol/static-data/v3/items
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getItemList
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.ItemList}
     */
    @Path("/items")
    @GET
    public ItemList getItemList(@QueryParam("platform") final String platformTag, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version,
        @QueryParam("tags") final Set<String> tags) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(ItemList.class, builder.build());
    }

    /**
     * /lol/static-data/v3/languages
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getLanguages
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.Languages}
     */
    @Path("/languages")
    @GET
    public Languages getLanguages(@QueryParam("platform") final String platformTag) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(Languages.class, query);
    }

    /**
     * /lol/static-data/v3/language-strings
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getLanguageStrings
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.LanguageStrings}
     */
    @Path("/language-strings")
    @GET
    public LanguageStrings getLanguageStrings(@QueryParam("platform") final String platformTag, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        return context.getPipeline().get(LanguageStrings.class, builder.build());
    }

    /**
     * /lol/static-data/v3/maps
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getMapData
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.MapData}
     */
    @Path("/maps")
    @GET
    public MapData getMapData(@QueryParam("platform") final String platformTag, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        return context.getPipeline().get(MapData.class, builder.build());
    }

    /**
     * /lol/static-data/v3/masteries/{id}
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getMasteryById
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param id
     *        the item id
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.Mastery}
     */
    @Path("/masteries/{id}")
    @GET
    public Mastery getMasteryById(@QueryParam("platform") final String platformTag, @PathParam("id") final int id, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("id", id);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(Mastery.class, builder.build());
    }

    /**
     * /lol/static-data/v3/masteries
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getMasteryList
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.MasteryList}
     */
    @Path("/masteries")
    @GET
    public MasteryList getMasteryList(@QueryParam("platform") final String platformTag, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(MasteryList.class, builder.build());
    }

    /**
     * /lol/static-data/v3/profile-icons
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getProfileIcons
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.ProfileIconData}
     */
    @Path("/profile-icons")
    @GET
    public ProfileIconData getProfileIcons(@QueryParam("platform") final String platformTag, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        return context.getPipeline().get(ProfileIconData.class, builder.build());
    }

    /**
     * /lol/static-data/v3/realms
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getRealm
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.Realm}
     */
    @Path("/realms")
    @GET
    public Realm getRealm(@QueryParam("platform") final String platformTag) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(Realm.class, query);
    }

    /**
     * /lol/static-data/v3/runes/{id}
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getRuneById
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param id
     *        the item id
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.Rune}
     */
    @Path("/runes/{id}")
    @GET
    public Rune getRuneById(@QueryParam("platform") final String platformTag, @PathParam("id") final int id, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("id", id);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(Rune.class, builder.build());
    }

    /**
     * /lol/static-data/v3/runes
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getRuneList
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.RuneList}
     */
    @Path("/runes")
    @GET
    public RuneList getRuneList(@QueryParam("platform") final String platformTag, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version,
        @QueryParam("tags") final Set<String> tags) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(RuneList.class, builder.build());
    }

    /**
     * /lol/static-data/v3/summoner-spells/{id}
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getSummonerSpellById
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param id
     *        the item id
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.SummonerSpell}
     */
    @Path("/summoner-spells/{id}")
    @GET
    public SummonerSpell getSummonerSpellById(@QueryParam("platform") final String platformTag, @PathParam("id") final int id,
        @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("id", id);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(SummonerSpell.class, builder.build());
    }

    /**
     * /lol/static-data/v3/summoner-spells
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getSummonerSpellList
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param locale
     *        the locale
     * @param version
     *        the version
     * @param tags
     *        what data to include
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.SummonerSpellList}
     */
    @Path("/summoner-spells")
    @GET
    public SummonerSpellList getSummonerSpellList(@QueryParam("platform") final String platformTag, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags,
        @QueryParam("dataById") @DefaultValue("false") final boolean dataById) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("dataById", dataById);

        if(locale != null) {
            builder.put("locale", locale);
        }

        if(version != null) {
            builder.put("version", version);
        }

        if(tags != null) {
            builder.put("includedData", tags);
        }

        return context.getPipeline().get(SummonerSpellList.class, builder.build());
    }

    /**
     * /lol/static-data/v3/versions
     *
     * @see https://developer.riotgames.com/api-methods/#lol-static-data-v3/GET_getVersions
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @return {@link com.merakianalytics.orianna.types.dto.staticdata.Versions}
     */
    @Path("/versions")
    @GET
    public Versions getVersions(@QueryParam("platform") final String platformTag) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(Versions.class, query);
    }
}
