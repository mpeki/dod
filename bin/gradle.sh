#!/usr/bin/env bash
set -uo pipefail

clear_cache(){
  rm -rf $HOME/.gradle/caches/
}

case "${@:$OPTIND:1}" in
help)
  help
  ;;
clear-cache)
  clear_cache
  ;;
esac
