import bottle

import merakikernel.apiproxy.statsapi
import merakikernel.common

@bottle.get("/api/lol/<region>/v1.3/stats/by-summoner/<summonerId>/ranked")
@merakikernel.common.riot_endpoint
def ranked_stats(region, summonerId):
    return merakikernel.apiproxy.statsapi.ranked_stats(region, summonerId, dict(bottle.request.query))


@bottle.get("/api/lol/<region>/v1.3/stats/by-summoner/<summonerId>/summary")
@merakikernel.common.riot_endpoint
def stats(region, summonerId):
    return merakikernel.apiproxy.statsapi.stats(region, summonerId, dict(bottle.request.query))