import bottle

import merakikernel.riotapi.staticdataapi
import merakikernel.common

######################
# Champion Endpoints #
######################

@bottle.route("/api/lol/static-data/<region>/v1.2/champion", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def champion(region):
    return merakikernel.riotapi.staticdataapi.champion(region, dict(bottle.request.query))


@bottle.route("/api/lol/static-data/<region>/v1.2/champion/<id>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def champion_id(region, id):
    return merakikernel.riotapi.staticdataapi.champion_id(region, id, dict(bottle.request.query))

##################
# Item Endpoints #
##################

@bottle.route("/api/lol/static-data/<region>/v1.2/item", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def item(region):
    return merakikernel.riotapi.staticdataapi.item(region, dict(bottle.request.query))


@bottle.route("/api/lol/static-data/<region>/v1.2/item/<id>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def item_id(region, id):
    return merakikernel.riotapi.staticdataapi.item_id(region, id, dict(bottle.request.query))

######################
# Language Endpoints #
######################

@bottle.route("/api/lol/static-data/<region>/v1.2/language-strings", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def language_strings(region):
    return merakikernel.riotapi.staticdataapi.language_strings(region, dict(bottle.request.query))


@bottle.route("/api/lol/static-data/<region>/v1.2/languages", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def languages(region):
    return merakikernel.riotapi.staticdataapi.languages(region, dict(bottle.request.query))

################
# Map Endpoint #
################

@bottle.route("/api/lol/static-data/<region>/v1.2/map", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def maps(region):
    return merakikernel.riotapi.staticdataapi.maps(region, dict(bottle.request.query))

#####################
# Mastery Endpoints #
#####################

@bottle.route("/api/lol/static-data/<region>/v1.2/mastery", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def mastery(region):
    return merakikernel.riotapi.staticdataapi.mastery(region, dict(bottle.request.query))


@bottle.route("/api/lol/static-data/<region>/v1.2/mastery/<id>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def mastery_id(region, id):
    return merakikernel.riotapi.staticdataapi.mastery_id(region, id, dict(bottle.request.query))

##################
# Realm Endpoint #
##################

@bottle.route("/api/lol/static-data/<region>/v1.2/realm", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def realm(region):
    return merakikernel.riotapi.staticdataapi.realm(region, dict(bottle.request.query))

##################
# Rune Endpoints #
##################

@bottle.route("/api/lol/static-data/<region>/v1.2/rune", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def rune(region):
    return merakikernel.riotapi.staticdataapi.rune(region, dict(bottle.request.query))


@bottle.route("/api/lol/static-data/<region>/v1.2/rune/<id>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def rune_id(region, id):
    return merakikernel.riotapi.staticdataapi.rune_id(region, id, dict(bottle.request.query))

############################
# Summoner Spell Endpoints #
############################

@bottle.route("/api/lol/static-data/<region>/v1.2/summoner-spell", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def summoner_spell(region):
    return merakikernel.riotapi.staticdataapi.summoner_spell(region, dict(bottle.request.query))


@bottle.route("/api/lol/static-data/<region>/v1.2/summoner-spell/<id>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def summoner_spell_id(region, id):
    return merakikernel.riotapi.staticdataapi.summoner_spell_id(region, id, dict(bottle.request.query))

#####################
# Versions Endpoint #
#####################

@bottle.route("/api/lol/static-data/<region>/v1.2/versions", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def versions(region):
    return merakikernel.riotapi.staticdataapi.versions(region, dict(bottle.request.query))