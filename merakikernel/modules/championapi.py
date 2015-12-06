import bottle

import merakikernel.riotapi.championapi
import merakikernel.common

@bottle.get("/api/lol/<region>/v1.2/champion")
@merakikernel.common.riot_endpoint
def champion(region):
    return merakikernel.riotapi.championapi.champion(region, dict(bottle.request.query))


@bottle.get("/api/lol/<region>/v1.2/champion/<id>")
@merakikernel.common.riot_endpoint
def champion_id(region, id):
    return merakikernel.riotapi.championapi.champion_id(region, id, dict(bottle.request.query))