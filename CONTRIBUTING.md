# Contributing to DoD - Drager & Dæmoner

First off, thank you for taking the time to contribute! :+1: :tada:

### Table of Contents

* [How to Contribute](#how-to-contribute)
    * [Create an Issue](#create-an-issue)
    * [Issue Lifecycle](#issue-lifecycle)
    * [Submit a Merge Request](#submit-a-merge-request)
    * [Participate in Reviews](#participate-in-reviews)
* [Source Code Style](#source-code-style)

### How to Contribute

#### Create an Issue

Reporting an issue or making a feature request is a great way to contribute. Your feedback
and the conversations that result from it provide a continuous flow of ideas. You can do so here:
[Issue Tracker](https://github.com/mpeki/dod/issues) for the DoD project.

[//]: # (#### Issue Lifecycle)

[//]: # ()
[//]: # (When an issue is first created, it is flagged `open` waiting for a team)

[//]: # (member to triage it. Once the issue has been reviewed, the team may ask for further)

[//]: # (information if needed, and based on the findings, the issue is either transitioned to `accepted`)

[//]: # (or is closed with a specific status.)

[//]: # ()
[//]: # (From here, it will be pulled into a future sprint cycle.)

[//]: # ()
[//]: # (When the fix has been made and reviewed, it will be merged into the `develop` branch, for integration testing.)

[//]: # ()
[//]: # (If you urgently need the fix/feature, you can choose to checkout the `develop` branch.)

[//]: # ()
[//]: # (`develop` is merged into `master` for broader consumption, on a regular interval.)

[//]: # ()
[//]: # ()
[//]: # (#### Submit a Merge Request)

[//]: # ()
[//]: # (If you would like to directly resolve the issue yourself: great! We welcome contributions!)

[//]: # ()
[//]: # (1. Should you create an issue first? No, just create the merge request and use the)

[//]: # (   description to provide context and motivation, as you would for an issue. If you want)

[//]: # (   to start a discussion first or have already created an issue, once a merge request is)

[//]: # (   merged, we will close the issue.)

[//]: # ()
[//]: # (1. Always check out the `develop` branch and submit merge requests against it.)

[//]: # (   Backports to prior versions will be considered on a case-by-case basis.)

[//]: # ()
[//]: # (1. Rebase your branch on top of the latest `develop` commit, before submitting your)

[//]: # (   merge quest)

[//]: # ()
[//]: # (1. All commits must follow [conventional commits standards]&#40;https://docs.tiatechnology.com/display/ARC/Semantic-release#Semanticrelease-Commitmessages&#41; to be accepted.)

[//]: # (   Gitlab will reject pushes if they don't follow the standard.)

[//]: # ()
[//]: # (1. Choose the granularity of your commits consciously and squash commits that represent)

[//]: # (   multiple edits or corrections of the same logical change. See)

[//]: # (   [Rewriting History section of Pro Git]&#40;https://git-scm.com/book/en/Git-Tools-Rewriting-History&#41;)

[//]: # (   for an overview of streamlining the commit history.)

[//]: # ()
[//]: # ()
[//]: # (1. If there is a prior issue, reference the JIRA issue number in both commit messages, and)

[//]: # (   the description of the merge request.)

[//]: # ()
[//]: # (If accepted, your contribution may be heavily modified as needed prior to merging.)

[//]: # ()
[//]: # (If asked to make corrections, simply push the changes against the same branch, and your)

[//]: # (merge request will be updated. In other words, you do not need to create a new merge request)

[//]: # (when asked to make changes.)

[//]: # ()
[//]: # (#### Participate in Reviews)

[//]: # ()
[//]: # (Helping to review merge requests is another great way to contribute. Your feedback)

[//]: # (can help to shape the implementation of new features, and often ARC needs help testing)

[//]: # (the changes. If you are assigned as a reviewer in a MR, it is because we would like your)

[//]: # (help either reviewing the code changes, or testing the change in your local TIC.)

[//]: # ()
[//]: # (### Source Code Style)

[//]: # ()
[//]: # (`tic.sh` is written in BASH. We try to follow BASH best practices as recommended by [shellcheck]&#40;https://github.com/koalaman/shellcheck&#41;)

[//]: # (but we don't have any official "code style" convention at this stage.)

[//]: # ()
[//]: # (Every commit to TIC triggers a Jenkins pipeline, which includes a shellcheck linting stage to)

[//]: # (ensure that the script stays in good health.)

[//]: # ()
[//]: # (If the linting test fails, the pipeline fails, and the code will not be merged until it passes the linter,)

[//]: # (or a shellcheck ignore definition is added &#40;with good cause&#41;.)

[//]: # ()
[//]: # (The project's Docker-compose files should follow these [docker compose standards] &#40;https://docs.tiatechnology.com/display/ARC/Tia+in+Compose#TiainCompose-Dockercomposefiles&#41;)

[//]: # ()
[//]: # (TIC also features a `.editorcofig` file, which your IDE should use in order to use consistent)

[//]: # (spacing, line endings, whitespace handling and so on.)
