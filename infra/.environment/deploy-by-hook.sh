#!/bin/sh
docker image rm -f magaiwer/personal-manager:latest
docker stop api-homolog
docker system prune -f
docker-compose -f docker-compose.homolog.yaml up -d

