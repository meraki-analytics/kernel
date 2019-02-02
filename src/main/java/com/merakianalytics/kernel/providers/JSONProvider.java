package com.merakianalytics.kernel.providers;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.apache.deltaspike.core.api.exclude.Exclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Replaces the default JSON serialization to exclude default values when serializing
 */
@Exclude
@Provider
public class JSONProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JodaModule()).setSerializationInclusion(Include.NON_DEFAULT);

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return mapper;
    }
}
