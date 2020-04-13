#!/bin/bash

docker run --name mysql -e "MYSQL_DATABASE=test" -e "MYSQL_ROOT_PASSWORD=password" -d --rm mysql:8
