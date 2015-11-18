import bottle

import merakikernel.rediscache
import merakikernel.requests
import merakikernel.common

_leagues_typename        = "Leagues"
_league_entries_typename = "LeagueEntries"

@bottle.route("/api/lol/<region>/v2.5/league/by-summoner/<summonerIds>", method="GET")
@merakikernel.common.forward_errors
def leagues_summoner(region, summonerIds):
    ids = summonerIds.split(",")

    # 10 summoners max
    if len(ids) > 10:
        bottle.abort(400)

    leagues = merakikernel.rediscache.get_values(_leagues_typename, ids, region)
    missing = []
    loc = []
    for i in range(len(ids)):
        if not leagues[i]:
            missing.append(ids[i])
            loc.append(i)

    if missing:
        url = "/api/lol/{}/v2.5/league/by-summoner/{}".format(region, ",".join(missing))
        new_leagues = merakikernel.requests.get(region, url, dict(bottle.request.query))

        for i in range(len(missing)):
            leagues[loc[i]] = new_leagues.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_leagues.items())]
        merakikernel.rediscache.put_values(_leagues_typename, unzipped[0], unzipped[1], region)

    return {ids[i]: leagues[i] for i in range(len(ids)) if leagues[i]}


@bottle.route("/api/lol/<region>/v2.5/league/by-summoner/<summonerIds>/entry", method="GET")
@merakikernel.common.forward_errors
def league_entries_summoner(region, summonerIds):
    ids = summonerIds.split(",")

    # 10 summoners max
    if len(ids) > 10:
        bottle.abort(400)

    leagues = merakikernel.rediscache.get_values(_league_entries_typename, ids, region)
    missing = []
    loc = []
    for i in range(len(ids)):
        if not leagues[i]:
            missing.append(ids[i])
            loc.append(i)

    if missing:
        url = "/api/lol/{}/v2.5/league/by-summoner/{}/entry".format(region, ",".join(missing))
        new_leagues = merakikernel.requests.get(region, url, dict(bottle.request.query))

        for i in range(len(missing)):
            leagues[loc[i]] = new_leagues.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_leagues.items())]
        merakikernel.rediscache.put_values(_league_entries_typename, unzipped[0], unzipped[1], region)

    return {ids[i]: leagues[i] for i in range(len(ids)) if leagues[i]}


@bottle.route("/api/lol/<region>/v2.5/league/by-team/<teamIds>", method="GET")
@merakikernel.common.forward_errors
def leagues_team(region, teamIds):
    ids = teamIds.split(",")

    # 10 teams max
    if len(ids) > 10:
        bottle.abort(400)

    leagues = merakikernel.rediscache.get_values(_leagues_typename, ids, region)
    missing = []
    loc = []
    for i in range(len(ids)):
        if not leagues[i]:
            missing.append(ids[i])
            loc.append(i)

    if missing:
        url = "/api/lol/{}/v2.5/league/by-team/{}".format(region, ",".join(missing))
        new_leagues = merakikernel.requests.get(region, url, dict(bottle.request.query))

        for i in range(len(missing)):
            leagues[loc[i]] = new_leagues.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_leagues.items())]
        merakikernel.rediscache.put_values(_leagues_typename, unzipped[0], unzipped[1], region)

    return {ids[i]: leagues[i] for i in range(len(ids)) if leagues[i]}


@bottle.route("/api/lol/<region>/v2.5/league/by-team/<teamIds>/entry", method="GET")
@merakikernel.common.forward_errors
def league_entries_team(region, teamIds):
    ids = teamIds.split(",")

    # 10 teams max
    if len(ids) > 10:
        bottle.abort(400)

    leagues = merakikernel.rediscache.get_values(_league_entries_typename, ids, region)
    missing = []
    loc = []
    for i in range(len(ids)):
        if not leagues[i]:
            missing.append(ids[i])
            loc.append(i)

    if missing:
        url = "/api/lol/{}/v2.5/league/by-team/{}/entry".format(region, ",".join(missing))
        new_leagues = merakikernel.requests.get(region, url, dict(bottle.request.query))

        for i in range(len(missing)):
            leagues[loc[i]] = new_leagues.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_leagues.items())]
        merakikernel.rediscache.put_values(_league_entries_typename, unzipped[0], unzipped[1], region)

    return {ids[i]: leagues[i] for i in range(len(ids)) if leagues[i]}


@bottle.route("/api/lol/<region>/v2.5/league/challenger", method="GET")
@merakikernel.common.forward_errors
def challenger(region):
    params = dict(bottle.request.query)
    key = "c[{}]".format(params.get("type", ""))

    challenger = merakikernel.rediscache.get_value(_leagues_typename, key, region)
    if challenger:
        return challenger

    url = "/api/lol/{}/v2.5/league/challenger".format(region)
    challenger = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_leagues_typename, key, challenger, region)

    return challenger


@bottle.route("/api/lol/<region>/v2.5/league/master", method="GET")
@merakikernel.common.forward_errors
def master(region):
    params = dict(bottle.request.query)
    key = "m[{}]".format(params.get("type", ""))

    master = merakikernel.rediscache.get_value(_leagues_typename, key, region)
    if master:
        return master

    url = "/api/lol/{}/v2.5/league/master".format(region)
    master = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_leagues_typename, key, master, region)

    return master