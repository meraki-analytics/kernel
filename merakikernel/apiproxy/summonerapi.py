import merakikernel.rediscache
import merakikernel.requests
import merakikernel.common

_summoner_typename           = "Summoner"
_summoner_masteries_typename = "SummonerMasteries"
_summoner_runes_typename     = "SummonerRunes"
_summoner_name_typename      = "SummonerName"


def summoners_by_name(region, summonerNames, params={}):
    region = region.lower()
    names  = [merakikernel.common.standardize_name(name) for name in summonerNames.split(",")]

    # 40 summoners max
    if len(names) > 40:
        raise ValueError()

    summoner_ids = merakikernel.rediscache.get_type_data(_summoner_typename, names, region)
    summoners    = merakikernel.rediscache.get_values(_summoner_typename, summoner_ids, region)

    missing = []
    loc     = []
    for i in range(len(summoner_ids)):
        if not summoners[i]:
            missing.append(names[i])
            loc.append(i)

    if missing:
        url           = "/api/lol/{}/v1.4/summoner/by-name/{}".format(region, ",".join(missing))
        new_summoners = merakikernel.requests.get(region, url, params)

        for i in range(len(missing)):
            summoners[loc[i]] = new_summoners.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_summoners.items())]
        ids = [summoner["id"] for summoner in unzipped[1]]
        merakikernel.rediscache.put_type_data(_summoner_typename, unzipped[0], ids, region)
        merakikernel.rediscache.put_values(_summoner_typename, ids, unzipped[1], region)
        
    return {names[i]: summoners[i] for i in range(len(summoners)) if summoners[i]}


def summoners_by_id(region, summonerIds, params={}):
    region       = region.lower()
    summoner_ids = summonerIds.split(",")

    # 40 summoners max
    if len(summoner_ids) > 40:
        raise ValueError()

    summoners = merakikernel.rediscache.get_values(_summoner_typename, summoner_ids, region)

    missing = []
    loc     = []
    for i in range(len(summoner_ids)):
        if not summoners[i]:
            missing.append(summoner_ids[i])
            loc.append(i)

    if missing:
        url           = "/api/lol/{}/v1.4/summoner/{}".format(region, ",".join(missing))
        new_summoners = merakikernel.requests.get(region, url, params)

        for i in range(len(missing)):
            summoners[loc[i]] = new_summoners.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_summoners.items())]
        merakikernel.rediscache.put_type_data(_summoner_typename, [merakikernel.common.standardize_name(summoner["name"]) for summoner in unzipped[1]], unzipped[0], region)
        merakikernel.rediscache.put_values(_summoner_typename, unzipped[0], unzipped[1], region)
        
    return {summoner_ids[i]: summoners[i] for i in range(len(summoners)) if summoners[i]}


def summoner_masteries(region, summonerIds, params={}):
    region       = region.lower()
    summoner_ids = summonerIds.split(",")

    # 40 summoners max
    if len(summoner_ids) > 40:
        raise ValueError()

    masteries = merakikernel.rediscache.get_values(_summoner_masteries_typename, summoner_ids, region)

    missing = []
    loc     = []
    for i in range(len(summoner_ids)):
        if not masteries[i]:
            missing.append(summoner_ids[i])
            loc.append(i)

    if missing:
        url           = "/api/lol/{}/v1.4/summoner/{}/masteries".format(region, ",".join(missing))
        new_masteries = merakikernel.requests.get(region, url, params)

        for i in range(len(missing)):
            masteries[loc[i]] = new_masteries.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_masteries.items())]
        merakikernel.rediscache.put_values(_summoner_masteries_typename, unzipped[0], unzipped[1], region)
        
    return {summoner_ids[i]: masteries[i] for i in range(len(masteries)) if masteries[i]}


def summoner_runes(region, summonerIds, params={}):
    region       = region.lower()
    summoner_ids = summonerIds.split(",")

    # 40 summoners max
    if len(summoner_ids) > 40:
        raise ValueError()

    runes = merakikernel.rediscache.get_values(_summoner_runes_typename, summoner_ids, region)

    missing = []
    loc     = []
    for i in range(len(summoner_ids)):
        if not runes[i]:
            missing.append(summoner_ids[i])
            loc.append(i)

    if missing:
        url       = "/api/lol/{}/v1.4/summoner/{}/runes".format(region, ",".join(missing))
        new_runes = merakikernel.requests.get(region, url, params)

        for i in range(len(missing)):
            runes[loc[i]] = new_runes.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_runes.items())]
        merakikernel.rediscache.put_values(_summoner_runes_typename, unzipped[0], unzipped[1], region)
        
    return {summoner_ids[i]: runes[i] for i in range(len(runes)) if runes[i]}


def summoner_names(region, summonerIds, params={}):
    # Summoner Name endpoint is currently cached entirely separately from Summoner endpoints to allow for different expiration times for each.
    # I feel this satisfies more use cases than trying to combine the caches.
    region       = region.lower()
    summoner_ids = summonerIds.split(",")

    # 40 summoners max
    if len(summoner_ids) > 40:
        raise ValueError()

    names = merakikernel.rediscache.get_values(_summoner_name_typename, summoner_ids, region)

    missing = []
    loc     = []
    for i in range(len(summoner_ids)):
        if not names[i]:
            missing.append(summoner_ids[i])
            loc.append(i)

    if missing:
        url       = "/api/lol/{}/v1.4/summoner/{}/name".format(region, ",".join(missing))
        new_names = merakikernel.requests.get(region, url, params)

        for i in range(len(missing)):
            names[loc[i]] = new_names.get(missing[i], None)

        unzipped = [list(t) for t in zip(*new_names.items())]
        merakikernel.rediscache.put_values(_summoner_name_typename, unzipped[0], unzipped[1], region)
        
    return {summoner_ids[i]: names[i] for i in range(len(names)) if names[i]}