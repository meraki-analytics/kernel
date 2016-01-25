import bottle

import merakikernel.riotapi.currentgameapi
import merakikernel.common


@bottle.route("/observer-mode/rest/consumer/getSpectatorGameInfo/<platformId>/<summonerId>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def current_game(platformId, summonerId):
    return merakikernel.riotapi.currentgameapi.current_game(platformId, summonerId, dict(bottle.request.query))
