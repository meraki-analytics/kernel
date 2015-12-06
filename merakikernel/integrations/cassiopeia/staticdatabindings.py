import merakikernel.riotapi.staticdataapi
import cassiopeia.type.dto.staticdata
import cassiopeia.dto.staticdataapi
import cassiopeia.dto.requests

######################
# Champion Endpoints #
######################

def get_champion(id_):
    region = cassiopeia.dto.requests.region

    params = {"champData": "all"}
    if cassiopeia.dto.staticdataapi._locale:
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.Champion(merakikernel.riotapi.staticdataapi.champion_id(region, id_, params))

def get_champions():
    region = cassiopeia.dto.requests.region

    params = {"champData": "all"}
    if cassiopeia.dto.staticdataapi._locale:
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.ChampionList(merakikernel.riotapi.staticdataapi.champion(region, params))

##################
# Item Endpoints #
##################

def get_item(id_):
    region = cassiopeia.dto.requests.region

    params = {"itemData": "all"}
    if cassiopeia.dto.staticdataapi._locale:
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.Item(merakikernel.riotapi.staticdataapi.item_id(region, id_, params))

def get_items():
    region = cassiopeia.dto.requests.region

    params = {"itemListData": "all"}
    if cassiopeia.dto.staticdataapi._locale:
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.ItemList(merakikernel.riotapi.staticdataapi.item(region, params))

######################
# Language Endpoints #
######################

def get_language_strings():
    region = cassiopeia.dto.requests.region

    params = {}
    if cassiopeia.dto.staticdataapi._locale:
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.LanguageStrings(merakikernel.riotapi.staticdataapi.language_strings(region, params))

def get_languages():
    region = cassiopeia.dto.requests.region
    return merakikernel.riotapi.staticdataapi.languages(region)

################
# Map Endpoint #
################

def get_maps():
    region = cassiopeia.dto.requests.region

    params = {}
    if cassiopeia.dto.staticdataapi._locale:
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.MapData(merakikernel.riotapi.staticdataapi.maps(region, params))

#####################
# Mastery Endpoints #
#####################

def get_mastery(id_):
    region = cassiopeia.dto.requests.region

    params = {"masteryData": "all"}
    if(cassiopeia.dto.staticdataapi._locale):
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.Mastery(merakikernel.riotapi.staticdataapi.mastery_id(region, id_, params))

def get_masteries():
    region = cassiopeia.dto.requests.region

    params = {"masteryListData": "all"}
    if(cassiopeia.dto.staticdataapi._locale):
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.MasteryList(merakikernel.riotapi.staticdataapi.mastery(region, params))

##################
# Realm Endpoint #
##################

def get_realm():
    region = cassiopeia.dto.requests.region
    return cassiopeia.type.dto.staticdata.Realm(merakikernel.riotapi.staticdataapi.realm(region))

##################
# Rune Endpoints #
##################

def get_rune(id_):
    region = cassiopeia.dto.requests.region

    params = {"runeData": "all"}
    if(cassiopeia.dto.staticdataapi._locale):
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.Rune(merakikernel.riotapi.staticdataapi.rune_id(region, id_, params))

def get_runes():
    region = cassiopeia.dto.requests.region

    params = {"runeListData": "all"}
    if(cassiopeia.dto.staticdataapi._locale):
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.RuneList(merakikernel.riotapi.staticdataapi.rune(region, params))

############################
# Summoner Spell Endpoints #
############################

def get_summoner_spell(id_):
    region = cassiopeia.dto.requests.region

    params = {"spellData": "all"}
    if(cassiopeia.dto.staticdataapi._locale):
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.SummonerSpell(merakikernel.riotapi.staticdataapi.summoner_spell_id(region, id_, params))

def get_summoner_spells():
    region = cassiopeia.dto.requests.region

    params = {"spellData": "all"}
    if(cassiopeia.dto.staticdataapi._locale):
        params["locale"] = cassiopeia.dto.staticdataapi._locale

    return cassiopeia.type.dto.staticdata.SummonerSpellList(merakikernel.riotapi.staticdataapi.summoner_spell(region, params))

#####################
# Versions Endpoint #
#####################

def get_versions():
    region = cassiopeia.dto.requests.region
    return merakikernel.riotapi.staticdataapi.versions(region)


def patch_api():
    get_champion.__doc__ = cassiopeia.dto.staticdataapi.get_champion.__doc__
    cassiopeia.dto.staticdataapi.get_champion = get_champion

    get_champions.__doc__ = cassiopeia.dto.staticdataapi.get_champions.__doc__
    cassiopeia.dto.staticdataapi.get_champions = get_champions

    get_item.__doc__ = cassiopeia.dto.staticdataapi.get_item.__doc__
    cassiopeia.dto.staticdataapi.get_item = get_item

    get_items.__doc__ = cassiopeia.dto.staticdataapi.get_items.__doc__
    cassiopeia.dto.staticdataapi.get_items = get_items

    get_language_strings.__doc__ = cassiopeia.dto.staticdataapi.get_language_strings.__doc__
    cassiopeia.dto.staticdataapi.get_language_strings = get_language_strings

    get_languages.__doc__ = cassiopeia.dto.staticdataapi.get_languages.__doc__
    cassiopeia.dto.staticdataapi.get_languages = get_languages

    get_maps.__doc__ = cassiopeia.dto.staticdataapi.get_maps.__doc__
    cassiopeia.dto.staticdataapi.get_maps = get_maps

    get_mastery.__doc__ = cassiopeia.dto.staticdataapi.get_mastery.__doc__
    cassiopeia.dto.staticdataapi.get_mastery = get_mastery

    get_masteries.__doc__ = cassiopeia.dto.staticdataapi.get_masteries.__doc__
    cassiopeia.dto.staticdataapi.get_masteries = get_masteries

    get_realm.__doc__ = cassiopeia.dto.staticdataapi.get_realm.__doc__
    cassiopeia.dto.staticdataapi.get_realm = get_realm

    get_rune.__doc__ = cassiopeia.dto.staticdataapi.get_rune.__doc__
    cassiopeia.dto.staticdataapi.get_rune = get_rune

    get_runes.__doc__ = cassiopeia.dto.staticdataapi.get_runes.__doc__
    cassiopeia.dto.staticdataapi.get_runes = get_runes

    get_summoner_spell.__doc__ = cassiopeia.dto.staticdataapi.get_summoner_spell.__doc__
    cassiopeia.dto.staticdataapi.get_summoner_spell = get_summoner_spell

    get_summoner_spells.__doc__ = cassiopeia.dto.staticdataapi.get_summoner_spells.__doc__
    cassiopeia.dto.staticdataapi.get_summoner_spells = get_summoner_spells

    get_versions.__doc__ = cassiopeia.dto.staticdataapi.get_versions.__doc__
    cassiopeia.dto.staticdataapi.get_versions = get_versions