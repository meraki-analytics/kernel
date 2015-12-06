import bottle

import merakikernel.riotapi.statusapi
import merakikernel.common

@bottle.get("/shards")
@merakikernel.common.riot_endpoint
def shards():
    return merakikernel.riotapi.statusapi.shards(dict(bottle.request.query))


@bottle.get("/shards/<region>")
@merakikernel.common.riot_endpoint
def shard_status(region):
    return merakikernel.riotapi.statusapi.shard_status(region, dict(bottle.request.query))