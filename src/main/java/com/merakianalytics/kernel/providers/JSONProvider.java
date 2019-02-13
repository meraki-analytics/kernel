package com.merakianalytics.kernel.providers;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.merakianalytics.kernel.KernelContext;

/**
 * Replaces the default JSON serialization to exclude default values when serializing
 */
@Dependent
@Provider
public class JSONProvider implements ContextResolver<ObjectMapper> {
    @Inject
    private KernelContext context;

    private ObjectMapper mapper;

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return mapper;
    }

    /**
     * The KernelContext won't be injected until after construction so we initialize the mapper then
     */
    @PostConstruct
    private void initializeMapper() {
        mapper = new ObjectMapper().registerModule(new JodaModule()).setSerializationInclusion(context.getSerializationInclusions());
    }
}
