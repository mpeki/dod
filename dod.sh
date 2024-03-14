#!/usr/bin/env bash
set -uoe pipefail

export SCRIPTS_DIR=bin

init() {
    source ./bin/utils.sh
}

help() {
    utils_help
}

dod() {
    init
    utils_selectOp "$@"
}
dod "$@"
