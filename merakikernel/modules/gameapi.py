import bottle

import merakikernel.riotapi.gameapi
import merakikernel.common


@bottle.route("/api/lol/<region>/v1.3/game/by-summoner/<summonerId>/recent", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def recent_games(region, summonerId):
    return merakikernel.riotapi.gameapi.recent_games(region, summonerId, dict(bottle.request.query))
