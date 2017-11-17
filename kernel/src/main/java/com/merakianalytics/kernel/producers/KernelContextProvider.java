package com.merakianalytics.kernel.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.merakianalytics.datapipelines.DataPipeline;
import com.merakianalytics.kernel.KernelConfiguration;
import com.merakianalytics.kernel.KernelContext;
import com.merakianalytics.orianna.datapipeline.riotapi.RiotAPI;

public class KernelContextProvider {
    @Inject
    private KernelConfiguration config;

    @Produces
    @ApplicationScoped
    public KernelContext provideKernelContext() {
        final KernelContext context = new KernelContext();
        context.setDefaultPlatform(config.getDefaultPlatform());
        context.setPipeline(new DataPipeline(new RiotAPI()));
        return context;
    }
}
