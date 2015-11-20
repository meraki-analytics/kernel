import functools
import redis
import ujson

session  = None
timeouts = {}

def connect(*args, **kwargs):
    global session
    session = redis.StrictRedis(*args, **kwargs)

def _get_redis_key(typename, key, meta_data=""):
    return "{}({})[{}]".format(typename, key, meta_data)

def _get_redis_type_key(typename, key, meta_data=""):
    return "{}[{}][{}]".format(typename, key, meta_data)

def get_value(typename, key, meta_data=""):
    cached = session.get(_get_redis_key(typename, key, meta_data))
    return ujson.loads(cached) if cached else None

def get_values(typename, keys, meta_data=""):
    return list(map(lambda key: get_value(typename, key, meta_data), keys))

def get_all_values(typename, meta_data=""):
    keys = get_type_datum(typename, "all", meta_data)
    if not keys:
        return None

    # Assumes that the meta list entry will always expire before the entries it references
    return [ujson.loads(session.get(_get_redis_key(typename, key, meta_data))) for key in keys]

def get_type_datum(typename, key, meta_data=""):
    cached = session.get(_get_redis_type_key(typename, key, meta_data))
    return ujson.loads(cached) if cached else None

def get_type_data(typename, keys, meta_data=""):
    return list(map(lambda key: get_type_datum(typename, key, meta_data), keys))

def put_value(typename, key, value, meta_data=""):
    try:
        timeout = timeouts[typename]
    except KeyError:
        # Don't cache if no timeout is provided
        return

    redis_key = _get_redis_key(typename, key, meta_data)
    redis_value = ujson.dumps(value)
    if timeout > 0:
        session.setex(redis_key, timeout, redis_value)
    else:
        session.set(redis_key, redis_value)

def put_values(typename, keys, values, meta_data="", all_values=False):
    try:
        timeout = timeouts[typename]
    except KeyError:
        # Don't cache if no timeout is provided
        return

    if all_values:
        put_type_datum(typename, "all", keys, meta_data)

    for i in range(len(keys)):
        redis_key = _get_redis_key(typename, keys[i], meta_data)
        redis_value = ujson.dumps(values[i])
        session.setex(redis_key, timeout, redis_value) if timeout > 0 else session.set(redis_key, redis_value)

def put_type_datum(typename, key, value, meta_data=""):
    try:
        timeout = timeouts[typename]
    except KeyError:
        # Don't cache if no timeout is provided
        return

    redis_key = _get_redis_type_key(typename, key, meta_data)
    redis_value = ujson.dumps(value)
    if timeout > 0:
        session.setex(redis_key, timeout, redis_value)
    else:
        session.set(redis_key, redis_value)

def put_type_data(typename, keys, values, meta_data=""):
    try:
        timeout = timeouts[typename]
    except KeyError:
        # Don't cache if no timeout is provided
        return

    for i in range(len(keys)):
        redis_key = _get_redis_type_key(typename, keys[i], meta_data)
        redis_value = ujson.dumps(values[i])
        session.setex(redis_key, timeout, redis_value) if timeout > 0 else session.set(redis_key, redis_value)