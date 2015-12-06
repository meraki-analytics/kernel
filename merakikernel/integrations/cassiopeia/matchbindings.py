import merakikernel.riotapi.matchapi
import cassiopeia.type.dto.match
import cassiopeia.dto.matchapi
import cassiopeia.dto.requests

def get_match(id_, include_timeline=True):
    region = cassiopeia.dto.requests.region
    return cassiopeia.type.dto.match.MatchDetail(merakikernel.riotapi.matchapi.match(region, str(id_), {"includeTimeline": str(include_timeline)}))


def patch_api():
    get_match.__doc__ = cassiopeia.dto.matchapi.get_match.__doc__
    cassiopeia.dto.matchapi.get_match = get_match