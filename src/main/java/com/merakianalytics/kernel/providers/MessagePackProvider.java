package com.merakianalytics.kernel.providers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.msgpack.jackson.dataformat.MessagePackFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.merakianalytics.kernel.KernelContext;

/**
 * Allows MsgPack serialization. Excludes default values when serializing.
 */
@Dependent
@Provider
@Produces(MessagePackProvider.APPLICATION_MSGPACK)
@Consumes(MessagePackProvider.APPLICATION_MSGPACK)
public class MessagePackProvider<T> implements MessageBodyWriter<T>, MessageBodyReader<T> {
    public static final String APPLICATION_MSGPACK = "application/msgpack";
    public static final MediaType MEDIA_TYPE = MediaType.valueOf(APPLICATION_MSGPACK);

    @Inject
    private KernelContext context;

    private ObjectMapper mapper;

    /**
     * The KernelContext won't be injected until after construction so we initialize the mapper then
     */
    @PostConstruct
    private void initializeMapper() {
        mapper = new ObjectMapper(new MessagePackFactory()).registerModule(new JodaModule()).setSerializationInclusion(context.getSerializationInclusions());
    }

    @Override
    public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return mediaType.isCompatible(MEDIA_TYPE);
    }

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return mediaType.isCompatible(MEDIA_TYPE);
    }

    @Override
    public T readFrom(final Class<T> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType,
        final MultivaluedMap<String, String> httpHeaders, final InputStream entityStream) throws IOException, WebApplicationException {
        return mapper.readValue(entityStream, type);
    }

    @Override
    public void writeTo(final T object, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType,
        final MultivaluedMap<String, Object> httpHeaders, final OutputStream outputStream) throws IOException, WebApplicationException {
        outputStream.write(mapper.writeValueAsBytes(object));
    }
}
