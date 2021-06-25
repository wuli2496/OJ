#!/usr/bin/env bash
set -e

script_dir=$(realpath "$(dirname "$0")")
access_token=$("$script_dir"/graph-api-access-token.sh)

curl -H "Authorization: Bearer $access_token" \
  https://graph.microsoft.com/v1.0/subscriptions
