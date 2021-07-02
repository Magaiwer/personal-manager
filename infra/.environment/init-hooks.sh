#!/bin/sh
kill -9 $(ps -ef | grep 'webhook' | awk '{print $2}')
webhook -hooks hooks.json -verbose & >> log-hook
