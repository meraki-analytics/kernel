import configparser
import importlib
import argparse
import bottle

import merakikernel.rediscache
import merakikernel.requests
import merakikernel.rates

def _call_with_config_kwargs(function, config, config_header):
    try:
        kwargs = dict(config[config_header])
    except KeyError:
        kwargs = {}
    function(**kwargs)

def _parse_limit(limit_string):
    limit = limit_string.split("/")
    return (int(limit[0]), int(limit[1]))

class Server(object):
    def __init__(self, config_file_path):
        self.conf = config_file_path

    def run(self):
        # Read config file
        config = configparser.ConfigParser()
        config.optionxform = str  # Preserve config file case
        config.read(self.conf)

        # Set API key and rate limits
        merakikernel.requests.api_key = config["rates"]["api-key"]
        merakikernel.requests.print_calls = config["rates"].getboolean("print-calls")
        merakikernel.requests.retry_on_exceed = config["rates"].getboolean("retry-on-exceed")
        limits = list(map(_parse_limit, config["rates"]["limits"].split("\n")))
        if len(limits) > 1:
            merakikernel.requests.rate_limiter = merakikernel.rates.MultiRateLimiter(*limits)
        else:
            merakikernel.requests.rate_limiter = merakikernel.rates.SingleRateLimiter(limits[0][0], limits[0][1])

        # Load the APIs specified in the config file
        for api in config["apis"]:
            if config["apis"].getboolean(api):
                importlib.import_module("merakikernel.{}api".format(api))

        # Set configurable cache timeouts (and convert from minutes to seconds)
        timeouts = {}
        for typename in config["cache-timeouts"]:
            timeouts[typename] = int(config["cache-timeouts"].getfloat(typename) * 60.0)
        merakikernel.rediscache.timeouts = timeouts

        # Open redis connection and start the server
        _call_with_config_kwargs(merakikernel.rediscache.connect, config, "redis-py")
        _call_with_config_kwargs(bottle.run, config, "bottle")


def main():
    # Set program arguments
    parser = argparse.ArgumentParser(description="Meraki Kernel Server")
    parser.add_argument("--conf", type=str, required=True, help="path to the kernel configuration file")
    args = parser.parse_args()

    server = Server(args.conf)
    server.run()

if __name__ == "__main__":
    main()