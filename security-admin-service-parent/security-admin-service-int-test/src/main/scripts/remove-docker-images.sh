#!/usr/bin/env bash

docker rmi -f sleepingtalent/configuration-server:latest
docker rmi -f sleepingtalent/eureka-server:latest
docker rmi -f sleepingtalent/security-server:latest
docker rmi -f sleepingtalent/zuul-server:latest
docker rmi -f sleepingtalent/security-admin-service:snapshot
