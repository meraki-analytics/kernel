FROM merakianalytics/wildfly-alpine:11.0.0.Final

ENV JBOSS_DIRECTORY /opt/jboss/wildfly

# -- DEPLOY THE APPLICATION WAR
ADD target/lol.war $JBOSS_DIRECTORY/standalone/deployments

# -- DEPLOY THE APPLICATION MODULE FOR CONFIGURATION AND SET PROPER OWNERSHIP
ADD src/main/modules/com/merakianalytics/kernel $JBOSS_DIRECTORY/modules/com/merakianalytics/kernel
USER root
RUN chown -Rh jboss:jboss $JBOSS_DIRECTORY/modules/com/merakianalytics/kernel
