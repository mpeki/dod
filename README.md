# DoD
Electrifying the Swedish Roleplaying game: Drager &amp; Dæmoner

## Badges
We don't need no stinking badges: https://www.youtube.com/watch?v=VqomZQMZQCQ but here you go:

![example workflow](https://github.com/mpeki/dod/actions/workflows/main-project-actions.yml/badge.svg?event=push)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)

# GitHub
[Issue Tracking](https://github.com/mpeki/dod/projects/1) and [Wiki](https://github.com/mpeki/dod/wiki).

# Build
Gradle is building, verifying and publishing the project.

## Docker
[Jib](https://github.com/GoogleContainerTools/jib) and the 
[Jib gradle plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin) is used to containerize 
the project app.

# Releases
See the [CHANGELOG](CHANGELOG.md).

## Semantic Release
Currently using the [semantic-release project](https://semantic-release.gitbook.io/semantic-release/) to manage versions, 
since the project is being build with Gradle, the https://www.npmjs.com/package/gradle-semantic-release-plugin has been 
applied.
