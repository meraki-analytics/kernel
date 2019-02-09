package com.merakianalytics.kernel.providers;

import javax.inject.Inject;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.apache.deltaspike.core.api.exclude.Exclude;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.merakianalytics.kernel.KernelContext;

/**
 * Replaces the default JSON serialization to exclude default values when serializing
 */
@Exclude
@Provider
public class JSONProvider implements ContextResolver<ObjectMapper> {
    @Inject
    private KernelContext context;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JodaModule()).setSerializationInclusion(context.getSerializationInclusions());

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return mapper;
    }
}
