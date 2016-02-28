What is Kernel?
###############


I still need to write:
======================
* Can Redis run out of memory and crash the server?
* Is / how often is the cache saved to disk?


Kernel
======

**Meraki Kernel**, or just **Kernel** for short, is a python microservice designed to `cache <https://en.wikipedia.org/wiki/Cache_(computing)>`_ data from other microservices. By default, Kernel caches data from the Riot API that were requested using `Meraki's Cassiopeia <https://github.com/meraki-analytics/cassiopeia>`_ library, which is an example of a microservice. To cache its results, Kernel uses a Redis in-memory database.

When you have an application that requests data for your users, you can set up a server as an intermediary between your application and the Riot API. With Kernel installed, your application should send its requests to your server rather than the Riot API. For example, your application should make a request to ``your-server-hostname/api/lol/static-data/na/v1.2/champion?champData=all`` rather than ``https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion?champData=all``.

Kernel will cache all data that are requested so that further requests to a microservice will not require duplicate computation (and will not use up your rate limit in the case of Cassiopeia / the Riot API). Instead, the requested data will be loaded from Kernel's Redis in-memory cache (which is extremely fast) and returned to your user.

By using a server with Kernel as an intermediary between your application and the Riot API, your API key will also be hidden from the user. This helps to keep your `credentials secure <https://developer.riotgames.com/docs/credentials-security>`_ and ensure your application is approved for a production key.


Updating Data
"""""""""""""

As mentioned above, when a user requests data that is not stored in the Redis cache, that data is pulled from the appropriate microservice and is stored in the Redis cache. Any future calls that request the same data will load it from the Redis cache rather than making a call to the appropriate microservice (which may be time consuming). However, you or the user may wish to force the data to be updated by querying the microservice rather than getting old data from the cache. In order to do so, you can include ``force_update=True`` in any function call within Kernel and the Redis cache will not be queried; instead, the call will be made to the appropriate microservice and the results in the Redis cache will be updated.


Redis
=====

For an explanation of what Redis is, see the following help pages:

  * `Stack Overflow <http://stackoverflow.com/questions/7888880/what-is-redis-and-what-do-i-use-it-for>`_
  * `Introduction to Redis <http://redis.io/topics/introduction>`_
  * `Redis FAQ <http://redis.io/topics/faq>`_


Using Kernel Locally
====================

Even if you do not have a server, using Kernel locally may be convenient. Redis provides an in-memory database that can be saved to disk for persistance. This may be helpful if, for example, you are saving at most a few thousand matches but need to access them frequently.

