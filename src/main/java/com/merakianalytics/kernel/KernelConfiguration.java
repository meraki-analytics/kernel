package com.merakianalytics.kernel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.deltaspike.core.api.exclude.Exclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.merakianalytics.kernel.filters.CORSFilter;
import com.merakianalytics.kernel.producers.KernelConfigurationProducer;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration.PipelineElementConfiguration;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration.TransformerConfiguration;
import com.merakianalytics.orianna.datapipeline.riotapi.RiotAPI;
import com.merakianalytics.orianna.datapipeline.riotapi.RiotAPIService.FailedRequestStrategy;
import com.merakianalytics.orianna.types.common.Platform;

/**
 * The application configuration
 */
@Exclude
public class KernelConfiguration {
    private static final String CONFIGURATION_PATH_ENVIRONMENT_VARIABLE = "KERNEL_CONFIGURATION_PATH";
    private static final String DEFAULT_CONFIGURATION_RESOURCE = "com/merakianalytics/kernel/default-kernel-config.json";
    private static final Logger LOGGER = LoggerFactory.getLogger(KernelConfiguration.class);

    private static PipelineConfiguration defaultPipelineConfiguration() {
        final PipelineConfiguration config = new PipelineConfiguration();

        final Set<TransformerConfiguration> transformers = ImmutableSet.of();
        config.setTransformers(transformers);

        final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_DEFAULT);
        final PipelineElementConfiguration riotAPI = PipelineElementConfiguration.defaultConfiguration(RiotAPI.class);
        ((ObjectNode)riotAPI.getConfig()).set("http404Strategy", mapper.valueToTree(new FailedRequestStrategy.ThrowException()));

        final List<PipelineElementConfiguration> elements = ImmutableList.of(riotAPI);
        config.setElements(elements);

        return config;
    }

    /**
     * Loads the application configuration from standard resource locations, or falls back on the default configuration
     *
     * @return the application configuration
     */
    public static KernelConfiguration load() {
        final ObjectMapper mapper = new ObjectMapper().enable(Feature.ALLOW_COMMENTS);

        KernelConfiguration config = null;
        String message = null;

        final String configPath = System.getenv(CONFIGURATION_PATH_ENVIRONMENT_VARIABLE);
        if(configPath != null) {
            try {
                config = mapper.readValue(new File(configPath), KernelConfiguration.class);
                message = " from " + configPath + " as specified by " + CONFIGURATION_PATH_ENVIRONMENT_VARIABLE;
            } catch(final IOException e) {
                LOGGER.error("Failed to load Kernel Configuration! Attempting to load default configuration!", e);
            }
        }

        if(config == null) {
            try(InputStream inputStream = KernelConfigurationProducer.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIGURATION_RESOURCE)) {
                config = mapper.readValue(inputStream, KernelConfiguration.class);
                message = " from default resource file " + DEFAULT_CONFIGURATION_RESOURCE;
            } catch(final IOException e) {
                LOGGER.error("Failed to load default Kernel Configuration! Using default constructor instead!", e);
            }
        }

        if(config == null) {
            config = new KernelConfiguration();
            message = " using the default constructor";
        }

        LOGGER.info("Loaded new Kernel Configuration" + message + "!");
        return config;
    }

    private CORSFilter.Configuration CORS = new CORSFilter.Configuration();
    private Platform defaultPlatform = Platform.NORTH_AMERICA;
    private PipelineConfiguration pipeline = defaultPipelineConfiguration();
    private boolean produceCoreData = false;

    /**
     * @return the CORS
     */
    public CORSFilter.Configuration getCORS() {
        return CORS;
    }

    /**
     * @return the defaultPlatform
     */
    public Platform getDefaultPlatform() {
        return defaultPlatform;
    }

    /**
     * @return the pipeline
     */
    public PipelineConfiguration getPipeline() {
        return pipeline;
    }

    /**
     * @return the produceCoreData
     */
    public boolean isProduceCoreData() {
        return produceCoreData;
    }

    /**
     * @param CORS
     *        the CORS to set
     */
    public void setCORS(final CORSFilter.Configuration CORS) {
        this.CORS = CORS;
    }

    /**
     * @param defaultPlatform
     *        the defaultPlatform to set
     */
    public void setDefaultPlatform(final Platform defaultPlatform) {
        this.defaultPlatform = defaultPlatform;
    }

    /**
     * @param pipeline
     *        the pipeline to set
     */
    public void setPipeline(final PipelineConfiguration pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * @param produceCoreData
     *        the produceCoreData to set
     */
    public void setProduceCoreData(final boolean produceCoreData) {
        this.produceCoreData = produceCoreData;
    }
}
