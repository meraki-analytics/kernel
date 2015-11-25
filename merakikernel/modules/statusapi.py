import bottle

import merakikernel.rediscache
import merakikernel.requests
import merakikernel.common

_shards_typename        = "Shards"
_shard_status_typename  = "ShardStatus"


@bottle.route("/shards", method="GET")
@merakikernel.common.riot_endpoint
def shards():
    shards = merakikernel.rediscache.get_value(_shards_typename, "", "")
    
    if shards:
        return shards

    url    = "/shards"
    shards = merakikernel.requests.get("", url, dict(bottle.request.query), True, True)

    merakikernel.rediscache.put_value(_shards_typename, "", shards, "")

    return shards


@bottle.route("/shards/<region>", method="GET")
@merakikernel.common.riot_endpoint
def shard_status(region):
    region = region.lower()
    
    status = merakikernel.rediscache.get_value(_shard_status_typename, "", region)
    
    if status:
        return status

    url    = "/shards/{}".format(region)
    status = merakikernel.requests.get("", url, dict(bottle.request.query), True, True)

    merakikernel.rediscache.put_value(_shard_status_typename, "", status, region)

    return status