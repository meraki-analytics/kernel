package com.merakianalytics.kernel;

import org.apache.deltaspike.core.api.exclude.Exclude;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merakianalytics.kernel.filters.CORSFilter;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration;
import com.merakianalytics.orianna.datapipeline.riotapi.RiotAPI;
import com.merakianalytics.orianna.types.common.Platform;

@Exclude
public class KernelConfiguration {
    private static PipelineConfiguration defaultPipelineConfiguration() {
        final PipelineConfiguration config = new PipelineConfiguration();

        final PipelineConfiguration.PipelineElementConfiguration riotAPI = new PipelineConfiguration.PipelineElementConfiguration();
        riotAPI.setClassName(RiotAPI.class.getCanonicalName());
        riotAPI.setConfigClassName(RiotAPI.Configuration.class.getCanonicalName());
        riotAPI.setConfig(new ObjectMapper().valueToTree(new RiotAPI.Configuration()));
        config.getElements().add(riotAPI);

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
