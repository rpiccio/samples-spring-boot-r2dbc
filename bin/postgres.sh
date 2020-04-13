#!/bin/bash

docker run --name postgres -e "POSTGRES_PASSWORD=password" -d --rm postgres:10
