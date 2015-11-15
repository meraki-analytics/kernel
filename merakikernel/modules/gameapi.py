import bottle

import merakikernel.rediscache
import merakikernel.requests

@bottle.route("/api/lol/<region>/v1.3/game/by-summoner/<summonerId>/recent", method="GET")
@merakikernel.requests.forward_errors
def recent_games(region, summonerId):
	games = merakikernel.rediscache.get_value("RecentGames", summonerId, region)
	if games:
		return games

	url = "/api/lol/{}/v1.3/game/by-summoner/{}/recent".format(region, summonerId)
	games = merakikernel.requests.get(region, url, dict(bottle.request.query))

	merakikernel.rediscache.put_value("RecentGames", summonerId, games, region)

	return games