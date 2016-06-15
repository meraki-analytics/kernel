import urllib.error
import urllib.parse
import urllib.request
import ujson
import zlib


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
        request = urllib.request.Request(url)
        request.add_header("Accept-Encoding", "gzip")
        response = urllib.request.urlopen(request)
        content = response.read()
        if content:
            if "gzip" == response.getheader("Content-Encoding"):
                content = zlib.decompress(content, zlib.MAX_WBITS | 16)
            return ujson.loads(content)
        else:
            return None
    finally:
        if(response):
            response.close()


def get(region, url, params, global_server=False, status_endpoint=False):
    if not status_endpoint:
        params["api_key"] = api_key
        encoded_params = urllib.parse.urlencode(params)
        request = "https://{}.api.pvp.net{}?{}".format(region if not global_server else "global", url, encoded_params)
    else:
        request = "http://status.leagueoflegends.com{}".format(url)

    try:
        return rate_limiter.call(_executeRequest, request) if rate_limiter and not global_server else _executeRequest(request)
    except urllib.error.HTTPError as e:
        # Reset rate limiter and retry on 429 (rate limit exceeded)
        if e.code == 429 and rate_limiter:
            retry_after = 1
            if e.headers["Retry-After"]:
                retry_after += int(e.headers["Retry-After"])

            rate_limiter.reset_in(retry_after)
            return get(region, url, params, global_server, status_endpoint)
        else:
            raise e
