package com.merakianalytics.kernel.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.merakianalytics.kernel.KernelConfiguration;
import com.merakianalytics.kernel.KernelContext;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration;

public class KernelContextProvider {
    @Inject
    private KernelConfiguration config;

    @Produces
    @ApplicationScoped
    public KernelContext provideKernelContext() {
        final KernelContext context = new KernelContext();
        context.setCORS(config.getCORS());
        context.setDefaultPlatform(config.getDefaultPlatform());
        context.setPipeline(PipelineConfiguration.toPipeline(config.getPipeline()));
        return context;
    }
}
