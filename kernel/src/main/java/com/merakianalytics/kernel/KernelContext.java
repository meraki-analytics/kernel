package com.merakianalytics.kernel;

import java.util.UUID;

import org.apache.deltaspike.core.api.exclude.Exclude;

import com.merakianalytics.datapipelines.DataPipeline;
import com.merakianalytics.kernel.filters.CORSFilter;
import com.merakianalytics.orianna.datapipeline.PipelineConfiguration;
import com.merakianalytics.orianna.datapipeline.riotapi.RiotAPI;
import com.merakianalytics.orianna.types.common.Platform;

@Exclude
public class KernelContext {
    public static KernelContext fromConfiguration(final KernelConfiguration config) {
        final KernelContext context = new KernelContext();
        context.updateFromConfiguration(config);
        return context;
    }

    private CORSFilter.Configuration CORS = new CORSFilter.Configuration();
    private Platform defaultPlatform = Platform.NORTH_AMERICA;
    private DataPipeline pipeline = new DataPipeline(new RiotAPI());
    private String secret = UUID.randomUUID().toString();

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
     * @return the secret
     */
    public String getSecret() {
        return secret;
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
     * @param secret
     *        the secret to set
     */
    public void setSecret(final String secret) {
        this.secret = secret;
    }

    public void updateFromConfiguration(final KernelConfiguration config) {
        setCORS(config.getCORS());
        setDefaultPlatform(config.getDefaultPlatform());
        setPipeline(PipelineConfiguration.toPipeline(config.getPipeline()));
        setSecret(config.getSecret());
    }
}
