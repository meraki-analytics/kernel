# Kernel - A plug & play Riot API proxy server
Kernel is built on top of [orianna](https://github.com/meraki-analytics/orianna), a Riot API wrapper and framework for working with API data. The API Kernel provides mirrors the [Riot API](https://developer.riotgames.com/api-methods/), and allows users to proxy their API calls through Kernel to avoid "leaking" their Riot API key to end users.

Kernel is built using the [Thorntail](https://thorntail.io/) application server with [JAX-RS](https://github.com/jax-rs) and [RestEasy](https://github.com/resteasy/Resteasy).

Kernel is licensed under the [MIT License](https://github.com/meraki-analytics/kernel/blob/master/LICENSE.txt).

## Features
Kernel also includes the following built-in features:
  - Automatic rate limit handling for the Riot API
  - Configurable strategies for handling Riot API errors (retry, expontential backoff, return null, etc)
  - A configurable pipeline for automatic caching of API results
    - Plug & Play support for several common databases in the works [here](https://github.com/meraki-analytics/orianna-datastores)
  - Support for [MessagePack](https://msgpack.org/index.html) serialization in addition to JSON

## API Differences from Riot API
Kernel is intended to mirror the Riot API exactly. The Riot API, however, is distributed accross regional platform domains to split computation load and localize content distribution (e.g. na1.api.riotgames.com or euw1.api.riotgames.com). Kernel supports serving _all_ platforms from a single server instance using the `platform` query parameter, which is available for every API endpoint in Kernel. All Riot API platforms are supported by this feature. A default platform can be configured to direct requests without a `platform` query parameter to that platform.

Example: the Riot API call `https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/FatalElement` becomes `http://localhost/lol/summoner/v4/summoners/by-name/FatalElement?platform=NA1` for Kernel.

## How to get it
Kernel can be obtained and installed either through [Docker](https://www.docker.com/) or by building the application locally using [Maven](https://maven.apache.org/).

### Docker
Kernel docker images are available on [Docker Hub](https://hub.docker.com/r/merakianalytics/kernel/). If you're unfamiliar with docker, here's how to run them on your local machine:

- Install & Configure Docker
  - [Windows 10 Professional](https://docs.docker.com/docker-for-windows/install/) (or other windows version with Hyper-V support)
  - [Other Windows](https://docs.docker.com/toolbox/toolbox_install_windows/)
  - [Ubuntu](https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/)
  - [Debian](https://docs.docker.com/engine/installation/linux/docker-ce/debian/)
  - [CentOS](https://docs.docker.com/engine/installation/linux/docker-ce/centos/)
  - [Fedora](https://docs.docker.com/engine/installation/linux/docker-ce/fedora/)
  - [Mac](https://docs.docker.com/docker-for-mac/install/)
- Install [Docker Compose](https://docs.docker.com/compose/install/)
- Choose an off-the-shelf configuration of Kernel to use and download the `docker-compose.yml` file associated with it
  - [Base Configuration (No Caching Included)](https://github.com/meraki-analytics/kernel/tree/master/configurations/dto/base)
  - [JetBrains Xodus Configuration (Filesystem-based Embedded DB)](https://github.com/meraki-analytics/kernel/tree/master/configurations/dto/xodus)
  - [MongoDB Configuration](https://github.com/meraki-analytics/kernel/tree/master/configurations/dto/mongo)
  - [More configurations forthcoming as support for additional popular databases is completed for orianna](https://github.com/meraki-analytics/orianna-datastores)
- Set your `RIOT_API_KEY` environment variable to your [Riot API Key](https://developer.riotgames.com/)
- Navigate to the directory where you downloaded `docker-compose.yml` on your local machine and open a terminal or command prompt
  - Run `docker-compose up -d --force-recreate` to lanch Kernel in the background
  - Run `docker-compose down` to shut down Kernel
- The default `docker-compose.yml` files will start Kernel on port 80, as well as launching a [Swagger UI](https://hub.docker.com/r/swaggerapi/swagger-ui/) on port 12357 to explore the API.

If you want to change some configuration settings in Kernel, you can edit the `kernel-config.json` file from the configuration you chose, then add a volume entry to the `docker-compose.yml` file to load your config into the container.

Example: [original compose file](https://github.com/meraki-analytics/kernel/blob/master/configurations/dto/base/docker-compose.yml) | [compose file with custom kernel-config.json](https://gist.github.com/robrua/9a89b908e2a6c3848cc4ab3ec5a0638e)

### Building Locally with Maven
- Install [JDK 8 or higher](https://openjdk.java.net/install/)
- Install [Maven](https://maven.apache.org/install.html)
- Clone the Kernel source code locally & navigate to the folder
- Run `mvn clean package` to build the application. Some maven profiles are available to bundle datastore dependencies in with the application. Use `mvn clean package -P some,profile,names` to activate them
  - [mongo](https://github.com/meraki-analytics/kernel/blob/master/pom.xml#L295-L338)
  - [xodus](https://github.com/meraki-analytics/kernel/blob/master/pom.xml#L339-L382)
  - [More configurations forthcoming as support for additional popular databases is completed for orianna](https://github.com/meraki-analytics/orianna-datastores)
- From your Kernel source folder, copy `target/kernel-thorntail.jar` wherever you'd like to serve Kernel from
- From your Kernel source folder, copy `target/kernel-config.json` wherever you'd like to serve Kernel from
- Set your `KERNEL_CONFIGURATION_PATH` environment variable to point at your `kernel-config.json`
- Set your `RIOT_API_KEY` environment variable to your [Riot API Key](https://developer.riotgames.com/)
- Run `java -D"swarm.http.port=8080" -D"java.net.preferIPv4Stack=true" -D"java.net.preferIPv6Stack=true" -D"java.awt.headless=true" -jar kernel-thorntail.jar` to launch Kernel.

## Questions & Contributions
Feel free to send pull requests or to contact us via GitHub or [Discord](https://discord.gg/JRDk2JU).

## Disclaimer
Kernel isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.
