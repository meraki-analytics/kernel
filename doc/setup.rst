Setup
#####


Install Redis
=============
Kernel requires a running Redis server. See `Redis's quickstart <http://redis.io/topics/quickstart>`_ for installation instructions.

Install Meraki Kernel
=====================
First, clone the repository. Go to `Kernel's Github page <https://github.com/meraki-analytics/kernel>`_ and either download the zip or ``git clone https://github.com/meraki-analytics/kernel`` into a directory of your choice.

Next, add the newly downloaded kernel source directory to your ``PYTHONPATH`` environment variable. If a ``PYTHONPATH`` environment variable does not exist on your system (which may be true if you have a newly installed version of python), you will need to create it.

On Windows, follow the instructions `here <https://www.microsoft.com/resources/documentation/windows/xp/all/proddocs/en-us/sysdm_advancd_environmnt_addchange_variable.mspx?mfr=true>`_. Note that if you need multiple paths on your ``PYTHONPATH``, you can separate them by a ``;``.

On Mac or Linux, add ``export PYTHONPATH=$PYTHONPATH:<CASSIOPEIA PATH>`` to the end of your shell rc file (this should be ``~/.bashrc`` for most), where ``<CASSIOPEIA PATH>`` is the path of the directory you cloned, or the cassiopeia.zip file you downloaded.

Restart your terminal.

For more information, consult Google.

