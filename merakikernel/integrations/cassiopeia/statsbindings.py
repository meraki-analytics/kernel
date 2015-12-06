import merakikernel.riotapi.statsapi
import cassiopeia.type.dto.stats
import cassiopeia.dto.statsapi
import cassiopeia.dto.requests

def get_ranked_stats(summoner_id, season=None):
    region = cassiopeia.dto.requests.region

    params = {}
    if season:
        params["season"] = season

    return cassiopeia.type.dto.stats.RankedStats(merakikernel.riotapi.statsapi.ranked_stats(region, str(summoner_id), params))

def get_stats(summoner_id, season=None):
    region = cassiopeia.dto.requests.region

    params = {}
    if season:
        params["season"] = season

    return cassiopeia.type.dto.stats.PlayerStatsSummaryList(merakikernel.riotapi.statsapi.stats(region, str(summoner_id), params))


def patch_api():
    get_ranked_stats.__doc__ = cassiopeia.dto.statsapi.get_ranked_stats.__doc__
    cassiopeia.dto.statsapi.get_ranked_stats = get_ranked_stats

    get_stats.__doc__ = cassiopeia.dto.statsapi.get_stats.__doc__
    cassiopeia.dto.statsapi.get_stats = get_stats