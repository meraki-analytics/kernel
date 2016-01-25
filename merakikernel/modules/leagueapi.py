import bottle

import merakikernel.riotapi.leagueapi
import merakikernel.common


@bottle.route("/api/lol/<region>/v2.5/league/by-summoner/<summonerIds>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def leagues_summoner(region, summonerIds):
    try:
        return merakikernel.riotapi.leagueapi.leagues_summoner(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.route("/api/lol/<region>/v2.5/league/by-summoner/<summonerIds>/entry", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def league_entries_summoner(region, summonerIds):
    try:
        return merakikernel.riotapi.leagueapi.league_entries_summoner(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.route("/api/lol/<region>/v2.5/league/by-team/<teamIds>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def leagues_team(region, teamIds):
    try:
        return merakikernel.riotapi.leagueapi.leagues_team(region, teamIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.route("/api/lol/<region>/v2.5/league/by-team/<teamIds>/entry", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def league_entries_team(region, teamIds):
    try:
        return merakikernel.riotapi.leagueapi.league_entries_team(region, teamIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.route("/api/lol/<region>/v2.5/league/challenger", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def challenger(region):
    return merakikernel.riotapi.leagueapi.challenger(region, dict(bottle.request.query))


@bottle.route("/api/lol/<region>/v2.5/league/master", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def master(region):
    return merakikernel.riotapi.leagueapi.master(region, dict(bottle.request.query))
