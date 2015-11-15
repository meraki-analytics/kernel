import bottle

import merakikernel.rediscache
import merakikernel.requests

# Special note - since the featured games endpoint doesn't take region as an argument and is served
# per server, kernel takes the region as a GET parameter. You can safely send this parameter to the
# Riot servers as well without any effect on the response you'd normally get.

@bottle.route("/observer-mode/rest/featured", method="GET")
@merakikernel.requests.forward_errors
def featured_games():
	params = dict(bottle.request.query)
	try:
		region = params["region"].lower()
	except KeyError:
		bottle.abort(400, "No region parameter specified!")

	games = merakikernel.rediscache.get_value("FeaturedGames", "", region)
	if games:
		return games

	url = "/observer-mode/rest/featured"
	games = merakikernel.requests.get(region, url, params)

	merakikernel.rediscache.put_value("FeaturedGames", "", games, region)

	return games