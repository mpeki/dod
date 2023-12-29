#!/usr/bin/env bash
set -uo pipefail

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
        ./dod.sh test api\n\n"
}
