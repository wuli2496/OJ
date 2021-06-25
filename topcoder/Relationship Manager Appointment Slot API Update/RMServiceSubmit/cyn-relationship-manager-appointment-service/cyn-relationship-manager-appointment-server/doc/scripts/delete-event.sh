#!/usr/bin/env bash
set -e

script_dir=$(realpath "$(dirname "$0")")
access_token=$("$script_dir"/graph-api-access-token.sh)
user_id='pvmagacho@pvmagachotc.onmicrosoft.com'
calendar_id='AAMkADg0Mjk2OGMwLTAwYjUtNDJlZC1iNzA4LWZjZWZkN2JhNGNlMgBGAAAAAADXZ1tZ77AGTZ36u-ZxsDplBwDoMpmE5if0Qqws4YqxcBFqAAAAAAEGAADoMpmE5if0Qqws4YqxcBFqAAADA-1VAAA='

id=$1
curl -H "Authorization: Bearer $access_token" -X DELETE \
  "https://graph.microsoft.com/v1.0/users/$user_id/calendars/$calendar_id/events/$id"
