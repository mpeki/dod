#!/usr/bin/env bash

# Fetch latest data from the remote
git fetch -p

# Loop through each local branch
for branch in $(git branch | cut -c 3-); do
    # Check if the branch exists on the remote
    if ! git show-ref --quiet refs/remotes/origin/$branch; then
        # Ask the user if they want to delete the local branch
        read -p "Delete local branch $branch? [y/N] " response
        if [[ $response =~ ^([yY][eE][sS]|[yY])$ ]]; then
            git branch -D $branch
        fi
    fi
done