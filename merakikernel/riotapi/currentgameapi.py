import merakikernel.rediscache
import merakikernel.requests
import merakikernel.riotapi.common


_current_game_typename = "CurrentGame"


def current_game(platformId, summonerId, params={}):
    region = merakikernel.riotapi.common.platform_regions[platformId.upper()]

    game = merakikernel.rediscache.get_value(_current_game_typename, summonerId, region)

    if game:
        return game

    url = "/observer-mode/rest/consumer/getSpectatorGameInfo/{}/{}".format(platformId, summonerId)
    game = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_current_game_typename, summonerId, game, region)

    return game
