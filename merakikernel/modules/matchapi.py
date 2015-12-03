import bottle

import merakikernel.rediscache
import merakikernel.requests
import merakikernel.common

_match_typename = "Match"

@bottle.get("/api/lol/<region>/v2.2/match/<matchId>")
@merakikernel.common.riot_endpoint
def match(region, matchId):
    params           = dict(bottle.request.query)
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