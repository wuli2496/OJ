#!/usr/bin/env bash
set -e

script_dir=$(realpath "$(dirname "$0")")
access_token=$("$script_dir"/graph-api-access-token.sh)
user_id='pvmagacho@pvmagachotc.onmicrosoft.com'

calendar_name=$1

payload="{\"name\":\"$calendar_name\"}"
curl --silent -X POST -H 'Content-Type: application/json'\
  -H "Authorization: Bearer $access_token" \
  -d "$payload" "https://graph.microsoft.com/v1.0/users/$user_id/calendars"
