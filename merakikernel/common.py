import urllib.error
import bottle
import ujson
import zlib

def riot_endpoint(function):
    def wrapped(*args, **kwargs):
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

def standardize_name(name):
    return name.replace(" ", "").lower()