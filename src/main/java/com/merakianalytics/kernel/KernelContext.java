package com.merakianalytics.kernel;

import org.apache.deltaspike.core.api.exclude.Exclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.merakianalytics.datapipelines.DataPipeline;
import com.merakianalytics.kernel.filters.CORSFilter;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration;
import com.merakianalytics.orianna.datapipeline.riotapi.RiotAPI;
import com.merakianalytics.orianna.types.common.Platform;

/**
 * Holds the application state
 */
@Exclude
public class KernelContext {
    private static final boolean DEFAULT_PRODUCE_CORE_DATA = false;
    private static final Include DEFAULT_SERIALIZATION_INCLUSIONS = Include.NON_DEFAULT;

    /**
     * Creates a new applications state from a {@link com.merakianalytics.kernel.KernelConfiguration}
     *
     * @param config
     *        the configuration
     * @return the new application state
     */
    public static KernelContext fromConfiguration(final KernelConfiguration config) {
        final KernelContext context = new KernelContext();
        context.setCORS(config.getCORS());
        context.setDefaultPlatform(config.getDefaultPlatform());
        context.setPipeline(PipelineConfiguration.toPipeline(config.getPipeline()));
        context.setProduceCoreData(config.isProduceCoreData());
        context.setSerializationInclusions(config.getSerializationInclusions());
        return context;
    }

    private CORSFilter.Configuration CORS = new CORSFilter.Configuration();
    private Platform defaultPlatform = Platform.NORTH_AMERICA;
    private DataPipeline pipeline = new DataPipeline(new RiotAPI());
    private boolean produceCoreData = DEFAULT_PRODUCE_CORE_DATA;
    private Include serializationInclusions = DEFAULT_SERIALIZATION_INCLUSIONS;

    /**
     * @return the cors
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
    public DataPipeline getPipeline() {
        return pipeline;
    }

    /**
     * @return the serializationInclusions
     */
    public Include getSerializationInclusions() {
        return serializationInclusions;
    }

    /**
     * @return the produceCoreData
     */
    public boolean isProduceCoreData() {
        return produceCoreData;
    }

    /**
     * @param cors
     *        the cors to set
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
    public void setPipeline(final DataPipeline pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * @param produceCoreData
     *        the produceCoreData to set
     */
    public void setProduceCoreData(final boolean produceCoreData) {
        this.produceCoreData = produceCoreData;
    }

    /**
     * @param serializationInclusions
     *        the serializationInclusions to set
     */
    public void setSerializationInclusions(final Include serializationInclusions) {
        this.serializationInclusions = serializationInclusions;
    }
}
