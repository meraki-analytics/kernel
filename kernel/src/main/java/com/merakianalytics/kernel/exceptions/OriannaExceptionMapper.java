package com.merakianalytics.kernel.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merakianalytics.orianna.datapipeline.common.TimeoutException;
import com.merakianalytics.orianna.datapipeline.riotapi.exceptions.BadRequestException;
import com.merakianalytics.orianna.datapipeline.riotapi.exceptions.ForbiddenException;
import com.merakianalytics.orianna.datapipeline.riotapi.exceptions.InternalServerErrorException;
import com.merakianalytics.orianna.datapipeline.riotapi.exceptions.NotFoundException;
import com.merakianalytics.orianna.datapipeline.riotapi.exceptions.RateLimitExceededException;
import com.merakianalytics.orianna.datapipeline.riotapi.exceptions.ServiceUnavailableException;
import com.merakianalytics.orianna.datapipeline.riotapi.exceptions.UnsupportedMediaTypeException;
import com.merakianalytics.orianna.types.common.OriannaException;

/**
 * Handles forwarding HTTP errors from orianna on to the user and proper HTTP 500 behavior on unhandled
 * {@link com.merakianalytics.orianna.types.common.OriannaException}s
 *
 * @see com.merakianalytics.orianna.types.common.OriannaException
 */
public class OriannaExceptionMapper implements ExceptionMapper<OriannaException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OriannaExceptionMapper.class);

    /**
     * Handling logic for encountered {@link com.merakianalytics.orianna.types.common.OriannaException}s
     *
     * @param exception
     *        the exception
     * @return a {@link javax.ws.rs.core.Response} with the proper error code and headers set
     * @see com.merakianalytics.orianna.types.common.OriannaException
     */
    public static Response handle(final OriannaException exception) {
        if(exception instanceof TimeoutException) {
            final TimeoutException e = (TimeoutException)exception;
            switch(e.getType()) {
                case HTTP:
                    LOGGER.debug("Encountered a TimeoutException from orianna on the HTTP request to Riot!", e);
                    break;
                case RATE_LIMITER:
                    LOGGER.debug("Encountered a TimeoutException from orianna on acquiring the rate limiter!", e);
                    break;
                default:
                    LOGGER.debug("Encountered a TimeoutException from orianna!", e);
                    break;
            }
            return Response.status(Response.Status.GATEWAY_TIMEOUT).header("X-Timeout-Type", e.getType()).build();
        }

        if(exception instanceof BadRequestException) {
            final BadRequestException e = (BadRequestException)exception;
            LOGGER.error("Encountered a BadRequestException from orianna!", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if(exception instanceof ForbiddenException) {
            final ForbiddenException e = (ForbiddenException)exception;
            LOGGER.error("Encountered a ForbiddenException from orianna!", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if(exception instanceof InternalServerErrorException) {
            final InternalServerErrorException e = (InternalServerErrorException)exception;
            LOGGER.debug("Encountered an InternalServerErrorException from orianna!", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if(exception instanceof NotFoundException) {
            final NotFoundException e = (NotFoundException)exception;
            LOGGER.debug("Encountered a NotFoundException from orianna!", e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(exception instanceof RateLimitExceededException) {
            final RateLimitExceededException e = (RateLimitExceededException)exception;
            LOGGER.error("Encountered a RateLimitExceededException from orianna!", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if(exception instanceof ServiceUnavailableException) {
            final ServiceUnavailableException e = (ServiceUnavailableException)exception;
            LOGGER.info("Encountered a ServiceUnavailableException from orianna!", e);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }

        if(exception instanceof UnsupportedMediaTypeException) {
            final UnsupportedMediaTypeException e = (UnsupportedMediaTypeException)exception;
            LOGGER.error("Encountered a UnsupportedMediaTypeException from orianna!", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        LOGGER.error("Encountered an OriannaException from orianna!", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public Response toResponse(final OriannaException exception) {
        return handle(exception);
    }
}
