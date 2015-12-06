import merakikernel.riotapi.summonerapi
import cassiopeia.type.dto.summoner
import cassiopeia.dto.summonerapi
import cassiopeia.dto.requests

def get_summoners_by_name(summoner_names):
    region = cassiopeia.dto.requests.region
    summoner_names = ",".join(summoner_names) if isinstance(summoner_names, list) else summoner_names
    response = merakikernel.riotapi.summonerapi.summoners_by_name(region, summoner_names)

    for name, summoner in response.items():
        response[name] = cassiopeia.type.dto.summoner.Summoner(summoner)

    return response

def get_summoners_by_id(summoner_ids):
    region = cassiopeia.dto.requests.region
    summoner_ids = ",".join([str(id_) for id_ in summoner_ids]) if isinstance(summoner_ids, list) else str(summoner_ids)
    response = merakikernel.riotapi.summonerapi.summoners_by_id(region, summoner_ids)

    for id_, summoner in response.items():
        response[id_] = cassiopeia.type.dto.summoner.Summoner(summoner)

    return response

def get_summoner_masteries(summoner_ids):
    region = cassiopeia.dto.requests.region
    summoner_ids = ",".join([str(id_) for id_ in summoner_ids]) if isinstance(summoner_ids, list) else str(summoner_ids)
    response = merakikernel.riotapi.summonerapi.summoner_masteries(region, summoner_ids)

    for id_, masteries in response.items():
        response[id_] = cassiopeia.type.dto.summoner.MasteryPages(masteries)

    return response

def get_summoner_names(summoner_ids):
    region = cassiopeia.dto.requests.region
    summoner_ids = ",".join([str(id_) for id_ in summoner_ids]) if isinstance(summoner_ids, list) else str(summoner_ids)

    return merakikernel.riotapi.summonerapi.summoner_names(region, summoner_ids)

def get_summoner_runes(summoner_ids):
    region = cassiopeia.dto.requests.region
    summoner_ids = ",".join([str(id_) for id_ in summoner_ids]) if isinstance(summoner_ids, list) else str(summoner_ids)
    response = merakikernel.riotapi.summonerapi.summoner_runes(region, summoner_ids)

    for id_, runes in response.items():
        response[id_] = cassiopeia.type.dto.summoner.RunePages(runes)

    return response


def patch_api():
    get_summoners_by_name.__doc__ = cassiopeia.dto.summonerapi.get_summoners_by_name.__doc__
    cassiopeia.dto.summonerapi.get_summoners_by_name = get_summoners_by_name

    get_summoners_by_id.__doc__ = cassiopeia.dto.summonerapi.get_summoners_by_id.__doc__
    cassiopeia.dto.summonerapi.get_summoners_by_id = get_summoners_by_id

    get_summoner_masteries.__doc__ = cassiopeia.dto.summonerapi.get_summoner_masteries.__doc__
    cassiopeia.dto.summonerapi.get_summoner_masteries = get_summoner_masteries

    get_summoner_names.__doc__ = cassiopeia.dto.summonerapi.get_summoner_names.__doc__
    cassiopeia.dto.summonerapi.get_summoner_names = get_summoner_names

    get_summoner_runes.__doc__ = cassiopeia.dto.summonerapi.get_summoner_runes.__doc__
    cassiopeia.dto.summonerapi.get_summoner_runes = get_summoner_runes