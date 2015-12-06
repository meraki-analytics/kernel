import merakikernel.rediscache
import merakikernel.requests

_team_typename = "Team"


def summoner_teams(region, summonerIds, params={}):
    region       = region.lower()
    summoner_ids = summonerIds.split(",")

    # 10 summoners max
    if len(summoner_ids) > 10:
        raise ValueError()

    team_ids = merakikernel.rediscache.get_type_data(_team_typename, summoner_ids, region)

    teams   = {}
    missing = []
    for i in range(len(summoner_ids)):
        if team_ids[i]:
            teams[summoner_ids[i]] = merakikernel.rediscache.get_values(_team_typename, team_ids[i], region)
        else:
            missing.append(summoner_ids[i])

    if missing:
        url       = "/api/lol/{}/v2.4/team/by-summoner/{}".format(region, ",".join(missing))
        new_teams = merakikernel.requests.get(region, url, params)

        teams.update(new_teams)

        ids = set()
        tms = []
        for summoner_id, s_teams in new_teams.items():
            merakikernel.rediscache.put_type_datum(_team_typename, summoner_id, [team["fullId"] for team in s_teams], region)
            for team in s_teams:
                if not team["fullId"] in ids:
                    ids.add(team["fullId"])
                    tms.append(team)
        merakikernel.rediscache.put_values(_team_typename, [team["fullId"] for team in tms], tms, region)

    return teams


def team(region, teamIds, params={}):
    region   = region.lower()
    team_ids = teamIds.split(",")

    # 10 teams max
    if len(team_ids) > 10:
        raise ValueError()

    teams = merakikernel.rediscache.get_values(_team_typename, team_ids, region)

    missing = []
    loc     = []
    for i in range(len(team_ids)):
        if not teams[i]:
            missing.append(team_ids[i])
            loc.append(i)

    if missing:
        url       = "/api/lol/{}/v2.4/team/{}".format(region, ",".join(missing))
        new_teams = merakikernel.requests.get(region, url, params)

        for i in range(len(missing)):
            teams[loc[i]] = new_teams.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_teams.items())]
        merakikernel.rediscache.put_values(_team_typename, unzipped[0], unzipped[1], region)
        
    return {team_ids[i]: teams[i] for i in range(len(teams)) if teams[i]}