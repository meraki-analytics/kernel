package com.merakianalytics.kernel.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.merakianalytics.kernel.KernelConfiguration;

public class KernelConfigurationProducer {
    @Produces
    @ApplicationScoped
    public static KernelConfiguration produceKernelConfiguration() {
        return KernelConfiguration.load();
    }
}
