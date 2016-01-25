import merakikernel.rediscache
import merakikernel.requests

_champion_typename = "Champion"
_item_typename = "Item"
_language_string_typename = "LanguageStrings"
_languages_typename = "Languages"
_maps_typename = "Maps"
_mastery_typename = "Mastery"
_realm_typename = "Realm"
_rune_typename = "Rune"
_summoner_spell_typename = "SummonerSpell"
_versions_typename = "Versions"

######################
# Champion Endpoints #
######################
_champ_data_options = {
    "allytips",
    "blurb",
    "enemytips",
    "image",
    "info",
    "lore",
    "partype",
    "passive",
    "recommended",
    "skins",
    "spells",
    "stats",
    "tags"
}


def _get_champion_meta(region, params):
    return "|".join([region.lower(), params.get("version", ""), params.get("locale", "")])


def _wrap_champions(champions, data_by_id, version, all_data):
    champion_list = {
        "data": {champion["key"] if not data_by_id else champion["id"]: champion for champion in champions},
        "type": "champion",
        "version": version
    }
    if all_data:
        champion_list["keys"] = {champion["id"]: champion["key"] for champion in champions}
        champion_list["format"] = "full"
    return champion_list


def champion(region, params={}):
    data_by_id = params.get("dataById", "false") == "true"
    champ_data = params.get("champData", "")
    champ_data = set(champ_data.split(",")) if champ_data else set()
    if _champ_data_options.issubset(champ_data) or "all" in champ_data:
        champ_data = {"all"}

    # See if we have the champions cached with the required champData fields
    meta_data = _get_champion_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_champion_typename, "data", meta_data)
    champions = merakikernel.rediscache.get_all_values(_champion_typename, meta_data)
    try:
        version = params["version"]
    except KeyError:
        version = merakikernel.rediscache.get_type_datum(_champion_typename, "version", meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if champions:
        all_stored = stored_data == {"all"}
        if all_stored and champ_data == {"all"}:
            return _wrap_champions(champions, data_by_id, version, all_stored)
        elif stored_data == {"all"} or champ_data.issubset(stored_data):
            extras = (_champ_data_options if all_stored else stored_data) ^ champ_data
            for field in extras:
                for champion in champions:
                    try:
                        del champion[field]
                    except KeyError:
                        pass
            return _wrap_champions(champions, data_by_id, version, all_stored)

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/champion".format(region)
    champions = merakikernel.requests.get(region, url, params, True)

    # Cache results
    keys = []
    values = []
    for champion in champions["data"].values():
        keys.append(champion["id"])
        values.append(champion)

    champ_data = list(champ_data)
    if "version" not in params:
        merakikernel.rediscache.put_type_datum(_champion_typename, "version", champions["version"], meta_data)
    merakikernel.rediscache.put_type_datum(_champion_typename, "data", champ_data, meta_data)
    merakikernel.rediscache.put_type_data(_champion_typename, keys, [champ_data for _ in keys], meta_data)
    merakikernel.rediscache.put_values(_champion_typename, keys, values, meta_data, True)

    return champions


def champion_id(region, id, params={}):
    champ_data = params.get("champData", "")
    champ_data = set(champ_data.split(",")) if champ_data else set()
    if _champ_data_options.issubset(champ_data) or "all" in champ_data:
        champ_data = {"all"}

    # See if we have the champion cached with the required champData fields
    meta_data = _get_champion_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_champion_typename, id, meta_data)
    champion = merakikernel.rediscache.get_value(_champion_typename, id, meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if champion:
        all_stored = stored_data == {"all"}
        if all_stored and champ_data == {"all"}:
            return champion
        elif all_stored or champ_data.issubset(stored_data):
            extras = (_champ_data_options if all_stored else stored_data) ^ champ_data
            for field in extras:
                try:
                    del champion[field]
                except KeyError:
                    pass
            return champion

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/champion/{}".format(region, id)
    champion = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_type_datum(_champion_typename, id, list(champ_data), meta_data)
    merakikernel.rediscache.put_value(_champion_typename, id, champion, meta_data)

    return champion

##################
# Item Endpoints #
##################
_item_data_options = {
    "colloq",
    "consumeOnFull",
    "consumed",
    "depth",
    "effect",
    "from",
    "gold",
    "hideFromAll",
    "image",
    "inStore",
    "into",
    "maps",
    "requiredChampion",
    "sanitizedDescription",
    "specialRecipe",
    "stacks",
    "stats",
    "tags"
}


def _get_item_meta(region, params):
    return "|".join([region.lower(), params.get("version", ""), params.get("locale", "")])


def _wrap_items(items, version, basic, groups=None, tree=None):
    item_list = {
        "data": {item["id"]: item for item in items},
        "type": "item",
        "version": version,
        "basic": basic
    }
    if groups:
        item_list["groups"] = groups
    if tree:
        item_list["tree"] = tree
    return item_list


def item(region, params={}):
    item_data = params.get("itemListData", "")
    item_data = set(item_data.split(",")) if item_data else set()
    add_groups = "groups" in item_data or "all" in item_data
    add_tree = "tree" in item_data or "all" in item_data
    if _item_data_options.issubset(item_data | {"groups", "tree"}) or "all" in item_data:
        item_data = {"all"}

    # See if we have the items cached with the required itemListData fields
    meta_data = _get_item_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_item_typename, "data", meta_data)
    basic = merakikernel.rediscache.get_type_datum(_item_typename, "basic", meta_data)
    groups = merakikernel.rediscache.get_type_datum(_item_typename, "groups", meta_data) if add_groups else None
    tree = merakikernel.rediscache.get_type_datum(_item_typename, "tree", meta_data) if add_tree else None
    items = merakikernel.rediscache.get_all_values(_item_typename, meta_data)
    try:
        version = params["version"]
    except KeyError:
        version = merakikernel.rediscache.get_type_datum(_item_typename, "version", meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if items and (not add_groups or groups) and (not add_tree or tree):
        all_stored = stored_data == {"all"}
        if all_stored and item_data == {"all"}:
            return _wrap_items(items, version, basic, groups, tree)
        elif stored_data == {"all"} or item_data.issubset(stored_data):
            extras = (_item_data_options if all_stored else stored_data) ^ item_data
            for field in extras:
                for item in items:
                    try:
                        del item[field]
                    except KeyError:
                        pass
            return _wrap_items(items, version, basic, groups, tree)

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/item".format(region)
    items = merakikernel.requests.get(region, url, params, True)

    # Cache results
    keys = []
    values = []
    for item in items["data"].values():
        keys.append(item["id"])
        values.append(item)

    item_data = list(item_data)
    if "version" not in params:
        merakikernel.rediscache.put_type_datum(_item_typename, "version", items["version"], meta_data)
    if "groups" in items:
        merakikernel.rediscache.put_type_datum(_item_typename, "groups", items["groups"], meta_data)
    if "tree" in items:
        merakikernel.rediscache.put_type_datum(_item_typename, "tree", items["tree"], meta_data)
    merakikernel.rediscache.put_type_datum(_item_typename, "basic", items["basic"], meta_data)
    merakikernel.rediscache.put_type_datum(_item_typename, "data", item_data, meta_data)
    merakikernel.rediscache.put_type_data(_item_typename, keys, [item_data for _ in keys], meta_data)
    merakikernel.rediscache.put_values(_item_typename, keys, values, meta_data, True)

    return items


def item_id(region, id, params={}):
    item_data = params.get("itemData", "")
    item_data = set(item_data.split(",")) if item_data else set()
    if _item_data_options.issubset(item_data) or "all" in item_data:
        item_data = {"all"}

    # See if we have the item cached with the required itemData fields
    meta_data = _get_item_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_item_typename, id, meta_data)
    item = merakikernel.rediscache.get_value(_item_typename, id, meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if item:
        all_stored = stored_data == {"all"}
        if all_stored and item_data == {"all"}:
            return item
        elif all_stored or item_data.issubset(stored_data):
            extras = (_item_data_options if all_stored else stored_data) ^ item_data
            for field in extras:
                try:
                    del item[field]
                except KeyError:
                    pass
            return item

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/item/{}".format(region, id)
    item = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_type_datum(_item_typename, id, list(item_data), meta_data)
    merakikernel.rediscache.put_value(_item_typename, id, item, meta_data)

    return item


######################
# Language Endpoints #
######################
def _get_language_meta(region, params):
    return "|".join([region.lower(), params.get("version", ""), params.get("locale", "")])


def language_strings(region, params={}):
    # See if we have the language strings cached
    meta_data = _get_item_meta(region, params)
    language_strings = merakikernel.rediscache.get_value(_language_string_typename, "", meta_data)

    if language_strings:
        return language_strings

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/language-strings".format(region)
    language_strings = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_value(_language_string_typename, "", language_strings, meta_data)

    return language_strings


def languages(region, params={}):
    region = region.lower()

    # See if we have the languages cached
    languages = merakikernel.rediscache.get_value(_languages_typename, "", region)

    if languages:
        return languages

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/languages".format(region)
    languages = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_value(_languages_typename, "", languages, region)

    return languages


################
# Map Endpoint #
################
def _get_maps_meta(region, params):
    return "|".join([region.lower(), params.get("version", ""), params.get("locale", "")])


def maps(region, params={}):
    # See if we have the maps cached
    meta_data = _get_maps_meta(region, params)
    maps = merakikernel.rediscache.get_value(_maps_typename, "", meta_data)

    if maps:
        return maps

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/map".format(region)
    maps = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_value(_maps_typename, "", maps, meta_data)

    return maps


#####################
# Mastery Endpoints #
#####################
_mastery_data_options = {
    "image",
    "masteryTree",
    "prereq",
    "ranks",
    "sanitizedDescription"
}


def _get_mastery_meta(region, params):
    return "|".join([region.lower(), params.get("version", ""), params.get("locale", "")])


def _wrap_masteries(masteries, version, tree=None):
    mastery_list = {
        "data": {mastery["id"]: mastery for mastery in masteries},
        "type": "mastery",
        "version": version
    }
    if tree:
        mastery_list["tree"] = tree
    return mastery_list


def mastery(region, params={}):
    mastery_data = params.get("masteryListData", "")
    mastery_data = set(mastery_data.split(",")) if mastery_data else set()
    add_tree = "tree" in mastery_data or "all" in mastery_data
    if _mastery_data_options.issubset(mastery_data | {"tree"}) or "all" in mastery_data:
        mastery_data = {"all"}

    # See if we have the masteries cached with the required masteryListData fields
    meta_data = _get_mastery_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_mastery_typename, "data", meta_data)
    tree = merakikernel.rediscache.get_type_datum(_mastery_typename, "tree", meta_data) if add_tree else None
    masteries = merakikernel.rediscache.get_all_values(_mastery_typename, meta_data)
    try:
        version = params["version"]
    except KeyError:
        version = merakikernel.rediscache.get_type_datum(_mastery_typename, "version", meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if masteries and (not add_tree or tree):
        all_stored = stored_data == {"all"}
        if all_stored and mastery_data == {"all"}:
            return _wrap_masteries(masteries, version, tree)
        elif stored_data == {"all"} or mastery_data.issubset(stored_data):
            extras = (_mastery_data_options if all_stored else stored_data) ^ mastery_data
            for field in extras:
                for mastery in masteries:
                    try:
                        del mastery[field]
                    except KeyError:
                        pass
            return _wrap_masteries(masteries, version, tree)

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/mastery".format(region)
    masteries = merakikernel.requests.get(region, url, params, True)

    # Cache results
    keys = []
    values = []
    for mastery in masteries["data"].values():
        keys.append(mastery["id"])
        values.append(mastery)

    mastery_data = list(mastery_data)
    if "version" not in params:
        merakikernel.rediscache.put_type_datum(_mastery_typename, "version", masteries["version"], meta_data)
    if "tree" in masteries:
        merakikernel.rediscache.put_type_datum(_mastery_typename, "tree", masteries["tree"], meta_data)
    merakikernel.rediscache.put_type_datum(_mastery_typename, "data", mastery_data, meta_data)
    merakikernel.rediscache.put_type_data(_mastery_typename, keys, [mastery_data for _ in keys], meta_data)
    merakikernel.rediscache.put_values(_mastery_typename, keys, values, meta_data, True)

    return masteries


def mastery_id(region, id, params={}):
    mastery_data = params.get("masteryData", "")
    mastery_data = set(mastery_data.split(",")) if mastery_data else set()
    if _mastery_data_options.issubset(mastery_data) or "all" in mastery_data:
        mastery_data = {"all"}

    # See if we have the mastery cached with the required masteryData fields
    meta_data = _get_mastery_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_mastery_typename, id, meta_data)
    mastery = merakikernel.rediscache.get_value(_mastery_typename, id, meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if mastery:
        all_stored = stored_data == {"all"}
        if all_stored and mastery_data == {"all"}:
            return mastery
        elif all_stored or mastery_data.issubset(stored_data):
            extras = (_mastery_data_options if all_stored else stored_data) ^ mastery_data
            for field in extras:
                try:
                    del mastery[field]
                except KeyError:
                    pass
            return mastery

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/mastery/{}".format(region, id)
    mastery = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_type_datum(_mastery_typename, id, list(mastery_data), meta_data)
    merakikernel.rediscache.put_value(_mastery_typename, id, mastery, meta_data)

    return mastery


##################
# Realm Endpoint #
##################
def realm(region, params={}):
    region = region.lower()

    # See if we have the realm cached
    realm = merakikernel.rediscache.get_value(_realm_typename, "", region)

    if realm:
        return realm

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/realm".format(region)
    realm = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_value(_realm_typename, "", realm, region)

    return realm


##################
# Rune Endpoints #
##################
_rune_data_options = {
    "image",
    "sanitizedDescription",
    "stats",
    "tags"
}


def _get_rune_meta(region, params):
    return "|".join([region.lower(), params.get("version", ""), params.get("locale", "")])


def _wrap_runes(runes, version, basic=None):
    rune_list = {
        "data": {rune["id"]: rune for rune in runes},
        "type": "rune",
        "version": version
    }
    if basic:
        rune_list["basic"] = basic
    return rune_list


def rune(region, params={}):
    rune_data = params.get("runeListData", "")
    rune_data = set(rune_data.split(",")) if rune_data else set()
    add_basic = "basic" in rune_data or "all" in rune_data
    if _rune_data_options.issubset(rune_data | {"basic"}) or "all" in rune_data:
        rune_data = {"all"}

    # See if we have the runes cached with the required runeListData fields
    meta_data = _get_rune_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_rune_typename, "data", meta_data)
    basic = merakikernel.rediscache.get_type_datum(_rune_typename, "basic", meta_data) if add_basic else None
    runes = merakikernel.rediscache.get_all_values(_rune_typename, meta_data)
    try:
        version = params["version"]
    except KeyError:
        version = merakikernel.rediscache.get_type_datum(_rune_typename, "version", meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if runes and (not add_basic or basic):
        all_stored = stored_data == {"all"}
        if all_stored and rune_data == {"all"}:
            return _wrap_runes(runes, version, basic)
        elif stored_data == {"all"} or rune_data.issubset(stored_data):
            extras = (_rune_data_options if all_stored else stored_data) ^ rune_data
            for field in extras:
                for rune in runes:
                    try:
                        del rune[field]
                    except KeyError:
                        pass
            return _wrap_runes(runes, version, basic)

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/rune".format(region)
    runes = merakikernel.requests.get(region, url, params, True)

    # Cache results
    keys = []
    values = []
    for rune in runes["data"].values():
        keys.append(rune["id"])
        values.append(rune)

    rune_data = list(rune_data)
    if "version" not in params:
        merakikernel.rediscache.put_type_datum(_rune_typename, "version", runes["version"], meta_data)
    if "basic" in runes:
        merakikernel.rediscache.put_type_datum(_rune_typename, "basic", runes["basic"], meta_data)
    merakikernel.rediscache.put_type_datum(_rune_typename, "data", rune_data, meta_data)
    merakikernel.rediscache.put_type_data(_rune_typename, keys, [rune_data for _ in keys], meta_data)
    merakikernel.rediscache.put_values(_rune_typename, keys, values, meta_data, True)

    return runes


def rune_id(region, id, params={}):
    rune_data = params.get("runeData", "")
    rune_data = set(rune_data.split(",")) if rune_data else set()
    if _rune_data_options.issubset(rune_data) or "all" in rune_data:
        rune_data = {"all"}

    # See if we have the rune cached with the required runeData fields
    meta_data = _get_rune_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_rune_typename, id, meta_data)
    rune = merakikernel.rediscache.get_value(_rune_typename, id, meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if rune:
        all_stored = stored_data == {"all"}
        if all_stored and rune_data == {"all"}:
            return rune
        elif all_stored or rune_data.issubset(stored_data):
            extras = (_rune_data_options if all_stored else stored_data) ^ rune_data
            for field in extras:
                try:
                    del rune[field]
                except KeyError:
                    pass
            return rune

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/rune/{}".format(region, id)
    rune = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_type_datum(_rune_typename, id, list(rune_data), meta_data)
    merakikernel.rediscache.put_value(_rune_typename, id, rune, meta_data)

    return rune


############################
# Summoner Spell Endpoints #
############################
_spell_data_options = {
    "cooldown",
    "cooldownBurn",
    "cost",
    "costBurn",
    "costType",
    "effect",
    "effectBurn",
    "image",
    "key",
    "leveltip",
    "maxrank",
    "modes",
    "range",
    "rangeBurn",
    "resource",
    "sanitizedDescription",
    "sanitizedTooltip",
    "tooltip",
    "vars"
}


def _get_summoner_spell_meta(region, params):
    return "|".join([region.lower(), params.get("version", ""), params.get("locale", "")])


def _wrap_summoner_spells(summoner_spells, data_by_id, version):
    summoner_spell_list = {
        "data": {summoner_spell["key"] if not data_by_id else summoner_spell["id"]: summoner_spell for summoner_spell in summoner_spells},
        "type": "summoner",
        "version": version
    }
    return summoner_spell_list


def summoner_spell(region, params={}):
    data_by_id = params.get("dataById", "false") == "true"
    spell_data = params.get("spellData", "")
    spell_data = set(spell_data.split(",")) if spell_data else set()
    if _spell_data_options.issubset(spell_data) or "all" in spell_data:
        spell_data = {"all"}

    # See if we have the summoner spells cached with the required spellData fields
    meta_data = _get_summoner_spell_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_summoner_spell_typename, "data", meta_data)
    summoner_spells = merakikernel.rediscache.get_all_values(_summoner_spell_typename, meta_data)
    try:
        version = params["version"]
    except KeyError:
        version = merakikernel.rediscache.get_type_datum(_summoner_spell_typename, "version", meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if summoner_spells:
        all_stored = stored_data == {"all"}
        if all_stored and spell_data == {"all"}:
            return _wrap_summoner_spells(summoner_spells, data_by_id, version)
        elif stored_data == {"all"} or spell_data.issubset(stored_data):
            extras = (_spell_data_options if all_stored else stored_data) ^ spell_data
            for field in extras:
                for summoner_spell in summoner_spells:
                    try:
                        del summoner_spell[field]
                    except KeyError:
                        pass
            return _wrap_summoner_spells(summoner_spells, data_by_id, version)

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/summoner-spell".format(region)
    summoner_spells = merakikernel.requests.get(region, url, params, True)

    # Cache results
    keys = []
    values = []
    for summoner_spell in summoner_spells["data"].values():
        keys.append(summoner_spell["id"])
        values.append(summoner_spell)

    spell_data = list(spell_data)
    if "version" not in params:
        merakikernel.rediscache.put_type_datum(_summoner_spell_typename, "version", summoner_spells["version"], meta_data)
    merakikernel.rediscache.put_type_datum(_summoner_spell_typename, "data", spell_data, meta_data)
    merakikernel.rediscache.put_type_data(_summoner_spell_typename, keys, [spell_data for _ in keys], meta_data)
    merakikernel.rediscache.put_values(_summoner_spell_typename, keys, values, meta_data, True)

    return summoner_spells


def summoner_spell_id(region, id, params={}):
    spell_data = params.get("spellData", "")
    spell_data = set(spell_data.split(",")) if spell_data else set()
    if _spell_data_options.issubset(spell_data) or "all" in spell_data:
        spell_data = {"all"}

    # See if we have the summoner spell cached with the required spellData fields
    meta_data = _get_summoner_spell_meta(region, params)
    stored_data = merakikernel.rediscache.get_type_datum(_summoner_spell_typename, id, meta_data)
    summoner_spell = merakikernel.rediscache.get_value(_summoner_spell_typename, id, meta_data)
    stored_data = set(stored_data) if stored_data is not None else stored_data

    if summoner_spell:
        all_stored = stored_data == {"all"}
        if all_stored and spell_data == {"all"}:
            return summoner_spell
        elif all_stored or spell_data.issubset(stored_data):
            extras = (_spell_data_options if all_stored else stored_data) ^ spell_data
            for field in extras:
                try:
                    del summoner_spell[field]
                except KeyError:
                    pass
            return summoner_spell

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/summoner-spell/{}".format(region, id)
    summoner_spell = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_type_datum(_summoner_spell_typename, id, list(spell_data), meta_data)
    merakikernel.rediscache.put_value(_summoner_spell_typename, id, summoner_spell, meta_data)

    return summoner_spell


#####################
# Versions Endpoint #
#####################
def versions(region, params={}):
    region = region.lower()

    # See if we have the versions cached
    versions = merakikernel.rediscache.get_value(_versions_typename, "", region)

    if versions:
        return versions

    # Make request to Riot
    url = "/api/lol/static-data/{}/v1.2/versions".format(region)
    versions = merakikernel.requests.get(region, url, params, True)

    # Cache results
    merakikernel.rediscache.put_value(_versions_typename, "", versions, region)

    return versions
