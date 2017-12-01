package com.merakianalytics.kernel;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.merakianalytics.kernel.filters.CORSFilter;
import com.merakianalytics.kernel.providers.JSONProvider;
import com.merakianalytics.kernel.providers.MessagePackProvider;
import com.merakianalytics.kernel.riotapi.ChampionAPI;
import com.merakianalytics.kernel.riotapi.ChampionMasteryAPI;
import com.merakianalytics.kernel.riotapi.LeagueAPI;
import com.merakianalytics.kernel.riotapi.MatchAPI;
import com.merakianalytics.kernel.riotapi.SpectatorAPI;
import com.merakianalytics.kernel.riotapi.StaticDataAPI;
import com.merakianalytics.kernel.riotapi.StatusAPI;
import com.merakianalytics.kernel.riotapi.SummonerAPI;
import com.merakianalytics.kernel.riotapi.ThirdPartyCodeAPI;

@ApplicationPath("/")
public class WebServices extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> services = new HashSet<>();

        // Utilities
        services.add(CORSFilter.class);
        services.add(JSONProvider.class);
        services.add(MessagePackProvider.class);

        // Riot API
        services.add(ChampionMasteryAPI.class);
        services.add(ChampionAPI.class);
        services.add(LeagueAPI.class);
        services.add(StaticDataAPI.class);
        services.add(StatusAPI.class);
        services.add(MatchAPI.class);
        services.add(SpectatorAPI.class);
        services.add(SummonerAPI.class);
        services.add(ThirdPartyCodeAPI.class);

        return services;
    }
}
