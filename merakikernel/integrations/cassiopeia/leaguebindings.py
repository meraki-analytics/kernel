import merakikernel.riotapi.leagueapi
import cassiopeia.type.dto.league
import cassiopeia.dto.leagueapi
import cassiopeia.dto.requests

def get_leagues_by_summoner(summoner_ids):
    region = cassiopeia.dto.requests.region
    summoner_ids = ",".join([str(id_) for id_ in summoner_ids]) if isinstance(summoner_ids, list) else str(summoner_ids)
    response = merakikernel.riotapi.leagueapi.leagues_summoner(region, summoner_ids)

    for id_, leagues in response.items():
        for i, league in enumerate(leagues):
            leagues[i] = cassiopeia.type.dto.league.League(league)

    return response

def get_league_entries_by_summoner(summoner_ids):
    region = cassiopeia.dto.requests.region
    summoner_ids = ",".join([str(id_) for id_ in summoner_ids]) if isinstance(summoner_ids, list) else str(summoner_ids)
    response = merakikernel.riotapi.leagueapi.league_entries_summoner(region, summoner_ids)

    for id_, leagues in response.items():
        for i, league in enumerate(leagues):
            leagues[i] = cassiopeia.type.dto.league.League(league)

    return response

def get_leagues_by_team(team_ids):
    region = cassiopeia.dto.requests.region
    team_ids = ",".join(team_ids) if isinstance(team_ids, list) else team_ids
    response = merakikernel.riotapi.leagueapi.leagues_team(region, team_ids)

    for id_, leagues in response.items():
        response[id_] = [cassiopeia.type.dto.league.League(league) for league in leagues]

    return response

def get_league_entries_by_team(team_ids):
    region = cassiopeia.dto.requests.region
    team_ids = ",".join(team_ids) if isinstance(team_ids, list) else team_ids
    response = merakikernel.riotapi.leagueapi.league_entries_team(region, team_ids)

    for id_, leagues in response.items():
        response[id_] = [cassiopeia.type.dto.league.League(league) for league in leagues]

    return response

def get_challenger(queue_type):
    region = cassiopeia.dto.requests.region
    return cassiopeia.type.dto.league.League(merakikernel.riotapi.leagueapi.challenger(region, {"type": queue_type}))

def get_master(queue_type):
    region = cassiopeia.dto.requests.region
    return cassiopeia.type.dto.league.League(merakikernel.riotapi.leagueapi.master(region, {"type": queue_type}))


def patch_api():
    get_leagues_by_summoner.__doc__ = cassiopeia.dto.leagueapi.get_leagues_by_summoner.__doc__
    cassiopeia.dto.leagueapi.get_leagues_by_summoner = get_leagues_by_summoner

    get_league_entries_by_summoner.__doc__ = cassiopeia.dto.leagueapi.get_league_entries_by_summoner.__doc__
    cassiopeia.dto.leagueapi.get_league_entries_by_summoner = get_league_entries_by_summoner

    get_leagues_by_team.__doc__ = cassiopeia.dto.leagueapi.get_leagues_by_team.__doc__
    cassiopeia.dto.leagueapi.get_leagues_by_team = get_leagues_by_team

    get_league_entries_by_team.__doc__ = cassiopeia.dto.leagueapi.get_league_entries_by_team.__doc__
    cassiopeia.dto.leagueapi.get_league_entries_by_team = get_league_entries_by_team

    get_challenger.__doc__ = cassiopeia.dto.leagueapi.get_challenger.__doc__
    cassiopeia.dto.leagueapi.get_challenger = get_challenger

    get_master.__doc__ = cassiopeia.dto.leagueapi.get_master.__doc__
    cassiopeia.dto.leagueapi.get_master = get_master