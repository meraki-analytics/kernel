import bottle

import merakikernel.riotapi.statsapi
import merakikernel.common

@bottle.route("/api/lol/<region>/v1.3/stats/by-summoner/<summonerId>/ranked", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def ranked_stats(region, summonerId):
    return merakikernel.riotapi.statsapi.ranked_stats(region, summonerId, dict(bottle.request.query))


@bottle.route("/api/lol/<region>/v1.3/stats/by-summoner/<summonerId>/summary", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def stats(region, summonerId):
    return merakikernel.riotapi.statsapi.stats(region, summonerId, dict(bottle.request.query))