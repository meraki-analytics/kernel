import urllib.error
import bottle
import ujson

def riot_endpoint(function):
    def wrapped(*args, **kwargs):
        try:
            response = ujson.dumps(function(*args, **kwargs))
            bottle.response.content_type = "application/json; charset=UTF-8"
            return response
        except urllib.error.HTTPError as e:
            bottle.abort(e.code)
    return wrapped

def standardize_name(name):
    return name.replace(" ", "").lower()