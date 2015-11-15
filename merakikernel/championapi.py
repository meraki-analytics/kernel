import bottle
import threading

import merakikernel.rediscache
import merakikernel.requests

def _wrap_statuses(statuses):
    return {
        "champions": statuses
    }

_meta_lock = threading.Lock()  # Make sure a race condition doesn't unsync the metadata values

@bottle.route("/api/lol/<region>/v1.2/champion", method="GET")
@merakikernel.requests.forward_errors
def champion(region):
    # Get params
    params = dict(bottle.request.query)
    try:
        free_to_play = params["freeToPlay"].lower() == "true"
    except KeyError:
        free_to_play = False

    # Check for cached values
    with _meta_lock:
        statuses = merakikernel.rediscache.get_all_values("ChampionStatus", region)
        only_free = merakikernel.rediscache.get_meta_data("ChampionStatus", "free", region)
    if statuses:
        if free_to_play:
            return _wrap_statuses([status for status in statuses if status["freeToPlay"]])
        elif not only_free:
            return _wrap_statuses(statuses)
    
    # Make request to Riot
    url = "/api/lol/{}/v1.2/champion".format(region)
    statuses = merakikernel.requests.get(region, url, params)

    # Cache results
    values = statuses["champions"]
    keys = [status["id"] for status in values]
    with _meta_lock:
        merakikernel.rediscache.put_values("ChampionStatus", keys, values, region, True)
        merakikernel.rediscache.put_meta_data("ChampionStatus", "free", free_to_play, region)

    return statuses


@bottle.route("/api/lol/<region>/v1.2/champion/<id>", method="GET")
@merakikernel.requests.forward_errors
def champion_id(region, id):
    status = merakikernel.rediscache.get_value("ChampionStatus", id, region)
    if status:
        return status

    url = "/api/lol/{}/v1.2/champion/{}".format(region, id)
    status = merakikernel.requests.get(region, url, dict(bottle.request.query))

    merakikernel.rediscache.put_value("ChampionStatus", status["id"], status, region)

    return status