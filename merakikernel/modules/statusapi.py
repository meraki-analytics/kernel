import bottle

import merakikernel.riotapi.statusapi
import merakikernel.common

@bottle.route("/shards", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def shards():
    return merakikernel.riotapi.statusapi.shards(dict(bottle.request.query))


@bottle.route("/shards/<region>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def shard_status(region):
    return merakikernel.riotapi.statusapi.shard_status(region, dict(bottle.request.query))