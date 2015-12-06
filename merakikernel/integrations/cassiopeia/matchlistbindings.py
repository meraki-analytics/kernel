import merakikernel.riotapi.matchlistapi
import cassiopeia.type.dto.matchlist
import cassiopeia.dto.matchlistapi
import cassiopeia.dto.requests

def get_match_list(summoner_id, num_matches=0, begin_index=0, begin_time=0, end_time=0, champion_ids=None, ranked_queues=None, seasons=None):
    region = cassiopeia.dto.requests.region

    params = {}
    if num_matches:
        params["endIndex"] = begin_index + num_matches
    if begin_index or num_matches:
        params["beginIndex"] = begin_index
    if begin_time:
        params["beginTime"] = begin_time
    if end_time:
        params["endTime"] = end_time
    if champion_ids:
        params["championIds"] = ",".join(champion_ids) if isinstance(champion_ids, list) else str(champion_ids)
    if ranked_queues:
        params["rankedQueues"] = ",".join(ranked_queues) if isinstance(ranked_queues, list) else str(ranked_queues)
    if seasons:
        params["seasons"] = ",".join(seasons) if isinstance(seasons, list) else str(seasons)

    return cassiopeia.type.dto.matchlist.MatchList(merakikernel.riotapi.matchlistapi.match_list(region, str(summoner_id), params))


def patch_api():
    get_match_list.__doc__ = cassiopeia.dto.matchlistapi.get_match_list.__doc__
    cassiopeia.dto.matchlistapi.get_match_list = get_match_list