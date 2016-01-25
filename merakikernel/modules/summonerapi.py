import bottle

import merakikernel.riotapi.summonerapi
import merakikernel.common


@bottle.route("/api/lol/<region>/v1.4/summoner/by-name/<summonerNames>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def summoners_by_name(region, summonerNames):
    try:
        return merakikernel.riotapi.summonerapi.summoners_by_name(region, summonerNames, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.route("/api/lol/<region>/v1.4/summoner/<summonerIds>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def summoners_by_id(region, summonerIds):
    try:
        return merakikernel.riotapi.summonerapi.summoners_by_id(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.route("/api/lol/<region>/v1.4/summoner/<summonerIds>/masteries", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def summoner_masteries(region, summonerIds):
    try:
        return merakikernel.riotapi.summonerapi.summoner_masteries(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.route("/api/lol/<region>/v1.4/summoner/<summonerIds>/runes", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def summoner_runes(region, summonerIds):
    try:
        return merakikernel.riotapi.summonerapi.summoner_runes(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.route("/api/lol/<region>/v1.4/summoner/<summonerIds>/name", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def summoner_names(region, summonerIds):
    try:
        return merakikernel.riotapi.summonerapi.summoner_names(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)
