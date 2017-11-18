package com.merakianalytics.kernel.filters;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import com.merakianalytics.kernel.KernelContext;

public class CORSFilter implements ContainerResponseFilter {
    public static class Configuration {
        private String allowCredentials = "true";
        private String allowHeaders = "*";
        private String allowMethods = "*";
        private String allowOrigin = "*";
        private String maxAge = "3600";

        /**
         * @return the allowCredentials
         */
        public String getAllowCredentials() {
            return allowCredentials;
        }

        /**
         * @return the allowHeaders
         */
        public String getAllowHeaders() {
            return allowHeaders;
        }

        /**
         * @return the allowMethods
         */
        public String getAllowMethods() {
            return allowMethods;
        }

        /**
         * @return the allowOrigin
         */
        public String getAllowOrigin() {
            return allowOrigin;
        }

        /**
         * @return the maxAge
         */
        public String getMaxAge() {
            return maxAge;
        }

        /**
         * @param allowCredentials
         *        the allowCredentials to set
         */
        public void setAllowCredentials(final String allowCredentials) {
            this.allowCredentials = allowCredentials;
        }

        /**
         * @param allowHeaders
         *        the allowHeaders to set
         */
        public void setAllowHeaders(final String allowHeaders) {
            this.allowHeaders = allowHeaders;
        }

        /**
         * @param allowMethods
         *        the allowMethods to set
         */
        public void setAllowMethods(final String allowMethods) {
            this.allowMethods = allowMethods;
        }

        /**
         * @param allowOrigin
         *        the allowOrigin to set
         */
        public void setAllowOrigin(final String allowOrigin) {
            this.allowOrigin = allowOrigin;
        }

        /**
         * @param maxAge
         *        the maxAge to set
         */
        public void setMaxAge(final String maxAge) {
            this.maxAge = maxAge;
        }
    }

    @Inject
    private KernelContext context;

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", context.getCORS().getAllowOrigin());
        responseContext.getHeaders().add("Access-Control-Allow-Headers", context.getCORS().getAllowHeaders());
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", context.getCORS().getAllowCredentials());
        responseContext.getHeaders().add("Access-Control-Allow-Methods", context.getCORS().getAllowMethods());
        responseContext.getHeaders().add("Access-Control-Max-Age", context.getCORS().getMaxAge());
    }
}
