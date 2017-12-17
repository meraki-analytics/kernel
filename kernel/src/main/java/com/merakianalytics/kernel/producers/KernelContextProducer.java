package com.merakianalytics.kernel.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.merakianalytics.kernel.KernelConfiguration;
import com.merakianalytics.kernel.KernelContext;

public class KernelContextProducer {
    @Inject
    private KernelConfiguration config;

    @Produces
    @ApplicationScoped
    public KernelContext produceKernelContext() {
        return KernelContext.fromConfiguration(config);
    }
}
