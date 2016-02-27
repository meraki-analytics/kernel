import merakikernel.rediscache
import merakikernel.requests


_match_typename = "Match"


def match(region, matchId, params={}):
    include_timeline = params.get("includeTimeline", "false").lower() == "true"
    region = region.lower()

    match = merakikernel.rediscache.get_value(_match_typename, matchId, region)
    stored_timeline = merakikernel.rediscache.get_type_datum(_match_typename, matchId, region)

    # The match was stored in the cache and the timeline data is present if requested, return the match
    if match:
        # If the timeline was stored and was requested, return it.
        if stored_timeline == include_timeline:
            return match
        # If the timeline was store but not requested, return the match without the timeline.
        elif stored_timeline:
            del match["timeline"]
            return match

    # Request the match from Riot
    url = "/api/lol/{}/v2.2/match/{}".format(region, matchId)
    match = merakikernel.requests.get(region, url, params)

    # Store the match in the redis cache
    merakikernel.rediscache.put_value(_match_typename, matchId, match, region)
    merakikernel.rediscache.put_type_datum(_match_typename, matchId, include_timeline, region)

    return match
