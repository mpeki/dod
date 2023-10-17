#!/bin/bash
BUCKET_NAME=dodgame-dev
UI_IMAGE=macp/dod-ui
ENVIRONMENT=awsdev
TARGET_PROFILE=AdminAccess-dodgame-dev
FRONTEND_URL=www.dod-game.com

[[ -d deploy ]] && rm -rf deploy && mkdir deploy

docker create --name temp-container ${UI_IMAGE}
docker cp temp-container:/usr/share/nginx/html/. ./deploy
docker rm temp-container

mv deploy/env/environment-${ENVIRONMENT}.json deploy/env/environment.json

aws --profile ${TARGET_PROFILE} s3 cp deploy s3://${BUCKET_NAME} --recursive

DISTRIBUTION_ID=$(aws --profile ${TARGET_PROFILE} cloudfront list-distributions --query 'DistributionList.Items[?Status==`Deployed` && Aliases.Items[0]==`'${FRONTEND_URL}'`].Id' --output text)
[[ ! -z $DISTRIBUTION_ID ]] && [ "$DISTRIBUTION_ID" != "None" ] && aws --profile ${TARGET_PROFILE} cloudfront create-invalidation --distribution-id ${DISTRIBUTION_ID} --paths "/*"
