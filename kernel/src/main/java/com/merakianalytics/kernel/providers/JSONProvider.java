package com.merakianalytics.kernel.providers;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.apache.deltaspike.core.api.exclude.Exclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Exclude
@Provider
public class JSONProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return mapper;
    }
}
