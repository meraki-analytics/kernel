import bottle
import urllib.parse
import urllib.request

import merakikernel.rates

try:
    # ujson is faster for lots of small encodes/decodes (http://artem.krylysov.com/blog/2015/09/29/benchmark-python-json-libraries/)
    import ujson as json
except ImportError:
    import json

api_key = ""
rate_limiter = None
print_calls = False

def _executeRequest(url):
    """Executes an HTTP GET request and returns the result in a string
    url       str    the full URL to send a GET request to
    return    str    the content returned by the server
    """
    if(print_calls):
        print(url)

    response = None
    try:
        response = urllib.request.urlopen(url)
        content = response.read().decode(encoding="UTF-8")
        return content
    finally:
        if(response): 
            response.close()

def get(region, url, params):
    params["api_key"] = api_key
    encoded_params = urllib.parse.urlencode(params)
    request = "https://{}.api.pvp.net{}?{}".format(region, url, encoded_params)

    try:
        content = rate_limiter.call(_executeRequest, request) if rate_limiter else _executeRequest(request)
        return json.loads(content)
    except urllib.error.HTTPError as e:
        # Reset rate limiter and retry on 429 (rate limit exceeded)
        if e.code == 429 and rate_limiter:
            retry_after = 1
            if e.headers["Retry-After"]:
                retry_after += int(e.headers["Retry-After"])
                
            rate_limiter.reset_in(retry_after)
            return get(request, params, static)
        else:
            raise e

def forward_errors(function):
    def wrapped(*args, **kwargs):
        try:
            return function(*args, **kwargs)
        except urllib.error.HTTPError as e:
            bottle.abort(e.code)
    return wrapped