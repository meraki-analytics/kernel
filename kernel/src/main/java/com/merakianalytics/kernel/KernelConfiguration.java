package com.merakianalytics.kernel;

import java.util.List;
import java.util.Set;

import org.apache.deltaspike.core.api.exclude.Exclude;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.merakianalytics.kernel.filters.CORSFilter;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration.PipelineElementConfiguration;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration.TransformerConfiguration;
import com.merakianalytics.orianna.datapipeline.riotapi.RiotAPI;
import com.merakianalytics.orianna.types.common.Platform;

@Exclude
public class KernelConfiguration {
    private static PipelineConfiguration defaultPipelineConfiguration() {
        final PipelineConfiguration config = new PipelineConfiguration();

        final Set<TransformerConfiguration> transformers = ImmutableSet.of();
        config.setTransformers(transformers);

        final List<PipelineElementConfiguration> elements = ImmutableList.of(
            PipelineElementConfiguration.defaultConfiguration(RiotAPI.class));
        config.setElements(elements);

        return config;
    }

    private CORSFilter.Configuration CORS = new CORSFilter.Configuration();
    private Platform defaultPlatform = Platform.NORTH_AMERICA;
    private PipelineConfiguration pipeline = defaultPipelineConfiguration();

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
}
