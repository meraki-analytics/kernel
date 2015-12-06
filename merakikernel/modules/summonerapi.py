import bottle

import merakikernel.apiproxy.summonerapi
import merakikernel.common

@bottle.get("/api/lol/<region>/v1.4/summoner/by-name/<summonerNames>")
@merakikernel.common.riot_endpoint
def summoners_by_name(region, summonerNames):
    try:
        return merakikernel.apiproxy.summonerapi.summoners_by_name(region, summonerNames, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.get("/api/lol/<region>/v1.4/summoner/<summonerIds>")
@merakikernel.common.riot_endpoint
def summoners_by_id(region, summonerIds):
    try:
        return merakikernel.apiproxy.summonerapi.summoners_by_id(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.get("/api/lol/<region>/v1.4/summoner/<summonerIds>/masteries")
@merakikernel.common.riot_endpoint
def summoner_masteries(region, summonerIds):
    try:
        return merakikernel.apiproxy.summonerapi.summoner_masteries(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.get("/api/lol/<region>/v1.4/summoner/<summonerIds>/runes")
@merakikernel.common.riot_endpoint
def summoner_runes(region, summonerIds):
    try:
        return merakikernel.apiproxy.summonerapi.summoner_runes(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.get("/api/lol/<region>/v1.4/summoner/<summonerIds>/name")
@merakikernel.common.riot_endpoint
def summoner_names(region, summonerIds):
    try:
        return merakikernel.apiproxy.summonerapi.summoner_names(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)