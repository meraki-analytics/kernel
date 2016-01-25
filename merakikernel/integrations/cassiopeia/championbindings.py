import merakikernel.riotapi.championapi
import cassiopeia.type.dto.champion
import cassiopeia.dto.championapi
import cassiopeia.dto.requests


def get_champion_status(id_):
    region = cassiopeia.dto.requests.region
    return cassiopeia.type.dto.champion.Champion(merakikernel.riotapi.championapi.champion_id(region, str(id_)))


def get_champion_statuses(freeToPlay=False):
    region = cassiopeia.dto.requests.region
    return cassiopeia.type.dto.champion.ChampionList(merakikernel.riotapi.championapi.champion(region, {"freeToPlay": str(freeToPlay)}))


def patch_api():
    get_champion_status.__doc__ = cassiopeia.dto.championapi.get_champion_status.__doc__
    cassiopeia.dto.championapi.get_champion_status = get_champion_status

    get_champion_statuses.__doc__ = cassiopeia.dto.championapi.get_champion_statuses.__doc__
    cassiopeia.dto.championapi.get_champion_statuses = get_champion_statuses
