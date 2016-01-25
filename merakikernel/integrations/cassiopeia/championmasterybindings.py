import merakikernel.riotapi.championmasteryapi
import cassiopeia.type.dto.championmastery
import cassiopeia.dto.championmasteryapi
import cassiopeia.dto.requests


_region_platforms = {region: platform for platform, region in merakikernel.riotapi.common.platform_regions.items()}


def get_champion_mastery(summoner_id, champion_id):
    platform = _region_platforms[cassiopeia.dto.requests.region]
    return cassiopeia.type.dto.championmastery.ChampionMastery(merakikernel.riotapi.championmasteryapi.champion_mastery(platform, str(summoner_id), str(champion_id)))


def get_champion_masteries(summoner_id):
    platform = _region_platforms[cassiopeia.dto.requests.region]
    return [cassiopeia.type.dto.championmastery.ChampionMastery(cm) for cm in merakikernel.riotapi.championmasteryapi.champion_masteries(platform, str(summoner_id))]


def get_champion_mastery_score(summoner_id):
    platform = _region_platforms[cassiopeia.dto.requests.region]
    return [cassiopeia.type.dto.championmastery.ChampionMastery(cm) for cm in merakikernel.riotapi.championmasteryapi.champion_mastery_score(platform, str(summoner_id))]


def get_top_champion_masteries(summoner_id, count=3):
    platform = _region_platforms[cassiopeia.dto.requests.region]
    return cassiopeia.type.dto.championmastery.ChampionMastery(merakikernel.riotapi.championmasteryapi.top_champion_masteries(platform, str(summoner_id), str(count)))


def patch_api():
    get_champion_mastery.__doc__ = cassiopeia.dto.championmasteryapi.get_champion_mastery.__doc__
    cassiopeia.dto.championmasteryapi.get_champion_mastery = get_champion_mastery

    get_champion_masteries.__doc__ = cassiopeia.dto.championmasteryapi.get_champion_masteries.__doc__
    cassiopeia.dto.championmasteryapi.get_champion_masteries = get_champion_masteries

    get_champion_mastery_score.__doc__ = cassiopeia.dto.championmasteryapi.get_champion_mastery_score.__doc__
    cassiopeia.dto.championmasteryapi.get_champion_mastery_score = get_champion_mastery_score

    get_top_champion_masteries.__doc__ = cassiopeia.dto.championmasteryapi.get_top_champion_masteries.__doc__
    cassiopeia.dto.championmasteryapi.get_top_champion_masteries = get_top_champion_masteries
