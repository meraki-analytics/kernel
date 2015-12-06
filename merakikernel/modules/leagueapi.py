import bottle

import merakikernel.apiproxy.leagueapi
import merakikernel.common

@bottle.get("/api/lol/<region>/v2.5/league/by-summoner/<summonerIds>")
@merakikernel.common.riot_endpoint
def leagues_summoner(region, summonerIds):
    try:
        return merakikernel.apiproxy.leagueapi.leagues_summoner(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.get("/api/lol/<region>/v2.5/league/by-summoner/<summonerIds>/entry")
@merakikernel.common.riot_endpoint
def league_entries_summoner(region, summonerIds):
    try:
        return merakikernel.apiproxy.leagueapi.league_entries_summoner(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.get("/api/lol/<region>/v2.5/league/by-team/<teamIds>")
@merakikernel.common.riot_endpoint
def leagues_team(region, teamIds):
    try:
        return merakikernel.apiproxy.leagueapi.leagues_team(region, teamIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.get("/api/lol/<region>/v2.5/league/by-team/<teamIds>/entry")
@merakikernel.common.riot_endpoint
def league_entries_team(region, teamIds):
    try:
        return merakikernel.apiproxy.leagueapi.league_entries_team(region, teamIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.get("/api/lol/<region>/v2.5/league/challenger")
@merakikernel.common.riot_endpoint
def challenger(region):
    return merakikernel.apiproxy.leagueapi.challenger(region, dict(bottle.request.query))


@bottle.get("/api/lol/<region>/v2.5/league/master")
@merakikernel.common.riot_endpoint
def master(region):
    return merakikernel.apiproxy.leagueapi.master(region, dict(bottle.request.query))