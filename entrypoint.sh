#!/bin/sh

set -ue

CodeCommitUrl="https://git-codecommit.ap-southeast-2.amazonaws.com/v1/repos/test"

git config --global --add safe.directory /github/workspace
git config --global credential.'https://git-codecommit.*.amazonaws.com'.helper '!aws codecommit credential-helper $@'
git config --global credential.UseHttpPath true
git remote add sync ${CodeCommitUrl}
git push sync --mirror
