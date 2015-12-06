try:
    __KERNEL_CASSIOPEIA__
except NameError:
    __KERNEL_CASSIOPEIA__ = False

if not __KERNEL_CASSIOPEIA__:
    import cassiopeia.dto.requests
    cassiopeia.dto.requests.rate_limiter = None

    import cassiopeia.type.api.store
    import cassiopeia.core.requests
    cassiopeia.core.requests.data_store = cassiopeia.type.api.store.VoidDataStore()

    from . import championbindings
    championbindings.patch_api()

    from . import currentgamebindings
    currentgamebindings.patch_api()

    from . import featuredgamesbindings
    featuredgamesbindings.patch_api()

    from . import gamebindings
    gamebindings.patch_api()

    from . import leaguebindings
    leaguebindings.patch_api()

    from . import matchbindings
    matchbindings.patch_api()

    from . import matchlistbindings
    matchlistbindings.patch_api()

    from . import staticdatabindings
    staticdatabindings.patch_api()

    from . import statsbindings
    statsbindings.patch_api()

    from . import statusbindings
    statusbindings.patch_api()

    from . import summonerbindings
    summonerbindings.patch_api()

    from . import teambindings
    teambindings.patch_api()