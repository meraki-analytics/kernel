import merakikernel.rediscache
import merakikernel.requests

_ranked_stats_typename = "RankedStats"
_stats_typename        = "Stats"


def ranked_stats(region, summonerId, params={}):
    meta = "{}|{}".format(region.lower(), params.get("season", ""))

    stats = merakikernel.rediscache.get_value(_ranked_stats_typename, summonerId, meta)

    if stats:
        return stats

    url   = "/api/lol/{}/v1.3/stats/by-summoner/{}/ranked".format(region, summonerId)
    stats = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_ranked_stats_typename, summonerId, stats, meta)

    return stats


def stats(region, summonerId, params={}):
    meta = "{}|{}".format(region.lower(), params.get("season", ""))

    stats = merakikernel.rediscache.get_value(_stats_typename, summonerId, meta)

    if stats:
        return stats

    url   = "/api/lol/{}/v1.3/stats/by-summoner/{}/summary".format(region, summonerId)
    stats = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_stats_typename, summonerId, stats, meta)

    return stats