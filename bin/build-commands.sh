#!/usr/bin/env bash
set -uoe pipefail

#default values
USE_LOCAL_GRADLE=false
USE_WRAPPER=false
MAIN_CMD=gradle
SUB_CMD=clean
AUTO_DETECT_GRADLE=true
DO_CLEAN=false
DO_CLEAR_CACHE=false

source ./bin/utils.sh

help() {
  printf "\nThe \"build\" command is used to build the various modules of this project.\n\n\
    Available sub-commands: \n\n\
        help      : prints this message\n\
        api       : builds the api module\n\
        ui        : builds the ui module\n\
        security  : builds the security module\n\
        all       : builds all project modules\n\n\
    Available options: \n\n\
        -l : Use a local Gradle installation if available.\n\
        -w : Use The Gradle Wrapper\n\n\
    Usage example: \n\n\
        ./dod.sh build ui\n\n"
}

compare_gradle_versions() {

  # Retrieve the local Gradle version
  local_gradle_version=$(gradle --version | grep 'Gradle' | awk '{print $2}')

  # Retrieve Gradle wrapper version
  wrapper_gradle_version=$(./gradlew --version | grep 'Gradle' | awk '{print $2}')

  # Compare versions
  if [[ "$local_gradle_version" != "$wrapper_gradle_version" ]]; then
    echo "Local Gradle version $local_gradle_version is different from the Gradle Wrapper version $wrapper_gradle_version."
  fi
}

build_api() {
  SUB_CMD=":b:clean :b:build :b:jDB -x test -x asciidoctor"
  main
}

build_ui() {
  MAIN_CMD="docker-compose build"
  SUB_CMD="ui"
  main
}

build_keycloak_theme() {
  SUB_CMD=":sec:key:theme:makeJar"
  if [[ $DO_CLEAN == "true" ]]; then
    SUB_CMD=":sec:key:theme:clean $SUB_CMD"
  fi
  main
}

build_security() {
  build_keycloak_theme
  MAIN_CMD="docker-compose build"
  SUB_CMD="security"
  if [[ $DO_CLEAN == "true" ]]; then
    SUB_CMD="--no-cache $SUB_CMD"
  fi
  main
}

build_all() {
  build_security
  build_all_gradle
  MAIN_CMD="docker-compose build"
  SUB_CMD="ui"
  if [[ $DO_CLEAN == "true" ]]; then
    SUB_CMD="--no-cache $SUB_CMD"
  fi
  main
}

build_all_gradle() {
  useBestGradle
  SUB_CMD=("b:test" "--tests" "'*ResourceApiDocTest'" "build" "-x" "selenium-test:build")
  if [[ $DO_CLEAN == "true" ]]; then
    SUB_CMD=("clean" "${SUB_CMD[@]}")
  fi
  main
}

useBestGradle() {
  printf "\nAutodetecting best available Gradle installation: "
  [[ -n $(gradle -v) ]] && setLocalGradle && return
  [[ -f ./gradlew ]] && setWrapperGradle && return
}

main() {
  if [[ $MAIN_CMD != *"docker"* ]]; then
    compare_gradle_versions
    [[ ${USE_LOCAL_GRADLE} == true && -n $(gradle -v) ]] && setLocalGradle
    [[ ${USE_WRAPPER} == true && -f ./gradlew ]] && setWrapperGradle
    [[ ${AUTO_DETECT_GRADLE} == true ]] && useBestGradle
  fi
  execute
}

execute() {
  if [[ $MAIN_CMD == *"gradle"* ]]; then
    SUB_CMD=("${SUB_CMD[@]}" "-Dsonar.gradle.skipCompile=true")
  fi
  SUB_CMD_STR=$(printf "%s " "${SUB_CMD[@]}")
  #Execute the build
  printf "Calling: %s %s\n" "${MAIN_CMD}" "${SUB_CMD_STR}"
  eval "${MAIN_CMD} ${SUB_CMD_STR}"
  SUB_CMD=()
}

# get options
while getopts "flwcC" option; do
  case "${option}" in
  l) USE_LOCAL_GRADLE=true ;;
  w)
    USE_WRAPPER=true
    USE_LOCAL_GRADLE=false
    ;;
  c) DO_CLEAN=true ;;
  C) DO_CLEAR_CACHE=true ;;
  *) help ;;
  esac
done

case "${@:$OPTIND:1}" in
help)
  help
  ;;
api)
  build_api
  ;;
ui)
  build_ui
  ;;
keycloak-theme)
  build_keycloak_theme
  ;;
security)
  build_security
  ;;
all)
  build_all
  ;;
*)
  help
  ;;
esac
