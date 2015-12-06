import merakikernel.rediscache
import merakikernel.requests

_match_typename = "Match"


def match(region, matchId, params={}):
    include_timeline = params.get("includeTimeline", "false").lower() == "true"
    region           = region.lower()

    match           = merakikernel.rediscache.get_value(_match_typename, matchId, region)
    stored_timeline = merakikernel.rediscache.get_type_datum(_match_typename, matchId, region)
    
    if match:
        if stored_timeline == include_timeline:
            return match
        elif stored_timeline:
            del match["timeline"]
            return match

    url   = "/api/lol/{}/v2.2/match/{}".format(region, matchId)
    match = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_match_typename, matchId, match, region)
    merakikernel.rediscache.put_type_datum(_match_typename, matchId, include_timeline, region)

    return match