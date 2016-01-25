import merakikernel.riotapi.gameapi
import cassiopeia.type.dto.game
import cassiopeia.dto.gameapi
import cassiopeia.dto.requests


def get_recent_games(summoner_id):
    region = cassiopeia.dto.requests.region
    return cassiopeia.type.dto.game.RecentGames(merakikernel.riotapi.gameapi.recent_games(region, str(summoner_id)))


def patch_api():
    get_recent_games.__doc__ = cassiopeia.dto.gameapi.get_recent_games.__doc__
    cassiopeia.dto.gameapi.get_recent_games = get_recent_games
