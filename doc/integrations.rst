Integrations
############

Integrations are the microservices that Kernel handles. By default, Kernel provides an integration for the Riot API through Cassiopeia. You can add additional integrations if you have other services that need to cache data.

Cassiopeia and the Riot API
^^^^^^^^^^^^^^^^^^^^^^^^^^^

Kernel provides a wrapper around Cassiopeia by default. This code is separated into three directories: ``modules/``, ``riotapi/``, and ``integrations/cassiopeia/``.

When a request is sent to the Kernel server, the functions in ``modules/`` specify how to handle each request. These functions provide `bottle <https://github.com/bottlepy/bottle>`_ wrappers that redirect the requests to the appropriate functions (which for Cassiopeia are contained in ``riotapi/``).

The functions in ``riotapi/`` handle pulling the data from the redis cache if it exists, and querying Riot's API and saving the data into the redis cache if it does not exist.

The functions in ``integrations/cassiopeia/`` override the Cassiopeia request functions (e.g. ``riotapi.get_champions()``) with the functions contained in ``riotapi/``. This means that the instance of Cassiopeia that is running your server will no longer send requests to Riot. Instead, it will send requests to this Kernel module when it needs data.

This code architecture allows you to write your application using Cassiopeia exactly as you would without Kernel, and when you are ready to move into production and use a server, you can add Kernel without changing any of your previous code (all you have to do is write a configuration file and include the Cassiopeia module names in it).

In summary, the functions in ``integrations/cassiopeia`` overwrite the Cassiopeia functions so that any requests made from Cassiopeia are sent to the Kernel server rather than the Riot API. Then, all requests sent to the Kernel server are handled by the bottle wrappers in ``modules/``, which call the functions in ``riotapi/`` to either pull data from the cache or from Riot.


Writing New Integrations
^^^^^^^^^^^^^^^^^^^^^^^^

You can extend Kernel to work with any microservce. To do so you will need to write new functions under ``modules/`` and ``integrations/``, as well as similar functions as those within ``riotapi/``. You can then add your microservice to the configuration file under ``[modules]``. Unless your microservice is as large as the Riot API, this process is likely not as daunting as it may seem, and you can use the files discussed above to get started.

