import bottle

import merakikernel.rediscache
import merakikernel.requests
import merakikernel.common

_ranked_stats_typename = "RankedStats"
_stats_typename        = "Stats"

@bottle.get("/api/lol/<region>/v1.3/stats/by-summoner/<summonerId>/ranked")
@merakikernel.common.riot_endpoint
def ranked_stats(region, summonerId):
    params = dict(bottle.request.query)
    meta   = "{}|{}".format(region.lower(), params.get("season", ""))

    stats = merakikernel.rediscache.get_value(_ranked_stats_typename, summonerId, meta)

    if stats:
        return stats

    url   = "/api/lol/{}/v1.3/stats/by-summoner/{}/ranked".format(region, summonerId)
    stats = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_ranked_stats_typename, summonerId, stats, meta)

    return stats


@bottle.get("/api/lol/<region>/v1.3/stats/by-summoner/<summonerId>/summary")
@merakikernel.common.riot_endpoint
def stats(region, summonerId):
    params = dict(bottle.request.query)
    meta   = "{}|{}".format(region.lower(), params.get("season", ""))

    stats = merakikernel.rediscache.get_value(_stats_typename, summonerId, meta)

    if stats:
        return stats

    url   = "/api/lol/{}/v1.3/stats/by-summoner/{}/summary".format(region, summonerId)
    stats = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_stats_typename, summonerId, stats, meta)

    return stats