import bottle

import merakikernel.riotapi.teamapi
import merakikernel.common


@bottle.route("/api/lol/<region>/v2.4/team/by-summoner/<summonerIds>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def summoner_teams(region, summonerIds):
    try:
        return merakikernel.riotapi.teamapi.summoner_teams(region, summonerIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)


@bottle.route("/api/lol/<region>/v2.4/team/<teamIds>", method=["GET", "OPTIONS"])
@merakikernel.common.riot_endpoint
def team(region, teamIds):
    try:
        return merakikernel.riotapi.teamapi.team(region, teamIds, dict(bottle.request.query))
    except ValueError:
        bottle.abort(400)
