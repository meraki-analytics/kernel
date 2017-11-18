package com.merakianalytics.kernel.producers;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merakianalytics.kernel.KernelConfiguration;

public class KernelConfigurationProducer {
    private static final String CONFIGURATION_RESOURCE = "kernel-config.json";
    private static final String DEFAULT_CONFIGURATION_RESOURCE = "com/merakianalytics/kernel/default-kernel-config.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(KernelConfigurationProducer.class);

    @Produces
    @ApplicationScoped
    public static KernelConfiguration produceKernelConfiguration() {
        final ObjectMapper mapper = new ObjectMapper().enable(Feature.ALLOW_COMMENTS);

        try(InputStream inputStream = KernelConfigurationProducer.class.getClassLoader().getResourceAsStream(CONFIGURATION_RESOURCE)) {
            return mapper.readValue(inputStream, KernelConfiguration.class);
        } catch(final IOException e) {
            LOGGER.error("Failed to load Kernel Configuration! Attempting to load default configuration!", e);
        }

        try(InputStream inputStream = KernelConfigurationProducer.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIGURATION_RESOURCE)) {
            return mapper.readValue(inputStream, KernelConfiguration.class);
        } catch(final IOException e) {
            LOGGER.error("Failed to load default Kernel Configuration! Using default constructor instead!", e);
        }

        return new KernelConfiguration();
    }
}
