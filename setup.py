#!/usr/bin/env python

from setuptools import setup, find_packages


install_requires = [
    "bottle",
    "redis",
    "ujson"
]

setup(
    name="merakikernel",
    version="0.0.1",
    author="Rob Rua",
    author_email="robrua@alumni.cmu.edu",
    url="https://github.com/meraki-analytics/kernel",
    description="Riot Games Developer API Proxy Server",
    keywords=["LoL", "League of Legends", "Riot Games", "API", "REST"],
    classifiers=[
        "Development Status :: 3 - Alpha",
        "Programming Language :: Python :: 3",
        "Environment :: Web Environment",
        "Operating System :: OS Independent",
        "Intended Audience :: Developers",
        "License :: OSI Approved :: MIT License",
        "Topic :: Games/Entertainment",
        "Topic :: Games/Entertainment :: Real Time Strategy",
        "Topic :: Games/Entertainment :: Role-Playing"
    ],
    license="MIT",
    packages=find_packages(),
    zip_safe=True,
    install_requires=install_requires
)
