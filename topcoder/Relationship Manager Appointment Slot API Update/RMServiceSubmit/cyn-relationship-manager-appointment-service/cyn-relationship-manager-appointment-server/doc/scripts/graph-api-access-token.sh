#!/usr/bin/env bash
set -e

curl --silent --location --request POST 'https://login.microsoftonline.com/bdbde11e-8377-490d-8d17-f864b60509c9/oauth2/v2.0/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=65e05f82-b637-4ebb-ba6b-073fb30a5430' \
--data-urlencode 'client_secret=M1bHU5aju2uvR..vmRg~KjM_2091F_iR9.' \
--data-urlencode 'scope=https://graph.microsoft.com/.default' \
--data-urlencode 'grant_type=client_credentials' | awk -F '"' '{print $12}'
