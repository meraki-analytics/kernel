import merakikernel.riotapi.statusapi
import cassiopeia.type.dto.status
import cassiopeia.dto.statusapi
import cassiopeia.dto.requests

def get_shards():
    return [cassiopeia.type.dto.status.Shard(shard) for shard in merakikernel.riotapi.statusapi.shards()]

def get_shard():
    region = cassiopeia.dto.requests.region
    return cassiopeia.type.dto.status.ShardStatus(merakikernel.riotapi.statusapi.shard_status(region))


def patch_api():
    get_shards.__doc__ = cassiopeia.dto.statusapi.get_shards.__doc__
    cassiopeia.dto.statusapi.get_shards = get_shards

    get_shard.__doc__ = cassiopeia.dto.statusapi.get_shard.__doc__
    cassiopeia.dto.statusapi.get_shard = get_shard