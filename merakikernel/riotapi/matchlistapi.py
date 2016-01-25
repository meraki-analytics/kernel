import merakikernel.rediscache
import merakikernel.requests


_match_list_typename = "MatchList"


def _get_match_list_meta(region, params):
    return "|".join([region.lower(), params.get("championIds", ""), params.get("rankedQueues", ""), params.get("seasons", ""), params.get("beginTime", ""), params.get("endTime", ""), params.get("beginIndex", ""), params.get("endIndex", "")])


def match_list(region, summonerId, params={}):
    meta_data = _get_match_list_meta(region, params)
    matches = merakikernel.rediscache.get_value(_match_list_typename, summonerId, meta_data)

    if matches:
        return matches

    url = "/api/lol/{}/v2.2/matchlist/by-summoner/{}".format(region, summonerId)
    matches = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_match_list_typename, summonerId, matches, meta_data)

    return matches
