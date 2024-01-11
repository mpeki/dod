#!/usr/bin/env bash
set -uoe pipefail

DEV_MAIN_TITLE="Dev Run Main"
DEV_LOG_TITLE="DB & Security Logs"

help() {
  printf "\nThe \"run\" command is used to the application in different modes.\n\n\
    Available sub-commands: \n\n\
        help      : prints this message\n\
        dev       : runs the application i dev mode - using 'npm start' and 'bootRun'\n\
    Available options: \n\n\
        -l : Use a local Gradle installation if available.\n\
        -w : Use The Gradle Wrapper\n\n\
    Usage example: \n\n\
        ./dod.sh build ui\n\n"
}

set_main_title() {
  echo -ne "\033]0;$1\007"
}

get_window_id() {
    set +e  # Disable 'exit on error' inside the subshell
    local output
    output=$(xdotool search --name "${DEV_LOG_TITLE}" 2>&1)
    local status=$?
    set -e  # Re-enable 'exit on error'
    if [ $status -ne 0 ]; then
        echo ""  # Return empty string on failure
    else
        echo "$output"  # Return the actual output on success
    fi
}

dev_run(){
#  clear
  set_main_title "${DEV_MAIN_TITLE}"
  printf "\nStarting DoD application in development mode!\n"
  LOG_WINDOW_ID=$(get_window_id)

  printf "\nStarting DB and Security!\n\n"
  docker-compose up -d db security
  if which gnome-terminal > /dev/null; then
    printf "\nStarting API ... "
    curl -s --fail http://localhost:8090/dodgame/api/health > /dev/null || gnome-terminal --title="API" --maximize --tab -- /bin/bash -c 'gradle bootRun -x test; exec bash'
    printf "started!\n\n"
    printf "Starting UI ... "
    curl -s --fail http://localhost:3000/ > /dev/null || gnome-terminal --title="UI" --maximize --tab -- /bin/bash -c 'cd frontend; npm start; exec bash'
    printf "started!\n\n"
    printf "Starting logs window ... "
    [[ -z "${LOG_WINDOW_ID}" ]] && gnome-terminal --title="${DEV_LOG_TITLE}" --maximize -- /bin/bash -c 'docker-compose logs -f; exec bash'
    printf "started!\n\n"
    local MAIN_WINDOW_ID=$(xdotool search --name "${DEV_MAIN_TITLE}")
    xdotool windowactivate "${MAIN_WINDOW_ID}" &> /dev/null
  else
      echo "gnome-terminal is not installed, currently this command only runs in gnome-terminal"
  fi
}



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
dev)
  dev_run
  ;;
*)
  help
  ;;
esac