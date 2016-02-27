New Integrations
################

The contents of ``modules/`` provide bottle wrappers that redirect API queries sent to a Kernel server to the appropriate Kernel functions (which for the Riot API are contained in ``riotapi/``).

The contents of ``riotapi/`` provide the functionality to handle pulling the data from the redis cache if it exists, and querying Riot's API and saving the data into the redis cache if the data does not exist.

The contents of ``integrations/cassiopeia/`` overwrite the Cassiopeia API request functions with Kernel's API request functions. This allows the user to write identical code for querying the Riot API through Cassiopeia whether or not they use Kernel. If they use Kernel, the requests will simply be sent to the Kernel server first, parsed by the wrappers in ``modules/``, and returned using the functions in ``riotapi/``. This patching is done when ``integrations.cassiopeia`` is imported because the patch functionality is contained within ``integrations/cassiopeia/__init__.py``.

