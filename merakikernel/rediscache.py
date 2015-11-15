import functools
import redis

try:
    # ujson is faster for lots of small encodes/decodes (http://artem.krylysov.com/blog/2015/09/29/benchmark-python-json-libraries/)
    import ujson as json
except ImportError:
    import json

session  = None
timeouts = {}

def connect(*args, **kwargs):
    global session
    session = redis.StrictRedis(*args, **kwargs)

def _get_redis_key(typename, key, region=""):
    return "{}{}({})".format(region.lower(), typename, key)

def _get_redis_key_meta(typename, key, region=""):
    return "{}{}[{}]".format(region.lower(), typename, key)

def get_value(typename, key, region=""):
    cached = session.get(_get_redis_key(typename, key, region))
    return json.loads(cached) if cached else None

def get_values(typename, keys, region=""):
    return list(map(get_value, keys))

def get_all_values(typename, region=""):
    keys = get_meta_data(typename, "all", region)
    if not keys:
        return None
    # Assumes that the meta list entry will always expire before the entries it references
    return [json.loads(session.get(_get_redis_key(typename, key, region))) for key in keys]

def get_meta_data(typename, key, region=""):
    cached = session.get(_get_redis_key_meta(typename, key, region))
    return json.loads(cached) if cached else None

def put_value(typename, key, value, region=""):
    try:
        timeout = timeouts[typename]
    except KeyError:
        # Don't cache if no timeout is provided
        return

    redis_key = _get_redis_key(typename, key, region)
    redis_value = json.dumps(value)
    if timeout > 0:
        session.setex(redis_key, timeout, redis_value)
    else:
        session.set(redis_key, redis_value)

def put_values(typename, keys, values, region="", all_values=False):
    try:
        timeout = timeouts[typename]
    except KeyError:
        # Don't cache if no timeout is provided
        return

    if all_values:
        put_meta_data(typename, "all", keys, region)

    for i in range(len(keys)):
        redis_key = _get_redis_key(typename, keys[i], region)
        redis_value = json.dumps(values[i])
        session.setex(redis_key, timeout, redis_value) if timeout > 0 else session.set(redis_key, redis_value)

def put_meta_data(typename, key, value, region=""):
    try:
        timeout = timeouts[typename]
    except KeyError:
        # Don't cache if no timeout is provided
        return

    redis_key = _get_redis_key_meta(typename, key, region)
    redis_value = json.dumps(value)
    if timeout > 0:
        session.setex(redis_key, timeout, redis_value)
    else:
        session.set(redis_key, redis_value)