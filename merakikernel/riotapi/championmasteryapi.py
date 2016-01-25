import merakikernel.rediscache
import merakikernel.requests
import merakikernel.riotapi.common


_champion_mastery_typename = "ChampionMastery"
_mastery_score_typename = "MasteryScore"
_max_champs_in_lol = 10000  # Rito help us if this is exceeded


def _get_champion_mastery_meta(region, summonerId):
    return "|".join([region, summonerId])


def champion_mastery(platformId, summonerId, championId, params={}):
    region = merakikernel.riotapi.common.platform_regions[platformId.upper()]
    meta_data = _get_champion_mastery_meta(region, summonerId)

    mastery = merakikernel.rediscache.get_value(_champion_mastery_typename, championId, meta_data)

    if mastery:
        return mastery

    url = "/championmastery/location/{}/player/{}/champion/{}".format(platformId, summonerId, championId)
    mastery = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_champion_mastery_typename, championId, mastery, meta_data)

    return mastery


def champion_masteries(platformId, summonerId, params={}):
    region = merakikernel.riotapi.common.platform_regions[platformId.upper()]
    meta_data = _get_champion_mastery_meta(region, summonerId)

    masteries = merakikernel.rediscache.get_all_values(_champion_mastery_typename, meta_data)

    if masteries:
        masteries.sort(key=lambda mastery: mastery["championPoints"], reverse=True)
        return masteries

    url = "/championmastery/location/{}/player/{}/champions".format(platformId, summonerId)
    masteries = merakikernel.requests.get(region, url, params)

    keys = []
    values = []
    for mastery in masteries:
        keys.append(str(mastery["championId"]))
        values.append(mastery)

    merakikernel.rediscache.put_type_datum(_champion_mastery_typename, "count", _max_champs_in_lol, meta_data)
    merakikernel.rediscache.put_values(_champion_mastery_typename, keys, values, meta_data, True)

    masteries.sort(key=lambda mastery: mastery["championPoints"], reverse=True)
    return masteries


def champion_mastery_score(platformId, summonerId, params={}):
    region = merakikernel.riotapi.common.platform_regions[platformId.upper()]

    score = merakikernel.rediscache.get_value(_mastery_score_typename, summonerId, region)

    if score:
        return score

    url = "/championmastery/location/{}/player/{}/score".format(platformId, summonerId)
    score = merakikernel.requests.get(region, url, params)

    merakikernel.rediscache.put_value(_mastery_score_typename, summonerId, score, region)

    return score


def top_champion_masteries(platformId, summonerId, params={}):
    region = merakikernel.riotapi.common.platform_regions[platformId.upper()]
    count = int(params.get("count", 3))
    meta_data = _get_champion_mastery_meta(region, summonerId)

    stored_count = merakikernel.rediscache.get_type_datum(_champion_mastery_typename, "count", meta_data)
    stored_count = stored_count if stored_count else 0

    if stored_count == _max_champs_in_lol:
        masteries = merakikernel.rediscache.get_all_values(_champion_mastery_typename, meta_data)
        masteries.sort(key=lambda mastery: mastery["championPoints"], reverse=True)
        return masteries[:count]
    elif stored_count >= count:
        keys = merakikernel.rediscache.get_type_datum(_champion_mastery_typename, "keys", meta_data)
        masteries = merakikernel.rediscache.get_values(_champion_mastery_typename, keys, meta_data)
        masteries.sort(key=lambda mastery: mastery["championPoints"], reverse=True)
        return masteries[:count]

    url = "/championmastery/location/{}/player/{}/topchampions".format(platformId, summonerId)
    masteries = merakikernel.requests.get(region, url, params)

    keys = [str(mastery["championId"]) for mastery in masteries]

    merakikernel.rediscache.put_type_datum(_champion_mastery_typename, "count", count, meta_data)
    merakikernel.rediscache.put_type_datum(_champion_mastery_typename, "keys", keys, meta_data)
    merakikernel.rediscache.put_values(_champion_mastery_typename, keys, masteries, meta_data)

    return masteries
