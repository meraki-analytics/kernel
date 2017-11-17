package com.merakianalytics.kernel.riotapi;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

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

@Path("/static-data/v3")
public class StaticDataAPI extends RiotAPIService {
    @Path("/champions/{id}")
    @GET
    public Champion champion(@QueryParam("platform") Platform platform, @PathParam("id") final int id, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/champions")
    @GET
    public ChampionList champions(@QueryParam("platform") Platform platform, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version,
        @QueryParam("tags") final Set<String> tags, @QueryParam("dataById") @DefaultValue("false") final boolean dataById) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/items/{id}")
    @GET
    public Item item(@QueryParam("platform") Platform platform, @PathParam("id") final int id, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/items")
    @GET
    public ItemList items(@QueryParam("platform") Platform platform, @QueryParam("locale") final String locale, @QueryParam("version") final String version,
        @QueryParam("tags") final Set<String> tags) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/languages")
    @GET
    public Languages languageStrings(@QueryParam("platform") Platform platform) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(Languages.class, query);
    }

    @Path("/language-strings")
    @GET
    public LanguageStrings languageStrings(@QueryParam("platform") Platform platform, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/maps")
    @GET
    public MapData maps(@QueryParam("platform") Platform platform, @QueryParam("locale") final String locale, @QueryParam("version") final String version) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/masteries")
    @GET
    public MasteryList masteries(@QueryParam("platform") Platform platform, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version,
        @QueryParam("tags") final Set<String> tags) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/masteries/{id}")
    @GET
    public Mastery mastery(@QueryParam("platform") Platform platform, @PathParam("id") final int id, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/profile-icons")
    @GET
    public ProfileIconData profileIcons(@QueryParam("platform") Platform platform, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/realms")
    @GET
    public Realm realm(@QueryParam("platform") Platform platform) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(Realm.class, query);
    }

    @Path("/runes/{id}")
    @GET
    public Rune rune(@QueryParam("platform") Platform platform, @PathParam("id") final int id, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/runes")
    @GET
    public RuneList runes(@QueryParam("platform") Platform platform, @QueryParam("locale") final String locale, @QueryParam("version") final String version,
        @QueryParam("tags") final Set<String> tags) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/summoner-spells/{id}")
    @GET
    public SummonerSpell summonerSpell(@QueryParam("platform") Platform platform, @PathParam("id") final int id, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/summoner-spells")
    @GET
    public SummonerSpellList summonerSpells(@QueryParam("platform") Platform platform, @QueryParam("locale") final String locale,
        @QueryParam("version") final String version, @QueryParam("tags") final Set<String> tags,
        @QueryParam("dataById") @DefaultValue("false") final boolean dataById) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
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

    @Path("/versions")
    @GET
    public Versions versions(@QueryParam("platform") Platform platform) {
        if(platform == null) {
            platform = context.getDefaultPlatform();
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .build();

        return context.getPipeline().get(Versions.class, query);
    }
}
