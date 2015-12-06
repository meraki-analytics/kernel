import bottle

import merakikernel.riotapi.matchapi
import merakikernel.common

@bottle.get("/api/lol/<region>/v2.2/match/<matchId>")
@merakikernel.common.riot_endpoint
def match(region, matchId):
    return merakikernel.riotapi.matchapi.match(region, matchId, dict(bottle.request.query))