#!/usr/bin/env bash
set -e

script_dir=$(realpath "$(dirname "$0")")
access_token=$("$script_dir"/graph-api-access-token.sh)

id=$1
curl -H "Authorization: Bearer $access_token" \
  -X DELETE "https://graph.microsoft.com/v1.0/subscriptions/$id"