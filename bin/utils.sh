#!/usr/bin/env bash

utils_help() {
    printf "\nThis is a project helper for the %s, it will help you get up and running.\n\n\
    Usage: \n\n\
        \$ ./%s.sh <command> [<options>] [<subcommand>]\n\n\
    Available commands: \n\n\
        git\n\
            Handles git specific tasks, like adding a project to the Tia GitLab server.\n\n\
        build\n\
            Build the project.\n\n\
        run\n\
            Run the project with dependencies.\n\n\
        deploy\n\
            Deploy the project.\n\n\
        help\n\
            Prints this message or to get help with a specific command use: \"./dod.sh <command> help\".\n\n\
    Examples:
        \$ ./dod.sh git addProject\n\
        \$ ./dod.sh build help\n\n"
}

utils_selectOp() {
    case "${@:$OPTIND:1}" in
        build)
            ./"${SCRIPTS_DIR}"/build-commands.sh "${@:2}"
            ;;
        gradle)
            ./"${SCRIPTS_DIR}"/gradle.sh "${@:2}"
            ;;
        run)
            ./"${SCRIPTS_DIR}"/run-commands.sh "${@:2}"
            ;;
        update-scripts)
            ./."${SERVICE_NAME}"/update-scripts.sh "${@:2}"
            ;;
        deploy)
            ./."${SERVICE_NAME}"/deploy.sh "${@:2}"
            ;;
        release)
            ./."${SERVICE_NAME}"/release.sh "${@:2}"
            ;;
        lint)
            ./."${SERVICE_NAME}"/lint.sh "${@:2}"
            ;;
        aws)
            ./."${SERVICE_NAME}"/aws.sh "${@:2}"
            ;;
        fix)
            ./."${SERVICE_NAME}"/fix.sh "${@:2}"
            ;;
        *)
            help
            ;;
    esac
}

setLocalGradle() {
  printf "using a local Gradle installation\n\n"
  gradle --version
  AUTO_DETECT_GRADLE=false
  MAIN_CMD=gradle
}

setWrapperGradle() {
  printf "using Gradle Wrapper\n\n"
  ./gradlew --version
  AUTO_DETECT_GRADLE=false
  MAIN_CMD=./gradlew
}


imageName() {

    COMPONENT_GROUP=$(projectProp component.group)
    IMAGE_NAME=
    if [[ -n ${COMPONENT_GROUP} ]]; then
        IMAGE_NAME="${COMPONENT_GROUP}-${SERVICE_NAME}"
    else
        IMAGE_NAME="${SERVICE_NAME}"
    fi
    printf "%s" "${IMAGE_NAME}"
}
export -f imageName

releaseProp() {
    if [[ -f release.properties ]]; then
        grep "^\\s*${1}=" release.properties|cut -d'=' -f2
    else
        printf "\nCould not find release.properties, exiting!\n"
        exit 1
    fi

}
export -f releaseProp

projectProp() {
    if [[ ! -f ./target/project.properties ]]; then
        ./."${SERVICE_NAME}"/build.sh -c "-B pl.project13.maven:git-commit-id-plugin:revision \
                                                properties:write-project-properties" -Q
        PROJECT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
        echo "project.version=${PROJECT_VERSION}" >> ./target/project.properties
    fi
    RESULT=$(grep "^\\s*${1}=" target/project.properties|cut -d'=' -f2)
    printf "%s" "${RESULT:-${2:-}}"
}
export -f projectProp

currentBranch(){
    if [[ -z ${BRANCH_NAME:=} ]]; then
        printf "%s" "$(git symbolic-ref --short -q HEAD)"
    else
        printf "%s" ${BRANCH_NAME}
    fi
}
export -f currentBranch

pushd () {
    command pushd "$@" > /dev/null
}
export -f pushd

popd () {
    command popd "$@" > /dev/null
}
export -f popd
