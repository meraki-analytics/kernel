import merakikernel.riotapi.teamapi
import cassiopeia.type.dto.team
import cassiopeia.dto.teamapi
import cassiopeia.dto.requests

def get_teams_by_summoner_id(summoner_ids):
    region = cassiopeia.dto.requests.region
    summoner_ids = ",".join([str(id_) for id_ in summoner_ids]) if isinstance(summoner_ids, list) else str(summoner_ids)
    response = merakikernel.riotapi.teamapi.summoner_teams(region, summoner_ids)

    for id_, teams in response.items():
        response[id_] = [cassiopeia.type.dto.team.Team(team) for team in teams]

    return response

def get_teams_by_id(team_ids):
    region = cassiopeia.dto.requests.region
    team_ids = ",".join(team_ids) if isinstance(team_ids, list) else team_ids
    response = merakikernel.riotapi.teamapi.team(region, team_ids)

    for id_, team in response.items():
        response[id_] = cassiopeia.type.dto.team.Team(team)

    return response


def patch_api():
    get_teams_by_summoner_id.__doc__ = cassiopeia.dto.teamapi.get_teams_by_summoner_id.__doc__
    cassiopeia.dto.teamapi.get_teams_by_summoner_id = get_teams_by_summoner_id

    get_teams_by_id.__doc__ = cassiopeia.dto.teamapi.get_teams_by_id.__doc__
    cassiopeia.dto.teamapi.get_teams_by_id = get_teams_by_id