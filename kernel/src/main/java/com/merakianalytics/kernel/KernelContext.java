package com.merakianalytics.kernel;

import org.apache.deltaspike.core.api.exclude.Exclude;

import com.merakianalytics.datapipelines.DataPipeline;
import com.merakianalytics.orianna.types.common.Platform;

@Exclude
public class KernelContext {
    private Platform defaultPlatform;
    private DataPipeline pipeline;

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
}
