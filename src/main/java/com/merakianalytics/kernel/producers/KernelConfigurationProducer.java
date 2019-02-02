package com.merakianalytics.kernel.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.merakianalytics.kernel.KernelConfiguration;

/**
 * Loads the configuration and produces it for the application
 */
public class KernelConfigurationProducer {
    @Produces
    @ApplicationScoped
    public static KernelConfiguration produceKernelConfiguration() {
        return KernelConfiguration.load();
    }
}
