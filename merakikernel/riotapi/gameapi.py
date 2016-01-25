import merakikernel.rediscache
import merakikernel.requests


_recent_games_typename = "RecentGames"


def recent_games(region, summonerId, params={}):
    region = region.lower()
    games = merakikernel.rediscache.get_value(_recent_games_typename, summonerId, region)

    if games:
        return games

    url = "/api/lol/{}/v1.3/game/by-summoner/{}/recent".format(region, summonerId)
    games = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_recent_games_typename, summonerId, games, region)

    return games
