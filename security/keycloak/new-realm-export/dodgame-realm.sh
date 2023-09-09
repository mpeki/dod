#!/usr/bin/env bash
docker run --network dod_default --rm -i -t -v $PWD:/workdir jetbrains/intellij-http-client export-dodgame-realm.http

cp ./data/dodgame-realm-exported.json ../dodgame-realm.json

rm -f ./data/dodgame-realm-exported.json