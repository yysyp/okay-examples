#! /bin/bash
set -o nounset
set -o errexit
source 0env-set.sh
source 0authlogin.sh

gcloud compute ssh $VM_NAME --zone $ZONE --internal-ip --command "sudo nohup dockerd > /dev/null 2>&1 &"

./2git-clone-appxxx.sh && ./3scp-upload-appxxx.sh

./5app-redeploy-restart-appxxx.sh
