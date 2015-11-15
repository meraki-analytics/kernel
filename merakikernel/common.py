import urllib.error
import bottle

def forward_errors(function):
    def wrapped(*args, **kwargs):
        try:
            return function(*args, **kwargs)
        except urllib.error.HTTPError as e:
            bottle.abort(e.code)
    return wrapped

def standardize_name(name):
    return name.replace(" ", "").lower()