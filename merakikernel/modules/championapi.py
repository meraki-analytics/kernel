import bottle

import merakikernel.riotapi.championapi
import merakikernel.common

@bottle.route("/api/lol/<region>/v1.2/champion", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def champion(region):
    return merakikernel.riotapi.championapi.champion(region, dict(bottle.request.query))


@bottle.route("/api/lol/<region>/v1.2/champion/<id>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def champion_id(region, id):
    return merakikernel.riotapi.championapi.champion_id(region, id, dict(bottle.request.query))