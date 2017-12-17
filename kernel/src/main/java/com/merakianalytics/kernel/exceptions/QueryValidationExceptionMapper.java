package com.merakianalytics.kernel.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merakianalytics.orianna.datapipeline.common.QueryValidationException;

public class QueryValidationExceptionMapper implements ExceptionMapper<QueryValidationException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryValidationExceptionMapper.class);

    public static Response handle(final QueryValidationException exception) {
        LOGGER.debug("Encountered an QueryValidationException from orianna!", exception);
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Override
    public Response toResponse(final QueryValidationException exception) {
        return handle(exception);
    }
}
