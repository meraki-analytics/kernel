import bottle

import merakikernel.rediscache
import merakikernel.requests
import merakikernel.common

_status_typename = "ChampionStatus"

def _wrap_statuses(statuses):
    return {
        "champions": statuses
    }

@bottle.route("/api/lol/<region>/v1.2/champion", method="GET")
@merakikernel.common.riot_endpoint
def champion(region):
    region       = region.lower()
    params       = dict(bottle.request.query)
    free_to_play = params.get("freeToPlay", "false").lower() == "true"

    # Check for cached values
    statuses  = merakikernel.rediscache.get_all_values(_status_typename, region)
    only_free = merakikernel.rediscache.get_type_datum(_status_typename, "free", region)

    if statuses:
        if free_to_play:
            return _wrap_statuses([status for status in statuses if status["freeToPlay"]])
        elif not only_free:
            return _wrap_statuses(statuses)
    
    # Make request to Riot
    url      = "/api/lol/{}/v1.2/champion".format(region)
    statuses = merakikernel.requests.get(region, url, params)

    # Cache results
    keys = []
    values = []
    for status in statuses["champions"]:
        keys.append(status["id"])
        values.append(status)
        
    merakikernel.rediscache.put_values(_status_typename, keys, values, region, True)
    merakikernel.rediscache.put_type_datum(_status_typename, "free", free_to_play, region)

    return statuses


@bottle.route("/api/lol/<region>/v1.2/champion/<id>", method="GET")
@merakikernel.common.riot_endpoint
def champion_id(region, id):
    region = region.lower()
    status = merakikernel.rediscache.get_value(_status_typename, id, region)
    
    if status:
        return status

    url    = "/api/lol/{}/v1.2/champion/{}".format(region, id)
    status = merakikernel.requests.get(region, url, dict(bottle.request.query))

    merakikernel.rediscache.put_value(_status_typename, id, status, region)

    return status