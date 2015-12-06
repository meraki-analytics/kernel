import merakikernel.rediscache
import merakikernel.requests

_shards_typename        = "Shards"
_shard_status_typename  = "ShardStatus"


def shards(params={}):
    shards = merakikernel.rediscache.get_value(_shards_typename, "", "")
    
    if shards:
        return shards

    url    = "/shards"
    shards = merakikernel.requests.get("", url, params, True, True)

    merakikernel.rediscache.put_value(_shards_typename, "", shards, "")

    return shards


def shard_status(region, params={}):
    region = region.lower()
    
    status = merakikernel.rediscache.get_value(_shard_status_typename, "", region)
    
    if status:
        return status

    url    = "/shards/{}".format(region)
    status = merakikernel.requests.get("", url, params, True, True)

    merakikernel.rediscache.put_value(_shard_status_typename, "", status, region)

    return status