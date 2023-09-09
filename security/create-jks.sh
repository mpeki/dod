#!/usr/bin/env bash
##If target directory exist, delete it and create a new one
if [ -d "./target" ]; then
  rm -rf ./target
fi
mkdir ./target

KC=$(docker ps -q -f name=security)
# If KC is empty, then exit script with a message
if [ -z "$KC" ]; then
  echo "Keycloak container is not running"
  exit 1
fi

echo "Copying server.keystore from Keycloak container to ./target"
docker cp ${KC}:/opt/keycloak/conf/server.keystore ./target/

echo "Fetching keycloak certificate..."
echo | openssl s_client -connect localhost:8443 2>/dev/null | openssl x509 > ./target/keycloak.crt

echo "Creating dodgame keystore..."
keytool -genkey -alias dodgame -keystore ./target/dodgame.jks -storepass dodgame -noprompt -keyalg RSA -keysize 2048 -validity 9999 -keypass dodgame -dname "CN=localhost, OU='Marc Pekilidi', O=dodgame, L=Roskilde, S=ID, C=DK"

echo "Importing keycloak certificate into dodgame keystore..."
keytool -import -trustcacerts -keystore ./target/dodgame.jks -storepass dodgame -noprompt -alias keycloak -file ./target/keycloak.crt

cp ./target/dodgame.jks ./dodgame.jks

keytool -list -keystore dodgame.jks -storepass dodgame
