import bottle

import merakikernel.riotapi.championmasteryapi
import merakikernel.common


@bottle.route("/championmastery/location/<platformId>/player/<summonerId>/champion/<championId>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def champion_mastery(platformId, summonerId, championId):
    return merakikernel.riotapi.championmasteryapi.champion_mastery(platformId, summonerId, championId, dict(bottle.request.query))


@bottle.route("/championmastery/location/<platformId>/player/<summonerId>/champions", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def champion_masteries(platformId, summonerId):
    return merakikernel.riotapi.championmasteryapi.champion_masteries(platformId, summonerId, dict(bottle.request.query))


@bottle.route("/championmastery/location/<platformId>/player/<summonerId>/score", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def champion_mastery_score(platformId, summonerId):
    return merakikernel.riotapi.championmasteryapi.champion_mastery_score(platformId, summonerId, dict(bottle.request.query))


@bottle.route("/championmastery/location/<platformId>/player/<summonerId>/topchampions", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def top_champion_masteries(platformId, summonerId):
    return merakikernel.riotapi.championmasteryapi.top_champion_masteries(platformId, summonerId, dict(bottle.request.query))
