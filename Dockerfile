FROM gcr.io/distroless/java:11


# -- SET JAVA OPTS FOR THORNTAIL
ENV JAVA_TOOL_OPTIONS -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv6Stack=true -Djava.awt.headless=true


# -- SETUP APPLICATION CONFIGURATION
ENV KERNEL_HOME /opt/meraki
ENV KERNEL_CONFIGURATION_PATH $KERNEL_HOME/kernel-config.json


# -- DEPLOY THE APPLICATION
ADD target/kernel-config.json $KERNEL_CONFIGURATION_PATH
ADD target/kernel-thorntail.jar $KERNEL_HOME/kernel.jar


# -- START THE APPLICATION
CMD ["/opt/meraki/kernel.jar"]
