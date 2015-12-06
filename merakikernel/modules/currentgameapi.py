import bottle

import merakikernel.apiproxy.currentgameapi
import merakikernel.common

@bottle.get("/observer-mode/rest/consumer/getSpectatorGameInfo/<platformId>/<summonerId>")
@merakikernel.common.riot_endpoint
def current_game(platformId, summonerId):
    return merakikernel.apiproxy.currentgameapi.current_game(platformId, summonerId, dict(bottle.request.query))