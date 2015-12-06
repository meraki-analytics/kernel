import merakikernel.riotapi.featuredgamesapi
import cassiopeia.type.dto.featuredgames
import cassiopeia.dto.featuredgamesapi
import cassiopeia.dto.requests

def get_featured_games():
    region = cassiopeia.dto.requests.region
    return cassiopeia.type.dto.featuredgames.FeaturedGames(merakikernel.riotapi.featuredgamesapi.featured_games(region))


def patch_api():
    get_featured_games.__doc__ = cassiopeia.dto.featuredgamesapi.get_featured_games.__doc__
    cassiopeia.dto.featuredgamesapi.get_featured_games = get_featured_games