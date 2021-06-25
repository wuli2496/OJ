#!/usr/bin/env bash
set -e

script_dir=$(realpath "$(dirname "$0")")
access_token=$("$script_dir"/graph-api-access-token.sh)
user_id='pvmagacho@pvmagachotc.onmicrosoft.com'
calendar_id='AAMkADg0Mjk2OGMwLTAwYjUtNDJlZC1iNzA4LWZjZWZkN2JhNGNlMgBGAAAAAADXZ1tZ77AGTZ36u-ZxsDplBwDoMpmE5if0Qqws4YqxcBFqAAAAAAEGAADoMpmE5if0Qqws4YqxcBFqAAADA-1VAAA='

server_url=$1
resource="/users/$user_id/calendars/$calendar_id/events"
payload="{\"changeType\":\"updated,deleted\",\"notificationUrl\":\"$server_url/v1/appointments/service/customer/ms.graph.notification\",\"resource\":\"$resource\",\"expirationDateTime\":\"2021-03-24T00:00:00.000000Z\",\"clientState\":\"clientState\"}"
curl --silent -X POST -H 'Content-Type: application/json'\
  -H "Authorization: Bearer $access_token" \
  -d "$payload" https://graph.microsoft.com/v1.0/subscriptions
