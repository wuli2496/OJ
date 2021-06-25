#!/usr/bin/env bash
set -e

script_dir=$(realpath "$(dirname "$0")")
access_token=$("$script_dir"/graph-api-access-token.sh)

curl --location --request GET \
     'https://graph.microsoft.com/v1.0/users/pvmagacho@pvmagachotc.onmicrosoft.com/calendars' \
     --header "Authorization: Bearer $access_token"
