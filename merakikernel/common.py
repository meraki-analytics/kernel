import urllib.error
import bottle
import ujson
import zlib

def riot_endpoint(function):
    def wrapped(*args, **kwargs):
        bottle.response.headers["Access-Control-Allow-Origin"] = bottle.request.headers.get("Origin", "*")
        bottle.response.headers["Access-Control-Allow-Methods"] = "PUT, GET, POST, DELETE, OPTIONS"
        bottle.response.headers["Access-Control-Allow-Headers"] = bottle.request.headers.get("Access-Control-Request-Headers", "Origin, Accept, Content-Type, X-Requested-With, X-CSRF-Token")

        if bottle.request.method != "OPTIONS":
            try:
                response = ujson.dumps(function(*args, **kwargs))

                if "gzip" in bottle.request.headers["Accept-Encoding"]:
                    gzip_compressor = zlib.compressobj(9, zlib.DEFLATED, zlib.MAX_WBITS | 16)
                    response = gzip_compressor.compress(bytes(response, "UTF-8")) + gzip_compressor.flush()
                    bottle.response.headers["Content-Encoding"] = "gzip"

                bottle.response.headers["Content-Type"] = "application/json; charset=UTF-8"
                return response
            except urllib.error.HTTPError as e:
                bottle.abort(e.code)
    return wrapped

def compressed_json(function):
    def wrapped(*args, **kwargs):
        response = ujson.dumps(function(*args, **kwargs))

        if "gzip" in bottle.request.headers["Accept-Encoding"]:
            gzip_compressor = zlib.compressobj(9, zlib.DEFLATED, zlib.MAX_WBITS | 16)
            response = gzip_compressor.compress(bytes(response, "UTF-8")) + gzip_compressor.flush()
            bottle.response.headers["Content-Encoding"] = "gzip"

        bottle.response.headers["Content-Type"] = "application/json; charset=UTF-8"
        return response
    return wrapped

def enable_cors(function, origin="*"):
    def _enable_cors(*args, **kwargs):
        bottle.response.headers["Access-Control-Allow-Origin"] = bottle.request.headers.get("Origin", "*") if origin == "*" else origin
        bottle.response.headers["Access-Control-Allow-Methods"] = "PUT, GET, POST, DELETE, OPTIONS"
        bottle.response.headers["Access-Control-Allow-Headers"] = bottle.request.headers.get("Access-Control-Request-Headers", "Origin, Accept, Content-Type, X-Requested-With, X-CSRF-Token")

        if bottle.request.method != "OPTIONS":
            return function(*args, **kwargs)

    return _enable_cors

def standardize_name(name):
    return name.replace(" ", "").lower()