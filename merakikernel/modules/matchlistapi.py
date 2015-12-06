import bottle

import merakikernel.riotapi.matchlistapi
import merakikernel.common

@bottle.route("/api/lol/<region>/v2.2/matchlist/by-summoner/<summonerId>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def match_list(region, summonerId):
    return merakikernel.riotapi.matchlistapi.match_list(region, summonerId, dict(bottle.request.query))