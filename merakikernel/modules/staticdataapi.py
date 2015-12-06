import bottle

import merakikernel.riotapi.staticdataapi
import merakikernel.common

######################
# Champion Endpoints #
######################

@bottle.get("/api/lol/static-data/<region>/v1.2/champion")
@merakikernel.common.riot_endpoint
def champion(region):
    return merakikernel.riotapi.staticdataapi.champion(region, dict(bottle.request.query))


@bottle.get("/api/lol/static-data/<region>/v1.2/champion/<id>")
@merakikernel.common.riot_endpoint
def champion_id(region, id):
    return merakikernel.riotapi.staticdataapi.champion_id(region, id, dict(bottle.request.query))

##################
# Item Endpoints #
##################

@bottle.get("/api/lol/static-data/<region>/v1.2/item")
@merakikernel.common.riot_endpoint
def item(region):
    return merakikernel.riotapi.staticdataapi.item(region, dict(bottle.request.query))


@bottle.get("/api/lol/static-data/<region>/v1.2/item/<id>")
@merakikernel.common.riot_endpoint
def item_id(region, id):
    return merakikernel.riotapi.staticdataapi.item_id(region, id, dict(bottle.request.query))

######################
# Language Endpoints #
######################

@bottle.get("/api/lol/static-data/<region>/v1.2/language-strings")
@merakikernel.common.riot_endpoint
def language_strings(region):
    return merakikernel.riotapi.staticdataapi.language_strings(region, dict(bottle.request.query))


@bottle.get("/api/lol/static-data/<region>/v1.2/languages")
@merakikernel.common.riot_endpoint
def languages(region):
    return merakikernel.riotapi.staticdataapi.languages(region, dict(bottle.request.query))

################
# Map Endpoint #
################

@bottle.get("/api/lol/static-data/<region>/v1.2/map")
@merakikernel.common.riot_endpoint
def maps(region):
    return merakikernel.riotapi.staticdataapi.maps(region, dict(bottle.request.query))

#####################
# Mastery Endpoints #
#####################

@bottle.get("/api/lol/static-data/<region>/v1.2/mastery")
@merakikernel.common.riot_endpoint
def mastery(region):
    return merakikernel.riotapi.staticdataapi.mastery(region, dict(bottle.request.query))


@bottle.get("/api/lol/static-data/<region>/v1.2/mastery/<id>")
@merakikernel.common.riot_endpoint
def mastery_id(region, id):
    return merakikernel.riotapi.staticdataapi.mastery_id(region, id, dict(bottle.request.query))

##################
# Realm Endpoint #
##################

@bottle.get("/api/lol/static-data/<region>/v1.2/realm")
@merakikernel.common.riot_endpoint
def realm(region):
    return merakikernel.riotapi.staticdataapi.realm(region, dict(bottle.request.query))

##################
# Rune Endpoints #
##################

@bottle.get("/api/lol/static-data/<region>/v1.2/rune")
@merakikernel.common.riot_endpoint
def rune(region):
    return merakikernel.riotapi.staticdataapi.rune(region, dict(bottle.request.query))


@bottle.get("/api/lol/static-data/<region>/v1.2/rune/<id>")
@merakikernel.common.riot_endpoint
def rune_id(region, id):
    return merakikernel.riotapi.staticdataapi.rune_id(region, id, dict(bottle.request.query))

############################
# Summoner Spell Endpoints #
############################

@bottle.get("/api/lol/static-data/<region>/v1.2/summoner-spell")
@merakikernel.common.riot_endpoint
def summoner_spell(region):
    return merakikernel.riotapi.staticdataapi.summoner_spell(region, dict(bottle.request.query))


@bottle.get("/api/lol/static-data/<region>/v1.2/summoner-spell/<id>")
@merakikernel.common.riot_endpoint
def summoner_spell_id(region, id):
    return merakikernel.riotapi.staticdataapi.summoner_spell_id(region, id, dict(bottle.request.query))

#####################
# Versions Endpoint #
#####################

@bottle.get("/api/lol/static-data/<region>/v1.2/versions")
@merakikernel.common.riot_endpoint
def versions(region):
    return merakikernel.riotapi.staticdataapi.versions(region, dict(bottle.request.query))