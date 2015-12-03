import bottle

import merakikernel.rediscache
import merakikernel.requests
import merakikernel.common

_current_game_typename = "CurrentGame"
_platform_regions      = {
    "BR1": "br",
    "EUN1": "eune",
    "EUW1": "euw",
    "KR": "kr",
    "LA1": "lan",
    "LA2": "las",
    "NA1": "na",
    "OC1": "oce",
    "TR1": "tr",
    "RU": "ru",
    "PBE1": "pbe"
}

@bottle.get("/observer-mode/rest/consumer/getSpectatorGameInfo/<platformId>/<summonerId>")
@merakikernel.common.riot_endpoint
def current_game(platformId, summonerId):
    region = _platform_regions[platformId.upper()]

    game = merakikernel.rediscache.get_value(_current_game_typename, summonerId, region)
    
    if game:
        return game

    url  = "/observer-mode/rest/consumer/getSpectatorGameInfo/{}/{}".format(platformId, summonerId)
    game = merakikernel.requests.get(region, url, dict(bottle.request.query))

    merakikernel.rediscache.put_value(_current_game_typename, summonerId, game, region)

    return game