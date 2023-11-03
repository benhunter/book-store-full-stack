#!/bin/zsh

watch -d -c 'http get localhost:8080/books | jq'
