#! /bin/bash
set -eu

export NEXUS_USER=user1
export NEXUS_PASS=`cat /d/patrick/NEXUS_PASS.txt`

if [ -z "$NEXUS_PASS" ]; then
  read -s -p "Please input NEXUS_PASS: " NEXUS_PASS
fi

export appversion=$(date +"%Y%m%d-%H%M%S")
export CURDIR=$(pwd)