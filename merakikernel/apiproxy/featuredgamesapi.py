import merakikernel.rediscache
import merakikernel.requests

_featured_games_typename = "FeaturedGames"


def featured_games(region, params={}):
    games = merakikernel.rediscache.get_value(_featured_games_typename, "", region)
    
    if games:
        return games

    url   = "/observer-mode/rest/featured"
    games = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_featured_games_typename, "", games, region)

    return games