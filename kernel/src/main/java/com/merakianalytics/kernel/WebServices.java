package com.merakianalytics.kernel;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.merakianalytics.kernel.filters.CORSFilter;
import com.merakianalytics.kernel.providers.MessagePackProvider;
import com.merakianalytics.kernel.riotapi.ChampionAPI;
import com.merakianalytics.kernel.riotapi.ChampionMasteryAPI;
import com.merakianalytics.kernel.riotapi.LeagueAPI;

@ApplicationPath("/")
public class WebServices extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> services = new HashSet<>();
        services.add(CORSFilter.class);
        services.add(MessagePackProvider.class);

        services.add(ChampionMasteryAPI.class);
        services.add(ChampionAPI.class);
        services.add(LeagueAPI.class);
        return services;
    }
}
