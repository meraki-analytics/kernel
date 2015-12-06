import urllib.error

import merakikernel.riotapi.currentgameapi
import cassiopeia.type.dto.currentgame
import cassiopeia.dto.currentgameapi
import cassiopeia.dto.requests

_region_platforms = {region: platform for platform, region in merakikernel.riotapi.currentgameapi._platform_regions.items()}

def get_current_game(summoner_id):
    platform = _region_platforms[cassiopeia.dto.requests.region]
    try:
    	return cassiopeia.type.dto.currentgame.CurrentGameInfo(merakikernel.riotapi.currentgameapi.current_game(platform, str(summoner_id)))
    except urllib.error.HTTPError as e:
    	if e.code == 404:
    		return None
    	raise e


def patch_api():
    get_current_game.__doc__ = cassiopeia.dto.currentgameapi.get_current_game.__doc__
    cassiopeia.dto.currentgameapi.get_current_game = get_current_game